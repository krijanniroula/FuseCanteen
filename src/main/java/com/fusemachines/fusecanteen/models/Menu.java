package com.fusemachines.fusecanteen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu")
public class Menu {

    @Id
    private String id;

    @DBRef
    private Set<FoodItem> foodItems =  new HashSet<>();

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private LocalDate date;

}
