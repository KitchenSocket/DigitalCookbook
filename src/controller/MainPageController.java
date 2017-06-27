package controller;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;
import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

import DAO.BookDAO;
import DAO.IngredientDAO;
import DAO.RecipeDAO;
import DAO.StepDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Ingredient;
import model.Recipe;
import model.Step;
import test.RecipeTest;
import view.Template;

/**
 * Controller of mainPage.
 * 
 * @ author Shi Wenbin, Gu Qiwen
 * 
 * @version 1.0
 */

public class MainPageController extends TemplateController implements Initializable {
	
	private int mainOrFavView = 0;

	private VBox rightGrid; 
	
    private VBox tutor = new VBox();

	public ArrayList<Recipe> recipeSearchResultsTVatLeft = new ArrayList<>();

	public static Recipe selectedRecipe;

	Label label = new Label("last");

	final ToggleGroup group = new ToggleGroup();
	
	private int enterTime = 0;
	
    @FXML
    private GridPane grid;
	
    @FXML
    protected ImageView recipeImg;

    @FXML
    protected Label descriptionLabel;
	
    @FXML Label cookingTime;

    @FXML
    protected VBox rightView;
    
    @FXML Label prepareTime;

    @FXML
    protected Button servingNumPlusBtn;

    @FXML
    protected Button servingNumMinusBtn;

    @FXML
    protected TextField servingNum;


	@FXML
	protected TextField searchbar;

	@FXML
	protected Button searchBtn;

	@FXML
	protected ListView<AnchorPane> matchRecipeList;

	@FXML
	protected RadioButton recipeNameRadioBtn;

	@FXML
	protected RadioButton ingredientNameRadioBtn;

	@FXML
	protected Label recipeName;

	@FXML
	protected Button editRecipeBtn;

	@FXML
	protected Button addFavBtn;

	@FXML
	protected ListView<String> stepList;

	@FXML
	protected Button deleteRecipeBtn;
	
    @FXML
    private Button exportPdfBtn;
	

    @FXML
    void exportPdf(ActionEvent event) throws DocumentException, IOException {

		BookDAO onePage = new BookDAO();

		onePage.creatFile();
		
		System.out.println("Export PDF Successfully");
    	
    }

	@FXML
	protected TableView<Ingredient> ingredientTable;

	protected RecipeDAO recipeDAO = new RecipeDAO();

	protected IngredientDAO myIngredientDAO = new IngredientDAO();

	protected StepDAO myStepDAO = new StepDAO();

	protected TableColumn<Ingredient, String> name = new TableColumn<>("Name");

	protected TableColumn<Ingredient, Double> quantity = new TableColumn<>("Quantity");

	protected TableColumn<Ingredient, String> unit = new TableColumn<>("Unit");
	
	 protected ArrayList<Ingredient> ingredients= new ArrayList<>(); 
	
	/*
	 * A listener method , when enter key clicked, do the search method.
	 * 
	 * @param
	 * 
	 * @author Shi Wenbin
	 */

	
    @FXML
    void onEnter(KeyEvent event) throws IOException {
    	
        if (event.getCode() == KeyCode.ENTER) {

        	searchBehaviour();
        	
          }
    	
    	
    }
    
	/*
	 * A listener method when minus serving Number button is clecked, recaculate the preparation time.
	 * 
	 * @param
	 * 
	 * @author Shi Wenbin
	 */

    @FXML
    protected void servingNumMinus(ActionEvent event) throws IOException {

     	int servingNumber = new Integer(servingNum.getText());

    	
    	if(servingNumber>0){
    		
        	servingNumber--;
        	
        	int iniServing = selectedRecipe.getServingNum();
        	float multi =(float)( servingNumber)/iniServing;
        	
        	servingNum.setText(new Integer(servingNumber).toString());
        	
			cookingTime.setText(new Integer((int) (selectedRecipe.getCookingTime() * multi)).toString());

			prepareTime.setText(new Integer((int) (selectedRecipe.getPreparationTime() * multi)).toString());
			
			showIngredientTable(selectedRecipe.getId(), multi);
    		
    	}
    	
    }
    
