package jTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.regexp.internal.recompile;

import DAO.RecipeDAO;

public class RecipeDAOTest {
	
	private RecipeDAO recipeDAOTestTarget;

	@Before
	public void setUp() throws Exception {
		
		recipeDAOTestTarget = new RecipeDAO();
		
	}

	@Test
	public void testGetRecipeById() throws IOException {
		
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
		
	}

	@Test
	public void testGetRecipeByIdInFavorite() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testGetRecipeListByName() throws IOException {

		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testGetRecipeListByNameInFavorite() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testGetRecipeListByIngredientName() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testGetRecipeListByIngredientNameInFavorite() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testAddRecipe() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testUpdateRecipe() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testDeleteRecipe() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testAddFavorite() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

	@Test
	public void testRemoveFavorite() throws IOException {
		assertTrue(recipeDAOTestTarget.getRecipeListByName("Hong").get(0).getServingNum() == 4);;
	}

}
