package personalfinanceapp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import personalfinanceapp.controller.RegisterController;
import personalfinanceapp.model.User;
import personalfinanceapp.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterControllerTest {

	@Autowired
	private FilterChainProxy springSecurityFilterChain;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private MockMvc mockMvc;

	@Before
	public void setup() {

		mockMvc = MockMvcBuilders.standaloneSetup(new RegisterController(userService, passwordEncoder))
				.apply(springSecurity(springSecurityFilterChain)).build();
	}
	
	@Test
	public void registerMappingTesting() throws Exception {

		mockMvc.perform(get("/register"))
			.andExpect(status().isOk())
			.andExpect(view().name("registerPage"));
	}
	
	@Test
	public void creatingUserTesting() throws Exception {
		
		mockMvc.perform(post("/doregister")
			.param("username", "test92")
			.param("password", "testing")
			.param("email", "test99@test.com").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("createUserPage"));
		
		User userToVerify = new User("test92", "test99@test.com", passwordEncoder.encode("testing"), true, "ROLE_USER");
		
		verify(userService, times(1)).save(userToVerify);
	}

}
