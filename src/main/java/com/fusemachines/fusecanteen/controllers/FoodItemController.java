package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fooditem")
@PreAuthorize("hasRole('ADMIN')")
public class FoodItemController {

    @Autowired
    FoodItemService foodItemService;

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {

            List<FoodItem> foodItemList = foodItemService.getAllFoodItems();
            if (foodItemList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foodItemList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemsByName(@PathVariable String id) {
        FoodItem foodItem = foodItemService.getFoodItemByid(id);
        return new ResponseEntity<>( foodItem, HttpStatus.OK );

    }

    @PostMapping
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem foodItem) {
        return new ResponseEntity<>( foodItemService.save(foodItem),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable String id, @RequestBody FoodItem foodItem) {
        FoodItem foodItemNew = foodItemService.update(id,foodItem);
        return new ResponseEntity<>( foodItemNew,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFoodItemByName(@PathVariable String id) {
            foodItemService.deleteByName(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
