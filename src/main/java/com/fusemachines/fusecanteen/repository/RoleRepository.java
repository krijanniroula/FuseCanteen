package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.user.ERole;
import com.fusemachines.fusecanteen.models.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(ERole name);
}
