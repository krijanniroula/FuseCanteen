package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.FoodItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FoodItemRepository extends MongoRepository<FoodItem,String> {

    Optional<FoodItem> findByName(String name);
    Boolean existsByName(String name);
}
