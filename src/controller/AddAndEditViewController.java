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
 * @author Gang Shao, Qiwen Gu
 */
public class AddAndEditViewController {

    private int selectedRecipeId;
    private boolean isNew = true;
    private boolean hasShowEditInformation = false;

    private Recipe recipe;
    private ObservableList<Step> steps = FXCollections.observableArrayList();
    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

    //initiate DAOs
    private RecipeDAO myRecipeDAO = new RecipeDAO();
    private IngredientDAO myIngredientDAO = new IngredientDAO();
    private StepDAO myStepDAO = new StepDAO();

    //initiate filechooser
    private FileChooser fileChooser = new FileChooser();
    private Path thumbnailSourcePath;
    private String thumbnailName;

    //define elements in the fxml
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
    private Label titleLbl;
    @FXML
    private Label briefDescriptionLbl;
    @FXML
    private Label descriptionLbl;


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

    /**
     * initialize buttons, data and thumbnail.
     *
     * @author Gang Shao
     */
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

    /**
     * initialize the TableView for input of ingredients
     *
     * @param ingredientsObservableList the ingredients that will be put into the TableView
     * @author Gang Shao
     */
    private void initIngredientsTV(ObservableList<Ingredient> ingredientsObservableList) {

        //set cell value factory for each column for the value in each cell
        ingredientNameCol.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getName()));
        ingredientQuantityCol
                .setCellValueFactory(cellValue -> new SimpleDoubleProperty(cellValue.getValue().getQuantity()));
        ingredientUnitCol.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getUnit()));

        //set preferred width for each column
        ingredientNoCol.prefWidthProperty().bind(ingredientsTV.widthProperty().multiply(0.05));
        ingredientNameCol.prefWidthProperty().bind(ingredientsTV.widthProperty().multiply(0.445));
        ingredientQuantityCol.prefWidthProperty().bind(ingredientsTV.widthProperty().multiply(0.2));
        ingredientUnitCol.prefWidthProperty().bind(ingredientsTV.widthProperty().multiply(0.3));

        //set cell factory for each column for how to display value in each cell
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

        //set ingredientsObservableList to TableView
        ingredientsTV.setItems(ingredientsObservableList);
        ingredientsTV.setEditable(true);
        ingredientsTV.getSelectionModel().setCellSelectionEnabled(true);

        //set listener which will save edit and request focus when committing edit
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

        //set alert to remind user clicking outside will loose the edits
        ingredientNameCol.setOnEditCancel(event -> {
            if (!hasShowEditInformation) {
                showEditInformation();
            }
            ingredientsTV.requestFocus();
        });

        ingredientQuantityCol.setOnEditCancel(event -> {
            if (!hasShowEditInformation) {
                showEditInformation();
            }
            ingredientsTV.requestFocus();
        });

        ingredientUnitCol.setOnEditCancel(event -> {
            if (!hasShowEditInformation) {
                showEditInformation();
            }
            ingredientsTV.requestFocus();
        });

        //set listener so that it switches to edit mode when pressing any digital/letter key.
        // this must be KeyEvent.KEY_PRESSED so that the key gets forwarded to, the editing cell; otherwise, it wouldn't be forwarded on KEY_RELEASED
        ingredientsTV.addEventFilter(KeyEvent.KEY_PRESSED, new EasyEdit<>(ingredientsTV));

        ingredientsTV.addEventFilter(KeyEvent.KEY_RELEASED, new EasySelection<>(ingredientsTV));

    }

    /**
     * initialize the TableView for input of setps
     *
     * @param stepObservableList the steps that will be put into the TableView
     * @author Gang Shao
     */
    private void initStepsTV(ObservableList<Step> stepObservableList) {

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

        stepDescriptionCol.setOnEditCommit(event -> {
            cellEditCommitForStep(event);
            stepsTV.requestFocus();
        });

        // switch to edit mode on keypress
        // this must be KeyEvent.KEY_PRESSED so that the key gets forwarded to
        // the editing cell; it wouldn't be forwarded on KEY_RELEASED
        stepsTV.addEventFilter(KeyEvent.KEY_PRESSED, new EasyEdit<>(stepsTV));

        stepsTV.addEventFilter(KeyEvent.KEY_RELEASED, new EasySelection<>(stepsTV));

        stepDescriptionCol.setOnEditCancel(event -> {
            if (!hasShowEditInformation) {
                showEditInformation();
            }
            ingredientsTV.requestFocus();
        });
    }

    /**
     * set listeners on all the buttons
     *
     * @author Gang Shao
     */
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

    /**
     * initiate thumbnail area dimensions
     *
     * @author Gang Shao
     */
    private void initThumbnail() {
        thumbnailGP.prefWidthProperty().bind(frameGrid.widthProperty().multiply(0.58));

        thumbnailIV.fitWidthProperty().bind(frameGrid.widthProperty().multiply(0.58).multiply(0.8));
        thumbnailIV.fitHeightProperty().bind(detailVB.heightProperty().multiply(0.5));

        thumbnailRec.widthProperty().bind(frameGrid.widthProperty().multiply(0.58).multiply(0.8));
        thumbnailRec.heightProperty().bind(detailVB.heightProperty().multiply(0.5));
    }

    /**
     * add a new row to TableView
     *
     * @param tableView the TableView where a new row will be added to
     * @author Gang Shao
     */
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

    /**
     * remove the row selected in a TableView
     *
     * @param tableView the TableView from which the row will be removed
     * @author Gang Shao
     */
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

    /*
     * Save the recipe to the database
     *
     * @author Gang Shao, Qiwen Gu
     */
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
                if (!optimize()) {
                    return;
                }

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

    /**
     * cancel edit and then jump to main view
     *
     * @throws IOException exception
     * @author Gang Shao
     */
    private void cancelEdit() throws IOException {

        TemplateController.loadContent("/view/MainOrFavView.fxml", "Main");
    }

    /**
     * check if the the inputs in title, serving and time empty
     *
     * @return true if valid, false if invalid.
     */
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


    /**
     * clear empty items in ingredients and steps list
     *
     * @author Gang Shao
     */
    private boolean optimize() {
        boolean isOptimized = false;
        if (showTooLongInformation(titleLbl, titleFld) == ButtonType.OK && showTooLongInformation(briefDescriptionLbl, briefDescriptionFld) == ButtonType.OK &&
                showTooLongInformation(descriptionLbl, descriptionFld) == ButtonType.OK) {
            isOptimized = true;
        } else {
            System.out.println("Not OK, abort!");
            return isOptimized;
        }

        Iterator<Ingredient> ingredientIterator = ingredients.iterator();
        Iterator<Step> stepIterator = steps.iterator();

        Ingredient ingredientTemp;
        Step stepTemp;

        int stepOrder = 1;

        while (ingredientIterator.hasNext()) {
            ingredientTemp = ingredientIterator.next();
            if (ingredientTemp.getName().equals("") || ingredientTemp.getName() == null) {
                ingredientIterator.remove();
            } else {
                if (!isNew) {
                    ingredientTemp.setRecipeId(selectedRecipeId);
                }
            }
        }

        while (stepIterator.hasNext()) {
            stepTemp = stepIterator.next();
            if (stepTemp.getStepDescription().equals("") || stepTemp.getStepDescription() == null) {
                stepIterator.remove();
            } else {
                stepTemp.setStepOrder(stepOrder);
                stepOrder++;
                if (!isNew) {
                    stepTemp.setRecipeId(selectedRecipeId);
                }
            }
        }

        return isOptimized;
    }

    /**
     * save the item when commit edit in the cell of Ingredients TableView
     *
     * @param event cell edit event
     */
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

    /**
     * save the item when commit edit in the cell of Steps TableView
     *
     * @param event cell edit event
     * @author Gang Shao
     */
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
     * @param newValue                 the new value of this input
     * @param oldValue                 the old value of this input
     * @param ingredientObservableList the list of ingredients
     * @return the index of the duplication if found, otherwise return -1.
     * @author Gang Shao
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

    /**
     * look for any duplicate of step of the same.
     *
     * @param newValue           the new value of this input
     * @param oldValue           the old value of this input
     * @param stepObservableList the list of steps
     * @return the index of the duplication if found, otherwise return -1.
     * @author Gang Shao
     */
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

    /**
     * read and initiate data from the recipe selected to fill in the text fields
     *
     * @throws MalformedURLException malformed thumbnail URL exception
     * @author Gang Shao
     */
    private void initData() throws MalformedURLException {
        //if it is a new recipe then initiate fake data.
        if (isNew) {
            initFakeData();
            return;
        }
        //otherwise, read and fill in the fields with the recipe data
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
        System.out.println(thumbnailName);

        //initiate thumbnail
        showThumbnail(thumbnailName);

        //initiate TableViews
        initIngredientsTV(ingredients);
        initStepsTV(steps);
    }

    /**
     * initiate fake data if user is adding a new recipe
     *
     * @author Gang Shao
     */
    private void initFakeData() {
        ingredients.addAll(new Ingredient("Name", 0, "unit"), new Ingredient("", 0, ""),
                new Ingredient("", 0, ""));

        steps.addAll(new Step(1, "Your step description"), new Step(2, ""), new Step(3, ""));

        initIngredientsTV(ingredients);
        initStepsTV(steps);
    }

    /**
     * a class extending TableCell for rendering numbers in the '#' column
     *
     * @param <T> Type of the items stored in the TableView
     * @author Gang Shao
     */
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
    }

    ;

    /**
     * class implements EventHandler<KeyEvent> to start edit when user type any letter or digit key.
     *
     * @param <T> generic type of table view
     * @author Gang Shao
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
     * class implements EventHandler<KeyEvent> to move to next cell when user release "Enter".
     *
     * @param <T> generic type of table view
     * @author Gang Shao
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

    /**
     * constructor
     */
    public AddAndEditViewController() {

    }

    /**
     * configures the file chooser for recipe thumbnail
     *
     * @param fileChooser the fileChooser needed to be configured
     * @author Gang Shao
     */
    private void configurePictureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Choose your recipe pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Image", "*.jpeg", "*.jpg", "*.png", "*.bmp"));
    }

    /**
     * show thumbnail to the view with an instance of file
     *
     * @param file the thumbnail file
     * @author Gang Shao
     */
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

    /**
     * show thumbnail to the view with the file name
     *
     * @param thumbnailName the file name
     * @author Gang Shao
     */
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

    /*
     * Copy the chosen Thumbnail into resources
     *
     * @param file	 the path of the picture
     * @param toFile	the path of the destination
     *
     * @author Qiwen Gu
     */
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

    /*
     * Copy the chosen Thumbnail into resources
     *
     * @param sourcePath	 the path of the picture
     * @param newRecipeId	the owner' recipeId of the picture
     *
     * @author Qiwen Gu, Gang Shao
     */
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

    /**
     * show information alert to remind user edits will be ignored when clicking out of the cell
     *
     * @author Gang Shao
     */
    private void showEditInformation() {
        Alert editLost = new Alert(Alert.AlertType.INFORMATION);
        editLost.setHeaderText("Sorry for your inconvenience. \nPlease press ENTER to save.");
        editLost.setContentText("Edits will only be saved if you press \"Enter\", clicking outside or press ESC will ignore what you have just entered.");
        editLost.show();
        hasShowEditInformation = true;
    }

    private ButtonType showTooLongInformation(Label label, TextInputControl textFld) {
        String labelText = label.getText();
        int maxLength = 0;
        switch (labelText) {
            case "Title":
                maxLength = 80;
                break;
            case "Brief Introduction":
                maxLength = 250;
                break;
            case "Detailed Description":
                maxLength = 500;
                break;
        }
        if (textFld.getText().length() > maxLength) {
            Alert tooLongAlert = new Alert(Alert.AlertType.CONFIRMATION);
            tooLongAlert.setTitle("Something too long...");
            tooLongAlert.setHeaderText("Text in " + label.getText() + " is longer than maximum " + maxLength + " letters.");
            tooLongAlert.setContentText("Press OK so the application read the first " + maxLength + " letters, or Cancel to edit it yourself.");
            Optional<ButtonType> autoOrManual = tooLongAlert.showAndWait();
            if (autoOrManual.isPresent()) {
                if (autoOrManual.get() == ButtonType.OK) {
                    textFld.setText(textFld.getText().substring(0, 80));
                    return ButtonType.OK;
                } else {
                    return ButtonType.CANCEL;
                }
            }

        } else {
            return ButtonType.OK;
        }
        return ButtonType.OK;
    }

}
