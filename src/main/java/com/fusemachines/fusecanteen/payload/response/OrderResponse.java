package com.fusemachines.fusecanteen.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fusemachines.fusecanteen.models.FoodItem;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderResponse {

    private Set<FoodItem> foodItems;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate date;

    private int totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fullName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mobile;

    private String orderStatus;

    public OrderResponse(Set<FoodItem> foodItems,LocalDate date,int totalPrice,String orderStatus){
        this.foodItems=foodItems;
        this.date=date;
        this.totalPrice=totalPrice;
        this.orderStatus=orderStatus;
    }

}

