package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.FoodRequest;
import com.fusemachines.fusecanteen.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodRequestRepository extends MongoRepository<FoodRequest,String> {

    FoodRequest findByName(String name);
    List<FoodRequest> findByDate(LocalDate date);
    List<FoodRequest> findByUser(User user);
    List<FoodRequest> findByDateAndUserId(LocalDate date, String userId);
}
