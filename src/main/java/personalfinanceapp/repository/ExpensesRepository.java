package personalfinanceapp.repository;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import personalfinanceapp.model.Subcategory;
import personalfinanceapp.model.Expenses;

@Repository
@Transactional
public class ExpensesRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public List<Expenses> getExpensesByCriteria(String category, Subcategory subcategory, Double fromAmount,
			Double toAmount, Date fromDate, Date toDate, String username, String orderBy) {

		Criteria searchCriteria = session().createCriteria(Expenses.class);

		if (category != null && !category.isEmpty()) {
			searchCriteria.add(Restrictions.eq("category", category));
		}

		if (subcategory != null) {
			searchCriteria.add(Restrictions.eq("subcategory", subcategory));
		}

		if (fromAmount != null) {
			searchCriteria.add(Restrictions.ge("amountOfExpense", fromAmount));
		}

		if (toAmount != null) {
			searchCriteria.add(Restrictions.le("amountOfExpense", toAmount));
		}

		if (fromDate != null) {
			searchCriteria.add(Restrictions.ge("dateOFMakingtheExpense", fromDate));
		}

		if (toDate != null) {
			searchCriteria.add(Restrictions.le("dateOFMakingtheExpense", toDate));
		}

		if (username != null) {
			searchCriteria.add(Restrictions.eq("username", username));
		}
		
		if(orderBy != null) {
			searchCriteria.addOrder(Order.desc(orderBy));
		}

		return searchCriteria.list();

	}
	
	public int getIdOfLastExpenseInDatabase() {
		
		return (int)session().createQuery("select max(id) from Expenses").uniqueResult();
		
	}	
}