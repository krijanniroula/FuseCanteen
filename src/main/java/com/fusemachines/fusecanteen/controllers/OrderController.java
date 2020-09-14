package com.fusemachines.fusecanteen.controllers;

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
    public ResponseEntity<List<Order>> getAllOrders() {

        List<Order> OrderList = orderService.getAllOrder();
        if (OrderList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(OrderList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Order> getOrdersById(@PathVariable String id) {
        Order Order = orderService.getOrderById(id);
        return new ResponseEntity<>( Order, HttpStatus.OK );

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {

        Order order = new Order();
        order.setFoodItem(getFoodItemsFromName(orderRequest));
        orderService.save(order);
        return new ResponseEntity<>( new OrderResponse(order.getFoodItem(),order.getDate(),orderService.getTotalPrice(order)),HttpStatus.CREATED);
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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateOrder(@PathVariable String id, @RequestBody OrderRequest orderRequest) {
        Order order = new Order();
        order.setFoodItem( getFoodItemsFromName(orderRequest) );
        order.setDate( LocalDate.parse(orderRequest.getDate()) );

        orderService.update( id, order );
        return new ResponseEntity<>( new OrderResponse(order.getFoodItem(),order.getDate(),orderService.getTotalPrice(order)) ,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<HttpStatus> deleteOrderById(@PathVariable String id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
