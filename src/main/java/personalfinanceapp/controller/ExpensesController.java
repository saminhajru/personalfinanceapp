package personalfinanceapp.controller;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import personalfinanceapp.categories.Categories;
import personalfinanceapp.expenses.ExpensesDTO;
import personalfinanceapp.model.Expenses;
import personalfinanceapp.model.Subcategory;
import personalfinanceapp.service.ExpensesService;
import personalfinanceapp.service.SubcategoryService;

@Controller
public class ExpensesController {
	
	private SubcategoryService subcategoryService;

	private ExpensesService expenseService;
	
	public ExpensesController() {
	}
	
	@Autowired
	public ExpensesController(SubcategoryService subSer, ExpensesService expSer) {
		this.subcategoryService = subSer;
		this.expenseService = expSer;
	}

	@RequestMapping(value = "/expenses", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	private String expenses(Model model, Principal principal) {

		Categories[] categories = Categories.values();
		model.addAttribute("categories", categories);

		List<Expenses> expenses = expenseService.getAllExpenses(principal.getName());
		model.addAttribute("expenses", expenses);
		
		List<String> subcategories = subcategoryService.getAllSubcategoriesNames(("HOME"),
				principal.getName());
		model.addAttribute("subcategories", subcategories);
		
		
		
		return "expenses/expenses";
	}

	@RequestMapping(value = "/sendingCategory", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("isAuthenticated()")
	private String sendingCategory(Model model, @RequestBody HashMap<String, String> category, Principal principal) {

		List<String> subcategories = subcategoryService.getAllSubcategoriesNames(category.get("category"),
				principal.getName());
		model.addAttribute("subcategories", subcategories);

		return "expenses/expensesSelectTemplate";
	}

	@RequestMapping(value = "/getExpensesAmountAndDate", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()")
	private @ResponseBody List<ExpensesDTO> getExpensesAmountAndDate(Principal principal) throws ParseException {

		return expenseService.getAllExpensesDTOForUserAndCurrentMonth(principal.getName());
		
	}

	@RequestMapping(value = "/expensesGraphView", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	private String expensesGraphView() {
		
		return "expenses/expensesGraphView";
		
	}

	@RequestMapping(value = "/saveExpenseAndImage", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	private String saveExpenseAndImageOfThisExpense(Model model, Principal principal,
			@RequestParam(name = "subcategory", required = false) String subcategory,
			@RequestParam(name = "amountOfExpense", required = false) String amountOfExpense,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "categoryRow", required = false) String categoryRow,
			@RequestParam(name = "photoForExpense", required = false) MultipartFile photo)
			throws IllegalStateException, IOException {
		
		if(subcategory != null && categoryRow != null) {
		
		Subcategory sub = new Subcategory();
		sub.setName(subcategory);

		Expenses expense = expenseService.createExpense(sub, categoryRow, amountOfExpense, description, principal.getName(), new Date());

		expenseService.save(expense);

		model.addAttribute("expense", expense);
		
		}
		
		if(photo != null) {

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

		return "expenses/expensesDisplayTemplate";

	}

	@RequestMapping(value = "/sendingPropertiesForQueryingExpense", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	private String queryingExpense(Model model, Principal principal,
			@RequestParam(name = "categorySelect", required = false) String category,
			@RequestParam(name = "subcategory", required = false) String subcategory,
			@RequestParam(name = "fromAmount", required = false) Double fromAmount,
			@RequestParam(name = "toAmount", required = false) Double toAmount,
			@RequestParam(name = "startDate", required = false) String startDate,
			@RequestParam(name = "endDate", required = false) String endDate,
			@RequestParam(name = "orderBy", required = false) String orderBy) throws ParseException {

		String username = principal.getName();
			
		model.addAttribute("expense", expenseService.getExpenses(category, subcategory, fromAmount, toAmount, startDate, endDate, username, orderBy));
			
		return "expenses/expensesDisplayTemplate";

	}
	
	@RequestMapping(value = "/csvFile", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("isAuthenticated()")
	private String csvFileDownloading(Model model, @RequestBody HashMap<String, String> propertiesRecieved, Principal principal) throws ParseException, IOException {

		if(propertiesRecieved.get("csv") != null ) {
		expenseService.csvFileDownloading(principal.getName());
		}
		return "expenses/expensesSelectTemplate";
	}
	

	@RequestMapping(value = "/excelFile", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("isAuthenticated()")
	private String excelFileDownloading(Model model, @RequestBody HashMap<String, String> propertiesRecieved, Principal principal) throws ParseException, IOException {

		if(propertiesRecieved.get("excel") != null ) {
		expenseService.excelFileDownloading(principal.getName());
		}
		return "expenses/expensesSelectTemplate";
	}
		
	@RequestMapping(value = "/csvFileUpload", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	private @ResponseBody String csvFileUploading(@RequestParam(name="csvFile", required = false) MultipartFile csvFile , Principal principal) throws ParseException, IOException {
		
		String message = null;
		
		File file = new File(csvFile.getName());
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(csvFile.getBytes());
		
		String username = principal.getName();
		
		message = expenseService.csvFileUpload(file, username);
		
		fileOutputStream.close();
		file.delete();
	
		return message;
	}
}
