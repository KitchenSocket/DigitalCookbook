package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;

import javax.swing.JOptionPane;

import com.sun.scenario.effect.impl.prism.PrImage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.CookBook;
import model.DatabaseAccess;
import model.Recipe;
import model.Step;
import test.RecipeTest;
import view.MainApp;

/**
 * Controller of mainPage.
 * 
 * @ author Shi Wenbin, Gu Qiwen
 * 
 * @version 1.0
 * */

public class MainPageController implements Initializable {

	public static Stack<Recipe> matchRecipes = new Stack<Recipe>();

	public static ArrayList<Recipe> recipeCopies = new ArrayList<>();

	public static Recipe selectedRecipe;

	@FXML
	private TextField searchbar;

	@FXML
	private Button searchBtn;

	@FXML
	private ListView<AnchorPane> matchRecipeList;

	@FXML
	private RadioButton recipeNameRadioBtn;

	@FXML
	private RadioButton ingredientNameRadioBtn;

	@FXML
	private Label recipeName;

	@FXML
	private Button editRecipeBtn;

	@FXML
	private Button addFavBtn;


	@FXML
	private ListView<String> stepList;
	
    @FXML
    private Button deleteRecipeBtn;

    @FXML
    private TableView<?> ingredientTable;

    @FXML
    void deleteRecipe(ActionEvent event) {

    }
    
    @FXML
    void addFavRecipe(ActionEvent event) {

    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		searchBtn.setGraphic(new ImageView(new Image(
				new File("src/resources/recipe_search_button.png").toURI().toString(), 15, 17, false, false)));
		
		addFavBtn.setGraphic(new ImageView(new Image(
				new File("src/resources/add_fav_recipe.png").toURI().toString(), 15, 17, false, false)));
		
		editRecipeBtn.setGraphic(new ImageView(new Image(
				new File("src/resources/edit.png").toURI().toString(), 15, 17, false, false)));
		
		deleteRecipeBtn.setGraphic(new ImageView(new Image(
				new File("src/resources/delete.png").toURI().toString(), 15, 17, false, false)));

		try {
			showRecipeList("%");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		addRecipeListListenner();

		//ingredientList.setEditable(false);// user cannot edit textArea at main
											// page
		editRecipeBtn.setDisable(true);

		addFavBtn.setDisable(true);

	}


	/*
	 * A listener method set to monitor the recipeList, if user click one of those recipes, this recipe's detailed info will be shown at the right side. 
	 * 
	 * @param 
	 * 
	 * @author Shi Wenbin
	 */
	private void addRecipeListListenner() {

		matchRecipeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnchorPane>() {
			@Override
			public void changed(ObservableValue<? extends AnchorPane> observable, AnchorPane oldValue,
					AnchorPane newValue) {

				try {

					selectedRecipe = recipeCopies.get(matchRecipeList.getSelectionModel().getSelectedIndex());// get
																												// user
																												// clicked
																												// recipe

					recipeName.setText(selectedRecipe.getName());

					//ingredientList.setText(selectedRecipe.getIngredients().toString());

					editRecipeBtn.setDisable(false);// button active

					addFavBtn.setDisable(false);

					//showStepList(selectedRecipe);

				} catch (Exception e) {
					// System.out.println("ignored error:
					// ArrayIndexOutOfBoundsException.");
				}

			}
		});

	}
	
	/*
	 * A method to show searched recipes in the recipeList, for user to choose and click. 
	 * 
	 * @param String searchName
	 * 
	 * @author Shi Wenbin
	 */

	private void showRecipeList(String seacrchName) throws IOException {
		
		ArrayList<Recipe> results = RecipeTest.getRecipeList(seacrchName );
		
		
    	
    	for(int i = 0; i < results.size(); i++){
    		
        	matchRecipes.push(results.get(i));
    		
    	}

		ObservableList<AnchorPane> recipeList = FXCollections.observableArrayList();

		recipeCopies.clear();

		LinkedList<AnchorPane> recipes = new LinkedList<>();

		while (!matchRecipes.isEmpty()) {

			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(MainApp.class.getResource("../view/BriefRecipeInMainPage.fxml"));

			AnchorPane eachRecipe = (AnchorPane) loader.load();

			recipes.add(eachRecipe);

		}

		recipeList.setAll(recipes);

		matchRecipeList.setItems(recipeList);

	}

	/*
	 * add favorite recipe method(button color change need to be done)
	 * 
	 * @param event search click event
	 * 
	 * @author Qiwen Gu
	 */
//	@FXML
//	void addFavRecipe(ActionEvent event) {
//
//		//boolean isFav = selectedRecipe.isFavourite();
//
//		if (isFav == false) {
//
//			int favorite = JOptionPane.showConfirmDialog(null, "Add this recipe into Favorite?", null,
//					JOptionPane.YES_NO_OPTION);// Jpane check
//
//			if (favorite == JOptionPane.YES_OPTION) {
//
//				//selectedRecipe.addFavourite();
//				// DatabaseAccess.updateRecipe(selectedRecipe);//not complete
//				// method
//
//				System.out.print(selectedRecipe.getName() + " add favorite " + selectedRecipe.isFavourite());
//
//			}
//
//		} else {
//
//			int favorite = JOptionPane.showConfirmDialog(null, "Remove this recipe from Favorite?", null,
//					JOptionPane.YES_NO_OPTION);// Jpane check
//
//			if (favorite == JOptionPane.YES_OPTION) {
//
//				selectedRecipe.removeFavourite();// DatabaseAccess.updateRecipe(selectedRecipe);//not
//													// complete
//				// method
//
//				System.out.print(selectedRecipe.getName() + " remove favorite " + selectedRecipe.isFavourite());
//
//			}
//		}
//	}

	/*
	 * edit recipe method(need a new view to be done)
	 */
	@FXML
	void editRecipe(ActionEvent event) {

	}

	/*
	 * search method
	 * 
	 * @param event search click event
	 * 
	 * @author Qiwen Gu
	 */
	@FXML
	void search(ActionEvent event) throws IOException {

		String searchWord = new String(searchbar.getText());

		ArrayList<Recipe> recipes = null;

		if (searchWord.equals("")) {
			JOptionPane.showMessageDialog(null, "No entry word", null, JOptionPane.ERROR_MESSAGE);// Jpane alert

		} else {

			if (recipeNameRadioBtn.isSelected()) {

				System.out.println("searchByRecipeName");
				showRecipeList(searchbar.getText());
				// recipes =
				// DatabaseAccess.searchByIngredientName(searchWord);//I
				// have no Sijie's search method

			} else if (ingredientNameRadioBtn.isSelected()) {

				System.out.println("searchByIngredientName");
				// recipes = DatabaseAccess.searchByRecipeName(searchWord);//I
				// have
				// no Sijie's search method

			}

			/*
			 * for (int recipeNumber = 0; recipeNumber < recipes.size();
			 * recipeNumber++){ matchRecipes.push(recipes.get(recipeNumber)); }
			 */

//			matchRecipes.push(CookBook.createGongBaoJiding());
//
//			matchRecipes.push(CookBook.createHongShaoRou());
//
//			showRecipeList();

		}
	}
}
