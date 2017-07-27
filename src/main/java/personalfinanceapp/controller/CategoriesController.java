package personalfinanceapp.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import personalfinanceapp.categories.Categories;
import personalfinanceapp.model.Subcategory;
import personalfinanceapp.model.User;
import personalfinanceapp.service.SubcategoryService;

@Controller
public class CategoriesController {

	private SubcategoryService subcategoryService;
	
	public CategoriesController(SubcategoryService subcategoryService) {
		this.subcategoryService = subcategoryService;
	}

	@RequestMapping("/categories")
	public String categories(Model model) {
		
		model.addAttribute("categories", Categories.values());
		return "category/categories";
	}

	@RequestMapping(value = "/subcategories", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String subcategories(Model model, Principal principal) {

		model.addAttribute("categories",  Categories.values());

		List<Subcategory> subcategories = subcategoryService.findSubcategoriesByUsername(principal.getName());

		if (!subcategories.isEmpty()) {
			model.addAttribute("subcategories", subcategories);
		}
		return "subcategory/subcategories";
	}

	@RequestMapping(value = "/saveSubcategory", consumes = "application/json", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveSubcategory(@RequestBody HashMap<String, String> data, Principal principal,
			Model model) {
				
			String category = data.get("category");
			String subcategoryName = data.get("subcategory");
			String color =  data.get("color");

			User user = new User();
			user.setUsername(principal.getName());
			Subcategory subcategory = new Subcategory(subcategoryName, category, user, color);
			
			subcategoryService.saveSubcategory(subcategory);

			model.addAttribute("subcategoryName", subcategoryName);
			String image = "images/svg/" + "" + category+ "" + ".svg";
			model.addAttribute("image", image);
			
			String backgroundColor = "background-color : " + "" + color;
			model.addAttribute("color", backgroundColor);
			
		return "subcategory/subcategoryTableTemplate";
	}
}
