package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItem;

import java.util.List;

public interface FoodItemService {
    FoodItem save(FoodItem foodItem);
    FoodItem update(String name,FoodItem foodItem);
    List<FoodItem> getAllFoodItems();
    FoodItem getFoodItemByName(String name);
    FoodItem getFoodItemByid(String id);
    void deleteById(String id);
}
