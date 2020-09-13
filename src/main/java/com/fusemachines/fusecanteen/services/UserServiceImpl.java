package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.user.Role;
import com.fusemachines.fusecanteen.models.user.User;
import com.fusemachines.fusecanteen.repository.RoleRepository;
import com.fusemachines.fusecanteen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(String id,User user) {

        User userNew = userRepository.findById(id).orElse(null);
        if(userNew==null){
            return null;
        }
        userNew.setFirstName(user.getFirstName());
        userNew.setLastName(user.getLastName());
        userNew.setMobileNumber(user.getMobileNumber());
        userNew.setEmail(user.getEmail());
        userNew.setUsername(user.getUsername());
        if (!user.getRoles().isEmpty()){
            userNew.setRoles(user.getRoles());
        }
        return userRepository.save(userNew);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.get());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

}
