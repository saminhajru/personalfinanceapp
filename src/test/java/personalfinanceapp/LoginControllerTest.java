package personalfinanceapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import personalfinanceapp.controller.LoginController;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@WithMockUser(username = "test", password = "testPass", roles = "USER")
public class LoginControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void loginTesting() throws Exception {
		
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("loginPage"));
	}

	@Test
	public void loginFailureTesting() throws Exception {

		mockMvc.perform(get("/loginFailure"))
			.andExpect(status().isOk()).andExpect(view().name("loginPage"))
			.andExpect(model().attributeExists("errorMessage"));
	}
}
