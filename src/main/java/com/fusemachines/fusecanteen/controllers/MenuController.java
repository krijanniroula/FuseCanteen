package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.Menu;
import com.fusemachines.fusecanteen.payload.request.MenuRequest;
import com.fusemachines.fusecanteen.payload.response.MessageResponse;
import com.fusemachines.fusecanteen.services.FoodItemService;
import com.fusemachines.fusecanteen.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    FoodItemService foodItemService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Menu>> getAllMenus() {

        List<Menu> MenuList = menuService.getAllMenu();
        if (MenuList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(MenuList, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Menu> getMenusByDate(@PathVariable String date) {

        Menu menu = menuService.getMenuByDate( date );
        if (menu == null){
            throw new ResourceNotFoundException("Menu not found for date = "+date);
        }
        return new ResponseEntity<>( menu, HttpStatus.OK );

    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Menu> getMenusForToday() {

        Menu menu = menuService.getMenuByDate( LocalDate.now().toString() );
        if (menu == null){
            throw new ResourceNotFoundException("Menu not found for today!");
        }
        return new ResponseEntity<>( menu, HttpStatus.OK );

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createMenu(@RequestBody MenuRequest menuRequest) {

       LocalDate localDate = LocalDate.now();

        if ( ( menuService.existsByDate(localDate) ) ) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Menu for the date "+localDate+" is already created!"));
        }

        Menu menu = new Menu(localDate);
        menu.setFoodItems(getFoodItemsFromName(menuRequest));
        return new ResponseEntity<>(menuService.save(menu),HttpStatus.CREATED);
    }

    public Set<FoodItem> getFoodItemsFromName(MenuRequest request) {
        Set<String> foodItemNames = request.getFoodItems();
        Set<FoodItem> foodItems = new HashSet<>();

        foodItemNames.forEach(name -> {
            FoodItem foodItem = foodItemService.getFoodItemByName(name);
            foodItems.add(foodItem);
        });
        return foodItems;
    }

    @PutMapping("/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Menu> updateMenu(@PathVariable String date, @RequestBody MenuRequest menuRequest) {

        Set<FoodItem> foodItemSet = getFoodItemsFromName(menuRequest);
        return new ResponseEntity<>( menuService.update(date,foodItemSet),HttpStatus.OK);
    }

    @DeleteMapping("/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteMenuById(@PathVariable String date) {
        menuService.deleteByDate(date);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
