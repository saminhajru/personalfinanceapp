package personalfinanceapp.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceException {
	
	@ExceptionHandler(DataAccessException.class)
	public String error(DataAccessException e) {
		e.printStackTrace();
		return "error";
	}
	
}
