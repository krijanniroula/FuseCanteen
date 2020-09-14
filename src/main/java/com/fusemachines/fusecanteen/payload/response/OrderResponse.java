package com.fusemachines.fusecanteen.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private int totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fullName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mobile;

    public OrderResponse(Set<FoodItem> foodItems,LocalDate date,int totalPrice){
        this.foodItems=foodItems;
        this.date=date;
        this.totalPrice=totalPrice;
    }

}

