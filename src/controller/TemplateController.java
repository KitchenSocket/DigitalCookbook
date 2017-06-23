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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Recipe;
import view.Template;

/*
 * A Tablet whose center area holds all other GUIs, like Main Page, add page, Favourite View.
 * 
 * @version 1.0
 * 
 * @author Shi Wenbin, Gang Shao
 */


public class TemplateController implements Initializable {

    @FXML
    private Button mainPageBtn;

    @FXML
    private Button favBtn;

    @FXML
    private Button addRecipeBtn;


    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        mainPageBtn.setGraphic(new ImageView(new Image( new File("src/resources/unclick_home.png").toURI().toString(),  31, 31, false, false)));
        
        favBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_fav_recipe.png").toURI().toString(),  31, 31, false, false)));
        
        addRecipeBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_recipe_btn.png").toURI().toString(),  31, 31, false, false)));

        threeBtnColorClear();
        
        mainPageBtn.setOnAction(event -> {
            try {
            	threeBtnColorClear();
            	mainPageBtn.setStyle("-fx-background-color: #FFFFFF;");
            	MainPageController.mainOrFavView = 1;
                loadContent("../view/MainOrFavView.fxml", "Main");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        favBtn.setOnAction(event -> {
            try {
            	threeBtnColorClear();
            	favBtn.setStyle("-fx-background-color: #FFFFFF;"); 
            	MainPageController.mainOrFavView = 2;
                loadContent("../view/MainOrFavView.fxml", "Main");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addRecipeBtn.setOnAction(event -> {
            try {
            	threeBtnColorClear();
            	addRecipeBtn.setStyle("-fx-background-color: #FFFFFF;"); 
                loadContent("../view/AddAndEditRecipeView.fxml", "Add");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    public void threeBtnColorClear() {
    	mainPageBtn.setStyle("-fx-background-color: Transparent;"); 
    	favBtn.setStyle("-fx-background-color: Transparent;"); 
    	addRecipeBtn.setStyle("-fx-background-color: Transparent;"); 
    }

    public void loadContent(Node node) throws IOException {
        Template.getRoot().getChildren().remove(1,1);
        Template.getRoot().getChildren().add(node);
    }

    static void loadContent(String location, String type) throws IOException {
        if(Template.getRoot().getChildren().size() == 2) {
            Template.getRoot().getChildren().remove(1);
        }

        FXMLLoader loader = new FXMLLoader(TemplateController.class.getResource(location));

        switch (type) {
            case "Main": {

                break;
            }

            case "Add": {
                AddAndEditViewController controller = new AddAndEditViewController();
                controller.setIsNew(true);
                loader.setController(controller);
                break;
            }

            case "Edit": {
                AddAndEditViewController controller = new AddAndEditViewController();
                controller.setIsNew(false);
                controller.setRecipe(MainPageController.selectedRecipe);
                loader.setController(controller);
                break;
            }

            case "Fav": {

                break;
            }
        }

        Node node = loader.load();
        GridPane.setColumnIndex(node, 1);
        GridPane.setRowIndex(node, 0);
        Template.getRoot().getChildren().add(node);
    }
}


