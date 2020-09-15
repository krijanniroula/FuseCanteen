package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.models.Menu;
import com.fusemachines.fusecanteen.payload.request.MenuRequest;
import com.fusemachines.fusecanteen.payload.response.MessageResponse;
import com.fusemachines.fusecanteen.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllMenus() {

        List<Menu> menuList = menuService.getAllMenu();
        if (menuList.isEmpty()){
            return ResponseEntity.ok(new MessageResponse("Menu list is empty!"));
        }
        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMenusByDate(@PathVariable String date) {

        Menu menu = menuService.getMenuByDate( date );
        return new ResponseEntity<>( menu, HttpStatus.OK );

    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> getMenusForToday() {

        Menu menu = menuService.getMenuByDate( LocalDate.now().toString() );
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

        return new ResponseEntity<>(menuService.createMenu(menuRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Menu> updateMenu(@PathVariable String date, @RequestBody MenuRequest menuRequest) {

        return new ResponseEntity<>( menuService.updateMenu(date,menuRequest),HttpStatus.OK);
    }

    @DeleteMapping("/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteMenuById(@PathVariable String date) {
        menuService.deleteByDate(date);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
