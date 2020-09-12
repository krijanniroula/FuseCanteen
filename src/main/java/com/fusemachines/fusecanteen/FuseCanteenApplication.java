package com.fusemachines.fusecanteen;

import com.fusemachines.fusecanteen.models.user.ERole;
import com.fusemachines.fusecanteen.models.user.Role;
import com.fusemachines.fusecanteen.repository.RoleRepository;
import com.fusemachines.fusecanteen.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class FuseCanteenApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuseCanteenApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository){
		return args -> {
			Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElse( null );
			if (adminRole==null){
				adminRole = new Role();
				adminRole.setName(ERole.ROLE_ADMIN.name());
				roleRepository.save(adminRole);
			}

			Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE).orElse(null);
			if (employeeRole==null){
				employeeRole = new Role();
				employeeRole.setName(ERole.ROLE_EMPLOYEE.name());
				roleRepository.save(employeeRole);
			}
		};
	}

}
