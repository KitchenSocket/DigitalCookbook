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
import inter.RecipeOperation;
import model.Ingredient;
import model.Recipe;

/**
 * Recipe data access object class
 * contains public functions for front end to call
 * 
 * @author VanillaChocola CHANDIM
 * @version 1.0
 *
 */
public class RecipeDAO {
	
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
    	//Recipe r = new Recipe();
    	
    	RecipeDAO DAO=new RecipeDAO();
    	//r = DAO.getRecipeById(1);
    	//r.setCookingTime(90);
    	//DAO.getRecipeListByIngredientName("pork");
    	System.out.println();
    	System.out.println("===============================");
    	System.out.println();
    	//DAO.getRecipeListByIngredientNameInFavorite("pork");
    	
    	
    	//DAO.getRecipeListByName("yu xiang");
    	//DAO.getRecipeListByNameInFavorite("mapo dofu rou");
    	//DAO.getRecipeListByIngredientName("prok");

    	//Recipe r = new Recipe();
    	//r = DAO.getRecipeById(2);
    	//DAO.addRecipe(r);
    	System.out.println();
    	System.out.println("===============================");
    	System.out.println();
    	//DAO.addFavorite(3);
    	//DAO.removeFavorite(3);
    	//DAO.getRecipeListByIngredientName("a");
    	//DAO.getRecipeListByIngredientNameInFavourite("o");
    }
    
    /**
     * returns the required Recipe class by the received id
     * 
     * @param id
     * @return Recipe class
     */
    public Recipe getRecipeById(int id){
    	
    	//get resources
    	Recipe result = new Recipe();
    	
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);           
			result = recipeOperation.selectRecipeByID(id);	
			
			//test code
			//System.out.println(result);
		} finally {
		    session.close();
		}
		return result;
    }
    
    /**
     * returns the required Recipe class by the received id in favorite
     * 
     * @param id
     * @return Recipe class
     */
    public Recipe getRecipeByIdInFavorite(int id){
    	
    	//get resources
    	Recipe result = new Recipe();
    	
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);           
			result = recipeOperation.selectRecipeByIDInFavorite(id);	
			
			//test code
			//System.out.println(result);
		} finally {
		    session.close();
		}
		return result;
    }
  
    /**
     * returns an ArrayList of class recipe by name
     * 
     * @param name
     * @return ArrayList<Recipe>
     * @throws IOException 
     */
    public ArrayList<Recipe> getRecipeListByName(String name) throws IOException{
    	//correct spell
    	String subWord = new Correct("words.txt").correct(name);
    	
    	//get resources
    	String searchName = "%"+subWord+"%";	  

    	//String searchName = name;	
    	ArrayList<Recipe> results = new ArrayList<>();
	  
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);           
		    List<Recipe> recipes = recipeOperation.selectRecipes(searchName);
		    for(Recipe recipe:recipes){
		    	//test code
		    	System.out.println(recipe);
		    	if(recipe != null)
		    		results.add(recipe);
		    }
		
		} finally {
		    session.close();
		}
		return results;
	  }
    
    
    
    /**
     * returns an ArrayList of class recipe by name in favorite
     * 
     * @param name
     * @return ArrayList<Recipe>
     * @throws IOException 
     */
    public ArrayList<Recipe> getRecipeListByNameInFavorite(String name) throws IOException{
    	//correct spell
    	String subWord = new Correct("words.txt").correct(name);
    	
    	//get resources
    	String searchName = "%"+subWord+"%";	     
    	ArrayList<Recipe> results = new ArrayList<>();
	  
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);           
		    List<Recipe> recipes = recipeOperation.selectRecipesInFavorite(searchName);
		    for(Recipe recipe:recipes){
		    	
		    	//test code
		    	//System.out.println(recipe);
		    	if(recipe != null)
		    		results.add(recipe);
		    }
		
		} finally {
		    session.close();
		}
		return results;
	  }
    
    /**
     * returns an ArrayList of class recipe by an ingredient name
     * 
     * @param name
     * @return ArrayList<Recipe>
     * @throws IOException 
     */
    public ArrayList<Recipe> getRecipeListByIngredientName(String name) throws IOException{
    	//correct spell
    	String subWord = new Correct("words.txt").correct(name);
    	
    	//get resources
    	String searchName = "%"+subWord+"%";
    	Recipe temp = new Recipe();
    	ArrayList<Recipe> results = new ArrayList<>();
    	ArrayList<Integer> recipeIds = new ArrayList<Integer>();
    	ArrayList<Integer> noRepeat = new ArrayList<Integer>();
    	
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);           
		    List<Ingredient> ingredients = ingredientOperation.selectIngredients(searchName);
		    for(Ingredient ingredient:ingredients){
		    	
		    	//test code
		    	//System.out.println(ingredient);
		    	
		    	recipeIds.add(ingredient.getRecipeId());		    	
		    }
		    for(int recipeId:recipeIds){
	    		if(!noRepeat.contains(recipeId))
	    			noRepeat.add(recipeId);
	    	}
	    	for (int recipeId:noRepeat){
	    		temp = this.getRecipeById(recipeId);
	    		if(temp != null)
	    			results.add(temp);
	    		
	    		//test code
		    	//System.out.println(temp);
	    	}
		
		} finally {
		    session.close();
		}
		
		//test code
    	System.out.println(results);
		return results;    	
    }
    
    /**
     * returns an ArrayList of class recipe by an ingredient name in favorite
     * 
     * @param name
     * @return ArrayList<Recipe>
     * @throws IOException 
     */
    public ArrayList<Recipe> getRecipeListByIngredientNameInFavorite(String name) throws IOException{
    	//correct spell
    	String subWord = new Correct("words.txt").correct(name);
    	
    	//get resources
    	String searchName = "%"+subWord+"%";
    	Recipe temp = new Recipe();
    	ArrayList<Recipe> results = new ArrayList<>();
    	ArrayList<Integer> recipeIds = new ArrayList<Integer>();
    	ArrayList<Integer> noRepeat = new ArrayList<Integer>();
    	
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IngredientOperation ingredientOperation=session.getMapper(IngredientOperation.class);           
		    List<Ingredient> ingredients = ingredientOperation.selectIngredients(searchName);
		    for(Ingredient ingredient:ingredients){
		    	
		    	//test code
		    	//System.out.println(ingredient);
		    	
		    	recipeIds.add(ingredient.getRecipeId());		    	
		    }
		    for(int recipeId:recipeIds){
	    		if(!noRepeat.contains(recipeId))
	    			noRepeat.add(recipeId);
	    	}
	    	for (int recipeId:noRepeat){
	    		temp = this.getRecipeByIdInFavorite(recipeId);
	    		if(temp != null)
	    			results.add(temp);
	    		
	    		//test code
		    	//System.out.println(temp);
		    	//System.out.println("+++++++++++++++++++++++++++++");
	    	}
	    	
		
		} finally {
		    session.close();
		}
		
		//test code
    	System.out.println(results);
		return results;    	
    }
    
    /**
     * add a new Recipe into database
     * 
     * @param recipe
     */
    //TODO
    public void addRecipe(Recipe recipe){
    	
    	//execute sql
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);
        	recipeOperation.addRecipe(recipe);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    /**
     * update a Recipe in database 
     * 
     * @param recipe
     */    
    //TODO
    public void updateRecipe(Recipe recipe){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);
        	recipeOperation.updateRecipe(recipe);
            session.commit();
        } finally {
            session.close();
        }
    }
        
    /**
     * delete a Recipe in database by id
     * 
     * @param id
     */
    //TODO
    public void deleteRecipe(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);
        	recipeOperation.deleteRecipe(id);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    
    /**
     * add a Recipe into favorite
     * 
     * @param id
     */
    //TODO
    public void addFavorite(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);
        	recipeOperation.addFavorite(id);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    /**
     * remove a Recipe from favorite
     * 
     * @param id
     */
    //TODO
    public void removeFavorite(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);
        	recipeOperation.removeFavorite(id);
            session.commit();
        } finally {
            session.close();
        }
    }
}
