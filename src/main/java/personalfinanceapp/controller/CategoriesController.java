package personalfinanceapp.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import personalfinanceapp.categories.Categories;
import personalfinanceapp.categories.Subcategory;
import personalfinanceapp.categories.SubcategoryService;
import personalfinanceapp.user.User;

@Controller
public class CategoriesController {
	
	@Autowired
	private SubcategoryService subcategoryService;

	@RequestMapping("/categories")
	public String categories(Model model) {

		Categories[] categories = Categories.values();

		model.addAttribute("categories", categories);

		return "categories";

	}

	@RequestMapping("/subcategories")
	public String subcategories(Model model, Principal principal) {

		Categories[] categories = Categories.values();

		model.addAttribute("categories", categories);
		
		if(principal != null) {
		List<Subcategory> subcategories = subcategoryService.getAllSubcategoriesFromUser(principal.getName());
		
		if(!subcategories.isEmpty()) {
			model.addAttribute("subcategories", subcategories);
		} 
		} else {
			return "login";
		}
		
		return "subcategories";
	}

	@RequestMapping(value = "/saveSubcategory", consumes = "application/json", method=RequestMethod.POST)
	public String saveSubcategory(@RequestBody HashMap<String, String> categoryAndSubcategory, Principal principal, Model model) {
		
		if(principal != null){
		
		User user = new User();
		user.setUsername(principal.getName());
		Subcategory subcategory = new Subcategory(categoryAndSubcategory.get("nameOfTheSubcategory"), categoryAndSubcategory.get("category"), user);
		
		subcategoryService.saveSubcategory(subcategory);
		
		model.addAttribute("nameOfTheSubcategory", categoryAndSubcategory.get("nameOfTheSubcategory"));	
		String image = "images/svg/" + "" + categoryAndSubcategory.get("category") + "" + ".svg";
		model.addAttribute("image", image);
		}	else {
			return "login";
		}
		return "subcategoryTableTemplate";
	}
	}
