package personalfinanceapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import personalfinanceapp.model.Subcategory;
import personalfinanceapp.repository.SubcategoryJPARepository;
import personalfinanceapp.repository.SubcategoryRepository;

@Service
public class SubcategoryService {
	
	private SubcategoryJPARepository subcategoryJPARepository;
	private SubcategoryRepository subcategoryRepository;
	
	public SubcategoryService(SubcategoryJPARepository subcategoryJPARepository,
			 SubcategoryRepository subcategoryRepository) {
		this.subcategoryJPARepository = subcategoryJPARepository;
		this.subcategoryRepository = subcategoryRepository;
	}
	
	public boolean saveSubcategory(Subcategory subcategory) {
		return subcategoryJPARepository.save(subcategory) != null;
	}
	
	public List<Subcategory> findSubcategoriesByUsername(String username) {
		return subcategoryJPARepository.findByUserUsername(username);
	}	
	
	public List<String> getAllSubcategoriesNames(String category, String username) {
		return subcategoryRepository.getAllSubcategoriesNames(category, username);
	}
}
