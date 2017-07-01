package DAO;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import inter.StepOperation;
import model.Ingredient;
import model.Step;

/**
 * Step data access object class
 * contains public functions for front end to call
 * 
 * @author VanillaChocola CHANDIM
 * @version 1.0
 * 
 */
public class StepDAO {
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
    	
    	StepDAO DAO=new StepDAO();
    	
    	DAO.deleteStepListByRecipeId(7);
    	
    	DAO.getStepListByRecipyId(2);

    	
  }
    
    /**
     * returns an ArrayList contains all the steps belonging to a recipe
     * 
     * @param recipeId
     * @return ArrayList<Step>
     */
    public ArrayList<Step> getStepListByRecipyId(int recipeId){
    	//get resources     
    	ArrayList<Step> results = new ArrayList<Step>();
	  
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StepOperation stepOperation=session.getMapper(StepOperation.class);           
		    List<Step> steps = stepOperation.selectStepsByRecipeId(recipeId);
		    for(Step step:steps){
		    	//test code
		    	System.out.println(step);
		        results.add(step);
		    }
		
		} finally {
		    session.close();
		}
		return results;
	  }
    
    
    /**
     * add a new Step into database
     * 
     * @param step
     */
    public void addStep(Step step){
    	
    	//execute sql
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	StepOperation stepOperation=session.getMapper(StepOperation.class);
        	stepOperation.addStep(step);
            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * update all the steps of a recipe
     * 
     * @param steps
     */ 
    public void updateSteps(ArrayList<Step> steps){
        int recipeId = steps.get(0).getRecipeId();
        deleteStepListByRecipeId(recipeId);
        for(Step step : steps) {
        	addStep(step);
        }
    } 

    
    /**
     * delete all the steps from a recipe by recipe id
     * 
     * @param recipeId
     */
    public void deleteStepListByRecipeId(int recipeId){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	StepOperation stepOperation=session.getMapper(StepOperation.class);
        	stepOperation.deleteStepListByRecipeId(recipeId);
            session.commit();
        } finally {
            session.close();
        }
    }
    

}
