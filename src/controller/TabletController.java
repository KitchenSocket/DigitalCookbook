package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    	
        FXMLLoader loader = new FXMLLoader();
    	
    	MainApp.tabletLayout.setRight((BorderPane)loader.load(getClass().getResource("../view/MainPage.fxml")));


    }

    @FXML
    void mainPageClick(ActionEvent event) throws IOException {
    	
        FXMLLoader loader = new FXMLLoader();
    	
    	MainApp.tabletLayout.setRight((BorderPane)loader.load(getClass().getResource("../view/MainPage.fxml")));

    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
     
        mainPageBtn.setGraphic(new ImageView(new Image( new File("src/resources/unclick_home.png").toURI().toString(),  40, 40, false, false)));
        
        addFavBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_fav_recipe.png").toURI().toString(),  40, 40, false, false)));
        
        addRecipeBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_recipe_btn.png").toURI().toString(),  40, 40, false, false)));
    	
        
    }
    

}

