package personalfinanceapp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personalfinanceapp.expenses.ExpensesDTO;
import personalfinanceapp.model.Expenses;
import personalfinanceapp.model.Subcategory;
import personalfinanceapp.repository.ExpensesJPARepository;
import personalfinanceapp.repository.ExpensesRepository;

@Service
@Transactional
public class ExpensesService {

	private static final List<String> FILE_HEADER = new ArrayList<>(Arrays.asList(new String[]{"ID", "SUBCATEGORY", "CATEGORY", "USER", "AMOUNT", "DESCRIPTION", "DATE"}));
	
	DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
	
	private ExpensesJPARepository expensesJPARepository;
	private ExpensesRepository expensesRepository;
	
	public ExpensesService() {
	}
	
	@Autowired
	public ExpensesService(ExpensesJPARepository expensesJPARepository, ExpensesRepository expensesRepository) {
		this.expensesJPARepository = expensesJPARepository;
		this.expensesRepository = expensesRepository;
	}

	public int getNumberOfExpenses() {
		return (int) expensesJPARepository.count();
	}

	public void save(Expenses expenses) {
		expensesJPARepository.save(expenses);
	}

	public List<Expenses> getAllExpenses(String username) {
		return expensesJPARepository.findByUsername(username);
	}
	
	public List<ExpensesDTO> getAllExpensesDTOForUserAndCurrentMonth(String username) throws ParseException {

		List<ExpensesDTO> expensesDTOList = new ArrayList<>();
		int currentYear = getDayOrMonthOrYear("currentYear");
		int currentMonth = getDayOrMonthOrYear("currentMonth");

		for (int i = 1; i <= getDayOrMonthOrYear("totalDays"); i++) {

			String date = getDateInStringFormat(currentYear, currentMonth, i);
			Date dateFormatted = formatter.parse(date);

			List<Expenses> expensesList = expensesJPARepository.findByDateOFMakingtheExpenseAndUsername(dateFormatted,
					username);

			if (expensesList == null) {
				ExpensesDTO expensesDTO = new ExpensesDTO();
				expensesDTO.setDate(date);
				expensesDTOList.add(expensesDTO);
			}

			ExpensesDTO expensesDTO = new ExpensesDTO();

			for (Expenses expense : expensesList) {
				expensesDTO.setAmount(expensesDTO.getAmount() + expense.getAmount());
			}

			expensesDTO.setDate(date);
			expensesDTOList.add(expensesDTO);
		}
		return expensesDTOList;
	}
	
	public List<Expenses> getAllExpensesForLoggedUserAndCurrentMonth(String username) throws ParseException {

		int currentYear = getDayOrMonthOrYear("currentYear");
		int currentMonth = getDayOrMonthOrYear("currentMonth");
		
		List<Expenses> totalListOfExpensesForCurrentMonth = new ArrayList<>();
		
		for (int i = 1; i <= getDayOrMonthOrYear("totalDays"); i++) {

			String date = getDateInStringFormat(currentYear, currentMonth, i);
			Date dateFormatted = formatter.parse(date);

			List<Expenses> expensesList = expensesJPARepository.findByDateOFMakingtheExpenseAndUsername(dateFormatted,
					username);

			if (expensesList != null) {
				
				for(Expenses expenses : expensesList) {
					
				Subcategory sub = new Subcategory();
				sub.setName(expenses.getSubcategoryName());
				
				expenses.setSubcategory(sub);
				
				totalListOfExpensesForCurrentMonth.add(expenses);
				}
			
			}
		}
		return totalListOfExpensesForCurrentMonth;
		
	}

	public String getDateInStringFormat(int currentYear, int currentMonth, int currentDay) {

		return currentMonth + "/" + currentDay + "/" + currentYear;

	}

	public List<Expenses> getExpenses(String category, String subcategory, Double fromAmount, Double toAmount,
			String startDate, String endDate, String username, String orderBy) throws ParseException {

		Date fromDate = null, toDate = null;

		Subcategory sub = null;

		if (subcategory != null && !subcategory.equals("") && !subcategory.equals("undefined")) {

			sub = new Subcategory();

			sub.setName(subcategory);
		}

		if (startDate != null && !startDate.isEmpty()) {
			fromDate = formatter.parse(startDate);
		}

		if (endDate != null && !endDate.isEmpty()) {
			toDate = formatter.parse(endDate);
		}

		return expensesRepository.getExpensesByCriteria(category, sub, fromAmount, toAmount, fromDate, toDate,
				username, orderBy);

	}
	
