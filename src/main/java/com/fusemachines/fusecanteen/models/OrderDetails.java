package com.fusemachines.fusecanteen.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "orderdetails")
public class OrderDetails {
    @Id
    private String id;

    @DBRef
    private Order order;

    @DBRef
    private FoodItems foodItems;

    private String description;

}
