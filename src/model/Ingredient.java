package model;

import java.io.Serializable;

public class Ingredient implements Serializable {
	
	private int recipeId;
	
	private String name;
	
	private double quantity;
	
	private String unit;
		
	public Ingredient() {
		
		//
		
	}
	
	public int getRecipeId() {
		return this.recipeId;
	}
	
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public double getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return "Ingredient" + '\n' + "====================" + '\n' + 
				"recipeId: " + recipeId + '\n' + "name: " + name + '\n' + 
				"quantity: " + quantity + '\n' + 
				"unit: " + unit + '\n' + "====================" + '\n';
	}
	

}
