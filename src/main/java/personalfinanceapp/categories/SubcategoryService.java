package personalfinanceapp.categories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personalfinanceapp.model.Subcategory;

@Service
public class SubcategoryService {
	
	@Autowired
	private SubcategoryJPARepository subJPARepo;
	
	@Autowired
	private SubcategoryRepository subRepo;
	
	public void saveSubcategory(Subcategory subcategory) {
		subJPARepo.save(subcategory);
	}
	
	public List<Subcategory> getAllSubcategoriesFromUser(String username) {
		return subJPARepo.findByUserUsername(username);
	}	
	
	public List<String> getAllSubcategoriesNames(String category, String username) {
		return subRepo.getAllSubcategoriesNames(category, username);
	}
}
