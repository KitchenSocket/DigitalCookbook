package controller;

import javax.swing.JOptionPane;

import DAO.IngredientDAO;
import DAO.RecipeDAO;
import DAO.StepDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Ingredient;
import model.Recipe;
import model.Step;
import view.Template;

import java.io.IOException;

/**
 * Created by fexac on 13-Jun-17.
 */
public class AddRecipeViewController {
    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(new Ingredient("Edit me ...", 0, "please"),
            new Ingredient("some ingredient", 0, "g"), new Ingredient("another ingredient", 5, "g"));

    private ObservableList<Step> steps = FXCollections.observableArrayList(new Step(1, "Do it."),
            new Step(2, "Just do it."), new Step(3, "Please dooo it."));

    protected RecipeDAO recipeDAO = new RecipeDAO();

    protected IngredientDAO myIngredientDAO = new IngredientDAO();

    protected StepDAO myStepDAO = new StepDAO();

    @FXML
    protected TableView<Ingredient> ingredientsTV;
    @FXML
    protected TableColumn<Ingredient, Integer> ingredientNoCol;
    @FXML
    protected TableColumn<Ingredient, String> ingredientNameCol;
    @FXML
    protected TableColumn<Ingredient, Number> ingredientQuantityCol;
    @FXML
    protected TableColumn<Ingredient, String> ingredientUnitCol;

    @FXML
    protected TableView<Step> stepsTV;
    @FXML
    protected TableColumn<Step, Number> stepOrderCol;
    @FXML
    protected TableColumn<Step, String> stepDescriptionCol;

    @FXML
    protected Button ingredientsAddRowBtn;
    @FXML
    protected Button ingredientsRemoveRowBtn;
    @FXML
    protected Button stepsAddRowBtn;
    @FXML
    protected Button stepsRemoveRowBtn;
    @FXML
    protected Button saveRecipeBtn;
    @FXML
    protected Button cancelEditBtn;

    @FXML
    protected TextField titleFld;
    @FXML
    protected TextField servingsFld;
    @FXML
    protected TextField preparationTimeFld;
    @FXML
    protected TextField cookingTimeFld;
    @FXML
    protected TextField briefDescriptionFld;
    @FXML
    protected TextArea descriptionFld;


    @FXML
    private void initialize() {
        initIngredientsTV(ingredients);
        initBtns();
        initStepsTV(steps);
    }

