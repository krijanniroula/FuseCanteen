package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.Menu;
import com.fusemachines.fusecanteen.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuRepository menuRepository;

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public Optional<Menu> getMenuByDate(Date date) {
        return menuRepository.findByDate();
    }
}
