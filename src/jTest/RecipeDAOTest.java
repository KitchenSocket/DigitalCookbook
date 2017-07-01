package jTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import DAO.IngredientDAO;
import DAO.RecipeDAO;
import model.Ingredient;
import model.Recipe;

/**
 * JUnit test for RecipeDAO
 * 
 * @author CHANDIM
 *
 */
public class RecipeDAOTest {

	private RecipeDAO recipeDAOTestTarget;

	private Recipe newRecipe;

	@Before
	public void setUp() throws Exception { 

		recipeDAOTestTarget = new RecipeDAO();

		newRecipe = new Recipe();

		newRecipe.setName("Junit test");

		newRecipe.setBriefDescription("Just for Junit test");
		
		newRecipe.setIsFavorite(1);

		newRecipe.setServingNum(7);

	}

	@Test
	public void testGetRecipeById() throws IOException {
	
		recipeDAOTestTarget.addRecipe(newRecipe);
		
		Recipe realRecipe = recipeDAOTestTarget.getRecipeById(newRecipe.getId());
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(newRecipe.getIsFavorite(), realRecipe.getIsFavorite());

		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());	
	}

	@Test
	public void testGetRecipeByIdInFavorite() throws IOException {
		
		recipeDAOTestTarget.addRecipe(newRecipe);
		
		Recipe realRecipe = recipeDAOTestTarget.getRecipeByIdInFavorite(newRecipe.getId());
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(newRecipe.getIsFavorite(), realRecipe.getIsFavorite());

		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());	
		
	}

	@Test
	public void testGetRecipeListByName() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByName(newRecipe.getName());
		
		Recipe realRecipe = recipesT.get(recipesT.size()-1);

		assertNotNull(recipesT);
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(newRecipe.getIsFavorite(), realRecipe.getIsFavorite());

		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());

	}

	@Test
	public void testGetRecipeListByNameInFavorite() throws IOException {
		
		recipeDAOTestTarget.addRecipe(newRecipe);

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByNameInFavorite(newRecipe.getName());
		
		Recipe realRecipe = recipesT.get(recipesT.size()-1);

		assertNotNull(recipesT);
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(newRecipe.getIsFavorite(), realRecipe.getIsFavorite());

		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());

	}

	@Test
	public void testGetRecipeListByIngredientName() throws IOException {

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByIngredientName("pork");

		assertNotNull(recipesT);
		
		IngredientDAO ingredientDAO = new IngredientDAO();
		
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		int count = 0;
		
		boolean isContain = false;
		
		for(Recipe realRecipe: recipesT) {
			
			ingredients = ingredientDAO.getIngredientListByRecipyId(realRecipe.getId());
			
			for(Ingredient ingredient: ingredients) {

				if(ingredient.getName().equals("pork")) {
					
					count = 1;
					
					isContain = true;
					
				}
				
			}			
			
			assertTrue(isContain);
			
			isContain = false;
		}
		
		assertTrue(count > 0);

	}

	@Test
	public void testGetRecipeListByIngredientNameInFavorite() throws IOException {

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByIngredientNameInFavorite("pork");

		assertNotNull(recipesT);
		
		IngredientDAO ingredientDAO = new IngredientDAO();
		
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		int count = 0;
		
		boolean isContain = false;
		
		for(Recipe realRecipe: recipesT) {
			
			ingredients = ingredientDAO.getIngredientListByRecipyId(realRecipe.getId());
			
			for(Ingredient ingredient: ingredients) {

				if(ingredient.getName().equals("pork")) {
					
					count = 1;
					
					isContain = true;
					
				}
				
			}			
			
			assertTrue(isContain);
			
			isContain = false;
		}
		
		assertTrue(count > 0);

	}

	@Test
	public void testAddRecipe() throws IOException {
		
		recipeDAOTestTarget.addRecipe(newRecipe);
		
		Recipe realRecipe = recipeDAOTestTarget.getRecipeById(newRecipe.getId());
		
		assertNotNull(realRecipe);
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(newRecipe.getIsFavorite(), realRecipe.getIsFavorite());

		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());

	}

	@Test
	public void testUpdateRecipe() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		newRecipe.setBriefDescription("edit");
		
		Recipe realRecipe = recipeDAOTestTarget.getRecipeById(newRecipe.getId());
		
		assertNotEquals(newRecipe.getBriefDescription(),recipeDAOTestTarget.getRecipeById(newRecipe.getId()).getBriefDescription());

		recipeDAOTestTarget.updateRecipe(newRecipe);

		realRecipe = recipeDAOTestTarget.getRecipeById(newRecipe.getId());
		
		assertNotNull(realRecipe);
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(newRecipe.getIsFavorite(), realRecipe.getIsFavorite());

		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());

	}

	@Test
	public void testDeleteRecipe() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);
		
		Recipe realRecipe = recipeDAOTestTarget.getRecipeById(newRecipe.getId());
		
		assertNotNull(realRecipe);
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(newRecipe.getIsFavorite(), realRecipe.getIsFavorite());

		assertNotNull(realRecipe);
		
		//delete the recipe
		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());

		realRecipe = recipeDAOTestTarget.getRecipeById(newRecipe.getId());

		assertNull(realRecipe);

	}

	@Test
	public void testAddFavorite() throws IOException {

		newRecipe.setIsFavorite(0);

		recipeDAOTestTarget.addRecipe(newRecipe);
		
		Recipe realRecipe = recipeDAOTestTarget.getRecipeByIdInFavorite(newRecipe.getId());
		
		assertNull(realRecipe);

		recipeDAOTestTarget.addFavorite(newRecipe.getId());
		
		realRecipe = recipeDAOTestTarget.getRecipeByIdInFavorite(newRecipe.getId());

		assertNotNull(realRecipe);
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(1, realRecipe.getIsFavorite());
	
		//delete the recipe
		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());

	}

	@Test
	public void testRemoveFavorite() throws IOException {
	
		recipeDAOTestTarget.addRecipe(newRecipe);
		
		Recipe realRecipe = recipeDAOTestTarget.getRecipeByIdInFavorite(newRecipe.getId());
		
		assertNotNull(realRecipe);
		
		assertEquals(newRecipe.getBriefDescription(), realRecipe.getBriefDescription());

		assertEquals(newRecipe.getServingNum(), realRecipe.getServingNum());

		assertEquals(newRecipe.getThumbnail(), realRecipe.getThumbnail());
		
		assertEquals(newRecipe.getCookingTime(), realRecipe.getCookingTime());
		
		assertEquals(newRecipe.getPreparationTime(), realRecipe.getPreparationTime());
		
		assertEquals(newRecipe.getDescription(), realRecipe.getDescription());
		
		assertEquals(1, realRecipe.getIsFavorite());
		
		recipeDAOTestTarget.removeFavorite(newRecipe.getId());
		
		realRecipe = recipeDAOTestTarget.getRecipeByIdInFavorite(newRecipe.getId());
		
		assertNull(realRecipe);
		
		assertEquals(0, recipeDAOTestTarget.getRecipeById(newRecipe.getId()).getIsFavorite());
		 
		//delete the recipe
		recipeDAOTestTarget.deleteRecipe(newRecipe.getId());

	}

}
