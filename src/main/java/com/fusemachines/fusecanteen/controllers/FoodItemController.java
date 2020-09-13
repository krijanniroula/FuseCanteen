package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/fooditem")
@PreAuthorize("hasRole('ADMIN')")
public class FoodItemController {

    @Autowired
    FoodItemService foodItemService;

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        try{
            List<FoodItem> foodItemList = foodItemService.getAllFoodItems();
            if (foodItemList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foodItemList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<FoodItem> getFoodItemsByName(@PathVariable String name) {
        Optional<FoodItem> foodItem = foodItemService.getFoodItemByName(name);

        if (!foodItem.isPresent()){
            foodItem = foodItemService.getFoodItemByid(name);
        }

        if (foodItem.isPresent()){
            return new ResponseEntity<>(foodItem.get(),HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem foodItem) {
        try{
            return new ResponseEntity<>( foodItemService.save(foodItem),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable String id, @RequestBody FoodItem foodItem) {
        FoodItem foodItemNew = foodItemService.update(id,foodItem);
        if (foodItemNew != null){
            return new ResponseEntity<>( foodItemNew,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFoodItemByName(@PathVariable String id) {
        try{
            foodItemService.deleteByName(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
