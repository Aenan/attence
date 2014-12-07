package cn.edu.tju.se.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.tju.se.demo.dao.RecipeDao;
import cn.edu.tju.se.demo.domain.Recipe;
import cn.edu.tju.se.demo.domain.Recipes;
import cn.edu.tju.se.demo.service.RecipeService;
@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired RecipeDao recipeDao;
	
	public void setRecipeDao(RecipeDao recipeDao) {
		this.recipeDao = recipeDao;
	}

	@Override
	public Recipe get(Long id) {
		return recipeDao.get(id);
	}

	@Override
	public void update(Recipe recipe) {
		recipeDao.update(recipe);
	}

	@Override
	public void add(Recipe recipe) {
		recipeDao.save(recipe);
	}

	@Override
	public void remove(Long id) {
		recipeDao.remove(recipeDao.get(id));
	}

	@Override
	public List<Recipe> getAll() {
		return recipeDao.loadAll();
	}

}
