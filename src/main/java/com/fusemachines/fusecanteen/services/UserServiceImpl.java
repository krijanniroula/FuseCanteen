package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.user.User;
import com.fusemachines.fusecanteen.repository.RoleRepository;
import com.fusemachines.fusecanteen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        User userNew = getUserById(id);

        userNew.setFirstName( user.getFirstName( ) );
        userNew.setLastName( user.getLastName( ) );
        userNew.setMobileNumber( user.getMobileNumber( ));
        userNew.setEmail( user.getEmail( ) );
        userNew.setUsername( user.getUsername() );
        if (!user.getRoles() .isEmpty( ) ){
            userNew.setRoles(user.getRoles());
        }
        return userRepository.save(userNew);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById( String id ) {
        User user = getUserById( id );
        userRepository.delete( user );
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(String id) {
        User user = userRepository.findById( id ).orElseThrow( ()->new ResourceNotFoundException( "User not found with id = "+ id ));
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User not found with username = "+username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
