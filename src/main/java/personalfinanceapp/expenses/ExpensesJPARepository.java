package personalfinanceapp.expenses;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesJPARepository extends JpaRepository<Expenses, Integer> {
	
	List<Expenses> findByUsername(String username);

	List<Expenses> findByDateOFMakingtheExpenseAndUsername(Date dateFormatted, String username);
		
}
