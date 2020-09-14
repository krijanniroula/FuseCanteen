package com.fusemachines.fusecanteen.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String email;

    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    @DBRef
    private Set<Role> roles =  new HashSet<>();

    public String getFullName() {
        return firstName != null ? firstName.concat(" ").concat(lastName) : "";
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
