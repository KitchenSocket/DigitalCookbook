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

		newIngredient = new Ingredient();
		newIngredient.setName("Junit test");
		newIngredient.setQuantity(1);
		newIngredient.setUnit("test");
		newIngredient.setRecipeId(1);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setName("green onion");
		ingredient1.setQuantity(100);
		ingredient1.setUnit("g");
		ingredient1.setRecipeId(1);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setName("pork");
		ingredient2.setQuantity(500);
		ingredient2.setUnit("g");
		ingredient2.setRecipeId(1);

		newIngredients = new ArrayList<Ingredient>();
		newIngredients.add(ingredient1);
		newIngredients.add(ingredient2);
		newIngredients.add(newIngredient);

	}

	// @Test TODO
	// public void testGetSession() {
	// fail("Not yet implemented");
	// }

	@Test
	public void testGetIngredientListByName() throws IOException {

		ingredientDAOTestTarget.addIngredient(newIngredient);

		assertTrue(ingredientDAOTestTarget.getIngredientListByName(newIngredient.getName()).size() > 0);

	}

	@Test
	public void testGetIngredientListByRecipyId() throws IOException {

		assertTrue(ingredientDAOTestTarget.getIngredientListByRecipyId(newIngredient.getRecipeId()).size() > 0);
		
	}

	@Test
	public void testAddIngredient() {

		ingredientDAOTestTarget.addIngredient(newIngredient);

		assertTrue(ingredientDAOTestTarget.getIngredientListByRecipyId(newIngredient.getRecipeId()).size() > 0);

	}

//	// @Test TODO keep or not?
//	public void testUpdateIngredient() {
//	
//	 ingredientDAOTestTarget.addIngredient(newIngredient);
//	
//	 newIngredient.setName(newIngredient.getName() + " edit");
//	
//	 ingredientDAOTestTarget.up
//	
//	
//	 }

	@Test // TODO no problem? name == name???
	public void testUpdateIngredients() throws IOException {

		newIngredient.setName(newIngredient.getName() + " edit");

		newIngredients.set(2, newIngredient);

		ingredientDAOTestTarget.updateIngredients(newIngredients);

		assertTrue(ingredientDAOTestTarget.getIngredientListByName(newIngredient.getName()).get(0).getName()

				.equals(newIngredient.getName()));

	}

	@Test
	public void testDeleteIngredientListByRecipeId() {

		ingredientDAOTestTarget.deleteIngredientListByRecipeId(newIngredient.getRecipeId());

		assertFalse(isExist(1));

	}
	
	@After
	public void recover() {

		newIngredients.remove(newIngredient);

		ingredientDAOTestTarget.updateIngredients(newIngredients);


	}

	public boolean isExist(int recipeId) {

		ArrayList<Ingredient> ingreT = ingredientDAOTestTarget.getIngredientListByRecipyId(recipeId);

		return ingreT.size() > 0 ? true : false;

	}

}
