package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Recipe;

/*
 * One Anchor in a listview, this class put each recipes' info to this anchor. And all the anchorView makes up the brief info list in the Favourite View.
 * Waiting for user to click.
 * 
 * @version 1.0
 * 
 * @author Shi Wenbin
 */

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
		
		try{
			eachBriefRecipe = FavouriteViewController.favouriteRecipes.pop();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		FavouriteViewController.recipeCopies.add(eachBriefRecipe);

		recipeName.setText(eachBriefRecipe.getName());
		
		recipeDescription.setText(eachBriefRecipe.getBriefDescription());
		
		recipeImg.setImage(new Image( new File(eachBriefRecipe.getThumbnail()).toURI().toString(),  80, 80, false, false));
		
	}

}