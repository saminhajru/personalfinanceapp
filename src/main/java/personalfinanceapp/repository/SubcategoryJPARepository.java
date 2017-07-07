package personalfinanceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import personalfinanceapp.categories.Subcategory;

@Repository
public interface SubcategoryJPARepository extends JpaRepository<Subcategory, String>{
	
	public List<Subcategory> findByUserUsername(String username);

}
