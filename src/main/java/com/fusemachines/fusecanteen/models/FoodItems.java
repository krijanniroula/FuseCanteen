package com.fusemachines.fusecanteen.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "fooditems")
public class FoodItems {
    @Id
    private String id;

    private String name;

    private Integer price;

}
