package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.naming.spi.InitialContextFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainPageController implements Initializable  {

    @FXML
    private TextField searchbar;

    @FXML
    private Button searchBtn;

    @FXML
    private ListView<?> matchRecipeList;

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
    private ListView<?> stepList;

    @FXML
    void addFavRecipe(ActionEvent event) {

    }

    @FXML
    void editRecipe(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		searchBtn.setGraphic(new ImageView(new Image( new File("src/resources/recipe_search_button.png").toURI().toString(),  15, 17, false, false)));
		
		iniStepList();
		
	}
	
	private void iniStepList() {
		
		ObservableList<?> items =FXCollections.observableArrayList (
			    "Step1", "Step2", "Step3", "Step4");
		
		ArrayList<String> steps = new ArrayList<>();
		
		steps.add("step1");
			
		//stepList.setItems((ObservableList<?>) items);
		
	}

}
