package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Ingredient;

/**
 * Created by fexac on 13-Jun-17.
 */
public class AddRecipeViewController {
    private static ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(new Ingredient("good", 0.2, "g"),new Ingredient("good1", 0.1, "g"), new Ingredient("good2", 0.3, "g"));

    @FXML
    TableView<Ingredient> ingredientsTV;

    @FXML
    TableColumn<Ingredient, String> ingredientNameCol;
    
    @FXML
    TableColumn<Ingredient, Number> ingredientQuantityCol;
    
    @FXML
    TableColumn<Ingredient, String> ingredientUnitCol;
    



    public void addRow() {
        TablePosition pos = ingredientsTV.getFocusModel().getFocusedCell();
        ingredientsTV.getSelectionModel().clearSelection();
        //TODO ingredient constructor with parameters
        Ingredient ingredient = new Ingredient("", 0, "");
        //ingredientsTV.getItems().add(ingredient);
        ingredients.add(ingredient);
        int row = ingredientsTV.getItems().size() - 1;
        ingredientsTV.getSelectionModel().select(row, ingredientsTV.getColumns().get(0));
        ingredientsTV.scrollTo(ingredient);
    }

    @FXML
    private void initialize () {
        initIngredientsTV();
        initStepsTV();
    }



    private void initIngredientsTV() {
        ingredientNameCol.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getName()));
        ingredientQuantityCol.setCellValueFactory(cellValue -> new SimpleDoubleProperty(cellValue.getValue().getQuantity()));
        ingredientUnitCol.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getUnit()));

        ingredientNameCol.setCellFactory(createStringCellFactory());
        ingredientQuantityCol.setCellFactory(createDoubleCellFactory());
        ingredientUnitCol.setCellFactory(createStringCellFactory());

        ingredientsTV.setItems(ingredients);
        ingredientsTV.setEditable(true);
        ingredientsTV.getSelectionModel().setCellSelectionEnabled(true);
        //ingredientsTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ingredientNameCol.setOnEditCommit(event -> ingredientsTV.requestFocus());
        ingredientQuantityCol.setOnEditCommit(event -> ingredientsTV.requestFocus());
        ingredientUnitCol.setOnEditCommit(event -> ingredientsTV.requestFocus());

        // switch to edit mode on keypress
        // this must be KeyEvent.KEY_PRESSED so that the key gets forwarded to the editing cell; it wouldn't be forwarded on KEY_RELEASED
        ingredientsTV.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if( event.getCode() == KeyCode.ENTER) {
//                  event.consume(); // don't consume the event or else the values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( ingredientsTV.getEditingCell() == null) {
                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = ingredientsTV.getFocusModel().getFocusedCell();
                        ingredientsTV.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });

        ingredientsTV.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.TAB) {

                    return;
                }

                if( event.getCode() == KeyCode.ENTER ) {
                    // move focus & selection
                    // we need to clear the current selection first or else the selection would be added to the current selection since we are in multi selection mode
                    TablePosition pos = ingredientsTV.getFocusModel().getFocusedCell();

                    if (pos.getRow() == -1) {
                        ingredientsTV.getSelectionModel().select(0);
                    }
                    //select next cell in same row
                    else if (pos.getColumn() < ingredientsTV.getColumns().size() - 1) {
                        //ingredientsTV.getSelectionModel().clearAndSelect( pos.getRow(), ingredientsTV.getColumns().get(pos.getColumn() + 1));
                        ingredientsTV.getSelectionModel().selectNext();
                    }
                    // select first cell in next row, when reaching one row's end
                    else if (pos.getColumn() == ingredientsTV.getColumns().size() - 1 && pos.getRow() != ingredientsTV.getItems().size() -1) {
                        ingredientsTV.getSelectionModel().clearAndSelect( pos.getRow() + 1, ingredientsTV.getColumns().get(0));
                    }
                    // add new row when we are at the last row
                    else if (pos.getRow() == ingredientsTV.getItems().size() -1) {
                        addRow();
                    }
                }
            }
        });
    }

    public void initStepsTV() {

    }

    private Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>> createStringCellFactory() {
        Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>> factory = TextFieldTableCell.forTableColumn(new StringConverter<String>() {

            @Override
            public String fromString(String string) {
                return string;
            }

            @Override
            public String toString(String object) {
                return object.toString();
            }
        });

        return factory;
    }

    private Callback<TableColumn<Ingredient, Number>, TableCell<Ingredient, Number>>  createDoubleCellFactory() {
        Callback<TableColumn<Ingredient, Number>, TableCell<Ingredient, Number>> factory = TextFieldTableCell.forTableColumn(new StringConverter<Number>() {

            @Override
            public Number fromString(String string) {
                return Double.parseDouble(string);
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

//    public class BetterEditTableView<S> extends TableView<S>{
//        public BetterEditTableView() {
//            super();
//        }
//
//        public BetterEditTableView(ObservableList<S>) {
//            super();
//        }
//    }
}
