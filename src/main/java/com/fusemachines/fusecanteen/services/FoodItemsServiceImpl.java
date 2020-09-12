package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItems;
import com.fusemachines.fusecanteen.repository.FoodItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FoodItemsServiceImpl implements FoodItemsService{

    @Autowired
    FoodItemsRepository foodItemsRepository;

    @Override
    public FoodItems save(FoodItems foodItems) {
        return foodItemsRepository.save(foodItems);
    }

    @Override
    public FoodItems update(FoodItems foodItems) {
        return foodItemsRepository.save(foodItems);
    }

    @Override
    public List<FoodItems> getAllFoodItems() {
        return foodItemsRepository.findAll();
    }

    @Override
    public Optional<FoodItems> getFoodItemsByName(String name) {
        return foodItemsRepository.findByName();
    }
}