	/*
	 * A listener method when plus serving Number button is clecked, recaculate the preparation time.
	 * 
	 * @param
	 * 
	 * @author Shi Wenbin
	 */

    @FXML
    protected void servingNumPlus(ActionEvent event) throws IOException {
    	
    	int servingNumber = new Integer(servingNum.getText());

    	
    	if(servingNumber < 9){
    		
        	servingNumber++;
        	
        	int iniServing = selectedRecipe.getServingNum();
        	float multi =(float)( servingNumber)/iniServing;
        	
        	
        	servingNum.setText(new Integer(servingNumber).toString());
        	
			cookingTime.setText(new Integer((int) (selectedRecipe.getCookingTime() * multi)).toString());

			prepareTime.setText(new Integer((int) (selectedRecipe.getPreparationTime() * multi)).toString());
			
			showIngredientTable(selectedRecipe.getId(), multi);
    		
    	} 

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

	    
	    showTutorView();
	    

		
		if(mainOrFavView == 1){
			
			initialMainPage();
			
		} else if(mainOrFavView == 2){
			
			initialFavView();
			
		}
		






	}
	
	private void showTutorView()  {


		
	    grid.getChildren().remove(rightView);

	    HBox upup = new HBox();
	    
	    upup.setMinHeight(0);
	    
	    HBox up = new HBox();
	    
	    VBox mittel = new VBox();
	    
	    mittel.setMinHeight(230);
	    
	    mittel.setVisible(true);
	    
	    HBox down = new HBox();
	    
	    ImageView clickImgView1 = new ImageView();
	    
	    ImageView clickImgView2 = new ImageView();
	    
	    clickImgView1.setImage(new Image(
				new File("src/resources/click.png").toURI().toString(), 50, 50, false, false));
	    
	    clickImgView2.setImage(new Image(
				new File("src/resources/click.png").toURI().toString(), 50, 50, false, false));
	    
	    up.getChildren().add(clickImgView1);
	    
	    Label tutorLabel1 = new Label("Type \"Hong\" and Click search button.");
	    
	    tutorLabel1.setFont(new Font("Arial", 18));
	    
	    tutorLabel1.setAlignment(Pos.BOTTOM_LEFT);
	    
	    up.getChildren().add(tutorLabel1);
	    
	    down.getChildren().add(clickImgView2);
	    
	    Label tutorLabel2 = new Label("Click one Recipe.");
	    
	    tutorLabel2.setFont(new Font("Arial", 18));
	    
	    tutorLabel2.setAlignment(Pos.BOTTOM_LEFT);
	    
	    down.getChildren().add(tutorLabel2);
	    
	    tutor.getChildren().addAll(upup,up,mittel,down);
		
	    grid.add(tutor, 1, 0);
	    

		
	}

	private void initialFavView() {

		searchBtn.setGraphic(new ImageView(new Image(
				new File("src/resources/recipe_search_button.png").toURI().toString(), 15, 17, false, false)));


		

		try {
			
			

			recipeSearchResultsTVatLeft = recipeDAO.getRecipeListByNameInFavorite("%");

			showRecipeList(recipeSearchResultsTVatLeft);

		} catch (IOException e1) {

			e1.printStackTrace();
		}

		recipeItemsAtLeftClickListenner();

		initableValueType();

		recipeNameRadioBtn.setToggleGroup(group);// set the radio button into
		// group

		ingredientNameRadioBtn.setToggleGroup(group);

		recipeNameRadioBtn.setSelected(true);
		
	}

