package personalfinanceapp.expenses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExpensesService {
	
	DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

	@Autowired
	private ExpensesJPARepository expensesJPARepository;

	public int getNumberOfExpenses() {
		return (int) expensesJPARepository.count();

	}

	public void save(Expenses expense) {
		expensesJPARepository.save(expense);
	}

	public List<Expenses> getAllExpenses(String username) {

		return expensesJPARepository.findByUsername(username);
	}

	public List<ExpensesDTO> getAllExpensesDTOForUserAndCurrentMonth(String username) throws ParseException {

		List<ExpensesDTO> expensesDTOList = new ArrayList<>();

		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		int totalDaysdays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= totalDaysdays; i++) {
			
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
	
	public String getDateInStringFormat(int currentYear, int currentMonth, int currentDay) {
		return currentMonth + "/" + currentDay + "/" + currentYear;
	}
}
