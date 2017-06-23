package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock.Catch;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Recipe;

/*
 * One Anchor in a listview, this class put each recipes' info to this anchor. And all the anchorView makes up the brief info list in the Main Page.
 * Waiting for user to click.
 * 
 * @version 1.0
 * 
 * @author Shi Wenbin
 */

public class BriefRecipeInMainPageController implements Initializable {

    @FXML
    private ImageView recipeImg;

    @FXML
    private Label recipeName;

    @FXML
    private Label recipeDescription;
    
    private Recipe eachBriefRecipe;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		recipeImg.setImage(new Image(
				new File("src/resources/pizza_img.png").toURI().toString(), 80, 80, false, false));
		
		//recipeImg.setImage(new Image( new File(eachBriefRecipe.getThumbnail()).toURI().toString(),  80, 80, false, false));
		
		eachBriefRecipe = MainPageController.recipeListTVatLeft.pop();

		MainPageController.recipeListTVatLeftBackUp.add(eachBriefRecipe);
		
		recipeName.setText(eachBriefRecipe.getName());
		
		recipeDescription.setText(textProcessingBeforeOutput());
		
		
		

		
	}
	
	private String textProcessingBeforeOutput()  {
		// TODO Auto-generated method stub
		
		String outputText = "";
		try{
			
			char[] text = eachBriefRecipe.getBriefDescription().toCharArray();
			
			int textSize = text.length;
			
			
			
			for(int i=0; i < textSize; i++){
				
				
				
				outputText+= text[i];
				
				if(i%25== 0 && i != 0){
					
					outputText+= "-\n";
					
				}
				
				if(i > 50){
					
					outputText+= "...";
					
					break;
					
				}
				
			}
			
			
		} catch(NullPointerException exception){
			
		} finally{
			
			return outputText;
			
		}

		
	
		
	}
	

}
