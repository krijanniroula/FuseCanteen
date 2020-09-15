package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.Feedback;
import com.fusemachines.fusecanteen.payload.request.FeedbackRequest;

import java.util.List;

public interface FeedbackService {
    Feedback createFeedback(FeedbackRequest feedbackRequest);
    Feedback updateFeedback(String foodItemName,FeedbackRequest feedbackRequest);
    List<Feedback> getAllFeedback();
    List<FeedbackRequest> getAllFeedbackDynamic();
    List<FeedbackRequest> getFeedbackByFoodItemName(String foodItemName);
    List<FeedbackRequest> getFeedbackByUserUsername(String username);
    Feedback getFeedbackByFoodItemAndUser(String foodItemName, String username);
    void deleteByFoodItemName(String foodItemName);
}
