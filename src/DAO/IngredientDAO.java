package DAO;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import inter.IngredientOperation;
import model.Ingredient;

/**
 * Ingredient data access object class
 * contains public functions for front end to call
 * 
 * @author VanillaChocola CHANDIM
 * @version 1.0
 *
 */
public class IngredientDAO {
	
	private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader; 

    static{
        try{
            reader    = Resources.getResourceAsReader("Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }
    
    /**
     * main function, mainly for testing
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	
    	IngredientDAO DAO=new IngredientDAO();
    	
    	DAO.getIngredientListByName("%");
    	
    	//DAO.deleteIngredientListByRecipeId(3);

    	DAO.getIngredientListByRecipyId(2);
  }
  
    /**
     * returns an ArrayList of class Ingredient by name
     * 
     * @param name
     * @return ArrayList<Ingredient>
     * @throws IOException 
     */
    public ArrayList<Ingredient> getIngredientListByName(String name) throws IOException{
    	//correct spell
    	String subWord = new Correct("words.txt").correct(name);
    	
    	//get resources
    	String searchName = "%"+subWord+"%";	     
    	ArrayList<Ingredient> results = new ArrayList<Ingredient>();
	  
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);           
		    List<Ingredient> ingredients = ingredientOperation.selectIngredients(searchName);
		    for(Ingredient ingredient:ingredients){
		    	
		    	//test code
		    	System.out.println(ingredient);
		        results.add(ingredient);
		    }
		
		} finally {
		    session.close();
		}
		
		return results;
	  }
    
    /**
     * returns an ArrayList contains all the ingredients belonging to a recipe
     * 
     * @param recipeId
     * @return ArrayList<Ingredient>
     */
    public ArrayList<Ingredient> getIngredientListByRecipyId(int recipeId){
    	//get resources 
    	ArrayList<Ingredient> results = new ArrayList<Ingredient>();
	  
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);           
		    List<Ingredient> ingredients = ingredientOperation.selectIngredientsByRecipeId(recipeId);
		    for(Ingredient ingredient:ingredients){
		    	//test code
		    	System.out.println(ingredient);
		        results.add(ingredient);
		    }
		
		} finally {
		    session.close();
		}
		return results;
	  }
    
    /**
     * add a new Ingredient into database
     * 
     * @param step
     * @throws IOException 
     */
    //TODO
    public void addIngredient(Ingredient ingredient) throws IOException{
    	
    	//execute sql
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);
        	ingredientOperation.addIngredient(ingredient);
            session.commit();
        } finally {
            session.close();
        }
        new Correct("words.txt").updateDict(ingredient.getName());
        new Correct("words.txt").initDict();
    }
    
    /**
     * update all the ingredients of a recipe
     * 
     * @param ingredients
     * @throws IOException 
     */  
    public void updateIngredients(ArrayList<Ingredient> ingredients) throws IOException{
        int recipeId = ingredients.get(0).getRecipeId();
        deleteIngredientListByRecipeId(recipeId);
        for(Ingredient ingredient : ingredients) {
        	addIngredient(ingredient);
            new Correct("words.txt").updateDict(ingredient.getName());
        }
        new Correct("words.txt").initDict();
    }
   
    /**
     * delete all the ingredients from a recipe by recipe id
     * 
     * @param recipeId
     * @throws IOException 
     */
    //TODO
    public void deleteIngredientListByRecipeId(int recipeId) throws IOException{
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);
        	ingredientOperation.deleteIngredientListByRecipeId(recipeId);
            session.commit();
        } finally {
            session.close();
        }
        new Correct("words.txt").initDict();
    }
    
}
