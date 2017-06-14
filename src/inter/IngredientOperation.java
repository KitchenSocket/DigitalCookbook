package inter;

import java.util.List;

import model.Ingredient;

public interface IngredientOperation {
	
	public Ingredient selectIngredientByID(int id);
	
	public List<Ingredient> selectIngredients(String name);
	
	public List<Ingredient> selectIngredientsByRecipeId(int recipeId);
	
	public void addIngredient(Ingredient ingredient);
	
	public void updateIngredient(Ingredient ingredient);
	
	public void deleteIngredient(int id);

}
