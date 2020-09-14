package com.fusemachines.fusecanteen.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderRequest {

    private Set<String> foodItems =  new HashSet<>();

    private String date;
}
