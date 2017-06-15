package inter;

import java.util.ArrayList;

import model.Step;

public interface StepOperation {
	
	public Step selectStepByID(int id);
	
	public ArrayList<Step> selectStepsByDescription(String description);
	
	public ArrayList<Step> selectStepsByRecipeId(int recipeId);
	
	public void addStep(Step step);
	
	public void updateStep(Step step);
	
	public void deleteStep(int id);

}
