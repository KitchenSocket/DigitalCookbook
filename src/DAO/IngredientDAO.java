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
    	DAO.getIngredientListByName("prok");

    	//DAO.getIngredientListByRecipyId(2);
    	//DAO.getIngredientById(2);
  }
    
    /**
     * returns the required Ingredient class by the received id
     * 
     * @param id
     * @return Ingredient class
     */
    public Ingredient getIngredientById(int id){
    	
    	//get resources
    	Ingredient result = new Ingredient();
    	
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);           
			result = ingredientOperation.selectIngredientByID(id);	
			
			//test code
			//System.out.println(result);
		} finally {
		    session.close();
		}
		return result;
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
     */
    //TODO
    public void addIngredient(Ingredient ingredient){
    	
    	//execute sql
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);
        	ingredientOperation.addIngredient(ingredient);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    /**
     * update an Ingredient in database 
     * 
     * @param ingredient
     */    
    //TODO
    public void updateIngredient(Ingredient ingredient){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);
        	ingredientOperation.updateIngredient(ingredient);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    /**
     * delete an Ingredient in database by id
     * 
     * @param id
     */
    //TODO
    public void deleteIngredient(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);
        	ingredientOperation.deleteIngredient(id);
            session.commit();
        } finally {
            session.close();
        }
    }
}
