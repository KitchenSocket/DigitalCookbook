package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Recipe;

public class BriefRecipeInFavouriteViewController implements Initializable {

    @FXML
    private ImageView recipeImg;

    @FXML
    private Label recipeName;

    @FXML
    private Label recipeDescription;
    
    private Recipe eachBriefRecipe;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		eachBriefRecipe = FavouriteViewController.favouriteRecipes.pop();
		
		FavouriteViewController.recipeCopies.add(eachBriefRecipe);

		recipeName.setText(eachBriefRecipe.getName());
		
		recipeDescription.setText(eachBriefRecipe.getBriefDescription());
		
	}

}