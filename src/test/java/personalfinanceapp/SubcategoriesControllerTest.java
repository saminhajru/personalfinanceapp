package personalfinanceapp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import personalfinanceapp.controller.CategoriesController;
import personalfinanceapp.model.Subcategory;
import personalfinanceapp.service.SubcategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "test", password = "testPass", roles = "USER")
public class SubcategoriesControllerTest {

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@MockBean
	private SubcategoryService subcategoryService;

	private MockMvc mockMvc;

	private Subcategory sub1;

	private HashMap<String, String> mapaSubcategory;

	@Before
	public void setup() {

		mockMvc = MockMvcBuilders.standaloneSetup(new CategoriesController(subcategoryService))
				.apply(springSecurity(springSecurityFilterChain))
				.build();

		sub1 = new Subcategory("Pegla", "HOME", null, "#00f7ff");

		mapaSubcategory = new HashMap<>();

		mapaSubcategory.put("nameOfTheSubcategory", "Pegla");
		mapaSubcategory.put("category", "HOME");
		mapaSubcategory.put("color", "#00f7ff");

	}

	@Test
	public void getingAllSubcategoriesFromUserTesting() throws Exception {

		mockMvc.perform(get("/subcategories"))
			.andExpect(status().isOk())
			.andExpect(view().name("subcategory/subcategories"))
			.andExpect(model().attributeExists("categories"));

		verify(subcategoryService, times(1)).getAllSubcategoriesFromUser("test");
	}

	@Test
	public void saveSubcategoryTesting() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/saveSubcategory").contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(mapaSubcategory)).with(csrf()))
			.andExpect(status().is(200)).andExpect(view().name("subcategory/subcategoryTableTemplate"))
			.andExpect(model().attributeExists("image"));

		verify(subcategoryService, times(1)).saveSubcategory(sub1);
	}
}
