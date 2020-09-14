package com.fusemachines.fusecanteen.models.order;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "order")
public class Order {
    @Id
    private String id;

    private OrderStatus orderStatus;

    @DBRef
    private Set<FoodItem> foodItem = new HashSet<>();

    @DBRef
    private User user;

    private int totalPrice;

    private LocalDate date;

}
