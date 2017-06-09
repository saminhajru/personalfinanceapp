package personalfinanceapp.categories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubcategoryService {
	
	@Autowired
	private SubcategoryRepository subRepo;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	
	public void saveSubcategory(Subcategory subcategory) {
		subRepo.save(subcategory);
	}
	
	public List<Subcategory> getAllSubcategoriesFromUser(String username) {
		return subRepo.findByUserUsername(username);
	}
	
	public List<String> getAllSubcategoriesNames(String category, String username) {
		Query query =  (Query)session().createQuery("select subcategory from Subcategory where category = :category and username = :username");
		query.setParameter("category", category);
		query.setParameter("username", username);
	
		return query.list();
		
	}
	
}
