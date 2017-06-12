/**
 * A Recipe Class
 * 
 * @author Qiwen Gu, Gang Shao
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedList;

public class Recipe implements Serializable {

	private int id;
	private String name;
	private String briefDescription;	
	private String thumbnail;
	private String description;
	private int servingNum;
	private int isFavourite;
//	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
//	private ArrayList<Step> steps = new ArrayList<Step>();
	private int preparationTime;
	private int cookingTime;
	private Date createdAt;
	private Date deletedAt;
	private Date updatedAt;
	private String test;

	public Recipe() {
		super();
		this.name = "";
		this.briefDescription = "";
		this.description = "";
		this.thumbnail = "";
		this.preparationTime = 0;
		this.cookingTime = 0;
		this.createdAt = null;
		this.deletedAt = null;
		this.updatedAt = null;
		this.isFavourite = 0;
		this.servingNum = 0;
	}
	
	public String getTest(){
		return this.test;
	}
	
	public void setTest(String test) {
		this.test = test;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBriefDescription() {
		return briefDescription;
	}

	public void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}

	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

//	public ArrayList<Ingredient> getIngredients() {
//		return ingredients;
//	}
//
//	public void setIngredients(ArrayList<Ingredient> ingredients) {
//		this.ingredients = ingredients;
//	}
//
//	public ArrayList<Step> getSteps() {
//		return steps;
//	}
//
//	public void setSteps(ArrayList<Step> steps) {
//		this.steps = steps;
//	}

	public int getPreparationTime() {
		return this.preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public int getCookingTime() {
		return this.cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeletedAt() {
		return this.deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int isFavourite() {
		return this.isFavourite;
	}
	
	public void setIsFavourite(int isFavouritet) {
		this.isFavourite = isFavouritet;
	}

	public int getServingNum() {
		return this.servingNum;
	}

	public void setServingNum(int servingNum) {
		this.servingNum = servingNum;
	}

	/**
	 * Search for Recipes
	 * 
	 * @param name
	 *            Recipe name
	 * @return target recipes
	 */
	public static String search(String name, LinkedList<Recipe> myRecipeList) {

		String results = "";

		String[] search = name.toLowerCase().split(" ");
		for (int recipeId = 0; recipeId < myRecipeList.size(); recipeId++) {// ArrayList
			// Recipes
			String recipeName = myRecipeList.get(recipeId).getName().toLowerCase();
			String[] recipe = recipeName.split(" ");
			for (int recipeNum = 0; recipeNum < recipe.length; recipeNum++) {

				for (int searchNum = 0; searchNum < search.length; searchNum++) {

					if (recipe[recipeNum].equals(search[searchNum])) {
						results = results + myRecipeList.get(recipeId).toString();
						break;
					}
				}
				break;
			}

		}
		return results;
	}

	@Override
	public String toString() {
		return "Recipe" + '\n' + "====================" + '\n' + 
				"Name: " + name + '\n' + "ServingNum: " + servingNum + '\n' + 
				"BriefDescription: " + briefDescription + '\n' + 
				"Thumbnail: " + thumbnail + '\n' + "PreparationTime: " + preparationTime + '\n' + 
				"CreatedAt: " + createdAt + '\n' + "DeletedAt:" + '\n' + deletedAt + 
				"UpdatedAt: " + updatedAt + '\n'
				+ "IsFavourite=" + isFavourite + '\n' + "====================" + '\n';
	}
}
