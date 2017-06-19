package model;

import java.util.ArrayList;

import DAO.IngredientDAO;
import DAO.RecipeDAO;
import DAO.StepDAO;
import model.Ingredient;
import model.Recipe;
import model.Step;

public class Book {
	
	final RecipeDAO recipeDAO = new RecipeDAO();
	final IngredientDAO ingredientDAO = new IngredientDAO();
	final StepDAO stepDAO = new StepDAO();
	
	private String recipeName;
	private int recipePreTime;
	private int recipeCookTime;
	private String recipeDescrip;
	private int recipeServNum;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<Step> steps;
	
	public Book() {
//
	}

	public RecipeDAO getRecipeDAO() {
		return recipeDAO;
	}

	public IngredientDAO getIngredientDAO() {
		return ingredientDAO;
	}

	public StepDAO getStepDAO() {
		return stepDAO;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public int getRecipePreTime() {
		return recipePreTime;
	}

	public int getRecipeCookTime() {
		return recipeCookTime;
	}

	public String getRecipeDescrip() {
		return recipeDescrip;
	}

	public int getRecipeServNum() {
		return recipeServNum;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public void setRecipePreTime(int recipePreTime) {
		this.recipePreTime = recipePreTime;
	}

	public void setRecipeCookTime(int recipeCookTime) {
		this.recipeCookTime = recipeCookTime;
	}

	public void setRecipeDescrip(String recipeDescrip) {
		this.recipeDescrip = recipeDescrip;
	}

	public void setRecipeServNum(int recipeServNum) {
		this.recipeServNum = recipeServNum;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		return "Book [recipeName=" + recipeName + ", recipePreTime=" + recipePreTime + ", recipeCookTime="
				+ recipeCookTime + ", recipeDescrip=" + recipeDescrip + ", recipeServNum=" + recipeServNum
				+ ", ingredients=" + ingredients + ", steps=" + steps + "]";
	}
	
	public Book(Recipe recipe) {
		this.recipeName = recipe.getName();
		this.recipePreTime = recipe.getPreparationTime();
		this.recipeCookTime = recipe.getCookingTime();
		this.recipeDescrip = recipe.getDescription();
		this.recipeServNum = recipe.getServingNum();
		this.ingredients = this.getIngredientDAO().getIngredientListByRecipyId(recipe.getId());
		this.steps = this.getStepDAO().getStepListByRecipyId(recipe.getId());

	}
	
}
