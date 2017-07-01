package inter;

import java.util.ArrayList;
import java.util.Map;

import model.Step;

public interface StepOperation {
	
	public ArrayList<Step> selectStepsByRecipeId(int recipeId);
	
	public void addStep(Step step);
	
	public void deleteStepListByRecipeId(int recipeId);
	
}
