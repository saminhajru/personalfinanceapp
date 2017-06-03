package personalfinanceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import personalfinanceapp.categories.Categories;

@Controller
public class CategoriesController {
	
	@RequestMapping("/categories")
	public String categories(Model model) {
		
		Categories[] categories = Categories.values();
		
		model.addAttribute("categories", categories);
		
		return "categories";
		
	}
}
