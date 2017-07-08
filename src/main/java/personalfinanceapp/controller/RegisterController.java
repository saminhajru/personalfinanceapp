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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("/register")
	public String register(Model model, User user) {
		return "register";
	}
	
	@RequestMapping(value = "/doregister", method = RequestMethod.POST)
	public String doregister(@Validated(value = {FormValidationGroup.class}) User user, BindingResult result) {
		
		
		if(result.hasErrors()) {
			return "register";
		}
		
		if(userService.usernameAlreadyExist(user.getUsername())) {
			
			result.rejectValue("username", "DuplicateUsername.personalfinanceapp.user.User");
			return "register";
			
		}	
		
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return "doregister";
		
	}
}
