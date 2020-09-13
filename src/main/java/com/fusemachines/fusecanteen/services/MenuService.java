package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.Menu;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface MenuService {
    Menu save(Menu menu);
    Menu update(String date, Set<FoodItem> foodItems) throws ParseException;
    List<Menu> getAllMenu();
    Menu getMenuByDate(LocalDate date);
    Menu getMenuById(String id);
    void deleteById(String id);
}
