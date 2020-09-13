package com.fusemachines.fusecanteen.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MenuRequest {

    private Set<String> foodItems =  new HashSet<>();

}
