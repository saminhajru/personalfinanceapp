package personalfinanceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import personalfinanceapp.user.User;
import personalfinanceapp.user.UserService;

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
	public String doregister(Model model, User user, BindingResult result) {
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return "doregister";
		
	}
}
