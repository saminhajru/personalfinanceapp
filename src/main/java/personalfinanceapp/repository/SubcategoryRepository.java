package personalfinanceapp.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SubcategoryRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public List<String> getAllSubcategoriesNames(String category, String username) {
		Query query = (Query) session().createQuery("select subcategory from Subcategory where category = :category and username = :username");
		query.setParameter("category", category);
		query.setParameter("username", username);

		return query.list();
	}
}
