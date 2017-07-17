package personalfinanceapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import personalfinanceapp.model.User;
import personalfinanceapp.repository.UserJPARepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserServiceTest {
	
	@Autowired
	private UserJPARepository userJPARepo;
	
	private User user;
	
	@Before
	public void setup() {
		
		user = new User("test123", "test@test.com", "test123", true, "ROLE_USER");
	}
	
	@Test
	public void test() {
			
		userJPARepo.save(user);
		
		User userByName = userJPARepo.findByUsername("test123");
		
		assertNotNull(userByName);
		
		assertEquals(user.getUsername(), userByName.getUsername());
		
	}

}
