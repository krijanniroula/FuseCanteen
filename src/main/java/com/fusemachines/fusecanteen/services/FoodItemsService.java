package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItems;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FoodItemsService {
    FoodItems save(FoodItems foodItems);
    FoodItems update(FoodItems foodItems);
    List<FoodItems> getAllFoodItems();
    Optional<FoodItems> getFoodItemsByName(String name);
}
