package personalfinanceapp.expenses;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import personalfinanceapp.categories.Categories;
import personalfinanceapp.categories.Subcategory;
import personalfinanceapp.categories.SubcategoryService;

@Controller
public class ExpensesController {

	DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

	@Autowired
	private SubcategoryService subcategoryService;

	@Autowired
	private ExpensesService expenseService;

	@RequestMapping(value = "/expenses", method = RequestMethod.GET)
	private String expenses(Model model, Principal principal) {

		Categories[] categories = Categories.values();
		model.addAttribute("categories", categories);

		if (principal != null) {
			List<Expenses> expenses = expenseService.getAllExpenses(principal.getName());
			model.addAttribute("expenses", expenses);
			
			List<String> subcategories =  subcategoryService.getAllSubcategoriesNames("HOME", principal.getName());
			
			if(!subcategories.isEmpty()) {
			model.addAttribute("subcategories", subcategories);	
			}
		}

		return "expenses";
	}

	@RequestMapping(value = "/sendingCategory", method = RequestMethod.POST, consumes = "application/json")
	private String sendingCategory(Model model, @RequestBody HashMap<String, String> category, Principal principal) {

		if (principal != null) {

			List<String> subcategories = subcategoryService.getAllSubcategoriesNames(category.get("category"),
					principal.getName());
			model.addAttribute("subcategories", subcategories);
		}

		return "expensesSelectTemplate";
	}

	@RequestMapping(value = "/getExpensesAmountAndDate", method = RequestMethod.GET, produces = "application/json")
	private @ResponseBody List<ExpensesDTO> getExpensesAmountAndDate(Principal principal) throws ParseException {

		List<ExpensesDTO> expenses = new ArrayList<ExpensesDTO>();

		if (principal != null) {
			expenses = expenseService.getAllExpensesDTOForUserAndCurrentMonth(principal.getName());
		}

		return expenses;
	}

	@RequestMapping(value = "/expensesGraphView", method = RequestMethod.GET)
	private String expensesGraphView() {
		return "expensesGraphView";
	}

	@RequestMapping(value = "/saveExpenseAndImage", method = RequestMethod.POST)
	private String saveExpenseAndImageOfThisExpense(Model model, Principal principal,
			@RequestParam(name = "subcategory", required = false) String subcategory,
			@RequestParam(name = "amountOfExpense", required = false) String amountOfExpense,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "categoryRow", required = false) String categoryRow,
			@RequestParam(name = "photoForExpense", required = false) MultipartFile photo)
			throws IllegalStateException, IOException {

		Subcategory sub = new Subcategory();
		sub.setName(subcategory);

		Expenses expense = createExpense(sub, categoryRow, amountOfExpense, description, principal.getName());

		expenseService.save(expense);

		model.addAttribute("expense", expense);
		
		if(!photo.isEmpty()) {

		File directoryForSavingImage = new File(System.getProperty("user.dir") + "/src/main/resources/static/images/"
				+ principal.getName() + "/" + categoryRow + "/" + subcategory + "/");

		if (!directoryForSavingImage.isDirectory()) {
			directoryForSavingImage.mkdirs();
		}

		File fileToSaveImage = new File(directoryForSavingImage + "/" + photo.getOriginalFilename());

		byte[] bytes = photo.getBytes();

		Path path = Paths.get(fileToSaveImage.toString());

		Files.write(path, bytes);
		
		}

		return "expensesDisplayTemplate";

	}

	@RequestMapping(value = "/sendingPropertiesForQueryingExpense", method = RequestMethod.POST)
	private String queryingExpense(Model model, Principal principal,
			@RequestParam(name = "categorySelect", required = false) String category,
			@RequestParam(name = "subcategory", required = false) String subcategory,
			@RequestParam(name = "firstAmount", required = false) Double fromAmount,
			@RequestParam(name = "secondAmount", required = false) Double toAmount,
			@RequestParam(name = "startDate", required = false) String startDate,
			@RequestParam(name = "endDate", required = false) String endDate,
			@RequestParam(name = "orderBy", required = false) String orderBy) throws ParseException {

		String username = principal.getName();
	
		model.addAttribute("expense", expenseService.getExpenses(category, subcategory, fromAmount, toAmount, startDate, endDate, username, orderBy));
			
		return "expensesDisplayTemplate";

	}

	public Expenses createExpense(Subcategory sub, String categoryRow, String amount, String description,
			String username) {

		Expenses expense = new Expenses();
		expense.setExpensesId((expenseService.getNumberOfExpenses()) + 1);
		expense.setSubcategory(sub);
		expense.setCategory(categoryRow);
		expense.setAmount(Double.parseDouble(amount));
		expense.setDescription(description);
		expense.setUser(username);
		expense.setDate(new Date());

		return expense;
	}
}
