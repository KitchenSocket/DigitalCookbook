package DAO;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import inter.StepOperation;
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
    	//DAO.getStepById(2);
    	DAO.getStepListByDescription("prok");
    	//DAO.getStepListByRecipyId(1);
  }
    
    /**
     * returns the required Step class by the received id
     * 
     * @param id
     * @return Step class
     */
    public Step getStepById(int id){
    	
    	//get resources
    	Step result = new Step();
    	
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StepOperation stepOperation=session.getMapper(StepOperation.class);           
			result = stepOperation.selectStepByID(id);	
			
			//test code
			//System.out.println(result);
		} finally {
		    session.close();
		}
		return result;
    }
  
    /**
     * returns an ArrayList of class Step by description
     * 
     * @param description
     * @return ArrayList<Step>
     * @throws IOException 
     */
    public ArrayList<Step> getStepListByDescription(String description) throws IOException{
    	//correct spell
    	String subWord = new Correct("words.txt").correct(description);
    	
    	//get resources
    	String searchDescription = "%"+subWord+"%";	     
    	ArrayList<Step> results = new ArrayList<Step>();
	  
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StepOperation stepOperation=session.getMapper(StepOperation.class);           
		    List<Step> steps = stepOperation.selectStepsByDescription(searchDescription);
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
    //TODO
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
     * update a step in database 
     * 
     * @param step
     */    
    //TODO
    public void updateStep(Step step){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	StepOperation stepOperation=session.getMapper(StepOperation.class);
        	stepOperation.updateStep(step);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    /**
     * delete a step in database by id
     * 
     * @param id
     */
    //TODO
    public void deleteStep(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	StepOperation stepOperation=session.getMapper(StepOperation.class);
        	stepOperation.deleteStep(id);
            session.commit();
        } finally {
            session.close();
        }
    }
}
