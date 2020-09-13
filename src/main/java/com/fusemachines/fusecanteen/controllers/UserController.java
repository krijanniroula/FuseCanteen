package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.models.user.User;
import com.fusemachines.fusecanteen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            return new ResponseEntity<>( userService.save(user),HttpStatus.CREATED);
        }  catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id,@RequestBody User user){
        return new ResponseEntity<>(userService.update(id,user),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsersList() {
        try{
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        User user = userService.getUserByEmail(id);
        if (user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
