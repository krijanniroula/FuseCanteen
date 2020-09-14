package com.fusemachines.fusecanteen.payload.response;

import com.fusemachines.fusecanteen.models.user.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodRequestPopularity {

    private String name;
    private List<User> usernameList;
    private int requestCount;
}
