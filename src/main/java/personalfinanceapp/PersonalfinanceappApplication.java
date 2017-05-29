package personalfinanceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class PersonalfinanceappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalfinanceappApplication.class, args);
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
		rbms.setBasename("personalfinanceapp.messages.messages");
		return rbms;
	}
}
