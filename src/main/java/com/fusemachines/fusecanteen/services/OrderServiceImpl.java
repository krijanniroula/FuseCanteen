package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.common.Utils;
import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.models.order.OrderStatus;
import com.fusemachines.fusecanteen.models.user.User;
import com.fusemachines.fusecanteen.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Override
    public Order save(Order order) {

        LocalDate localDate = LocalDate.now();

        String username = Utils.getLoggedUsername();

        User user = userService.getUserByUsername(username);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        if (order.getDate()==null){
            order.setDate(localDate);
        }
        order.setTotalPrice(getTotalPrice(order));
        return orderRepository.save(order);
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
    public Order update(String date,Order order) {
        Order orderNew = getOrderByDateUsername( date, Utils.getLoggedUsername() );
        if (order.getDate()!=null){
            orderNew.setDate(order.getDate());
        }
        orderNew.setFoodItem(order.getFoodItem());
        orderNew.setTotalPrice(getTotalPrice(order));
        return orderRepository.save(orderNew);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
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
        return orderRepository.findByDateAndUserId( date,id );
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found with id = "+id));
    }

    @Override
    public Order getOrderByDateUsername(String date, String username){
        User user =userService.getUserByUsername(username);
        Order order = getOrderByDateUserId( LocalDate.parse(date),user.getId() );
        return order;
    }

    @Override
    public void deleteByDateUser(String username,String date) {
        orderRepository.delete( getOrderByDateUsername(date,username) );
    }
}