	private void initialMainPage() {

		searchBtn.setGraphic(new ImageView(new Image(
				new File("src/resources/recipe_search_button.png").toURI().toString(), 15, 17, false, false)));

		
		
		

		try {

			recipeSearchResultsTVatLeft = recipeDAO.getRecipeListByName("%");

			showRecipeList(recipeSearchResultsTVatLeft);
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		recipeItemsAtLeftClickListenner();

		initableValueType();

		recipeNameRadioBtn.setToggleGroup(group);// set the radio button into
													// group

		ingredientNameRadioBtn.setToggleGroup(group);

		recipeNameRadioBtn.setSelected(true);
		
	}

	/*
	 * Initialize table column type. which is name, quantity and unit.
	 * 
	 * @param
	 * 
	 * @author Shi Wenbin
	 */

	public void initableValueType() {
		name.setMinWidth(405);

		name.setCellValueFactory(new PropertyValueFactory<>("name"));

		quantity.setMinWidth(135);

		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		unit.setMinWidth(135);

		unit.setCellValueFactory(new PropertyValueFactory<>("unit"));

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
	public void recipeItemsAtLeftClickListenner() {

		matchRecipeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnchorPane>() {
			@Override
			public void changed(ObservableValue<? extends AnchorPane> observable, AnchorPane oldValue,
					AnchorPane newValue) {
				
				if(enterTime == 0){
					
				    grid.getChildren().remove(tutor);
				    
				    grid.add(rightView, 1, 0);
				    
				    enterTime++;
					
				}
				

				try {

					selectedRecipe = recipeSearchResultsTVatLeft.get(matchRecipeList.getSelectionModel().getSelectedIndex());// get
					
					if(selectedRecipe.getIsFavorite() == 1){
						
						addFavBtn.setGraphic(new ImageView(
								new Image(new File("src/resources/redFav.png").toURI().toString(), 30, 32, false, false)));
						
					} else {
						
						addFavBtn.setGraphic(new ImageView(
								new Image(new File("src/resources/add_fav_recipe.png").toURI().toString(), 30, 32, false, false)));
						
					}

					
					
					String uri ="src/resources/" +  selectedRecipe.getId() + ".png";
					
					recipeImg.setImage(new Image( new File(uri).toURI().toString(),  80, 80, false, false));
					
					editRecipeBtn.setGraphic(
							new ImageView(new Image(new File("src/resources/edit.png").toURI().toString(), 30, 32, false, false)));

					deleteRecipeBtn.setGraphic(new ImageView(
							new Image(new File("src/resources/delete.png").toURI().toString(), 30, 32, false, false)));
					
					servingNumPlusBtn.setGraphic(new ImageView(
							new Image(new File("src/resources/plus.png").toURI().toString(), 10, 10, false, false)));
					
					servingNumMinusBtn.setGraphic(new ImageView(
							new Image(new File("src/resources/minus.png").toURI().toString(), 10, 2, false, false)));

					
					// user
					// clicked
					
					showDetailedRecipe(selectedRecipe);
					
					String surving = String.valueOf(selectedRecipe.getServingNum());
					
					servingNum.setText(surving);

					recipeName.setText(selectedRecipe.getName());

					prepareTime.setText(new Integer(selectedRecipe.getPreparationTime()).toString());

					cookingTime.setText(new Integer(selectedRecipe.getCookingTime()).toString());
					



					
					editRecipeBtn.setDisable(false);// button active

					addFavBtn.setDisable(false);

					deleteRecipeBtn.setDisable(false);
					
					if(selectedRecipe.getDescription() == null){
						
						descriptionLabel.setText("");
						
					} else {
						
						descriptionLabel.setText(textProcessingBeforeOutput(selectedRecipe));
					}
					
					if(mainOrFavView == 1){
						
						exportPdfBtn.setOpacity(0);
						
						exportPdfBtn.setDisable(true);
						
					}else if(mainOrFavView == 2){
						
						exportPdfBtn.setGraphic(new ImageView(
								new Image(new File("src/resources/pdf.png").toURI().toString(), 30, 30, false, false)));
						
						exportPdfBtn.setOpacity(1);
						
						exportPdfBtn.setDisable(false);
						
					}
					

				} catch (Exception e) {
					// System.out.println("ignored error:
					// ArrayIndexOutOfBoundsException.");
				}

			}
		});

	}

	/*
	 * A method to show searched recipes in the recipeList, for user to choose
	 * and click.
	 * 
	 * @param String searchName
	 * 
	 * @author Shi Wenbin
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
		
		searchBehaviour();

	}

	protected void searchBehaviour() throws IOException {
		if(mainOrFavView == 1){
			
			String searchWord = new String(searchbar.getText());

			if (searchWord.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter key words.", null, JOptionPane.ERROR_MESSAGE);// Jpane
																												// alert

			} else {

				if (recipeNameRadioBtn.isSelected()) {

					System.out.println("searchByRecipeName");

					RecipeDAO recipeDAO = new RecipeDAO();

					ArrayList<Recipe> results = recipeDAO.getRecipeListByName(searchbar.getText());

					if (checkSearchResult(results)) {

						showRecipeList(results);

					}


				} else if (ingredientNameRadioBtn.isSelected()) {

					System.out.println("searchByIngredientName");

					RecipeDAO recipeDAO = new RecipeDAO();

					ArrayList<Recipe> results = recipeDAO.getRecipeListByIngredientName(searchbar.getText());

					if (checkSearchResult(results)) {

						showRecipeList(results);

					}


				}
			} 
			
		} else if(mainOrFavView == 2){
			
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
		
	}

	/*
	 * Given recipes to show, and display them in the listView.
	 * 
	 * @param ArrayList<Recipe> results, the searching results(matching recipes) after clicking the search button.
	 * 
	 * 
	 * @author Shi Wenbin
	 */

	public void showRecipeList(ArrayList<Recipe> results) throws IOException {
		
		ObservableList<AnchorPane> anchorPaneList = FXCollections.observableArrayList();

		for (int i = 0; i < results.size(); i++) {

			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(Template.class.getResource("../view/BriefRecipeInMainPage.fxml"));

			loader.load();
			
			BriefRecipeInMainPageController mBriefRecipeInMainPageController = loader.getController();
			
			mBriefRecipeInMainPageController.setSelectedRecipe(results.get(i));

			anchorPaneList.add(loader.getRoot());

		}


		matchRecipeList.setItems(anchorPaneList);

	}
	
	/*
	 * Display recipes' ingredients  in the table.
	 * 
	 * @param int recipeId, fetch ingredients by recipeId.
	 * 
	 * 
	 * @author Shi Wenbin
	 */

	public void showIngredientTable(int recipeId, float multi) throws IOException {

		ingredientTable.getColumns().clear();
		
		ingredients = null;
		
		ingredients = myIngredientDAO.getIngredientListByRecipyId(recipeId);
		
		 int ingredientSize = ingredients.size();
		 
		 for(int i = 0; i<ingredientSize;i++){
			 
			 ingredients.get(i).setQuantity((int)(ingredients.get(i).getQuantity() * multi));
			 
		 }

		ingredientTable
				.setItems(convertArrayListToOberservableList(ingredients));

		ingredientTable.getColumns().addAll(name, quantity, unit);

	}
	


	public ObservableList<Ingredient> convertArrayListToOberservableList(ArrayList<Ingredient> ingredients) {

		int size = ingredients.size();

		ObservableList<Ingredient> reIngredients = FXCollections.observableArrayList();

		for (int i = 0; i < size; i++) {

			reIngredients.add(ingredients.get(i));

		}

		return reIngredients;

	}

	/*
	 * add favorite recipe method(button color change need to be done)
	 * 
	 * @param event search click event
	 * 
	 * @author Qiwen Gu
	 */
	@FXML
	public void addFavRecipe(ActionEvent event) {

		int isFav = selectedRecipe.getIsFavorite();

		if (isFav == 1) {

			int favorite = JOptionPane.showConfirmDialog(null, "Remove this recipe from Favorite?", null, JOptionPane.YES_NO_OPTION);// Jpane check

		if (favorite == JOptionPane.YES_OPTION) {

				System.out.print(selectedRecipe.getName() + " remove favorite ");
				
				addFavBtn.setGraphic(new ImageView(
						new Image(new File("src/resources/add_fav_recipe.png").toURI().toString(), 30, 32, false, false)));

				recipeDAO.removeFavorite(selectedRecipe.getId());

				selectedRecipe.setIsFavorite(0);

		}

		} else {

//			int favorite = JOptionPane.showConfirmDialog(null, "Add this recipe into Favorite?", null,
//					JOptionPane.YES_NO_OPTION);// Jpane check
//
//			if (favorite == JOptionPane.YES_OPTION) {
				
				addFavBtn.setGraphic(new ImageView(
						new Image(new File("src/resources/redFav.png").toURI().toString(), 30, 32, false, false)));

				System.out.print(selectedRecipe.getName() + " add favorite ");

				recipeDAO.addFavorite(selectedRecipe.getId());

				selectedRecipe.setIsFavorite(1);

//			}
		}
	}

	/*
	 * edit recipe method(need a new view to be done)
	 */
	@FXML
	public void editRecipe(ActionEvent event) throws IOException {
		
		 TemplateController.loadContent("../view/AddAndEditRecipeView.fxml", "Edit");
	}

	
	/*
	 * delete recipe method
	 * 
	 * @param event search click event
	 * 
	 * @author Qiwen Gu
	 */
	@FXML
	public void deleteRecipe(ActionEvent event) throws IOException {


		int delete = JOptionPane.showConfirmDialog(null, "Do you want to delete this recipe?", null,
				JOptionPane.YES_NO_OPTION);

		if (JOptionPane.YES_OPTION == delete) {

			System.out.println("delete recipe");

			recipeDAO.deleteRecipe(selectedRecipe.getId());
			myStepDAO.deleteStepListByRecipeId(selectedRecipe.getId());
			myIngredientDAO.deleteIngredientListByRecipeId(selectedRecipe.getId());
			
			ArrayList<Recipe> results = recipeDAO.getRecipeListByName("%");

			try {
				showRecipeList(results);
				
				
				rightView.setOpacity(0);
				JOptionPane.showMessageDialog(null, "Delete suceeded!");  
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		


		} else

			System.out.println("not delete");

	}
	
	/*
	 * pop up window to say "No results matched", when user enter keywords which can not be found in database.
	 * 
	 * @param ArrayList<Recipe> results
	 * 
	 * @author Qiwen Gu
	 */

	public boolean checkSearchResult(ArrayList<Recipe> results) {

		if (results.size() == 0) {
			JOptionPane.showMessageDialog(null, "Sorry, no recipe is found.", null, JOptionPane.ERROR_MESSAGE);// Jpane
			// alert
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Display recipes' steps  in the table.
	 * 
	 * @param int recipeId, fetch steps by recipeId.
	 * 
	 * 
	 * @author Shi Wenbin
	 */
	public void showStepList(int recipeId) {

		ObservableList<String> recipeSteps = FXCollections.observableArrayList();

		ArrayList<Step> steps = myStepDAO.getStepListByRecipyId(recipeId);

		for (int i = 0; i < steps.size(); i++) {

			recipeSteps.add((i + 1) + ". " + steps.get(i).getStepDescription());

		}

		stepList.setItems(recipeSteps);

	}
	
	public void showDetailedRecipe(Recipe recipe) throws IOException {

		showIngredientTable(recipe.getId(),1); // recipe

		showStepList(recipe.getId());

		recipeName.setText(recipe.getName());

	}
	
	protected String textProcessingBeforeOutput(Recipe selectedRecipe) {
		// TODO Auto-generated method stub
		
		char[] text = selectedRecipe.getDescription().toCharArray();
		
		int textSize = text.length;
		
		String outputText = "";
		
		for(int i=0; i < textSize; i++){
			
			
			
			outputText+= text[i];
			
			if(i%60 == 0 && i != 0){
				
				outputText+= "-\n";
				
			}
			

			
		}
		return outputText;
		
	}

	public void setMainOrFav(int mainOrFavNum) {

		mainOrFavView = mainOrFavNum;
		
	}
}
