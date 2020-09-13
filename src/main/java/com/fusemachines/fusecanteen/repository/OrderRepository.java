package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.models.order.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findByDate(LocalDate date);
}
