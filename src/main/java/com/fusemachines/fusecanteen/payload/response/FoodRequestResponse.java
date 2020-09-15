package com.fusemachines.fusecanteen.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fusemachines.fusecanteen.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequestResponse {

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private String id;

   private String name;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private LocalDate date;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private String username;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private String fullName;

   @JsonInclude(JsonInclude.Include.NON_DEFAULT)
   private int requestCount;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private List<String> usernameList;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private String mobile;

   public FoodRequestResponse(String name, LocalDate date, String username, String fullName, String mobile) {
      this.name = name;
      this.date = date;
      this.username = username;
      this.fullName = fullName;
      this.mobile = mobile;
   }

   public FoodRequestResponse(String name,  List<String> usernameList, int count){
      this.name=name;
      this.usernameList=usernameList;
      this.requestCount=count;
   }

   public FoodRequestResponse(String id,String name, LocalDate date){
      this.id=id;
      this.name=name;
      this.date=date;
   }

}
