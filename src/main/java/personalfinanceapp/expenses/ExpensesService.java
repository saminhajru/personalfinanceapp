package personalfinanceapp.expenses;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExpensesService {
	
	@Autowired
	private ExpensesRepository expensesRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public int getNumberOfExpenses() {
		return (int)expensesRepository.count();

	}

	public void save(Expenses expense) {
		expensesRepository.save(expense);	
	}
	
	public List<Expenses> getAllExpenses(String username) {
		
		Query query = (Query)session().createQuery("from Expenses where username = :username");
		query.setParameter("username", username);
		
		return query.list();
	}
}
