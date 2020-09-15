package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.models.order.OrderStatus;
import com.fusemachines.fusecanteen.payload.request.OrderRequest;
import com.fusemachines.fusecanteen.payload.response.OrderResponse;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService {
    OrderResponse createNewOrder(OrderRequest orderRequest);
    OrderResponse updateOrder(String date,OrderRequest orderRequest);
    OrderResponse updateOrderStatus(String date,String username, String orderStatus);
    List<OrderResponse> getAllOrder();
    List<OrderResponse> getAllOrderDynamic();
    List<Order> getOrderByOrderStatus(OrderStatus orderStatus);
    List<Order> getOrderByDate(LocalDate date);
    int getTotalPrice(Order order);
    Order getOrderById(String id);
    Order getOrderByDateUsername(String date, String username);
    OrderResponse getOrderForAdmin(String date, String username);
    void deleteByDateUser(String username,String date);
    Object getOrderByDateDynamic(String date);
}
