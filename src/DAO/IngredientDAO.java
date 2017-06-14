package DAO;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import inter.IngredientOperation;
import model.Ingredient;

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
    
    public static void main(String[] args) {
    	
    	IngredientDAO DAO=new IngredientDAO();
    	//DAO.getIngredientListByName("pork");

    	DAO.getIngredientListByRecipyId(2);
    	//DAO.getIngredientById(2);
  }
    
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
     * get Ingredient list by name
     * @param name
     * @return
     */
    public ArrayList<Ingredient> getIngredientListByName(String name){
    	//get resources
    	String searchName = "%"+name+"%";	     
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
     * need alter
     * @param recipe
     */
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
     * need alter
     * @param recipe
     */
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
     * need alter
     * @param recipe
     */
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
