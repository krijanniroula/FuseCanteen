package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.models.order.OrderStatus;
import com.fusemachines.fusecanteen.models.user.User;
import com.fusemachines.fusecanteen.repository.OrderRepository;
import com.fusemachines.fusecanteen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        User user = userService.getUserByUsername(username);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.INPROCESS);
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
    public Order update(String id,Order order) {
        Order orderNew = getOrderById(id);
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

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found with id = "+id));
    }

    @Override
    public void deleteById(String id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}
