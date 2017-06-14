package DAO;

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
    
    public static void main(String[] args) {
    	//Recipe r = new Recipe();
    	
    	RecipeDAO DAO=new RecipeDAO();
    	//r = DAO.getRecipeById(1);
    	//r.setCookingTime(90);
    	//DAO.getRecipeListByIngredientName("pork");
    	System.out.println();
    	System.out.println("===============================");
    	System.out.println();
    	DAO.getRecipeListByName("rou");
    	System.out.println();
    	System.out.println("===============================");
    	System.out.println();
    	//DAO.addFavorite(3);
    	DAO.removeFavorite(3);
    	//DAO.getRecipeListByIngredientName("a");
    	//DAO.getRecipeListByIngredientNameInFavourite("o");
    }
    
    
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
    
    public Recipe getRecipeByIdInFavourite(int id){
    	
    	//get resources
    	Recipe result = new Recipe();
    	
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);           
			result = recipeOperation.selectRecipeByIDInFavourite(id);	
			
			//test code
			//System.out.println(result);
		} finally {
		    session.close();
		}
		return result;
    }
  
    /**
     * get Receipe list by name
     * @param name
     * @return
     */
    public ArrayList<Recipe> getRecipeListByName(String name){
    	//get resources
    	String searchName = "%"+name+"%";	  

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
     * get Receipes by name in favourite
     * @param name
     * @return
     */
    public ArrayList<Recipe> getRecipeListByNameInFavourite(String name){
    	//get resources
    	String searchName = "%"+name+"%";	     
    	ArrayList<Recipe> results = new ArrayList<>();
	  
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);           
		    List<Recipe> recipes = recipeOperation.selectRecipesInFavourite(searchName);
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
    
    public ArrayList<Recipe> getRecipeListByIngredientName(String name){
    	//get resources
    	String searchName = "%"+name+"%";
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
    
    public ArrayList<Recipe> getRecipeListByIngredientNameInFavourite(String name){
    	//get resources
    	String searchName = "%"+name+"%";
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
	    		temp = this.getRecipeByIdInFavourite(recipeId);
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
     * need alter
     * @param recipe
     */
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
     * need alter
     * @param recipe
     */
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
     * need alter
     * @param int
     */
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
     * need alter
     * @param int
     */
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
    
    
    
    /**
     * need alter
     * @param recipe
     */
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
}
