package personalfinanceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@SpringBootApplication
public class PersonalfinanceappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalfinanceappApplication.class, args);
	}
}
