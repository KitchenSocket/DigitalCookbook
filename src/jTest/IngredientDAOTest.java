package jTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.IngredientDAO;
import model.Ingredient;

/**
 * JUnit test for IngredientDAO
 * 
 * @author CHANDIM
 *
 */
public class IngredientDAOTest {

	private IngredientDAO ingredientDAOTestTarget;

	private Ingredient newIngredient;

	private ArrayList<Ingredient> newIngredients;

	@Before
	public void setUp() throws Exception {

		ingredientDAOTestTarget = new IngredientDAO();

		newIngredients = new ArrayList<Ingredient>();

		newIngredient = new Ingredient();
		newIngredient.setName("a Junit test");
		newIngredient.setQuantity(2);
		newIngredient.setUnit("test");
		newIngredient.setRecipeId(2);

		newIngredients = ingredientDAOTestTarget.getIngredientListByRecipyId(2);

	}

//	@Test
//	public void testGetIngredientListByName() throws IOException {
//
//		ingredientDAOTestTarget.addIngredient(newIngredient);
//
//		assertTrue(ingredientDAOTestTarget.getIngredientListByName(newIngredient.getName()).size() > 0);
//
//	}

	@Test
	public void testGetIngredientListByRecipyId() throws IOException {

		assertTrue(ingredientDAOTestTarget.getIngredientListByRecipyId(newIngredient.getRecipeId()).size() > 0);

	}

	@Test
	public void testAddIngredient() throws IOException {

		ingredientDAOTestTarget.addIngredient(newIngredient);

		assertTrue(ingredientDAOTestTarget.getIngredientListByRecipyId(newIngredient.getRecipeId()).size() > 0);

	}

	@Test
	public void testUpdateIngredients() throws IOException {

		newIngredients.removeAll(newIngredients);

		newIngredients.add(newIngredient);

		ingredientDAOTestTarget.updateIngredients(newIngredients);

		assertTrue(ingredientDAOTestTarget.getIngredientListByRecipyId(newIngredient.getRecipeId()).toString()

				.equals(newIngredients.toString()));

	}

	@Test
	public void testDeleteIngredientListByRecipeId() throws IOException {

		ingredientDAOTestTarget.deleteIngredientListByRecipeId(newIngredient.getRecipeId());

		assertFalse(isExist(1));

	}

	public boolean isExist(int recipeId) {

		ArrayList<Ingredient> ingreT = ingredientDAOTestTarget.getIngredientListByRecipyId(recipeId);

		return ingreT.size() > 0 ? true : false;

	}
	
	@After
	public void recover() throws IOException {

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setName("chicken");
		ingredient1.setQuantity(500);
		ingredient1.setUnit("g");
		ingredient1.setRecipeId(2);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setName("peanut");
		ingredient2.setQuantity(200);
		ingredient2.setUnit("g");
		ingredient2.setRecipeId(2);

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setName("soya sause");
		ingredient3.setQuantity(20);
		ingredient3.setUnit("ml");
		ingredient3.setRecipeId(2);

		newIngredients = new ArrayList<Ingredient>();
		newIngredients.add(ingredient1);
		newIngredients.add(ingredient2);
		newIngredients.add(ingredient3);

		ingredientDAOTestTarget.updateIngredients(newIngredients);

	}
	
}
