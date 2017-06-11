package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.CookBook;
import model.Recipe;
import model.Step;
import view.MainApp;

public class FavouriteViewController implements Initializable {

	public static Stack<Recipe> favouriteRecipes = new Stack<Recipe>();

	public static ArrayList<Recipe> recipeCopies = new ArrayList<>();

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
	private TextArea ingredientList;

	@FXML
	private ListView<String> stepList;

	@FXML
	void addFavRecipe(ActionEvent event) {

	}

	@FXML
	void editRecipe(ActionEvent event) {

	}

	@FXML
	void search(ActionEvent event) throws IOException {

		favouriteRecipes.push(CookBook.createHongShaoRou());

		favouriteRecipes.push(CookBook.createGongBaoJiding());

		favouriteRecipes.push(CookBook.createGongBaoJiding());

		showRecipeList();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		searchBtn.setGraphic(new ImageView(new Image(
				new File("src/resources/recipe_search_button.png").toURI().toString(), 15, 17, false, false)));

		iniRecipeList();

		addRecipeListListenner();

		try {
			showRecipeList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ingredientList.setEditable(false);// user cannot edit textArea at main
		// page
		editRecipeBtn.setDisable(true);

		addFavBtn.setDisable(true);

	}

	private void iniRecipeList() {

		favouriteRecipes.push(CookBook.createGongBaoJiding());

		favouriteRecipes.push(CookBook.createSuanLaFen());

		favouriteRecipes.push(CookBook.createHongShaoRou());

		favouriteRecipes.push(CookBook.createGongBaoJiding());

		favouriteRecipes.push(CookBook.createSuanLaFen());

		favouriteRecipes.push(CookBook.createHongShaoRou());

	}

	private void showRecipeList() throws IOException {

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

	private void addRecipeListListenner() {

		matchRecipeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnchorPane>() {
			@Override
			public void changed(ObservableValue<? extends AnchorPane> observable, AnchorPane oldValue,
					AnchorPane newValue) {

				try {

					Recipe selectedRecipe = recipeCopies.get(matchRecipeList.getSelectionModel().getSelectedIndex());

					recipeName.setText(selectedRecipe.getName());

					ingredientList.setText(selectedRecipe.getIngredients().toString());

					editRecipeBtn.setDisable(false);// button active

					addFavBtn.setDisable(false);

					showStepList(selectedRecipe);

				} catch (Exception e) {
					// System.out.println("ignored error:
					// ArrayIndexOutOfBoundsException.");
				}

			}
		});

	}

	private void showStepList(Recipe selectedRecipe) {

		ObservableList<String> recipeSteps = FXCollections.observableArrayList();

		ArrayList<Step> steps = selectedRecipe.getSteps();

		for (int i = 0; i < steps.size(); i++) {

			recipeSteps.add(steps.get(i).getStepDescription());

		}

		stepList.setItems(recipeSteps);

	}

}
