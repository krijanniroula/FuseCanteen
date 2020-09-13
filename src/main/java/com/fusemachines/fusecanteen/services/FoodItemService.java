package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItem;

import java.util.List;
import java.util.Optional;

public interface FoodItemService {
    FoodItem save(FoodItem foodItem);
    FoodItem update(String name,FoodItem foodItem);
    List<FoodItem> getAllFoodItems();
    Optional<FoodItem> getFoodItemByName(String name);
    FoodItem getFoodItemByid(String id);
    void deleteByName(String id);
}
