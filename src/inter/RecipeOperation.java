package inter;

import java.util.List;

import model.Recipe;

public interface RecipeOperation {
	
	public Recipe selectRecipeByID(int id);
	
	public List<Recipe> selectRecipes(String name);
	
	public void addRecipe(Recipe recipe);
	
	public void updateRecipe(Recipe recipe);
	
	public void deleteRecipe(int id);

}