	public Expenses createExpense(Subcategory sub, String categoryRow, String amount, String description,
			String username) {

		Expenses expense = new Expenses();
		
		expense.setExpensesId((getNumberOfExpenses()) + 1);
		expense.setSubcategory(sub);
		expense.setCategory(categoryRow);
		expense.setAmount(Double.parseDouble(amount));
		expense.setDescription(description);
		expense.setUser(username);
		expense.setDate(new Date());

		return expense;
	}
	
	public int getDayOrMonthOrYear(String gettinDate) {
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		
		int dayOrMonthOrYear = 0;
		
		if(gettinDate.equals("currentYear")) {
			dayOrMonthOrYear = cal.get(Calendar.YEAR);
			} else if (gettinDate.equals("currentMonth")) {
			dayOrMonthOrYear = cal.get(Calendar.MONTH) + 1;
			} else if (gettinDate.equals("totalDays")){
			dayOrMonthOrYear = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
		return dayOrMonthOrYear;
	}
	
	public void csvFileDownloading(String username) throws ParseException {
		
		List<Expenses> expensesForCurrentMonth = 
				getAllExpensesForLoggedUserAndCurrentMonth(username);

		CSVFormat csvformat = CSVFormat.DEFAULT;

		String home = System.getProperty("user.home");
		
		String fileName = "expenses.csv";

		File file = new File(home + "/Downloads/" + "" + fileName + "" + ".csv");

		CSVPrinter csvPrinter = null;

		FileWriter fileWriter = null;

		try {

			fileWriter = new FileWriter(file);

			csvPrinter = new CSVPrinter(fileWriter, csvformat);

			for (String header : FILE_HEADER) {
				csvPrinter.print(header);
			}

			csvPrinter.println();

			for (Expenses expenses : expensesForCurrentMonth) {
				
				csvPrinter.print(expenses.getExpensesId());
				csvPrinter.print(expenses.getCategory());
				csvPrinter.print(expenses.getSubcategory().getName());
				csvPrinter.print(expenses.getUser());
				csvPrinter.print(expenses.getAmount());
				csvPrinter.print(expenses.getDescription().trim());
				csvPrinter.print(expenses.getDate().toString());
				csvPrinter.println();
			}
			
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				fileWriter.flush();
				csvPrinter.close();
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void excelFileDownloading(String name) throws ParseException {
		
		String home = System.getProperty("user.home");
		
		String fileName = "expenses.excel";

		File file = new File(home + "/Downloads/" + "" + fileName + "" + ".excel");
		
		List<Expenses> expensesForCurrentMonth = 
				getAllExpensesForLoggedUserAndCurrentMonth(name);
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		
		int rowCount = 0;
		Row rowHeader = sheet.createRow(rowCount);
		writeExpenses(FILE_HEADER, rowHeader);
		
		for(Expenses expenses : expensesForCurrentMonth) {
			Row row = sheet.createRow(++rowCount);
			writeExpenses(expenses, row);
		}
		
		FileOutputStream fileOutputStream = null;
		
		try {
			fileOutputStream = new FileOutputStream(file);
			workbook.write(fileOutputStream);
			
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private void writeExpenses(List<String> fileHeader, Row rowHeader) {
		
		Cell cell = rowHeader.createCell(0);
		cell.setCellValue(fileHeader.get(0));
		
		cell = rowHeader.createCell(1);
		cell.setCellValue(fileHeader.get(1));
		
		cell = rowHeader.createCell(2);
		cell.setCellValue(fileHeader.get(2));
		
		cell = rowHeader.createCell(3);
		cell.setCellValue(fileHeader.get(3));
		
		cell = rowHeader.createCell(4);
		cell.setCellValue(fileHeader.get(4));
		
		cell = rowHeader.createCell(5);
		cell.setCellValue(fileHeader.get(5));
		
		cell = rowHeader.createCell(6);
		cell.setCellValue(fileHeader.get(6));	
	}

	private void writeExpenses(Expenses expenses, Row row) {
		
		Cell cell = row.createCell(0);
		cell.setCellValue(Integer.toString(expenses.getExpensesId()));
		
		cell = row.createCell(1);
		cell.setCellValue(expenses.getCategory());
		
		cell = row.createCell(2);
		cell.setCellValue(expenses.getSubcategoryName());
		
		cell = row.createCell(3);
		cell.setCellValue(expenses.getAmount());
		
		cell = row.createCell(4);
		cell.setCellValue(expenses.getUser());
		
		cell = row.createCell(5);
		cell.setCellValue(expenses.getDescription());
		
		cell = row.createCell(6);
		cell.setCellValue(expenses.getDate());		
	}
}
