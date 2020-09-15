package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.Menu;
import com.fusemachines.fusecanteen.payload.request.MenuRequest;
import com.fusemachines.fusecanteen.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    FoodItemService foodItemService;

    @Override
    public Menu createMenu(MenuRequest menuRequest) {
        Menu menu = new Menu(LocalDate.now());
        menu.setFoodItems( getFoodItemsFromName(menuRequest) );
        return menuRepository.save(menu);
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

    @Override
    public Menu updateMenu(String date, MenuRequest menuRequest) {
        Menu menuNew = getMenuByDate(date);
        Set<FoodItem> foodItemSet = getFoodItemsFromName(menuRequest);
        menuNew.setFoodItems(foodItemSet);
        return menuRepository.save(menuNew);
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuByDate(String date) {
        Menu menu = menuRepository.findByDate(LocalDate.parse(date));
        if (menu==null){
            throw new ResourceNotFoundException("Menu not found for date = "+date);
        }
        return menu;
    }

    @Override
    public Menu getMenuById(String id) {
        Menu menu = menuRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Menu not found with id = "+id));
        return menu;
    }

    @Override
    public void deleteById(String id) {
        Menu menu = getMenuById(id);
        menuRepository.delete(menu);
    }

    @Override
    public void deleteByDate(String date) {
        Menu menu = getMenuByDate(date);
        menuRepository.delete(menu);
    }

    @Override
    public Boolean existsByDate(LocalDate date) {
        return menuRepository.existsByDate(date);
    }
}
