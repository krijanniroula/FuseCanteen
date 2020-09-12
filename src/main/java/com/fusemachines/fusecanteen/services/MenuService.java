package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItems;
import com.fusemachines.fusecanteen.models.Menu;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MenuService {
    Menu save(Menu menu);
    Menu update(Menu menu);
    List<Menu> getAllMenu();
    Optional<Menu> getMenuByDate(Date date);
}
