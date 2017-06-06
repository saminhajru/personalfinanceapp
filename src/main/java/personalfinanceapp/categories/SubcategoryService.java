package personalfinanceapp.categories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryService {
	
	@Autowired
	private SubcategoryRepository subRepo;
	
	
	public void saveSubcategory(Subcategory subcategory) {
		subRepo.save(subcategory);
	}
	
	public List<Subcategory> getAllSubcategoriesFromUser(String username) {
		return subRepo.findByUserUsername(username);
	}
	
}