    protected void initIngredientsTV(ObservableList<Ingredient> ingredientsObservableList) {
        ingredientNameCol.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getName()));
        ingredientQuantityCol
                .setCellValueFactory(cellValue -> new SimpleDoubleProperty(cellValue.getValue().getQuantity()));
        ingredientUnitCol.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getUnit()));
        //ingredientNoCol.setCellValueFactory(cellValue -> new ReadOnlyObjectWrapper<Integer>(cellValue.getValue()));

        // ingredientNameCol.setCellFactory(createStringCellFactory());
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
        ingredientQuantityCol.setCellFactory(createDoubleCellFactory());
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

        ingredientNoCol.setCellFactory(cell -> {
            return new TableCell<Ingredient, Integer> () {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (this.getTableRow() != null) {
                        int index = this.getTableRow().getIndex();
                        if(index < this.getTableView().getItems().size()) {
                            setText(index + 1 + "");
                        }
                    } else {
                        setText("");
                    }
                }
        };
        });


        ingredientsTV.setItems(ingredientsObservableList);
        ingredientsTV.setEditable(true);
        ingredientsTV.getSelectionModel().setCellSelectionEnabled(true);
        // ingredientsTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
        ingredientsTV.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER) {
                    // event.consume(); // don't consume the event or else the
                    // values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't
                // already in edit mode
                if (ingredientsTV.getEditingCell() == null) {
                    if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = ingredientsTV.getFocusModel().getFocusedCell();
                        ingredientsTV.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });

        ingredientsTV.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
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
                    TablePosition pos = ingredientsTV.getFocusModel().getFocusedCell();

                    if (pos.getRow() == -1) {
                        ingredientsTV.getSelectionModel().select(0);
                    }
                    // select next cell in same row
                    else if (pos.getColumn() < ingredientsTV.getColumns().size() - 1) {
                        // ingredientsTV.getSelectionModel().clearAndSelect(
                        // pos.getRow(),
                        // ingredientsTV.getColumns().get(pos.getColumn() + 1));
                        ingredientsTV.getSelectionModel().selectNext();
                    }
                    // select first cell in next row, when reaching one row's
                    // end
                    else if (pos.getColumn() == ingredientsTV.getColumns().size() - 1
                            && pos.getRow() != ingredientsTV.getItems().size() - 1) {
                        ingredientsTV.getSelectionModel().clearAndSelect(pos.getRow() + 1,
                                ingredientsTV.getColumns().get(0));
                    }
                    // add new row when we are at the last row
                    else if (pos.getRow() == ingredientsTV.getItems().size() - 1) {
                        addRow(ingredientsTV);
                    }
                }
            }
        });
    }

    protected void initStepsTV(ObservableList<Step> stepObservableList) {
        stepOrderCol.setCellValueFactory(cellValue -> new SimpleIntegerProperty(cellValue.getValue().getStepOrder()));
        stepDescriptionCol
                .setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getStepDescription()));

        stepDescriptionCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String fromString(String string) {
                return string;
            }

            @Override
            public String toString(String object) {
                return object.toString();
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
        stepsTV.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER) {
                    // event.consume(); // don't consume the event or else the
                    // values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't
                // already in edit mode
                if (stepsTV.getEditingCell() == null) {
                    if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = stepsTV.getFocusModel().getFocusedCell();
                        stepsTV.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });

        stepsTV.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.TAB) {

                    return;
                }

                if (event.getCode() == KeyCode.ENTER) {
                    // move focus & selection
                    TablePosition pos = stepsTV.getFocusModel().getFocusedCell();

                    if (pos.getRow() == -1) {
                        stepsTV.getSelectionModel().select(0);
                    }
                    // select next cell in same row
                    else if (pos.getColumn() < stepsTV.getColumns().size() - 1) {
                        // ingredientsTV.getSelectionModel().clearAndSelect(
                        // pos.getRow(),
                        // ingredientsTV.getColumns().get(pos.getColumn() + 1));
                        stepsTV.getSelectionModel().selectNext();
                    }
                    // select first cell in next row, when reaching one row's
                    // end
                    else if (pos.getColumn() == stepsTV.getColumns().size() - 1
                            && pos.getRow() != stepsTV.getItems().size() - 1) {
                        stepsTV.getSelectionModel().clearAndSelect(pos.getRow() + 1, stepsTV.getColumns().get(1));
                    }
                    // add new row when we are at the last row
                    else if (pos.getRow() == stepsTV.getItems().size() - 1) {
                        addRow(stepsTV);
                    }
                }
            }
        });
    }

    protected void initBtns() {
        ingredientsAddRowBtn.setOnAction(event -> addRow(ingredientsTV));
        ingredientsRemoveRowBtn.setOnAction(event -> removeRow(ingredientsTV));
        stepsAddRowBtn.setOnAction(event -> addRow(stepsTV));
        stepsRemoveRowBtn.setOnAction(event -> removeRow(stepsTV));

        saveRecipeBtn.setOnAction(event -> saveRecipe());
        cancelEditBtn.setOnAction(event -> {
            try {
                cancelEdit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ingredientsAddRowBtn.setFocusTraversable(false);
        ingredientsRemoveRowBtn.setFocusTraversable(false);
        stepsAddRowBtn.setFocusTraversable(false);
        stepsRemoveRowBtn.setFocusTraversable(false);
    }

    protected void addRow(TableView tableView) {
        TablePosition pos = tableView.getFocusModel().getFocusedCell();
        tableView.getSelectionModel().clearSelection();
        Ingredient ingredient;
        Step step;
        int row = tableView.getItems().size() - 1;
        if (tableView.getId().equals("ingredientsTV")) {
            ingredient = new Ingredient("", 0, "");
            tableView.getItems().add(ingredient);
            tableView.getSelectionModel().select(row + 1, (TableColumn) tableView.getColumns().get(0));
            tableView.scrollTo(ingredient);
        } else if (tableView.getId().equals("stepsTV")) {
            step = new Step(row + 2, "");
            tableView.getItems().add(step);
            tableView.getSelectionModel().select(row + 1, pos.getTableColumn());
            tableView.scrollTo(step);
        }
    }

    protected void removeRow(TableView tableView) {
        TablePosition pos = tableView.getFocusModel().getFocusedCell();
        int row = pos.getRow();
        int col = pos.getColumn();
        tableView.getSelectionModel().clearSelection();
        tableView.getItems().remove(row);
        if (row < tableView.getItems().size()) {
            ingredientsTV.getSelectionModel().select(row, (TableColumn) tableView.getColumns().get(col));
        } else {
            ingredientsTV.getSelectionModel().select(row - 1, (TableColumn) tableView.getColumns().get(col));
        }
    }

    protected void saveRecipe() {
        if (!isValid()) {
            return;
        }

        // TODO implement save recipe
        int save = JOptionPane.showConfirmDialog(null, "Do you want to save this recipe?", null,
                JOptionPane.YES_NO_OPTION);

        if (JOptionPane.YES_OPTION == save) {

            Recipe newRecipe = new Recipe();

            newRecipe.setName(titleFld.getText());

            int servingNum = Integer.parseInt(servingsFld.getText());

            newRecipe.setServingNum(servingNum);

            int preparationTime = Integer.parseInt(preparationTimeFld.getText());

            newRecipe.setPreparationTime(preparationTime);

            int cookingTime = Integer.parseInt(cookingTimeFld.getText());

            newRecipe.setCookingTime(cookingTime);

            recipeDAO.addRecipe(newRecipe);

            for(Step step: steps) {
                step.setRecipeId(newRecipe.getId());
                System.out.println(newRecipe.getId());
                myStepDAO.addStep(step);
            }
            for(Ingredient ingredient: ingredients) {
                ingredient.setRecipeId(newRecipe.getId());
                System.out.println(newRecipe.getId());
                myIngredientDAO.addIngredient(ingredient);
            }
    }

}


    protected void cancelEdit() throws IOException {
        // TODO implement cancelEdit
        TemplateController.loadContent("/view/MainPage.fxml");
    }

    protected boolean isValid() {
        if (titleFld.getText().isEmpty() || servingsFld.getText().isEmpty() || preparationTimeFld.getText().isEmpty()
                || cookingTimeFld.getText().isEmpty()) {

            // Jpane alert
            JOptionPane.showMessageDialog(null, "Please fill the Title, Servings, Preparation Time and Cook Time!",
                    null, JOptionPane.ERROR_MESSAGE);

            return false;
        } else {
            return true;
        }
    }

    protected void cellEditCommitForIngredient(TableColumn.CellEditEvent event) {
        Object newValue = event.getNewValue();
        TablePosition pos = event.getTablePosition();
        int row = pos.getRow();
        int col = pos.getColumn();
        switch (col) {
            case 0:
                break;
            case 1:
                ingredientsTV.getItems().get(row).setName(newValue.toString());
                break;
            case 2:
                ingredientsTV.getItems().get(row).setQuantity(Double.parseDouble(newValue.toString()));
                break;
            case 3:
                ingredientsTV.getItems().get(row).setUnit(newValue.toString());
                break;
        }
    }

    protected void cellEditCommitForStep(TableColumn.CellEditEvent event) {
        Object newValue = event.getNewValue();
        TablePosition pos = event.getTablePosition();
        int row = pos.getRow();
        int col = pos.getColumn();
        switch (col) {
            case 1:
                stepsTV.getItems().get(row).setDescription(newValue.toString());
                break;
        }
    }

//    protected Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>> createStringCellFactory() {
//        Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>> factory = TextFieldTableCell
//                .forTableColumn(new StringConverter<String>() {
//
//                    @Override
//                    public String fromString(String string) {
//                        return string;
//                    }
//
//                    @Override
//                    public String toString(String object) {
//                        return object.toString();
//                    }
//                });
//
//        return factory;
//    }

    protected Callback<TableColumn<Ingredient, Number>, TableCell<Ingredient, Number>> createDoubleCellFactory() {
        Callback<TableColumn<Ingredient, Number>, TableCell<Ingredient, Number>> factory = TextFieldTableCell
                .forTableColumn(new StringConverter<Number>() {

                    @Override
                    public Number fromString(String string) {
                        try{
                            return Double.parseDouble(string);
                        }
                        catch (NumberFormatException e){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Only number allowed in \"Quantity\"");
                            alert.setHeaderText(null);
                            alert.setContentText("Your change of quantity to \"" + string + "\" is not valid. \n For an egg, you can set quantity to 0 and leave the unit blank.");
                            alert.showAndWait();
                        }
                        return 0d;
                    }

                    @Override
                    public String toString(Number object) {
                        return object.toString();
                    }
                });

        return factory;
    }

    public AddRecipeViewController() {

    }
}
