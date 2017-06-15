package inter;

import java.util.ArrayList;

import model.Ingredient;

public interface IngredientOperation {
	
	public Ingredient selectIngredientByID(int id);
	
	public ArrayList<Ingredient> selectIngredients(String name);
	
	public ArrayList<Ingredient> selectIngredientsByRecipeId(int recipeId);
	
	public void addIngredient(Ingredient ingredient);
	
	public void updateIngredient(Ingredient ingredient);
	
	public void deleteIngredient(int id);

}
