package personalfinanceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/loginFailure")
	public String loginFailure(Model model) {
		String errorMessage = "User with this username and password doesn't exist. Please try again.";
		model.addAttribute("errorMessage", errorMessage);
		
		return "login";
	}
}
