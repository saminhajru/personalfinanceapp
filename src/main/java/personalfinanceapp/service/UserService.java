package personalfinanceapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personalfinanceapp.model.User;

import personalfinanceapp.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public void save(User user) {
		userRepo.save(user);
	}

	public boolean usernameAlreadyExist(String username) {
		User user = userRepo.getOne(username);
	
		return (user != null);
	}
}
