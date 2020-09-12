package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.user.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User update(User user);
    List<User> getAllUsers();
    void deleteUserByEmail(String email);
    User getUserByEmail(String email);
    Optional<User> getUser(String id);

}
