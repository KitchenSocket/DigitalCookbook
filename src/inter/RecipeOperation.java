package inter;

import java.util.ArrayList;

import model.Recipe;

public interface RecipeOperation {
	
	public Recipe selectRecipeByID(int id);
	
	public Recipe selectRecipeByIDInFavorite(int id);
	
	public ArrayList<Recipe> selectRecipes(String name);
	
	public ArrayList<Recipe> selectRecipesInFavorite(String name);
	
	public void addRecipe(Recipe recipe);
	
	public void updateRecipe(Recipe recipe);
	
	public void deleteRecipe(int id);

	public void addFavorite(int id);
	
	public void removeFavorite(int id);

}
