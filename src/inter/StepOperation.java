package inter;

import java.util.List;

import model.Step;

public interface StepOperation {
	
	public Step selectStepByID(int id);
	
	public List<Step> selectSteps(String name);
	
	public List<Step> selectStepsByRecipeId(int recipeId);
	
	public void addStep(Step step);
	
	public void updateStep(Step step);
	
	public void deleteStep(int id);

}
