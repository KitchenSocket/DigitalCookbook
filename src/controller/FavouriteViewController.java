package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;
import javax.swing.JOptionPane;
import DAO.RecipeDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.CookBook;
import model.Recipe;
import model.Step;
import test.RecipeTest;
import view.MainApp;

/**
 * Controller of Favourite View.
 * 
 * @ author Shi Wenbin, Gu Qiwen
 * 
 * @version 1.0
 */

public class FavouriteViewController extends MainPageController implements Initializable {

	public static Stack<Recipe> favouriteRecipes = new Stack<Recipe>();
	
	public  Recipe selectedRecipe;
	
    @FXML
    void onEnter(KeyEvent event) throws IOException {
    	
        if (event.getCode() == KeyCode.ENTER) {

        	searchBehaviour();
        	
          }
    	
    	
    }
	
    @FXML
    protected void servingNumMinus(ActionEvent event) {

    	int servingNumber = new Integer(servingNum.getText());
    	
    	if(servingNumber>0){
    		
        	servingNumber--;
        	
        	servingNum.setText(new Integer(servingNumber).toString());
        	
			cookingTime.setText(new Integer(selectedRecipe.getCookingTime() * servingNumber).toString());

			prepareTime.setText(new Integer(selectedRecipe.getPreparationTime() * servingNumber).toString());
    		
    	}
    	
    }

    @FXML
    protected void servingNumPlus(ActionEvent event) {
    	
    	int servingNumber = new Integer(servingNum.getText());
    	
    	if(servingNumber < 9){
    		
        	servingNumber++;
        	
        	servingNum.setText(new Integer(servingNumber).toString());
        	
			cookingTime.setText(new Integer(selectedRecipe.getCookingTime() * servingNumber).toString());

			prepareTime.setText(new Integer(selectedRecipe.getPreparationTime() * servingNumber).toString());
    		
    	} 

    }

	/**
	 * The behaviour after clicking delete button.
	 * 
	 * @ @param
	 * 
	 * @ return
	 */

	/**
	 * The behaviour after clicking add button.
	 * 
	 * @ @param
	 * 
	 * @ return
	 */

