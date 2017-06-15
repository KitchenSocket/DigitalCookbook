package model;

import java.sql.Date;

/**
 * Java object for recipe model
 * 
 * @author VanillaChocola CHANDIM
 * @version 1.0
 * 
 */
public class Recipe {

	private int id;
	private String name;
	private String briefDescription;	
	private String thumbnail;
	private String description;
	private int servingNum;
	private int isFavorite;
	private int preparationTime;
	private int cookingTime;
	private Date createdAt;
	private Date deletedAt;
	private Date updatedAt;
	private String test;

	public Recipe() {
		
		//
		
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

	public int getIsFavorite() {
		return this.isFavorite;
	}
	
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}

	public int getServingNum() {
		return this.servingNum;
	}

	public void setServingNum(int servingNum) {
		this.servingNum = servingNum;
	}

	@Override
	public String toString() {
		return "Recipe" + '\n' + "====================" + '\n' + 
				"Name: " + name + '\n' + "ServingNum: " + servingNum + '\n' + 
				"BriefDescription: " + briefDescription + '\n' + 
				"Thumbnail: " + thumbnail + '\n' + "PreparationTime: " + preparationTime + '\n' + 
				"CreatedAt: " + createdAt + '\n' + "DeletedAt: " + deletedAt + '\n' +
				"UpdatedAt: " + updatedAt + '\n'
				+ "IsFavorite=" + isFavorite + '\n' + "====================" + '\n';
	}
}
