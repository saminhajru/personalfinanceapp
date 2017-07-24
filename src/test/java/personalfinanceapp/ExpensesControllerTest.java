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

import java.util.Date;
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

import personalfinanceapp.controller.ExpensesController;
import personalfinanceapp.model.Expenses;
import personalfinanceapp.model.Subcategory;
import personalfinanceapp.service.ExpensesService;
import personalfinanceapp.service.SubcategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "test", password = "testPass", roles = "USER")
public class ExpensesControllerTest {

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@MockBean
	private ExpensesService expService;

	@MockBean
	private SubcategoryService subService;

	private MockMvc mockMvc;

	private ObjectMapper oMapper = new ObjectMapper();

	@Before
	public void setup() {

		mockMvc = MockMvcBuilders.standaloneSetup(new ExpensesController(subService, expService))
				.apply(springSecurity(springSecurityFilterChain)).build();
	}

	@Test
	public void getExpensesTesting() throws Exception {

		mockMvc.perform(get("/expenses"))
			.andExpect(status().is(200))
			.andExpect(view().name("expenses/expenses"))
			.andExpect(model().attributeExists("categories"))
			.andExpect(model().attributeExists("expenses"));

		verify(expService, times(1)).getAllExpenses("test");
	}

	@Test
	public void sendingCategoryTesting() throws Exception {

		HashMap<String, String> mapCategory = new HashMap<>();
		mapCategory.put("category", "HOME");

		mockMvc.perform(post("/sendingCategory")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(oMapper.writeValueAsBytes(mapCategory)).with(csrf()))
			.andExpect(status().is(200))
			.andExpect(view().name("expenses/expensesSelectTemplate"))
			.andExpect(model().attributeExists("subcategories"));

		verify(subService, times(1)).getAllSubcategoriesNames("HOME", "test");
	}

	@Test
	public void getExpensesAmountAndDateTesting() throws Exception {

		mockMvc.perform(get("/getExpensesAmountAndDate"))
			.andExpect(status().is(200));

		verify(expService, times(1)).getAllExpensesDTOForUserAndCurrentMonth("test");
	}

	@Test
	public void expensesGraphViewTesting() throws Exception {

		mockMvc.perform(get("/expensesGraphView"))
			.andExpect(status().is(200))
			.andExpect(view().name("expenses/expensesGraphView"));
	}

	@Test
	public void saveExpenseAndImageOfThisExpenseTesting() throws Exception {

		mockMvc.perform(post("/saveExpenseAndImage").param("subcategory", "Pranje").param("amountOfExpense", "100")
			.param("description", "No description").param("categoryRow", "HOME").with(csrf()))
			.andExpect(status().is(200)).andExpect(view().name("expenses/expensesDisplayTemplate"));

		Subcategory sub = new Subcategory();
		sub.setName("Pranje");

		Expenses expense = expService.createExpense(sub, "HOME", "100", "There is no description", "test", new Date());

		verify(expService, times(1)).save(expense);
	}

	@Test
	public void queryingExpense() throws Exception {

		mockMvc.perform(post("/sendingPropertiesForQueryingExpense").param("subcategory", "Pranje")
			.param("firstAmount", "100").param("secondAmount", "200").param("description", "No description")
			.param("categorySelect", "HOME").with(csrf())).andExpect(status().is(200))
			.andExpect(view().name("expenses/expensesDisplayTemplate"))
			.andExpect(model().attributeExists("expense"));
	}	
}
