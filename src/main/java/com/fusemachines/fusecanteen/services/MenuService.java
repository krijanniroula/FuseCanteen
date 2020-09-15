package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.Menu;
import com.fusemachines.fusecanteen.payload.request.MenuRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface MenuService {
    Menu createMenu(MenuRequest menuRequest);
    Menu updateMenu(String date,MenuRequest menuRequest) ;
    List<Menu> getAllMenu();
    Menu getMenuByDate(String date);
    Menu getMenuById(String id);
    void deleteById(String id);
    void deleteByDate(String date);
    Boolean existsByDate(LocalDate date);
}
