package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Ingredient;
import model.Step;

/**
 * Created by fexac on 18-Jun-17.
 */
public class EditRecipeViewController extends AddRecipeViewController{

    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(new Ingredient("original", 0.2, "g"),new Ingredient("original1", 0.1, "g"), new Ingredient("original2", 0.3, "g"));

    private ObservableList<Step> steps = FXCollections.observableArrayList(new Step(1,"Do What?"), new Step(2,"Just do what?"), new Step(3, "Please dooo what??"));

    @FXML
    private void initialize() {
        System.out.println("Init...");
        initIngredientsTV(ingredients);
        initBtns();
        initStepsTV(steps);
        initFlds();
    }


    /**
     * initialize all fields with givien Recipe
     */
    protected void initFlds() {
        //TODO add method to read from the original function
    }

    public EditRecipeViewController() {
        //super();
    }
}
