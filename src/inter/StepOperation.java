package inter;

import java.util.ArrayList;
import java.util.Map;

import model.Step;

public interface StepOperation {
	
	public Step selectStepByID(int id);
	
	public Step selectStepByRecipeIdAndOrder(Map<String, Integer> map);
	
	public ArrayList<Step> selectStepsByDescription(String description);
	
	public ArrayList<Step> selectStepsByRecipeId(int recipeId);
	
	public void addStep(Step step);
	
	public void updateStep(Step step);
	
	public void deleteStep(int id);
	
	public void deleteStepListByRecipeId(int recipeId);
	
}
