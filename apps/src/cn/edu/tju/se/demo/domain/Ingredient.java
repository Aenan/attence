package cn.edu.tju.se.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.edu.tju.se.base.domain.BaseDomain;

@Entity
@Table(name="ingredient")

public class Ingredient extends BaseDomain{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	protected Long id;
	protected Long recipeId;
	protected Long amount;
	protected String amountUnits;
	protected String ingredientName;
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getAmountUnits() {
		return amountUnits;
	}
	public void setAmountUnits(String amountUnits) {
		this.amountUnits = amountUnits;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
	
}
