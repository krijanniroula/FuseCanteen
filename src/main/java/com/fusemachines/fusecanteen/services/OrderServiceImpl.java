package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.models.order.OrderStatus;
import com.fusemachines.fusecanteen.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        order.setOrderStatus(OrderStatus.INPROCESS);
        return orderRepository.save(order);
    }

    @Override
    public Order update(String id,Order order) {
        Order orderNew = getOrderById(id);
        orderNew.setDate(order.getDate());
        orderNew.setDescription(order.getDescription());
        orderNew.setOrderStatus(order.getOrderStatus());
        orderNew.setUser(order.getUser());
        orderNew.setFoodItem(order.getFoodItem());
        return orderRepository.save(order);
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
