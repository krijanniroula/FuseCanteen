package com.fusemachines.fusecanteen.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {

    private String foodItemName;

    private String comment;

    @Size(min = 1 ,max = 10)
    private int rating;

    private LocalDate date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userFullName;

    public FeedbackRequest(String foodItemName, String comment, int rating,LocalDate date) {
        this.foodItemName = foodItemName;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
    }

}
