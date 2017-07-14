package personalfinanceapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import personalfinanceapp.model.Expenses;
import personalfinanceapp.model.Subcategory;
import personalfinanceapp.repository.ExpensesJPARepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ExpensesServiceTest {
	
	@Autowired
	private ExpensesJPARepository expensesJPARepository;
		
	private Expenses expenses;
	
	private Date dateNow;
	
	@Before
	public void setup() {
		
		dateNow = new Date();
			
		int id = 30;
		expenses = new Expenses(id, new Subcategory("Pranje", null, null), "HOME", 200, "There is no descr", "test", dateNow);
		
		id++;
	}
	
	@Test
	public void  test() {
		
		long numberOfExpensesBeforeCallingSaveMethod = expensesJPARepository.count();
		
		assertNotNull(expensesJPARepository.save(expenses));
		
		assertNotNull(expensesJPARepository.save(expenses));
				
		assertEquals(numberOfExpensesBeforeCallingSaveMethod + 2, expensesJPARepository.count());
		
		List<Expenses> expensesByName =  expensesJPARepository.findByUsername("test");
		
		assertNotNull(expensesByName);
		assertEquals(2, expensesByName.size());
		
		List<Expenses> expensesList = expensesJPARepository.findByDateOFMakingtheExpenseAndUsername(dateNow, "test");
		
		assertNotNull(expensesList);		
		assertEquals(2, expensesList.size());		
	}
}
