package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.user.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User update(String id,User user);
    List<User> getAllUsers();
    void deleteUserById(String id);
    User getUserByEmail(String email);
    User getUserById(String id);
    User getUserByUsername(String username);
    boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
