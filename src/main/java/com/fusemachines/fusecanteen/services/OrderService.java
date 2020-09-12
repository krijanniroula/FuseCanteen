package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.models.order.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);
    Order update(Order order);
    List<Order> getAllOrder();
    List<Order> getOrderByOrderStatus(OrderStatus orderStatus);
    List<Order> getOrderByDate(Date date);
}
