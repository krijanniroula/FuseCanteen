package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface MenuRepository extends MongoRepository<Menu,String> {

    Optional<Menu> findByDate(Date date);
}
