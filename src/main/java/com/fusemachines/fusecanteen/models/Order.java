package com.fusemachines.fusecanteen.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "order")
public class Order {
    @Id
    private String id;

    public enum OrderStatus{
        PENDING,INPROCESS,READY
    }

    private OrderStatus orderStatus;

    private Date date;


}
