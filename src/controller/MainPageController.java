package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;

import com.sun.xml.internal.bind.v2.runtime.Name;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.CookBook;
import model.Ingredient;
import model.Recipe;
import model.Step;
import view.MainApp;

public class MainPageController implements Initializable  {
	
	public static Stack<Recipe> matchRecipes = new Stack<Recipe>();
	
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
    private ListView<String> stepList;
    
    @FXML
    private Button deleteRecipeBtn;
    
    @FXML
    private TableView<Ingredient> ingredientTable;
    
    private TableColumn<Ingredient, String> name = new TableColumn<>("Name");
    
    private TableColumn<Ingredient, Double> amount = new TableColumn<>("Amount");
    
    private TableColumn<Ingredient, String> unit = new TableColumn<>("Unit");

    @FXML
    void deleteRecipe(ActionEvent event) {

    }

    @FXML
    void addFavRecipe(ActionEvent event) {

    }

    @FXML
    void editRecipe(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) throws IOException {
    	
    	matchRecipes.push(CookBook.createHongShaoRou());
    	
    	matchRecipes.push(CookBook.createGongBaoJiding());	

    	showRecipeList();

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		searchBtn.setGraphic(new ImageView(new Image( new File("src/resources/recipe_search_button.png").toURI().toString(),  15, 17, false, false)));
    	
		editRecipeBtn.setGraphic(new ImageView(new Image( new File("src/resources/edit.png").toURI().toString(),  15, 17, false, false)));
    	
		deleteRecipeBtn.setGraphic(new ImageView(new Image( new File("src/resources/delete.png").toURI().toString(),  15, 17, false, false)));
    	
		addFavBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_fav_recipe.png").toURI().toString(),  15, 17, false, false)));
    	
		initableValueType();
		
		iniRecipeList();
		
		addRecipeListListenner();
    	
    	try {
			showRecipeList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		
	}
	
	private void initableValueType() {
		
		name.setMinWidth(196);

		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		amount.setMinWidth(83);
		
		amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		
		unit.setMinWidth(136);
		
		unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
		
	}

	private void iniRecipeList() {
		
    	matchRecipes.push(CookBook.createHongShaoRou());
    	
    	matchRecipes.push(CookBook.createGongBaoJiding());	
    	
    	matchRecipes.push(CookBook.createSuanLaFen());
    	
    	matchRecipes.push(CookBook.createHongShaoRou());
    	
    	matchRecipes.push(CookBook.createGongBaoJiding());	
    	
    	matchRecipes.push(CookBook.createSuanLaFen());
    	
	}
	
	private void showRecipeList() throws IOException {
		
		ObservableList<AnchorPane> recipeList =FXCollections.observableArrayList ();
		
		recipeCopies.clear();
		
		LinkedList<AnchorPane> recipes = new LinkedList<>();
		
		while(!matchRecipes.isEmpty()){
			
	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        
	        loader.setLocation(MainApp.class.getResource("../view/BriefRecipeInMainPage.fxml"));
	        
	        AnchorPane eachRecipe = (AnchorPane) loader.load();
			
	        recipes.add(eachRecipe);	        
	        
		}
		
		recipeList.setAll(recipes);
		
		matchRecipeList.setItems(recipeList);
				
	}
	
	private void addRecipeListListenner() {
		
		matchRecipeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnchorPane>() {
		    @Override
		    public void changed(ObservableValue<? extends AnchorPane> observable, AnchorPane oldValue, AnchorPane newValue) {
		        
		    	try{
		    		
		    		ingredientTable.getColumns().clear();
		    		
		    		Recipe selectedRecipe = recipeCopies.get(matchRecipeList.getSelectionModel().getSelectedIndex());
			    	
			    	recipeName.setText(selectedRecipe.getName());
			    	
			    	showStepList(selectedRecipe);
			    	
			    	ingredientTable.setItems(convertArrayListToOberservableList(selectedRecipe.getIngredients()));
		    		
			    	ingredientTable.getColumns().addAll(name, amount, unit);
			    	
		    	} catch (Exception e) {
		    		//System.out.println("ignored error: ArrayIndexOutOfBoundsException.");
				}
		    	
		    }

			private ObservableList<Ingredient> convertArrayListToOberservableList(ArrayList<Ingredient> ingredients) {
				
				int size = ingredients.size();
				
				ObservableList<Ingredient> reIngredients = FXCollections.observableArrayList();
				
				for (int i = 0; i<size; i++){
					
					reIngredients.add(ingredients.get(i));
					
				}
				
				return reIngredients;
			}
		});
		
	}

	private void showStepList(Recipe selectedRecipe) {
		
		ObservableList<String> recipeSteps =FXCollections.observableArrayList ();
		
		ArrayList<Step> steps = selectedRecipe.getSteps();
		
		for(int i = 0; i < steps.size(); i++){
			
			recipeSteps.add(steps.get(i).getStepDescription());
			
		}
			
		stepList.setItems(recipeSteps);
		
	}

}
