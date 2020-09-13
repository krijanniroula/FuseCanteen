package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.models.order.OrderStatus;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService {
    Order save(Order order);
    Order update(String id,Order order);
    List<Order> getAllOrder();
    List<Order> getOrderByOrderStatus(OrderStatus orderStatus);
    List<Order> getOrderByDate(LocalDate date);
    Order getOrderById(String id);
    void deleteById(String id);
}
