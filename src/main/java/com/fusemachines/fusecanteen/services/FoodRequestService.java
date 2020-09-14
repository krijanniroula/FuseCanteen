package com.fusemachines.fusecanteen.services;

import com.fusemachines.fusecanteen.models.FoodRequest;
import com.fusemachines.fusecanteen.payload.response.FoodRequestPopularity;
import com.fusemachines.fusecanteen.payload.response.FoodRequestResponse;

import java.time.LocalDate;
import java.util.List;

public interface FoodRequestService {
    FoodRequest save(FoodRequest foodRequest);
    FoodRequest update(String id,FoodRequest foodRequest);
    List<FoodRequest> getAllFoodRequests();
    FoodRequest getFoodRequestByName(String name);
    FoodRequest getFoodRequestByid(String id);
    void deleteById(String id);
    List<FoodRequest> getFoodRequestByDate(LocalDate date);
    List<FoodRequestResponse> getFoodRequestByPopularity();
    List<FoodRequest> getFoodRequestByUsername(String username);
}
