package personalfinanceapp.expenses;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import personalfinanceapp.categories.Categories;
import personalfinanceapp.categories.Subcategory;
import personalfinanceapp.categories.SubcategoryService;

@Controller
public class ExpensesController {
	
	@Autowired
	private SubcategoryService subcategoryService;
	
	@Autowired
	private ExpensesService expenseService;
	
	@RequestMapping(value="/expenses", method= RequestMethod.GET)
	private String expenses(Model model, Principal principal) {
		
		Categories[] categories = Categories.values();
		model.addAttribute("categories", categories);
		
		if(principal != null) {
		List<Expenses> expenses = expenseService.getAllExpenses(principal.getName());
		model.addAttribute("expenses", expenses);
		}		
		
		return "expenses";
	}
	
	@RequestMapping(value="/sendingCategory", method= RequestMethod.POST, consumes = "application/json")
	private String sendingCategory(Model model, @RequestBody HashMap<String,String> category, Principal principal) {
		
		if(principal != null) {
			
		List<String> subcategories = subcategoryService.getAllSubcategoriesNames(category.get("category"), principal.getName());
		model.addAttribute("subcategories", subcategories);
		
		}
		
		return "expensesSelectTemplate";
	}
	
	@RequestMapping(value = "/sendingExpense", method = RequestMethod.POST, consumes = "application/json")
	private String sendingExpenses(Model model, @RequestBody HashMap<String, String> expenseJson, Principal principal) throws ParseException {
		
		
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		Date date = (Date)formatter.parse(expenseJson.get("formattedDate"));
		
		Subcategory sub = new Subcategory();
		sub.setName(expenseJson.get("subcategory"));
		
		Expenses expense = new Expenses();
		expense.setExpensesId(expenseService.getNumberOfExpenses() + 1);
		expense.setSubcategory(sub);
		expense.setCategory(expenseJson.get("category"));
		expense.setAmount(Integer.parseInt(expenseJson.get("amount")));
		expense.setDescription(expenseJson.get("description"));
		expense.setUser(principal.getName());
		expense.setDate(date);
		
		expenseService.save(expense);
		
		model.addAttribute("expense", expense);
				
		return "expensesDisplayTemplate";
	}
}
