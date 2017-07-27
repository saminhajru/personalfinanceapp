package personalfinanceapp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import personalfinanceapp.controller.ExpensesController;
import personalfinanceapp.model.Expenses;
import personalfinanceapp.model.Subcategory;
import personalfinanceapp.service.ExpensesService;
import personalfinanceapp.service.SubcategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpensesControllerCSV {
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;	
	@SpyBean
	private ExpensesService expService;
	@MockBean
	private SubcategoryService subService;
	
	private MockMvc mockMvc;
	private Expenses expense;
	private DateFormat formatterYYMMDD = new SimpleDateFormat("yy-mm-dd");
	private Subcategory sub = new Subcategory();
	private String dir = System.getProperty("user.dir");

	@Before
	public void setup() throws ParseException {
		mockMvc = MockMvcBuilders.standaloneSetup(new ExpensesController(subService, expService))
								 .apply(springSecurity(springSecurityFilterChain))
								 .build();
		sub.setName("MOTOR");
	}
	
	@Test
	@WithMockUser(username = "test", password = "testPass", roles = "USER")
	public void testUploadingCSVFile() throws Exception {	
		File fileExpensesFirst = new File(dir + "/src/test/testresources/expensesFirst.csv.csv");
		File fileExpensesSecond = new File(dir + "/src/test/testresources/expensesSecond.csv.csv");
		File fileExpensesThird = new File(dir + "/src/test/testresources/expensesSecond.csv.csv");

		expense = expService.createExpense(sub, "CAR", 50.0, "This is a new brand Exception", "test",
				formatterYYMMDD.parse("2017-07-04"));

		System.out.println(expense);

		mockMvc.perform(fileUpload("/csvFileUpload").file("csvFile", Files.readAllBytes(fileExpensesFirst.toPath()))
				.with(csrf())).andExpect(status().isOk());

		mockMvc.perform(fileUpload("/csvFileUpload").file("csvFile", Files.readAllBytes(fileExpensesSecond.toPath()))
				.with(csrf())).andExpect(status().isOk());

		mockMvc.perform(fileUpload("/csvFileUpload").file("csvFile", Files.readAllBytes(fileExpensesThird.toPath()))
				.with(csrf())).andExpect(status().isOk());

		verify(expService, times(1)).save(expense);
	}	
}
