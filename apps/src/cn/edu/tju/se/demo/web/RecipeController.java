package cn.edu.tju.se.demo.web;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;

import cn.edu.tju.se.base.web.controller.BaseController;
import cn.edu.tju.se.demo.domain.Recipe;
import cn.edu.tju.se.demo.service.RecipeService;

import com.google.gson.Gson;
@Controller
@RequestMapping(value="/recipes")

public class RecipeController extends BaseController {

	@Autowired
	RecipeService recipeService;
	
	
	public RecipeService getRecipeService() {
		return recipeService;
	}

	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")	
	public @ResponseBody Recipe getRecipe(@PathVariable String id,Model model) {
		return recipeService.get(Long.parseLong(id));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateRecipeByPut(@RequestBody String body) {
		Recipe recipe = (new Gson()).fromJson(body, Recipe.class);
		recipeService.update(recipe);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateRecipeByPost(@RequestBody String body) {
		Recipe recipe = (new Gson()).fromJson(body, Recipe.class);
		recipeService.update(recipe);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addRecipe(@RequestBody String body) {
		Recipe recipe = (new Gson()).fromJson(body, Recipe.class);
		recipeService.add(recipe);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public @ResponseBody List<Recipe> removeRecipe(@PathVariable String id) {
		recipeService.remove(Long.parseLong(id));
		List<Recipe> recipes = recipeService.getAll();
		return recipes;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Recipe> getRecipes(Model model) {
		return recipeService.getAll();
	}	
}
