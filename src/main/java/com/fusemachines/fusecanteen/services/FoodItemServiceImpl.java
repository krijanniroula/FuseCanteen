package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    FoodItemRepository foodItemRepository;

    @Override
    public FoodItem save(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    @Override
    public FoodItem update(String id,FoodItem foodItem) {
        FoodItem foodItemNew = foodItemRepository.findById(id).orElse(null);
        if(foodItemNew==null){
            return null;
        }
        foodItemNew.setName(foodItem.getName());
        foodItemNew.setPrice(foodItem.getPrice());
        return foodItemRepository.save(foodItemNew);
    }

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    @Override
    public Optional<FoodItem> getFoodItemByName(String name) {
        return foodItemRepository.findByName(name);
    }

    @Override
    public Optional<FoodItem> getFoodItemByid(String id) {
        return foodItemRepository.findById(id);
    }

    @Override
    public void deleteByName(String id) {
        Optional<FoodItem> foodItem = foodItemRepository.findById(id);
        foodItemRepository.delete(foodItem.get());
    }
}
