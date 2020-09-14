package com.fusemachines.fusecanteen.models;

import com.fusemachines.fusecanteen.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Document(collection = "foodrequest")
public class FoodRequest {

    @Id
    private String id;

    private String name;

    @DBRef
    private User user;

    private LocalDate date;

}
