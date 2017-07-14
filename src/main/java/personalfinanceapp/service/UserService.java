package personalfinanceapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personalfinanceapp.model.User;

import personalfinanceapp.repository.UserJPARepository;

@Service
public class UserService {

	@Autowired
	private UserJPARepository userJPARepo;
	
	public void save(User user) {
		userJPARepo.saveAndFlush(user);
	}

	public boolean usernameAlreadyExist(String username) {
		User user = userJPARepo.findByUsername(username);
		boolean exist = true;
		
		if(user == null) {
			exist = false;
		}
		else if(!user.getUsername().isEmpty()) {
			exist = true;
		}
			
		return exist;
	}
}
