package personalfinanceapp.categories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryJPARepository extends JpaRepository<Subcategory, String>{
	
	public List<Subcategory> findByUserUsername(String username);

}
