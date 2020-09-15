package com.fusemachines.fusecanteen.common;

import com.fusemachines.fusecanteen.models.user.ERole;
import com.fusemachines.fusecanteen.models.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Utils {

    public static String getLoggedUsername(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }

    public static boolean userHasRoleAdmin(User user){
        boolean containAdmin = user.getRoles().stream().allMatch( role -> ERole.ROLE_ADMIN.name().equals(role.getName()) );
        return containAdmin;
    }

}
