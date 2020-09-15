package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodRequest;
import com.fusemachines.fusecanteen.payload.response.FoodRequestResponse;

import java.time.LocalDate;
import java.util.List;

public interface FoodRequestService {
    FoodRequest save(FoodRequest foodRequest);
    FoodRequest update(String id,FoodRequest foodRequest);
    List<FoodRequestResponse> getAllFoodRequests();
    FoodRequest getFoodRequestByName(String name);
    FoodRequest getFoodRequestByid(String id);
    void deleteById(String id);
    List<FoodRequest> getFoodRequestByDate(LocalDate date);
    List<FoodRequestResponse> getFoodRequestByPopularity();
    List<FoodRequest> getFoodRequestByUsername(String username);
    List<FoodRequestResponse> getFoodRequestForToday();
    List<FoodRequestResponse> getAllFoodRequestsByLogInUser();
    List<FoodRequestResponse> getAllFoodRequestsDynamic();
}
