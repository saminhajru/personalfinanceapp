package personalfinanceapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import personalfinanceapp.model.Subcategory;
import personalfinanceapp.model.User;
import personalfinanceapp.repository.SubcategoryJPARepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SubcategoryServiceTest {
	
	@Autowired
	private SubcategoryJPARepository subcategoryJpaRepo;
	
	private Subcategory sub;
	private Subcategory sub2;
	
	
	@Before
	public void setup() {
		
		sub = new Subcategory("Pranje", "HOME", createUser("testuser"), "blue");
		sub2 = new Subcategory("Pranje1", "HOME", createUser("testuser"), "blue");
	}
	
	@Test
	public void test() {
		
		assertNotNull(subcategoryJpaRepo.save(sub));
		
		assertEquals(sub.getCategory(), subcategoryJpaRepo.save(sub).getCategory());
		
		assertNotNull(subcategoryJpaRepo.save(sub2));
		
		assertEquals(sub2.getCategory(), subcategoryJpaRepo.save(sub2).getCategory());
		
		List<Subcategory> subcategoriesByName = subcategoryJpaRepo.findByUserUsername("testuser");
		
		assertEquals(2, subcategoriesByName.size());
		
	}
	
	public User createUser(String username) {
		User user = new User();
		user.setUsername(username);
		return user;
	}
}
