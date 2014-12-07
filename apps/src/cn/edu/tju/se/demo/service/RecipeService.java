package cn.edu.tju.se.demo.service;

import java.util.List;

import cn.edu.tju.se.demo.domain.Recipe;

public interface RecipeService {

	public Recipe get(Long id);

	public void update(Recipe recipe);

	public void add(Recipe recipe);

	public void remove(Long id);

	public List<Recipe> getAll();

}
