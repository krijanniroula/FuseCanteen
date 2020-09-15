package com.fusemachines.fusecanteen.repository;

import com.fusemachines.fusecanteen.models.Feedback;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback,String> {
    List<Feedback> findByFoodItem(FoodItem foodItem);
    List<Feedback> findByUser(User user);
    Feedback findByFoodItemAndUser(FoodItem foodItem, User user);
}
