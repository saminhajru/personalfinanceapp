package personalfinanceapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
public class ConfigurationBeans {

	@Bean
	public PasswordEncoder passwordEncoder() {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		return encoder;
	}
}
