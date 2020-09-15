package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.common.Utils;
import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.models.order.OrderStatus;
import com.fusemachines.fusecanteen.models.user.User;
import com.fusemachines.fusecanteen.payload.request.OrderRequest;
import com.fusemachines.fusecanteen.payload.response.OrderResponse;
import com.fusemachines.fusecanteen.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    FoodItemService foodItemService;

    @Override
    public OrderResponse createNewOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setFoodItem( getFoodItemsFromName(orderRequest) );

        User user = userService.getUserByUsername(Utils.getLoggedUsername());
        // set logged in user to order request
        order.setUser(user);

        order.setOrderStatus(OrderStatus.PENDING);
        if (orderRequest.getDate()==null){
            order.setDate(LocalDate.now());
        } else {
            order.setDate(LocalDate.parse(orderRequest.getDate()));
        }
        order.setTotalPrice(getTotalPrice(order));
        return getOrderResponceEmployee(orderRepository.save(order));
    }

    @Override
    public int getTotalPrice(Order order){
        Set<FoodItem> foodItemSet = order.getFoodItem();
        int q =0;
        for (FoodItem item: foodItemSet){
            q +=item.getPrice();
        }
        return q;
    }

    @Override
    public OrderResponse updateOrder(String date,OrderRequest orderRequest) {

        Order order = getOrderByDateUsername( date, Utils.getLoggedUsername() );
        order.setFoodItem( getFoodItemsFromName(orderRequest) );
        return getOrderResponceEmployee(orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return getOrderResponceAdminList(orderRepository.findAll());
    }

    @Override
    public List<OrderResponse> getAllOrderDynamic(){
        User user = userService.getUserByUsername(Utils.getLoggedUsername());
        if (Utils.userHasRoleAdmin(user)){
            return getAllOrder();
        } else {
            List<Order> orderList =orderRepository.findByUser(user);
            return getOrderResponceEmployeeList(orderList);
        }
    }

    @Override
    public List<Order> getOrderByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public List<Order> getOrderByDate(LocalDate date) {
        return orderRepository.findByDate(date);
    }

    public Order getOrderByDateUserId(LocalDate date,String id ) {
        Order order = orderRepository.findByDateAndUserId( date,id );
        if (order==null){
            throw new ResourceNotFoundException("Order not found with date = "+date);
        }
        return order;
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found with id = "+id));
    }

    @Override
    public Object getOrderByDateDynamic(String date){
        User user = userService.getUserByUsername(Utils.getLoggedUsername());
        if (Utils.userHasRoleAdmin(user)){
            List<Order> orderList = getOrderByDate(LocalDate.parse(date));
            return getOrderResponceAdminList(orderList);
        } else {
            Order order = getOrderByDateUsername(date, Utils.getLoggedUsername());
            return getOrderResponceEmployee(order);
        }
    }

    @Override
    public Order getOrderByDateUsername(String date, String username){
        User user =userService.getUserByUsername(username);
        Order order = getOrderByDateUserId( LocalDate.parse(date),user.getId() );
        if (order==null){
            throw  new ResourceNotFoundException("Order not found with date = "+date +" and username = "+username);
        }
        return order;
    }

    @Override
    public OrderResponse getOrderForAdmin(String date, String username){
        Order order = getOrderByDateUsername( date , username );
        return getOrderResponceAdmin(order);
    }

    @Override
    public void deleteByDateUser(String username,String date) {
        Order order = getOrderByDateUsername(date,username);
        orderRepository.delete( order );
    }

    public OrderResponse getOrderResponceAdmin(Order order){
        return new OrderResponse(order.getFoodItem(),order.getDate(),getTotalPrice(order),order.getUser().getUsername(),order.getUser().getFullName(),order.getUser().getMobileNumber());
    }

    public List<OrderResponse> getOrderResponceAdminList(List<Order> orderList){

        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order order : orderList){
            orderResponses.add(getOrderResponceAdmin(order));
        }
        return orderResponses;
    }

    public OrderResponse getOrderResponceEmployee(Order order){
        return new OrderResponse(order.getFoodItem(),order.getDate(),getTotalPrice(order));
    }

    public List<OrderResponse> getOrderResponceEmployeeList(List<Order> orderList){

        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order order : orderList){
            orderResponses.add(getOrderResponceEmployee(order));
        }
        return orderResponses;
    }

    /*
    *  return set of FoodItem from orderRequest
    * */
    public Set<FoodItem> getFoodItemsFromName(OrderRequest request) {
        Set<String> foodItemNames = request.getFoodItems();
        Set<FoodItem> foodItems = new HashSet<>();

        foodItemNames.forEach(name -> {
            FoodItem foodItem = foodItemService.getFoodItemByName(name);
            foodItems.add(foodItem);
        });
        return foodItems;
    }
}
