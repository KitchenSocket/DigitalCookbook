package model;

import javax.sound.midi.VoiceStatus;

/**
 * 
 * A Step Class which contains a step description and a Uri of picture depicting the step. 
 * 
 * @author Wenbin Shi
 * 
 * @version 1.0
 * 
 * */

public class Step {
	
	private int recipeId;
	
	private int stepOrder;
	
	private String description;
	
	private String pic;
	
	/**
	 * Constructor to construct a Step object with step description and picUri.
	 * 
	 * @param stepDescription String. 
	 * 
	 * @param picUri uri which points to the picture.
	 * 
	 * @return null.
	 * 
	 * */
	
	public Step() {
		
		//
		
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
