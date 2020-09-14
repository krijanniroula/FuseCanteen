package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.common.Utils;
import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.payload.request.OrderRequest;
import com.fusemachines.fusecanteen.payload.response.OrderResponse;
import com.fusemachines.fusecanteen.services.FoodItemService;
import com.fusemachines.fusecanteen.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    FoodItemService foodItemService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        List<OrderResponse> orderResponses = new ArrayList<>();

        List<Order> orderList = orderService.getAllOrder();
        if (orderList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Order order : orderList){
            orderResponses.add(getOrderResponceAdmin(order));
        }
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @GetMapping("/{date}/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAllOrdersByDate(@PathVariable String date) {

        List<OrderResponse> orderResponses = new ArrayList<>();

        List<Order> orderList = orderService.getOrderByDate(LocalDate.parse(date));
        if (orderList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Order order : orderList){
            orderResponses.add(getOrderResponceAdmin(order));
        }
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> getOrdersByDate(@PathVariable String date) {
        Order order = orderService.getOrderByDateUsername( date , Utils.getLoggedUsername() );
        if (order == null){
            throw new ResourceNotFoundException("Order not found for date = "+date);
        }
        return new ResponseEntity<>( getOrderResponceEmployee(order), HttpStatus.OK );

    }

    @GetMapping("/{date}/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getOrdersByDateUser( @PathVariable String date, @PathVariable String username) {
        Order order = orderService.getOrderByDateUsername( date , username );
        if (order == null){
            throw new ResourceNotFoundException("Order not found for date = "+date);
        }
        return new ResponseEntity<>(getOrderResponceAdmin(order), HttpStatus.OK );

    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {

        Order order = new Order();
        order.setFoodItem(getFoodItemsFromName(orderRequest));
        order.setDate(LocalDate.parse(orderRequest.getDate()));
        orderService.save(order);
        return new ResponseEntity<>( getOrderResponceEmployee(order), HttpStatus.CREATED);
    }

    public Set<FoodItem> getFoodItemsFromName(OrderRequest request) {
        Set<String> foodItemNames = request.getFoodItems();
        Set<FoodItem> foodItems = new HashSet<>();

        foodItemNames.forEach(name -> {
            FoodItem foodItem = foodItemService.getFoodItemByName(name);
            foodItems.add(foodItem);
        });
        return foodItems;
    }

    @PutMapping("/{date}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateOrder(@PathVariable String date, @RequestBody OrderRequest orderRequest) {
        Order order = new Order();
        order.setFoodItem( getFoodItemsFromName(orderRequest) );
        order.setDate( LocalDate.parse(orderRequest.getDate()) );

        orderService.update( date, order );
        return new ResponseEntity<>( getOrderResponceEmployee(order) ,HttpStatus.OK);
    }

    @DeleteMapping("/{date}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<HttpStatus> deleteOrderByDate(@PathVariable String date) {

        String username = Utils.getLoggedUsername();
        orderService.deleteByDateUser( username,date );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{date}/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteOrderByDateUser(@PathVariable String date,@PathVariable String username) {
        orderService.deleteByDateUser(username,date);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public OrderResponse getOrderResponceAdmin(Order order){
        return new OrderResponse(order.getFoodItem(),order.getDate(),orderService.getTotalPrice(order),order.getUser().getUsername(),order.getUser().getFullName(),order.getUser().getMobileNumber());
    }

    public OrderResponse getOrderResponceEmployee(Order order){
        return  new OrderResponse(order.getFoodItem(),order.getDate(),orderService.getTotalPrice(order));
    }
}
