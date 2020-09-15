package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.common.Utils;
import com.fusemachines.fusecanteen.exception.ResourceNotFoundException;
import com.fusemachines.fusecanteen.models.FoodRequest;
import com.fusemachines.fusecanteen.models.user.User;
import com.fusemachines.fusecanteen.payload.response.FoodRequestPopularity;
import com.fusemachines.fusecanteen.payload.response.FoodRequestResponse;
import com.fusemachines.fusecanteen.repository.FoodRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class FoodRequestServiceImpl implements FoodRequestService{

    @Autowired
    FoodRequestRepository foodRequestRepository;

    @Autowired
    UserService userService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public FoodRequest save(FoodRequest foodRequest) {
        LocalDate localDate = LocalDate.now();
        User user = userService.getUserByUsername(Utils.getLoggedUsername());
        foodRequest.setUser(user);
        foodRequest.setDate(localDate);
        return foodRequestRepository.save(foodRequest);
    }

    @Override
    public FoodRequest update(String id, FoodRequest foodRequest) {
        FoodRequest foodRequestNew = getFoodRequestByid(id);
        foodRequestNew.setName(foodRequest.getName());
        foodRequestNew.setDate(foodRequest.getDate());
        return foodRequestRepository.save(foodRequestNew);
    }

    @Override
    public List<FoodRequestResponse> getAllFoodRequests() {
        List<FoodRequest> foodRequests = foodRequestRepository.findAll();
        return getFoodRequestResponseAdminList(foodRequests);
    }

    @Override
    public FoodRequest getFoodRequestByName(String name) {
        return foodRequestRepository.findByName(name);
    }

    @Override
    public FoodRequest getFoodRequestByid(String id) {
        return foodRequestRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Food Request not found with id = "+id));
    }

    @Override
    public void deleteById(String id) {
        foodRequestRepository.delete(getFoodRequestByid(id));
    }

    @Override
    public List<FoodRequest> getFoodRequestByDate(LocalDate date) {
        return foodRequestRepository.findByDate(date);
    }

    @Override
    public List<FoodRequestResponse> getFoodRequestForToday(){

        List<FoodRequest> foodRequestList;
        User user = userService.getUserByUsername(Utils.getLoggedUsername());

        if ( Utils.userHasRoleAdmin(user) ){
            foodRequestList = getFoodRequestByDate(LocalDate.now());
            return getFoodRequestResponseAdminList(foodRequestList);
        } else {
            foodRequestList = foodRequestRepository.findByDateAndUserId(LocalDate.now(),user.getId());
            return getFoodRequestResponseEmployeeList(foodRequestList);
        }

    }

    public List<FoodRequestResponse> getFoodRequestResponseAdminList(List<FoodRequest> foodRequestList){
        List<FoodRequestResponse> foodRequestResponseList = new ArrayList<>();

        for(FoodRequest foodRequest : foodRequestList){
            FoodRequestResponse foodRequestResponse = new FoodRequestResponse(foodRequest.getName(),foodRequest.getDate(),foodRequest.getUser().getUsername(),foodRequest.getUser().getFullName(),foodRequest.getUser().getMobileNumber());
            foodRequestResponseList.add(foodRequestResponse);
        }
        return foodRequestResponseList;
    }

    public List<FoodRequestResponse> getFoodRequestResponseEmployeeList(List<FoodRequest> foodRequestList){
        List<FoodRequestResponse> foodRequestResponseList = new ArrayList<>();
        for(FoodRequest foodRequest : foodRequestList){
            FoodRequestResponse foodRequestResponse = new FoodRequestResponse(foodRequest.getName(),foodRequest.getDate());
            foodRequestResponseList.add(foodRequestResponse);
        }
        return foodRequestResponseList;
    }

    @Override
    public List<FoodRequestResponse> getFoodRequestByPopularity() {

        List<FoodRequestResponse> foodRequestResponseList = new ArrayList<>();

        Aggregation aggregation = newAggregation(
                match(Criteria.where("date").in( LocalDate.now() )),
                group("name").count().as("requestCount")
                .addToSet("user").as("usernameList"),
                project("usernameList","requestCount").and("name").previousOperation(),
                sort(Sort.Direction.DESC,"requestCount")
        );

        AggregationResults<FoodRequestPopularity> aggregationResults = mongoTemplate.aggregate(aggregation,FoodRequest.class, FoodRequestPopularity.class);
        List<FoodRequestPopularity> foodRequestPopularities = aggregationResults.getMappedResults();

        for (FoodRequestPopularity foodRequestPopularity : foodRequestPopularities){
            List<String> userList = new ArrayList<>();
            for (User user : foodRequestPopularity.getUsernameList()){
                userList.add(user.getUsername());
            }
            FoodRequestResponse foodRequestResponse = new FoodRequestResponse(foodRequestPopularity.getName(),userList,foodRequestPopularity.getRequestCount());
            foodRequestResponseList.add(foodRequestResponse);
        }
        return foodRequestResponseList;
    }

    @Override
    public List<FoodRequest> getFoodRequestByUsername(String username) {
        User user = userService.getUserByUsername(username);
        List<FoodRequest> foodRequests = foodRequestRepository.findByUser(user);
        return foodRequests;
    }

    @Override
    public List<FoodRequestResponse> getAllFoodRequestsByLogInUser(){
        List<FoodRequest> foodRequests = getFoodRequestByUsername( Utils.getLoggedUsername());
        return getFoodRequestResponseEmployeeList(foodRequests);
    }

    @Override
    public List<FoodRequestResponse> getAllFoodRequestsDynamic(){
        User user = userService.getUserByUsername(Utils.getLoggedUsername());
        if (Utils.userHasRoleAdmin(user)){
            return getAllFoodRequests();
        } else {
            return getAllFoodRequestsByLogInUser();
        }
    }

 }
