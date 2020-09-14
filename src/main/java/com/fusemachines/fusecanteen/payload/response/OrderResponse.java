package com.fusemachines.fusecanteen.payload.response;

import com.fusemachines.fusecanteen.models.FoodItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Set<FoodItem> foodItems;

    private LocalDate date;

    private Integer totalPrice;

}

