package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.Menu;
import com.fusemachines.fusecanteen.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuRepository menuRepository;

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(String date, Set<FoodItem> foodItems) {

        Menu menuNew = getMenuByDate(date);
        menuNew.setFoodItems(foodItems);
        return menuRepository.save(menuNew);
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuByDate(String date) {
        Menu menu = menuRepository.findByDate(LocalDate.parse(date));
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
}