	/*
	 * search method
	 * 
	 * @param event search click event
	 * 
	 * @author Qiwen Gu
	 */
	@FXML
	public void search(ActionEvent event) throws IOException {

		String searchWord = new String(searchbar.getText());

		ArrayList<Recipe> recipes = null;

		if (searchWord.equals("")) {
			JOptionPane.showMessageDialog(null, "No entry word", null, JOptionPane.ERROR_MESSAGE);// Jpane
																									// alert

		} else {

			if (recipeNameRadioBtn.isSelected()) {

				System.out.println("searchByRecipeName");

				RecipeDAO recipeDAO = new RecipeDAO();

				ArrayList<Recipe> results = recipeDAO.getRecipeListByNameInFavorite(searchbar.getText());

				if (checkSearchResult(results)) {

					showRecipeList(results);

				}


			} else if (ingredientNameRadioBtn.isSelected()) {

				System.out.println("searchByIngredientName");
				RecipeDAO recipeDAO = new RecipeDAO();

				ArrayList<Recipe> results = recipeDAO.getRecipeListByIngredientNameInFavorite(searchbar.getText());

				if (checkSearchResult(results)) {

					showRecipeList(results);

				}
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		searchBtn.setGraphic(new ImageView(new Image(
				new File("src/resources/recipe_search_button.png").toURI().toString(), 15, 17, false, false)));

		addFavBtn.setGraphic(new ImageView(
				new Image(new File("src/resources/add_fav_recipe.png").toURI().toString(), 30, 32, false, false)));

		editRecipeBtn.setGraphic(
				new ImageView(new Image(new File("src/resources/edit.png").toURI().toString(), 30, 32, false, false)));

		deleteRecipeBtn.setGraphic(new ImageView(
				new Image(new File("src/resources/delete.png").toURI().toString(), 30, 32, false, false)));
		
		servingNumPlusBtn.setGraphic(new ImageView(
				new Image(new File("src/resources/plus.png").toURI().toString(), 10, 10, false, false)));
		
		servingNumMinusBtn.setGraphic(new ImageView(
				new Image(new File("src/resources/minus.png").toURI().toString(), 10, 2, false, false)));
		
		rightViewPartTwo.setDisable(true);
		
		rightViewPartOne.setDisable(true);

		try {

			ArrayList<Recipe> results = recipeDAO.getRecipeListByNameInFavorite("%");

			showRecipeList(results);

		} catch (IOException e1) {

			e1.printStackTrace();
		}

		addRecipeListListenner();

		initableValueType();

		recipeNameRadioBtn.setToggleGroup(group);// set the radio button into
		// group

		ingredientNameRadioBtn.setToggleGroup(group);

		recipeNameRadioBtn.setSelected(true);

		// ingredientList.setEditable(false);// user cannot edit textArea at
		// main
		// page
//		editRecipeBtn.setDisable(true);
//
//		addFavBtn.setDisable(true);
//
//		deleteRecipeBtn.setDisable(true);

	}

	/*
	 * A method to show searched recipes in the recipeList, for user to choose
	 * and click.
	 * 
	 * @param String searchName
	 * 
	 * @author Shi Wenbin
	 */
	@FXML
	public void showRecipeList(ArrayList<Recipe> results) throws IOException {

		for (int i = 0; i < results.size(); i++) {

			favouriteRecipes.push(results.get(i));

		}

		ObservableList<AnchorPane> recipeList = FXCollections.observableArrayList();

		recipeCopies.clear();

		LinkedList<AnchorPane> recipes = new LinkedList<>();

		while (!favouriteRecipes.isEmpty()) {

			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(MainApp.class.getResource("../view/BriefRecipeInFavouriteView.fxml"));

			AnchorPane eachRecipe = (AnchorPane) loader.load();

			recipes.add(eachRecipe);

		}

		recipeList.setAll(recipes);

		matchRecipeList.setItems(recipeList);

	}

	/*
	 * A listener method set to monitor the recipeList, if user click one of
	 * those recipes, this recipe's detailed info will be shown at the right
	 * side.
	 * 
	 * @param
	 * 
	 * @author Shi Wenbin
	 */
	@FXML
	public void addRecipeListListenner() {

		matchRecipeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnchorPane>() {
			@Override
			public void changed(ObservableValue<? extends AnchorPane> observable, AnchorPane oldValue,
					AnchorPane newValue) {
				
				rightViewPartTwo.setDisable(false);
				
				rightViewPartOne.setDisable(false);

				try {

					selectedRecipe = recipeCopies.get(matchRecipeList.getSelectionModel().getSelectedIndex());
					
					prepareTime.setText(new Integer(selectedRecipe.getPreparationTime()).toString());

					cookingTime.setText(new Integer(selectedRecipe.getCookingTime()).toString());

					showDetailedRecipe(selectedRecipe);

					editRecipeBtn.setDisable(false);// button active

					addFavBtn.setDisable(false);

					deleteRecipeBtn.setDisable(false);

					// showStepList(selectedRecipe);

				} catch (Exception e) {
					// System.out.println("ignored error:
					// ArrayIndexOutOfBoundsException.");
				}
			}
		});
	}

	@FXML
	public void addFavRecipe(ActionEvent event) {

		int favorite = JOptionPane.showConfirmDialog(null, "Remove this recipe from Favorite?", null,
				JOptionPane.YES_NO_OPTION);// Jpane check

		if (favorite == JOptionPane.YES_OPTION) {

			System.out.print(selectedRecipe.getName() + " remove favorite ");

			recipeDAO.removeFavorite(selectedRecipe.getId());

			selectedRecipe.setIsFavorite(0);

			try {

				ArrayList<Recipe> results = recipeDAO.getRecipeListByNameInFavorite("%");

				showRecipeList(results);

				showDetailedRecipe(new Recipe());
				
				editRecipeBtn.setDisable(true);

				addFavBtn.setDisable(true);

				deleteRecipeBtn.setDisable(true);

			} catch (IOException e) {

				e.printStackTrace();

			}
		}
	}
}