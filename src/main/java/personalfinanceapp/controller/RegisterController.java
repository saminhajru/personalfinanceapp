package personalfinanceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import personalfinanceapp.model.User;
import personalfinanceapp.service.UserService;
import personalfinanceapp.validationGroups.FormValidationGroup;

@Controller
public class RegisterController {
	
	private UserService userService;
	
	private PasswordEncoder passwordEncoder;

	public RegisterController() {
	}
	
	@Autowired
	public RegisterController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model, User user) {
		return "registerPage";
	}
	
	@RequestMapping(value = "/doregister", method = RequestMethod.POST)
	public String doregister(@Validated(value = {FormValidationGroup.class}) User user, BindingResult result) {
		
		if(result.hasErrors()) {
			return "registerPage";
		}
		
		if(userService.usernameAlreadyExist(user.getUsername())) {
			result.rejectValue("username", "DuplicateUsername.user.username");

			return "registerPage";
		}	
		
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		
		return "createUserPage";
		
	}
}
