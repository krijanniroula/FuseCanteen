package com.fusemachines.fusecanteen.payload.request;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderRequest {

    private Set<String> foodItems =  new HashSet<>();

    private String date;

    private String status;
}
