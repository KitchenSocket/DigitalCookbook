package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import view.MainApp;

/*
 * A Tablet whose center area holds all other GUIs, like Main Page, add page, Favourite View.
 * 
 * @version 1.0
 * 
 * @author Shi Wenbin
 */


public class TabletController implements Initializable {

    @FXML
    private Button mainPageBtn;

    @FXML
    private Button addFavBtn;

    @FXML
    private Button addRecipeBtn;

    @FXML
    void addViewClick(ActionEvent event) {

    }

    @FXML
    void favViewClick(ActionEvent event) throws IOException {
    	
    	MainApp.tabletLayout.setCenter(null);
    	    	
    	MainApp.tabletLayout.setCenter((BorderPane)FXMLLoader.load(getClass().getResource("../view/FavouriteView.fxml")));

    }

    @FXML
    void mainPageClick(ActionEvent event) throws IOException {
    	
    	MainApp.tabletLayout.setCenter(null);
    	
    	MainApp.tabletLayout.setCenter((BorderPane)FXMLLoader.load(getClass().getResource("../view/MainPage.fxml")));

    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
     
        mainPageBtn.setGraphic(new ImageView(new Image( new File("src/resources/unclick_home.png").toURI().toString(),  31, 31, false, false)));
        
        addFavBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_fav_recipe.png").toURI().toString(),  31, 31, false, false)));
        
        addRecipeBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_recipe_btn.png").toURI().toString(),  31, 31, false, false)));
    	
        
    }
    

}

