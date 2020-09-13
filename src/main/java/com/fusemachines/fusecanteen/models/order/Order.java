package com.fusemachines.fusecanteen.models.order;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "order")
public class Order {
    @Id
    private String id;

    private OrderStatus orderStatus;

    @DBRef
    private FoodItem foodItem;

    @DBRef
    private User user;

    private Date date;

    private String description;

}
