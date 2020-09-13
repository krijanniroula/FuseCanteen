package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.models.order.Order;
import com.fusemachines.fusecanteen.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

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
    public ResponseEntity<Order> createOrder(@RequestBody Order Order) {
        return new ResponseEntity<>( orderService.save(Order),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order Order) {
        Order OrderNew = orderService.update(id,Order);
        return new ResponseEntity<>( OrderNew,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<HttpStatus> deleteOrderById(@PathVariable String id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
