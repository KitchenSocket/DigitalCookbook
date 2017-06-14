package DAO;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import inter.StepOperation;
import model.Step;

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
    
    public static void main(String[] args) {
    	
    	StepDAO DAO=new StepDAO();
    	//DAO.getStepById(2);
    	DAO.getStepListByName("pork");
    	//DAO.getStepListByRecipyId(1);
  }
    
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
     * get step list by name
     * @param name
     * @return
     */
    public ArrayList<Step> getStepListByName(String name){
    	//get resources
    	String searchName = "%"+name+"%";	     
    	ArrayList<Step> results = new ArrayList<Step>();
	  
    	//execute sql
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StepOperation stepOperation=session.getMapper(StepOperation.class);           
		    List<Step> steps = stepOperation.selectSteps(searchName);
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
     * need alter
     * @param recipe
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
     * need alter
     * @param recipe
     */
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
     * need alter
     * @param recipe
     */
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
