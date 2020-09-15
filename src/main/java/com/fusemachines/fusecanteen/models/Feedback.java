package com.fusemachines.fusecanteen.models;

import com.fusemachines.fusecanteen.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "feedback")
public class Feedback {

    @Id
    private String id;

    @DBRef
    private FoodItem foodItem;

    @DBRef
    private User user;

    private String comment;

    private LocalDate date;

    @Size(min = 1 ,max = 10)
    private int rating;
}
