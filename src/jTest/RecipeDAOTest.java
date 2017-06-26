package jTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.RecipeDAO;
import model.Recipe;

/**
 * JUnit test for RecipeDAO
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

		newRecipe.setId(50);

		newRecipe.setIsFavorite(1);

		newRecipe.setServingNum(7);

	}

	 @Test
	 public void testGetRecipeById() throws IOException {
	
			recipeDAOTestTarget.addRecipe(newRecipe);

			assertTrue(recipeDAOTestTarget
					
					.getRecipeById(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId())
					
					.getName().equals(newRecipe.getName()));

			recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());
			
	 }

	@Test
	public void testGetRecipeByIdInFavorite() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		assertTrue(recipeDAOTestTarget
				
				.getRecipeByIdInFavorite(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId())
				
				.getName().equals(newRecipe.getName()));

		recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());
		
	}

	@Test
	public void testGetRecipeListByName() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByName(newRecipe.getName());

		assertTrue(recipesT.size() > 0);

		recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());

	}

	@Test
	public void testGetRecipeListByNameInFavorite() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		assertTrue(recipeDAOTestTarget.getRecipeListByNameInFavorite(newRecipe.getName()).get(0).getBriefDescription()

				.equals(newRecipe.getBriefDescription()));

		recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());

	}

	// TODO
	@Test
	public void testGetRecipeListByIngredientName() throws IOException {

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByIngredientName("pork");

		assertNotNull(recipesT);

	}

	// TODO
	@Test
	public void testGetRecipeListByIngredientNameInFavorite() throws IOException {

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByIngredientNameInFavorite("pork");

		assertNotNull(recipesT);

	}

	@Test
	public void testAddRecipe() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		assertTrue(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getBriefDescription()

				.equals(newRecipe.getBriefDescription()));

		recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());

	}

	@Test
	public void testUpdateRecipe() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		newRecipe.setBriefDescription("edit");

		recipeDAOTestTarget.updateRecipe(newRecipe);

		assertTrue(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getBriefDescription()

				.equals("edit"));

		recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());

	}

	@Test
	public void testDeleteRecipe() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByName(newRecipe.getName());

		assertTrue(recipesT.size() == 0);

		// TODO assertNull(recipeT)

	}

	@Test
	public void testAddFavorite() throws IOException {

		newRecipe.setIsFavorite(0);

		recipeDAOTestTarget.addRecipe(newRecipe);

		recipeDAOTestTarget.addFavorite(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByNameInFavorite(newRecipe.getName());

		assertTrue(recipesT.size() != 0);

		recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());

		// TODO assertNull(recipeT)

	}

	@Test
	public void testRemoveFavorite() throws IOException {

		recipeDAOTestTarget.addRecipe(newRecipe);

		recipeDAOTestTarget.removeFavorite(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());

		ArrayList<Recipe> recipesT = recipeDAOTestTarget.getRecipeListByNameInFavorite(newRecipe.getName());

		assertTrue(recipesT.size() == 0);

		recipeDAOTestTarget.deleteRecipe(recipeDAOTestTarget.getRecipeListByName(newRecipe.getName()).get(0).getId());
	
	}
	
}
