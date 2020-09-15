package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.models.Feedback;
import com.fusemachines.fusecanteen.payload.request.FeedbackRequest;
import com.fusemachines.fusecanteen.payload.response.MessageResponse;
import com.fusemachines.fusecanteen.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> getAllFeedback() {

        List<FeedbackRequest> feedBackList = feedbackService.getAllFeedbackDynamic();
        if (feedBackList.isEmpty()){
            return ResponseEntity.ok(new MessageResponse("Feedback list is empty!"));
        }
        return new ResponseEntity<>(feedBackList, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getFeedbackByName(@PathVariable String username) {
        List<FeedbackRequest> feedbackRequestList = feedbackService.getFeedbackByUserUsername(username);
        return new ResponseEntity<>( feedbackRequestList, HttpStatus.OK );

    }

    @GetMapping("/fooditem/{foodItemName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getFeedbackByFoodItemName(@PathVariable String foodItemName) {
        List<FeedbackRequest> feedbackRequestList = feedbackService.getFeedbackByFoodItemName(foodItemName);
        return new ResponseEntity<>( feedbackRequestList, HttpStatus.OK );

    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackService.createFeedback(feedbackRequest);
        return new ResponseEntity( new FeedbackRequest(feedback.getFoodItem().getName(),feedback.getComment(),feedback.getRating(),feedback.getDate()) , HttpStatus.CREATED);
    }

    @PutMapping("/{foodItemName}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateFeedback(@PathVariable String foodItemName, @RequestBody FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackService.updateFeedback(foodItemName,feedbackRequest);
        return new ResponseEntity<>( new FeedbackRequest(feedback.getFoodItem().getName(),feedback.getComment(),feedback.getRating(),feedback.getDate()) ,HttpStatus.OK);
    }

    @DeleteMapping("/{foodItemName}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<HttpStatus> deleteFeedbackById(@PathVariable String foodItemName) {
        feedbackService.deleteByFoodItemName(foodItemName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
