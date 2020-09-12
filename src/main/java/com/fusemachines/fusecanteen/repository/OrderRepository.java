package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {

    List<Order> findByOrderStatus();
    List<Order> findByDate();
}
