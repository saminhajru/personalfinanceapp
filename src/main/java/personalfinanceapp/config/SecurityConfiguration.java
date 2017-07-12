package personalfinanceapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
				.usersByUsernameQuery("select username, password, enabled from user where " + "binary username = ?")
				.authoritiesByUsernameQuery("select username, authority from user where binary" + " username = ?")
				.dataSource(dataSource).passwordEncoder(passwordEncoder);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/categories").authenticated()
		.antMatchers("/subcategories").authenticated()
		.antMatchers("/expenses").authenticated()
		.antMatchers("/expensesGraphView").authenticated()
		.antMatchers("/saveSubcategory").authenticated()
		.antMatchers("/saveSubcategory").authenticated()
		.antMatchers("/sendingCategory").authenticated()
		.antMatchers("/getExpensesAmountAndDate").authenticated()
		.antMatchers("/saveExpenseAndImage").authenticated()
		.antMatchers("/sendingPropertiesForQueryingExpense").authenticated()
		.antMatchers("/sendingCategory").authenticated()
		.antMatchers("/logout").authenticated()
		.antMatchers("/register").not().authenticated()
			
		.and().exceptionHandling().accessDeniedPage("/accessDenied")
		.and().formLogin().loginPage("/login").failureUrl("/loginFailure")
		.and().logout().logoutSuccessUrl("/");

	}

}
