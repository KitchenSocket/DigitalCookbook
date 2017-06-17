package model;

/**
 * Java object for step model
 * 
 * @author VanillaChocola CHANDIM
 * @version 1.0
 * 
 */

public class Step {
	
	private int recipeId;
	
	private int stepOrder;
	
	private String description;
	
	private String pic;
		
	public Step() {
		
		//
		
	}
	
	/**
	 * @param stepOrder
	 * @param description
	 */
	public Step(int stepOrder, String description) {
		super();
		this.stepOrder = stepOrder;
		this.description = description;
	}

	public int getRecipeId() {		
		return this.recipeId;		
	}

	public void setRecipeId(int recipeId) {	
		this.recipeId = recipeId;		
	}
	
	public int getStepOrder() {		
		return this.stepOrder;		
	}

	public void setStepOrder(int stepOrder) {	
		this.stepOrder = stepOrder;		
	}

	public String getStepDescription() {		
		return this.description;		
	}

	public void setDescription(String description) {	
		this.description = description;		
	}

	public String getPic() {		
		return this.pic;		
	}

	public void setPic(String pic) {		
		this.pic = pic;		
	}
	
	@Override
	public String toString() {
		return  "Step" + '\n' + "====================" + '\n' + 
				"recipeId: " + recipeId + '\n' + "stepOrder: " + stepOrder + '\n' + 
				"description: " + description + '\n' + 
				"pic: " + pic + '\n' + "====================" + '\n';
	}

}
