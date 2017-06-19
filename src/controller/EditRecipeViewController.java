package controller;

import java.io.IOException;
import java.util.ArrayList;

import DAO.IngredientDAO;
import DAO.RecipeDAO;
import DAO.StepDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Ingredient;
import model.Recipe;
import model.Step;

import javax.swing.*;

/**
 * Created by fexac on 18-Jun-17.
 */
public class EditRecipeViewController extends AddRecipeViewController {

	// private ObservableList<Ingredient> ingredients =
	// FXCollections.observableArrayList(new Ingredient("original", 0.2,
	// "g"),new Ingredient("original1", 0.1, "g"), new Ingredient("original2",
	// 0.3, "g"));

	// private ObservableList<Step> steps =
	// FXCollections.observableArrayList(new Step(1,"Do What?"), new
	// Step(2,"Just do what?"), new Step(3, "Please dooo what??"));

	private int selectedRecipeId;

	private ObservableList<Step> steps = FXCollections.observableArrayList();

	private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		initFlds(MainPageController.selectedRecipe);

		initBtns();
	}

	/**
	 * initialize all fields with givien Recipe
	 */
	private void initFlds(Recipe recipe) {
		Recipe selectedRecipe = recipe;

		selectedRecipeId = selectedRecipe.getId();
		String servingNumber = String.valueOf(selectedRecipe.getServingNum());
		String preparationTime = String.valueOf(selectedRecipe.getPreparationTime());
		String cookingTime = String.valueOf(selectedRecipe.getCookingTime());
		ArrayList<Step> stepList = myStepDAO.getStepListByRecipyId(selectedRecipeId);
		ArrayList<Ingredient> ingredientList = myIngredientDAO.getIngredientListByRecipyId(selectedRecipeId);

		titleFld.setText(selectedRecipe.getName());
		servingsFld.setText(servingNumber);
		preparationTimeFld.setText(preparationTime);
		cookingTimeFld.setText(cookingTime);

		ingredients.addAll(ingredientList);
		steps.addAll(stepList);

		initStepsTV(steps);
		initIngredientsTV(ingredients);
	}

	@Override
	protected void saveRecipe() {
		if (!isValid()) {
			return;
		}

		// TODO implement save recipe
		int save = JOptionPane.showConfirmDialog(null, "Do you want to save this recipe?", null,
				JOptionPane.YES_NO_OPTION);

		if (JOptionPane.YES_OPTION == save) {

			Recipe newRecipe = new Recipe();
			
			newRecipe.setId(MainPageController.selectedRecipe.getId()); 

			newRecipe.setName(titleFld.getText());

			int servingNum = Integer.parseInt(servingsFld.getText());

			newRecipe.setServingNum(servingNum);

			int preparationTime = Integer.parseInt(preparationTimeFld.getText());

			newRecipe.setPreparationTime(preparationTime);

			int cookingTime = Integer.parseInt(cookingTimeFld.getText());

			newRecipe.setCookingTime(cookingTime);

			recipeDAO.updateRecipe(newRecipe);

			for(Step step: steps) {
				step.setRecipeId(newRecipe.getId());
				System.out.println(newRecipe.getId());
				myStepDAO.updateStep(step);
			}
			for(Ingredient ingredient: ingredients) {
				ingredient.setRecipeId(newRecipe.getId());
				System.out.println(newRecipe.getId());
				myIngredientDAO.updateIngredient(ingredient);
			}
		}
	}

	public EditRecipeViewController() {

	}

//	public void showIngredientTable(int recipeId) throws IOException {
//
//		ingredientsTV.getColumns().clear();
//
//		ingredientsTV
//				.setItems(convertArrayListToOberservableList(myIngredientDAO.getIngredientListByRecipyId(recipeId)));
//
//		ingredientsTV.getColumns().addAll(ingredientNameCol, ingredientQuantityCol, ingredientUnitCol);
//
//	}

//	public ObservableList<Ingredient> convertArrayListToOberservableList(ArrayList<Ingredient> ingredients) {
//
//		int size = ingredients.size();
//
//		ObservableList<Ingredient> reIngredients = FXCollections.observableArrayList();
//
//		for (int i = 0; i < size; i++) {
//
//			reIngredients.add(ingredients.get(i));
//
//		}
//
//		return reIngredients;
//
//	}
	

}
