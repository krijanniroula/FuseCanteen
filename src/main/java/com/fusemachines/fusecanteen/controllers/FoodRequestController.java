package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.common.Utils;
import com.fusemachines.fusecanteen.models.FoodRequest;

import com.fusemachines.fusecanteen.payload.response.FoodRequestResponse;
import com.fusemachines.fusecanteen.services.FoodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/foodrequest")
public class FoodRequestController {

    @Autowired
    FoodRequestService foodRequestService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllFoodRequests() {

        List<FoodRequest> foodRequestList = foodRequestService.getAllFoodRequests();
        List<FoodRequestResponse> foodRequestResponseList = getFoodRequestResponseAdmin(foodRequestList);

        if (foodRequestResponseList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(foodRequestResponseList, HttpStatus.OK);
    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllFoodRequestsForToday() {

        List<FoodRequest> foodRequestList = foodRequestService.getFoodRequestByDate(LocalDate.now());
        List<FoodRequestResponse> foodRequestResponseList = getFoodRequestResponseAdmin(foodRequestList);

        if (foodRequestResponseList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(foodRequestResponseList, HttpStatus.OK);
    }

    @GetMapping("/today/popular")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllFoodRequestsForTodayPopular() {

        List<FoodRequestResponse> foodRequestResponseList = foodRequestService.getFoodRequestByPopularity();
        if (foodRequestResponseList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(foodRequestResponseList, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> getAllFoodRequestsByLoggedInUser() {
        List<FoodRequestResponse> foodRequestResponseList = new ArrayList<>();
        List<FoodRequest> foodRequests = foodRequestService.getFoodRequestByUsername( Utils.getLoggedUsername());
        if (foodRequests.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for(FoodRequest foodRequest : foodRequests){
            FoodRequestResponse foodRequestResponse = getFoodRequestResponseEmployee(foodRequest);
            foodRequestResponseList.add(foodRequestResponse);
        }

        return new ResponseEntity<>( foodRequestResponseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<FoodRequestResponse> getFoodRequestsByName(@PathVariable String id) {
        FoodRequest foodRequest = foodRequestService.getFoodRequestByid(id);
        return new ResponseEntity<>( getFoodRequestResponseEmployee(foodRequest), HttpStatus.OK );

    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> createFoodRequest(@RequestBody FoodRequest foodRequest) {
        FoodRequest foodRequestNew =foodRequestService.save(foodRequest);
        return new ResponseEntity( getFoodRequestResponseEmployee(foodRequestNew),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateFoodRequest(@PathVariable String id, @RequestBody FoodRequest foodRequest) {
        FoodRequest foodRequestNew = foodRequestService.update(id,foodRequest);
        return new ResponseEntity<>( getFoodRequestResponseEmployee(foodRequestNew),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<HttpStatus> deleteFoodRequestById(@PathVariable String id) {
        foodRequestService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public List<FoodRequestResponse> getFoodRequestResponseAdmin(List<FoodRequest> foodRequestList){
        List<FoodRequestResponse> foodRequestResponseList = new ArrayList<>();

        for(FoodRequest foodRequest : foodRequestList){
            FoodRequestResponse foodRequestResponse = new FoodRequestResponse(foodRequest.getName(),foodRequest.getDate(),foodRequest.getUser().getUsername(),foodRequest.getUser().getFullName(),foodRequest.getUser().getMobileNumber());
            foodRequestResponseList.add(foodRequestResponse);
        }
        return foodRequestResponseList;
    }

    public FoodRequestResponse getFoodRequestResponseEmployee(FoodRequest foodRequest){
        return new FoodRequestResponse(foodRequest.getName(),foodRequest.getDate());
    }
}
