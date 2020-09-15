package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MenuRepository extends MongoRepository<Menu,String> {

    Menu findByDate(LocalDate date);
    Boolean existsByDate(LocalDate date);
}
