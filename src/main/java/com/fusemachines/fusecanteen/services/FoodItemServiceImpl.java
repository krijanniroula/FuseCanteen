package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
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
        FoodItem foodItemNew = getFoodItemByid(id);
        foodItemNew.setName(foodItem.getName());
        foodItemNew.setPrice(foodItem.getPrice());
        return foodItemRepository.save(foodItemNew);
    }

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem getFoodItemByName(String name) {
        return foodItemRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Food Item not found with the name = "+name));
    }

    @Override
    public FoodItem getFoodItemByid(String id) {
        FoodItem foodItem =  foodItemRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Food Item not found with the id = "+id));;
        return foodItem;
    }

    @Override
    public void deleteById(String id) {
        FoodItem foodItem = getFoodItemByid(id);
        foodItemRepository.delete(foodItem);
    }
}
