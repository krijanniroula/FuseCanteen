package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.common.Utils;
import com.fusemachines.fusecanteen.models.Feedback;
import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.models.user.User;
import com.fusemachines.fusecanteen.payload.request.FeedbackRequest;
import com.fusemachines.fusecanteen.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    FoodItemService foodItemService;

    @Autowired
    UserService userService;

    @Override
    public Feedback createFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback();

        User user = userService.getUserByUsername(Utils.getLoggedUsername());
        FoodItem foodItem = foodItemService.getFoodItemByName(feedbackRequest.getFoodItemName());

        feedback.setFoodItem(foodItem);
        feedback.setUser(user);
        feedback.setComment(feedbackRequest.getComment());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setDate(LocalDate.now());
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedback( String foodItemName, FeedbackRequest feedbackRequest) {
        Feedback feedback = getFeedbackByFoodItemAndUser( foodItemName,Utils.getLoggedUsername() );
        feedback.setRating(feedbackRequest.getRating());
        feedback.setComment(feedbackRequest.getComment());
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<FeedbackRequest> getAllFeedbackDynamic() {
        User user = userService.getUserByUsername(Utils.getLoggedUsername());

        if (Utils.userHasRoleAdmin(user)){
            List<Feedback> feedbackList =getAllFeedback();
            return getFeedbackRequestListAdmin(feedbackList);

        } else {
            List<Feedback> feedbackList =feedbackRepository.findByUser(user);
            return getFeedbackRequestListEmployee(feedbackList);
        }
    }

    @Override
    public List<FeedbackRequest> getFeedbackByFoodItemName(String foodItemName) {
        FoodItem foodItem = foodItemService.getFoodItemByName(foodItemName);
        List<Feedback> feedbackList = feedbackRepository.findByFoodItem(foodItem);
        return getFeedbackRequestListAdmin(feedbackList);
    }

    @Override
    public List<FeedbackRequest> getFeedbackByUserUsername(String username) {
        User user = userService.getUserByUsername(username);
        List<Feedback> feedbackList = feedbackRepository.findByUser(user);
        return getFeedbackRequestListAdmin(feedbackList);
    }

    @Override
    public Feedback getFeedbackByFoodItemAndUser(String foodItemName, String username) {
        FoodItem foodItem = foodItemService.getFoodItemByName(foodItemName);
        User user = userService.getUserByUsername(username);
        return feedbackRepository.findByFoodItemAndUser(foodItem,user);
    }

    @Override
    public void deleteByFoodItemName(String foodItemName) {
        Feedback feedback = getFeedbackByFoodItemAndUser( foodItemName,Utils.getLoggedUsername() );
        feedbackRepository.delete(feedback);
    }

    public List<FeedbackRequest> getFeedbackRequestListAdmin(List<Feedback> feedbackList){

        List<FeedbackRequest> feedbackRequestList = new ArrayList<>();
        for(Feedback feedback :feedbackList){
            FeedbackRequest feedbackRequest = new FeedbackRequest(feedback.getFoodItem().getName(),feedback.getComment(),feedback.getRating(),feedback.getDate(),feedback.getUser().getFullName());
            feedbackRequestList.add(feedbackRequest);
        }
        return feedbackRequestList;
    }

    public List<FeedbackRequest> getFeedbackRequestListEmployee(List<Feedback> feedbackList){
        List<FeedbackRequest> feedbackRequestList = new ArrayList<>();
        for(Feedback feedback :feedbackList){
            FeedbackRequest feedbackRequest = new FeedbackRequest(feedback.getFoodItem().getName(),feedback.getComment(),feedback.getRating(),feedback.getDate());
            feedbackRequestList.add(feedbackRequest);
        }
        return feedbackRequestList;
    }
}
