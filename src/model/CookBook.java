package model;

import java.sql.*;
import java.util.LinkedList;

import javafx.application.Application;
import view.Template;

/**
 * A class for the program entry point and some test recipes.
 *
 * @author Shi Wenbin, Gang Shao
 * 
 */
public class CookBook {

	private LinkedList<Recipe> myRecipeList = new LinkedList<>();
	
  
    /**
     * Program entry point.
     *
     * @param args  command line arguments; not used.
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
    	
      Application.launch(Template.class, args);

    }

//	private void add(Recipe recipe) {
//		// TODO Auto-generated method stub
//		myRecipeList.add(recipe);
//		
//	}
	
//	public void getRecipe(String recipeName) {
//		
//		System.out.println(Recipe.search(recipeName, myRecipeList));
//		
//	}
}