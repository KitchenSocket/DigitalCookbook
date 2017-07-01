package inter;

import java.util.ArrayList;

import model.Ingredient;

public interface IngredientOperation {
	
	public ArrayList<Ingredient> selectIngredients(String name);
	
	public ArrayList<Ingredient> selectIngredientsByRecipeId(int recipeId);
	
	public void addIngredient(Ingredient ingredient);
	
	public void deleteIngredientListByRecipeId(int recipeId);

}
