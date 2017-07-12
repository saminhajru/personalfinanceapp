package personalfinanceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "loginPage";
	}
	
	@RequestMapping(value = "/loginFailure", method = RequestMethod.GET)
	public String loginFailure(Model model) {
		
		String errorMessage = "User with this username and password doesn't exist. Please try again.";
		model.addAttribute("errorMessage", errorMessage);
		
		return "loginPage";
	}
}
