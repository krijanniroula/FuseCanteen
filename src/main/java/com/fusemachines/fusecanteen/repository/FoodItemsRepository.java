package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.FoodItems;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodItemsRepository extends MongoRepository<FoodItems,String> {

    Optional<FoodItems> findByName();
}
