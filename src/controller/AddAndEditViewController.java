package controller;

import DAO.IngredientDAO;
import DAO.RecipeDAO;
import DAO.StepDAO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.Ingredient;
import model.Recipe;
import model.Step;
import view.Template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

/**
 * a class for controlling and initializing add & edit view and further save.
 *
 * @author Gang Shao
 */
public class AddAndEditViewController {

	private int selectedRecipeId;
	private boolean isNew = true;

	private Recipe recipe;
	private ObservableList<Step> steps = FXCollections.observableArrayList();
	private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

	private RecipeDAO myRecipeDAO = new RecipeDAO();
	private IngredientDAO myIngredientDAO = new IngredientDAO();
	private StepDAO myStepDAO = new StepDAO();

	private FileChooser fileChooser = new FileChooser();
	private Path thumbnailSourcePath;
    private Path thumbnailOriginalPath;
    private String thumbnailName;

    @FXML
    private GridPane frameGrid;
    @FXML
    private VBox detailVB;

	@FXML
	private TableView<Ingredient> ingredientsTV;
	@FXML
	private TableColumn<Ingredient, Integer> ingredientNoCol;
	@FXML
	private TableColumn<Ingredient, String> ingredientNameCol;
	@FXML
	private TableColumn<Ingredient, Number> ingredientQuantityCol;
	@FXML
	private TableColumn<Ingredient, String> ingredientUnitCol;

	@FXML
	private TableView<Step> stepsTV;
	@FXML
	private TableColumn<Step, Integer> stepOrderCol;
	@FXML
	private TableColumn<Step, String> stepDescriptionCol;

	@FXML
	private ImageView thumbnailIV;
	@FXML
	private GridPane thumbnailGP;
    @FXML
    private Rectangle thumbnailRec;

	@FXML
	private Button ingredientsAddRowBtn;
	@FXML
	private Button ingredientsRemoveRowBtn;
	@FXML
	private Button stepsAddRowBtn;
	@FXML
	private Button stepsRemoveRowBtn;
	@FXML
	private Button saveRecipeBtn;
	@FXML
	private Button cancelEditBtn;
	@FXML
    private Button newThumbnailBtn;
    @FXML
    private Button removeThumbnailBtn;

	@FXML
	private TextField titleFld;
	@FXML
	private TextField servingsFld;
	@FXML
	private TextField preparationTimeFld;
	@FXML
	private TextField cookingTimeFld;
	@FXML
	private TextField briefDescriptionFld;
	@FXML
	private TextArea descriptionFld;

	@FXML
	private void initialize() {
		initBtns();

		System.out.println("initializing ...");
		if (recipe != null) {
			try {
				initData();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.out.println("error loading thumbnail.");
			}
		} else {
			initFakeData();
		}
        initThumbnail();
    }

