package com.fusemachines.fusecanteen.controllers;

import com.fusemachines.fusecanteen.models.FoodRequest;

import com.fusemachines.fusecanteen.payload.response.FoodRequestResponse;
import com.fusemachines.fusecanteen.payload.response.MessageResponse;
import com.fusemachines.fusecanteen.services.FoodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/foodrequest")
public class FoodRequestController {

    @Autowired
    FoodRequestService foodRequestService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> getAllFoodRequests() {
        List<FoodRequestResponse> foodRequestResponseList = foodRequestService.getAllFoodRequestsDynamic();
        if (foodRequestResponseList.isEmpty()){
            return ResponseEntity.ok(new MessageResponse("FoodRequest list is empty!"));
        }
        return new ResponseEntity<>(foodRequestResponseList, HttpStatus.OK);
    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<?> getAllFoodRequestsForToday() {

        List<FoodRequestResponse> foodRequestResponseList = foodRequestService.getFoodRequestForToday();
        if (foodRequestResponseList.isEmpty()){
            return ResponseEntity.ok(new MessageResponse("FoodRequest list for today is empty!"));
        }
        return new ResponseEntity<>(foodRequestResponseList, HttpStatus.OK);
    }

    @GetMapping("/today/popular")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllFoodRequestsForTodayPopular() {

        List<FoodRequestResponse> foodRequestResponseList = foodRequestService.getFoodRequestByPopularity();
        return new ResponseEntity<>(foodRequestResponseList, HttpStatus.OK);
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

    public FoodRequestResponse getFoodRequestResponseEmployee(FoodRequest foodRequest){
        return new FoodRequestResponse(foodRequest.getName(),foodRequest.getDate());
    }

}
