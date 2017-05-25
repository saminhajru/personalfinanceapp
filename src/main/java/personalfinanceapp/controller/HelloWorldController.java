package personalfinanceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloWorldController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "helloWorld";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homeNotLogged() {
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homeLogged() {
		return "home";
	}
}