	private void initIngredientsTV(ObservableList<Ingredient> ingredientsObservableList) {

		ingredientNameCol.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getName()));
		ingredientQuantityCol
				.setCellValueFactory(cellValue -> new SimpleDoubleProperty(cellValue.getValue().getQuantity()));
		ingredientUnitCol.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getUnit()));

		ingredientNoCol.prefWidthProperty().bind(ingredientsTV.widthProperty().multiply(0.05));
		ingredientNameCol.prefWidthProperty().bind(ingredientsTV.widthProperty().multiply(0.446));
		ingredientQuantityCol.prefWidthProperty().bind(ingredientsTV.widthProperty().multiply(0.2));
		ingredientUnitCol.prefWidthProperty().bind(ingredientsTV.widthProperty().multiply(0.3));

		ingredientNameCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
			@Override
			public String fromString(String string) {
				return string;
			}

			@Override
			public String toString(String object) {
				return object;
			}
		}));
		ingredientQuantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Number>() {
			@Override
			public Number fromString(String string) {
				try {
					return Double.parseDouble(string);
				} catch (NumberFormatException e) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Only number allowed in \"Quantity\"");
					alert.setHeaderText(null);
					alert.setContentText("Your change of quantity to \"" + string
							+ "\" is not valid. \n For an egg, you can set quantity to 0 and leave the unit blank.");
					alert.showAndWait();
				}
				return 0d;
			}

			@Override
			public String toString(Number object) {
				return object.toString();
			}
		}));
		ingredientUnitCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
			@Override
			public String fromString(String string) {
				return string;
			}

			@Override
			public String toString(String object) {
				return object;
			}
		}));

		ingredientNoCol.setCellFactory(cell -> new NumberCell());

		ingredientsTV.setItems(ingredientsObservableList);
		ingredientsTV.setEditable(true);
		ingredientsTV.getSelectionModel().setCellSelectionEnabled(true);

		ingredientNameCol.setOnEditCommit(event -> {
			cellEditCommitForIngredient(event);
			ingredientsTV.requestFocus();
		});

		ingredientQuantityCol.setOnEditCommit(event -> {
			cellEditCommitForIngredient(event);
			ingredientsTV.requestFocus();
		});
		ingredientUnitCol.setOnEditCommit(event -> {
			cellEditCommitForIngredient(event);
			ingredientsTV.requestFocus();
		});

		// switch to edit mode on keypress
		// this must be KeyEvent.KEY_PRESSED so that the key gets forwarded to
		// the editing cell; it wouldn't be forwarded on KEY_RELEASED
		ingredientsTV.addEventFilter(KeyEvent.KEY_PRESSED, new EasyEdit<>(ingredientsTV));

		ingredientsTV.addEventFilter(KeyEvent.KEY_RELEASED, new EasySelection<>(ingredientsTV));
	}

	private void initStepsTV(ObservableList<Step> stepObservableList) {
		// stepOrderCol.setCellValueFactory(cellValue -> new
		// SimpleIntegerProperty(cellValue.getValue().getStepOrder()));

		stepOrderCol.setCellFactory(cell -> new NumberCell<Step>());
		stepDescriptionCol
				.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getStepDescription()));

		stepOrderCol.prefWidthProperty().bind(stepsTV.widthProperty().multiply(0.05));
		stepDescriptionCol.prefWidthProperty().bind(stepsTV.widthProperty().multiply(0.946));

		stepDescriptionCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
			@Override
			public String fromString(String string) {
				return string;
			}

			@Override
			public String toString(String object) {
				return object;
			}
		}));

		stepsTV.setItems(stepObservableList);
		stepsTV.setEditable(true);
		stepsTV.getSelectionModel().setCellSelectionEnabled(true);
		// ingredientsTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		stepDescriptionCol.setOnEditCommit(event -> {
			cellEditCommitForStep(event);
			stepsTV.requestFocus();
		});

		// switch to edit mode on keypress
		// this must be KeyEvent.KEY_PRESSED so that the key gets forwarded to
		// the editing cell; it wouldn't be forwarded on KEY_RELEASED
		stepsTV.addEventFilter(KeyEvent.KEY_PRESSED, new EasyEdit<>(stepsTV));

		stepsTV.addEventFilter(KeyEvent.KEY_RELEASED, new EasySelection<>(stepsTV));
	}

	private void initBtns() {
		ingredientsAddRowBtn.setOnAction(event -> addRow(ingredientsTV));
		ingredientsRemoveRowBtn.setOnAction(event -> removeRow(ingredientsTV));
		stepsAddRowBtn.setOnAction(event -> addRow(stepsTV));
		stepsRemoveRowBtn.setOnAction(event -> removeRow(stepsTV));

		saveRecipeBtn.setOnAction(event -> {
			try {
				saveRecipe();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		cancelEditBtn.setOnAction(event -> {
			try {
				cancelEdit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

        newThumbnailBtn.setOnAction(event -> {
            configurePictureFileChooser(fileChooser);
			File file = fileChooser.showOpenDialog(Template.primaryStage);

			if (file != null) {
				showThumbnail(file);
                newThumbnailBtn.setText("Change Picture");
            }
		});

        removeThumbnailBtn.setOnAction(event -> {
            thumbnailIV.setImage(null);
			thumbnailSourcePath = null;
            thumbnailName = "";
            newThumbnailBtn.setText("New Picture");
        });

		ingredientsAddRowBtn.setFocusTraversable(false);
		ingredientsRemoveRowBtn.setFocusTraversable(false);
		stepsAddRowBtn.setFocusTraversable(false);
		stepsRemoveRowBtn.setFocusTraversable(false);
	}

	private void initThumbnail() {
        thumbnailGP.prefWidthProperty().bind(frameGrid.widthProperty().multiply(0.58));

        thumbnailIV.fitWidthProperty().bind(frameGrid.widthProperty().multiply(0.58).multiply(0.8));
        thumbnailIV.fitHeightProperty().bind(detailVB.heightProperty().multiply(0.5));

        thumbnailRec.widthProperty().bind(frameGrid.widthProperty().multiply(0.58).multiply(0.8));
        thumbnailRec.heightProperty().bind(detailVB.heightProperty().multiply(0.5));
    }

	private void addRow(TableView tableView) {
		TablePosition pos = tableView.getFocusModel().getFocusedCell();
		tableView.getSelectionModel().clearSelection();
		Ingredient ingredient;
		Step step;
		int row = tableView.getItems().size() - 1;
		if (tableView.getId().equals("ingredientsTV")) {
			ingredient = new Ingredient("", 0, "");
			tableView.getItems().add(ingredient);
			tableView.getSelectionModel().select(row + 1, pos.getTableColumn());
			tableView.scrollTo(ingredient);
		} else if (tableView.getId().equals("stepsTV")) {
			step = new Step(row + 2, "");
			tableView.getItems().add(step);
			tableView.getSelectionModel().select(row + 1, pos.getTableColumn());
			tableView.scrollTo(step);
		}
	}

	private void removeRow(TableView tableView) {
		TablePosition pos = tableView.getFocusModel().getFocusedCell();
		int row = pos.getRow();
		int col = pos.getColumn();
		tableView.getSelectionModel().clearSelection();
		tableView.getItems().remove(row);
		if (row < tableView.getItems().size()) {
			tableView.getSelectionModel().select(row, (TableColumn) tableView.getColumns().get(col));
		} else {
			tableView.getSelectionModel().select(row - 1, (TableColumn) tableView.getColumns().get(col));
		}
	}

	private void saveRecipe() throws Exception {
		if (!isValid()) {
			return;
		}

		Alert saveConfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
		saveConfirmAlert.setTitle("Confirmation");
		saveConfirmAlert.setHeaderText("Do you want to save?");
		Optional<ButtonType> saveOrNot = saveConfirmAlert.showAndWait();

		if (saveOrNot.isPresent()) {
			if (saveOrNot.get() == ButtonType.OK) {
				Recipe newRecipe = new Recipe();
				int servingNum = 0;
				int preparationTime = 0;
				int cookingTime = 0;
				int isFavorite = 0;
				try {
					servingNum = Integer.parseInt(servingsFld.getText());
					preparationTime = Integer.parseInt(preparationTimeFld.getText());
					cookingTime = Integer.parseInt(cookingTimeFld.getText());

				} catch (NumberFormatException nfe) {
					Alert aLert = new Alert(Alert.AlertType.ERROR);
					aLert.setTitle("Something is wrong ...");
					aLert.setHeaderText(null);
					aLert.setContentText("Please make sure serving, preparation & cooking time is numeric :D");
					aLert.showAndWait();
					return;
				}
				String briefDescription = briefDescriptionFld.getText();
				String description = descriptionFld.getText();

				newRecipe.setName(titleFld.getText());
				newRecipe.setServingNum(servingNum);
				newRecipe.setPreparationTime(preparationTime);
				newRecipe.setCookingTime(cookingTime);
				newRecipe.setBriefDescription(briefDescription);
				newRecipe.setDescription(description);
				newRecipe.setIsFavorite(isFavorite);

//				if (thumbnailSourcePath != null && !thumbnailSourcePath.equals("")) {
//					File srcFile = new File(thumbnailSourcePath.toString());
//					File projectFile = new File("");
//					String extension = new String(thumbnailSourcePath.toString().substring(thumbnailSourcePath.toString().lastIndexOf('.')));
//					thumbnailName = newRecipe.getId() + extension;
//					String destination = projectFile.getCanonicalPath() + "\\src\\resources\\" + newRecipe.getId() + extension;
//					
//					newRecipe.setThumbnail(thumbnailName);
//					
//					File desFile = new File(destination);
//					try {
//						copyThumbnail(srcFile, desFile);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				} else {
//					newRecipe.setThumbnail("");
//				}
				
				optimize();
				
				if (isNew) {
					myRecipeDAO.addRecipe(newRecipe);
					copyThumbnail(thumbnailSourcePath, newRecipe.getId());
					newRecipe.setThumbnail(thumbnailName);
					myRecipeDAO.updateRecipe(newRecipe);
					
					for (Step step : steps) {
						step.setRecipeId(newRecipe.getId());
						System.out.println(newRecipe.getId());
						myStepDAO.addStep(step);
					}
					for (Ingredient ingredient : ingredients) {
						System.out.println(newRecipe.getId());
						ingredient.setRecipeId(newRecipe.getId());
						System.out.println(newRecipe.getId());
						myIngredientDAO.addIngredient(ingredient);
					}
					
				} else {
					isFavorite = MainPageController.selectedRecipe.getIsFavorite();
					newRecipe.setIsFavorite(isFavorite);
					newRecipe.setId(recipe.getId());
                    copyThumbnail(thumbnailSourcePath, newRecipe.getId());
                    newRecipe.setThumbnail(thumbnailName);
					myRecipeDAO.updateRecipe(newRecipe);
					myStepDAO.updateSteps(new ArrayList<>(steps));
					myIngredientDAO.updateIngredients(new ArrayList<>(ingredients));
				}
				
				TemplateController.loadContent("/view/MainOrFavView.fxml", "Main");
			}

			
		}
	}

	private void cancelEdit() throws IOException {
		// TODO implement cancelEdit
		TemplateController.loadContent("/view/MainOrFavView.fxml", "Main");
	}

	private boolean isValid() {
		if (titleFld.getText().isEmpty() || servingsFld.getText().isEmpty() || preparationTimeFld.getText().isEmpty()
				|| cookingTimeFld.getText().isEmpty()) {

			Alert aLert = new Alert(Alert.AlertType.ERROR);
			aLert.setTitle("Something is missing ...");
			aLert.setHeaderText(null);
			aLert.setContentText(
					"Please make sure you have filled all title, serving, preparation & cooking time blanks :D");
			aLert.showAndWait();
			return false;
		} else {
			return true;
		}
	}

	public boolean compare(Recipe newRecipe) {

		boolean partOne = true;
		boolean partStep = true;
		boolean partIngredient = true;
		ArrayList<Ingredient> origionIngredients = myIngredientDAO
				.getIngredientListByRecipyId(MainPageController.selectedRecipe.getId());
		ArrayList<Step> origionSteps = myStepDAO.getStepListByRecipyId(MainPageController.selectedRecipe.getId());
		if (newRecipe.getName() != MainPageController.selectedRecipe.getName()
				|| newRecipe.getBriefDescription() != MainPageController.selectedRecipe.getBriefDescription()
				|| newRecipe.getDescription() != MainPageController.selectedRecipe.getDescription()
				|| newRecipe.getServingNum() != MainPageController.selectedRecipe.getServingNum()
				|| newRecipe.getCookingTime() != MainPageController.selectedRecipe.getCookingTime()
				|| newRecipe.getPreparationTime() != MainPageController.selectedRecipe.getPreparationTime()) {
			partOne = false;
		}

		if (steps.size() == origionSteps.size()) {
			int order = 0;
			for (Step step : steps) {
				if (step.getStepDescription() != origionSteps.get(order).getStepDescription()) {
					partStep = false;
					order++;
				}
			}
		}

		if (ingredients.size() == origionIngredients.size()) {
			int order = 0;
			for (Ingredient ingredient : ingredients) {
				if (ingredient.getName() != origionIngredients.get(order).getName()
						|| ingredient.getQuantity() != origionIngredients.get(order).getQuantity()
						|| ingredient.getUnit() != origionIngredients.get(order).getUnit()) {
					partIngredient = false;
					order++;
				}
			}
		}
		if (!partOne && !partStep && !partIngredient) {
			return true;
		} else {
			return false;
		}
	}

	private void optimize() {
		Iterator<Ingredient> ingredientIterator = ingredients.iterator();
		Iterator<Step> stepIterator = steps.iterator();

		Ingredient ingredientTemp;
		Step stepTemp;

		int stepOrder = 1;

		while (ingredientIterator.hasNext()) {
			ingredientTemp = ingredientIterator.next();
			if (ingredientTemp.getName().equals("") || ingredientTemp.getName() == null) {
				ingredientIterator.remove();
			}
		}

		while (stepIterator.hasNext()) {
			stepTemp = stepIterator.next();
			if (stepTemp.getStepDescription().equals("") || stepTemp.getStepDescription() == null) {
				stepIterator.remove();
			} else {
				stepTemp.setStepOrder(stepOrder);
				stepOrder++;
			}
		}
	}

	private void cellEditCommitForIngredient(TableColumn.CellEditEvent event) {
		Object newValue = event.getNewValue();
		TablePosition pos = event.getTablePosition();

		int row = pos.getRow();
		int col = pos.getColumn();
		switch (col) {
		case 0:
			break;
		case 1:
			int index = checkDuplicatesForIngredient((String) newValue, (String) event.getOldValue(), ingredients);
			if (index != -1) {
				ingredientsTV.refresh();
				Alert duplicateAlert = new Alert(Alert.AlertType.WARNING);
				duplicateAlert.setTitle("Duplicates found");
				duplicateAlert.setHeaderText("You have just entered the same ingredient.");
				duplicateAlert.setContentText(
						"It is the same as: \n \"" + (index + 1) + ". " + ingredients.get(index).getName() + " "
								+ ingredients.get(index).getQuantity() + " " + ingredients.get(index).getUnit() + "\"");
				duplicateAlert.showAndWait();

			} else {
				ingredientsTV.getItems().get(row).setName(newValue.toString());
			}
			break;
		case 2:
			ingredientsTV.getItems().get(row).setQuantity(Double.parseDouble(newValue.toString()));
			break;
		case 3:
			ingredientsTV.getItems().get(row).setUnit(newValue.toString());
			break;
		}
	}

	private void cellEditCommitForStep(TableColumn.CellEditEvent event) {
		Object newValue = event.getNewValue();
		TablePosition pos = event.getTablePosition();
		int row = pos.getRow();
		int col = pos.getColumn();
		switch (col) {
		case 1:
			int index = checkDuplicatesForStep((String) newValue, (String) event.getOldValue(), steps);
			if (index != -1) {
				stepsTV.refresh();
				Alert duplicateAlert = new Alert(Alert.AlertType.WARNING);
				duplicateAlert.setTitle("Duplicates found");
				duplicateAlert.setHeaderText("You have just entered the same step.");
				duplicateAlert.setHeaderText(
						"It is the same as: \n \"" + (index + 1) + ". " + steps.get(index).getStepDescription() + "\"");
				duplicateAlert.showAndWait();
			} else {
				stepsTV.getItems().get(row).setDescription(newValue.toString());
			}
			break;
		}
	}

	/**
	 * Look for any duplicate of ingredient of the same name.
	 * 
	 * @param newValue
	 *            the new value of this input
	 * @param oldValue
	 *            the old value of this input
	 * @param ingredientObservableList
	 *            the list of ingredients
	 * @return the index of the duplication if found, otherwise return -1.
	 */
	private int checkDuplicatesForIngredient(String newValue, String oldValue,
			ObservableList<Ingredient> ingredientObservableList) {
		if (newValue.equals(oldValue)) {
			return -1;
		}
		for (int i = 0; i < ingredientObservableList.size(); i++) {
			if (newValue.equals(ingredientObservableList.get(i).getName())) {
				return i;
			}
		}

		return -1;
	}

	private int checkDuplicatesForStep(String newValue, String oldValue, ObservableList<Step> stepObservableList) {
		if (newValue.equals(oldValue)) {
			return -1;
		}
		for (int i = 0; i < stepObservableList.size(); i++) {
			if (newValue.equals(stepObservableList.get(i).getStepDescription())) {
				return i;
			}
		}

		return -1;
	}

	private void initData() throws MalformedURLException {
		if (isNew) {
			initFakeData();
			return;
		}

		Recipe selectedRecipe = recipe;

		selectedRecipeId = selectedRecipe.getId();
		String servingNumber = String.valueOf(selectedRecipe.getServingNum());
		String preparationTime = String.valueOf(selectedRecipe.getPreparationTime());
		String cookingTime = String.valueOf(selectedRecipe.getCookingTime());
		ArrayList<Step> stepList = myStepDAO.getStepListByRecipyId(selectedRecipeId);
		ArrayList<Ingredient> ingredientList = myIngredientDAO.getIngredientListByRecipyId(selectedRecipeId);
		String briefDescription = String.valueOf(selectedRecipe.getBriefDescription());
		String description = String.valueOf(selectedRecipe.getDescription());

		titleFld.setText(selectedRecipe.getName());
		servingsFld.setText(servingNumber);
		preparationTimeFld.setText(preparationTime);
		cookingTimeFld.setText(cookingTime);
		ingredients.addAll(ingredientList);
		steps.addAll(stepList);
		briefDescriptionFld.setText(briefDescription);
		descriptionFld.setText(description);
        thumbnailName = selectedRecipe.getThumbnail();

        showThumbnail(thumbnailName);

		initIngredientsTV(ingredients);
		initStepsTV(steps);
	}

	private void initFakeData() {
        ingredients.addAll(new Ingredient("Name", 0, "unit"), new Ingredient("", 0, ""),
                new Ingredient("", 0, ""));

        steps.addAll(new Step(1, "Your step description"), new Step(2, ""), new Step(3, ""));

		initIngredientsTV(ingredients);
		initStepsTV(steps);
	}

	private class NumberCell<T> extends TableCell<T, Integer> {
		public NumberCell() {
			super();
		}

		@Override
		protected void updateItem(Integer item, boolean empty) {
			super.updateItem(item, empty);

			if (this.getTableRow() != null) {
				int index = this.getTableRow().getIndex();
				if (index < this.getTableView().getItems().size()) {
					setText(index + 1 + "");
				} else {
					setText("");
				}
			} else {
				setText("");
			}
		}
	};

	/**
	 * class implements EventHandler<KeyEvent> to start edit when user type any
	 * letter or digit key.
	 * 
	 * @param <T>
	 *            generic type of table view
	 */

	private class EasyEdit<T> implements EventHandler<KeyEvent> {
		private TableView<T> tableView;

		private EasyEdit(TableView<T> tableView) {
			this.tableView = tableView;
		}

		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				// event.consume(); // don't consume the event or else the
				// values won't be updated;
				return;
			}

			// switch to edit mode on keypress, but only if we aren't already in
			// edit mode
			if (tableView.getEditingCell() == null) {
				if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
					TablePosition focusedCellPosition = tableView.getFocusModel().getFocusedCell();
					tableView.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());
				}
			}

		}
	}

	/**
	 * class implements EventHandler<KeyEvent> to move to next cell when user
	 * release "Enter".
	 * 
	 * @param <T>
	 *            generic type of table view
	 */
	private class EasySelection<T> implements EventHandler<KeyEvent> {
		private TableView<T> tableView;

		private EasySelection(TableView<T> tableView) {

			this.tableView = tableView;
		}

		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.TAB) {
				return;
			}

			if (event.getCode() == KeyCode.ENTER) {
				// move focus & selection
				// we need to clear the current selection first or else the
				// selection would be added to the current selection since
				// we are in multi selection mode
				TablePosition pos = tableView.getFocusModel().getFocusedCell();

				// if the selection at this position is disabled, then select
				// first cell
				if (pos.getRow() == -1) {
					tableView.getSelectionModel().select(0);
				}
				// select next cell in same row
				else if (pos.getColumn() < tableView.getColumns().size() - 1) {
					// pos.getRow(),
					// ingredientsTV.getColumns().get(pos.getColumn() + 1));
					tableView.getSelectionModel().selectNext();
				}
				// select first cell in next row, when reaching one row's
				// end
				else if (pos.getColumn() == tableView.getColumns().size() - 1
						&& pos.getRow() != tableView.getItems().size() - 1) {
					tableView.getSelectionModel().clearAndSelect(pos.getRow() + 1, tableView.getColumns().get(1));
				}
				// add new row when we are at the last row
				else if (pos.getRow() == tableView.getItems().size() - 1) {
					addRow(tableView);
					tableView.getSelectionModel().clearAndSelect(pos.getRow() + 1, tableView.getColumns().get(1));
				}
			}
		}
	}

	public AddAndEditViewController() {

	}

	private void configurePictureFileChooser(FileChooser fileChooser) {
		fileChooser.setTitle("Choose your recipe pictures");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image", "*.jpeg", "*.jpg", "*.png", "*.bmp"));
	}

    private void showThumbnail(File file) {
        thumbnailSourcePath = Paths.get(file.toURI());
        if (thumbnailIV.getImage() != null) {
            thumbnailIV.setImage(null);
        }
        System.out.println(thumbnailSourcePath);
        try {
            thumbnailIV.setImage(new Image(thumbnailSourcePath.toUri().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        thumbnailIV.setPreserveRatio(true);
        thumbnailIV.setSmooth(true);
    }

    private void showThumbnail(String thumbnailName) {
        if (thumbnailName != null && !thumbnailName.equals("")) {
            Path thumbnailPath = Paths.get("src/resources/" + thumbnailName);
            try {
                thumbnailIV.setImage(new Image(thumbnailPath.toUri().toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            thumbnailIV.setPreserveRatio(true);
            thumbnailIV.setSmooth(true);
        }
    }

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public void setIsNew(boolean bool) {
		this.isNew = bool;
	}

	public static void copyThumbnail(File file, File toFile) throws Exception {
		byte[] buffer = new byte[1024];
		int dataNum;
		FileInputStream fis;
		FileOutputStream fos;
		if (file.isDirectory()) {
			String filepath = file.getAbsolutePath();
			filepath = filepath.replaceAll("\\\\", "/");
			String toFilepath = toFile.getAbsolutePath();
			toFilepath = toFilepath.replaceAll("\\\\", "/");
			int lastIndex = filepath.lastIndexOf("/");
			toFilepath = toFilepath + filepath.substring(lastIndex, filepath.length());
			File copy = new File(toFilepath);
			if (!copy.exists()) {
				copy.mkdir();
			}
			for (File f : file.listFiles()) {
				copyThumbnail(f, copy);
			}
		} else {
			if (toFile.isDirectory()) {
				String filepath = file.getAbsolutePath();
				filepath = filepath.replaceAll("\\\\", "/");
				String toFilepath = toFile.getAbsolutePath();
				toFilepath = toFilepath.replaceAll("\\\\", "/");
				int lastIndex = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndex, filepath.length());
				File newFile = new File(toFilepath);
				fis = new FileInputStream(file);
				fos = new FileOutputStream(newFile);
				while ((dataNum = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, dataNum);
				}
			} else {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(toFile);
				while ((dataNum = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, dataNum);
				}
			}
		}
	}

	public void copyThumbnail(Path sourcePath, int newRecipeId) throws Exception {
		if (sourcePath != null && !sourcePath.equals("")) {
			File srcFile = new File(sourcePath.toString());
			File projectFile = new File("");
			String extension = new String(sourcePath.toString().substring(sourcePath.toString().lastIndexOf('.')));
			
			thumbnailName = newRecipeId + extension;
			
			String destination = projectFile.getCanonicalPath() + "\\src\\resources\\" + thumbnailName;
            if (destination.equals(sourcePath.toString())) {
                return;
            }
            File desFile = new File(destination);
			
			byte[] buffer = new byte[1024];
			int dataNum;
			FileInputStream fis;
			FileOutputStream fos;
			if (srcFile.isDirectory()) {
				String filepath = srcFile.getAbsolutePath();
				filepath = filepath.replaceAll("\\\\", "/");
				String toFilepath = desFile.getAbsolutePath();
				toFilepath = toFilepath.replaceAll("\\\\", "/");
				int lastIndex = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndex, filepath.length());
				File copy = new File(toFilepath);
				if (!copy.exists()) {
					copy.mkdir();
				}
				for (File f : srcFile.listFiles()) {
					copyThumbnail(f, copy);
				}
			} else {
				if (desFile.isDirectory()) {
					String filepath = srcFile.getAbsolutePath();
					filepath = filepath.replaceAll("\\\\", "/");
					String toFilepath = desFile.getAbsolutePath();
					toFilepath = toFilepath.replaceAll("\\\\", "/");
					int lastIndex = filepath.lastIndexOf("/");
					toFilepath = toFilepath + filepath.substring(lastIndex, filepath.length());
					File newFile = new File(toFilepath);
					fis = new FileInputStream(srcFile);
					fos = new FileOutputStream(newFile);
					while ((dataNum = fis.read(buffer)) != -1) {
						fos.write(buffer, 0, dataNum);
					}
				} else {
					fis = new FileInputStream(srcFile);
					fos = new FileOutputStream(desFile);
					while ((dataNum = fis.read(buffer)) != -1) {
						fos.write(buffer, 0, dataNum);
					}
				}
			}
		}
			
	}
}
