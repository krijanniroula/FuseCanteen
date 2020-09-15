package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.common.Utils;
import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.payload.request.OrderRequest;
import com.fusemachines.fusecanteen.payload.response.MessageResponse;
import com.fusemachines.fusecanteen.payload.response.OrderResponse;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> getAllOrders() {

        List<OrderResponse> orderResponses = orderService.getAllOrderDynamic();
        if (orderResponses.isEmpty()){
            return ResponseEntity.ok(new MessageResponse("Order list is empty!"));
        }
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> getOrdersByDate(@PathVariable String date) {
        Object object = orderService.getOrderByDateDynamic( date );
        return new ResponseEntity<>( object, HttpStatus.OK );

    }

    @GetMapping("/{date}/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getOrdersByDateUser( @PathVariable String date, @PathVariable String username) {
        OrderResponse orderResponse = orderService.getOrderForAdmin( date , username );
        if (orderResponse==null){
            throw new ResourceNotFoundException("Order not found for date = "+date +" username = "+username);
        }
        return new ResponseEntity<>(orderResponse, HttpStatus.OK );

    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>( orderService.createNewOrder(orderRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{date}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateOrder(@PathVariable String date, @RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.updateOrder( date, orderRequest ) ,HttpStatus.OK);
    }

    @PutMapping("/{date}/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOrder(@PathVariable String date, @PathVariable String username, @RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.updateOrderStatus( date, username,orderRequest.getStatus() ) ,HttpStatus.OK);
    }

    @DeleteMapping("/{date}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<HttpStatus> deleteOrderByDate(@PathVariable String date) {

        orderService.deleteByDateUser( Utils.getLoggedUsername(),date );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{date}/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteOrderByDateUser(@PathVariable String date,@PathVariable String username) {
        orderService.deleteByDateUser(username,date);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
