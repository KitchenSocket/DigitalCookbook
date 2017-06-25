package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Recipe;
import view.Template;

/*
 * A Tablet whose center area holds all other GUIs, like Main Page, add page, Favourite View.
 * 
 * @version 1.0
 * 
 * @author Shi Wenbin, Gang Shao
 */


public class TemplateController implements Initializable {

    @FXML
    private Button mainPageBtn;

    @FXML
    private Button favBtn;

    @FXML
    private Button addRecipeBtn;
    
    @FXML
    private ProgressIndicator bar;
    



    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	bar.setOpacity(0);

		
        mainPageBtn.setGraphic(new ImageView(new Image( new File("src/resources/unclick_home.png").toURI().toString(),  31, 31, false, false)));
        
        favBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_fav_recipe.png").toURI().toString(),  31, 31, false, false)));
        
        addRecipeBtn.setGraphic(new ImageView(new Image( new File("src/resources/add_recipe_btn.png").toURI().toString(),  31, 31, false, false)));

        threeBtnColorClear();
        
        mainPageBtn.setOnAction(event -> {
            try {
            	createTimeDaemon(1.3).start();
            	threeBtnColorClear();
            	mainPageBtn.setStyle("-fx-background-color: #FFFFFF;");

                loadContent("../view/MainOrFavView.fxml", "Main");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        favBtn.setOnAction(event -> {
            try {
            	createTimeDaemon(1).start();
            	threeBtnColorClear();
            	favBtn.setStyle("-fx-background-color: #FFFFFF;"); 
            	


                loadContent("../view/MainOrFavView.fxml", "Fav");
                

            	
            	
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addRecipeBtn.setOnAction(event -> {
            try {
            	createTimeDaemon(0.5).start();
            	threeBtnColorClear();
            	addRecipeBtn.setStyle("-fx-background-color: #FFFFFF;"); 
                loadContent("../view/AddAndEditRecipeView.fxml", "Add");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Thread createTimeDaemon(double time){
    	
    	return new Thread(){
            public void run() {
                double size =time;
                bar.setOpacity(1);
                bar.setProgress(0);
                for (double i = 0.0; i <= size; i=i+0.01){
                    final double step = i;
                    Platform.runLater(() -> bar.setProgress( step / size ));
                    
                    //System.out.printf("Complete: %02.2f%n", i * 10);

                    try {
                        Thread.sleep(10); 
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                bar.setOpacity(0);
            }
            
           
        };
    	
    }
    
    public void threeBtnColorClear() {
    	mainPageBtn.setStyle("-fx-background-color: Transparent;"); 
    	favBtn.setStyle("-fx-background-color: Transparent;"); 
    	addRecipeBtn.setStyle("-fx-background-color: Transparent;"); 
    }

    public void loadContent(Node node) throws IOException {
        Template.getRoot().getChildren().remove(1,1);
        Template.getRoot().getChildren().add(node);
    }

    static void loadContent(String location, String type) throws IOException {
        if(Template.getRoot().getChildren().size() == 2) {
            Template.getRoot().getChildren().remove(1);
        }

        FXMLLoader loader = new FXMLLoader(TemplateController.class.getResource(location));

        switch (type) {
            case "Main": {
 
            	MainPageController myMainPageController = new MainPageController();
            	myMainPageController.setMainOrFav(1);
                loader.setController(myMainPageController);
                break;
            }

            case "Add": {
                AddAndEditViewController controller = new AddAndEditViewController();
                controller.setIsNew(true);
                loader.setController(controller);
                break;
            }

            case "Edit": {
                AddAndEditViewController controller = new AddAndEditViewController();
                controller.setIsNew(false);
                controller.setRecipe(MainPageController.selectedRecipe);
                loader.setController(controller);
                break;
            }

            case "Fav": {
            	MainPageController myMainPageController = new MainPageController();
            	myMainPageController.setMainOrFav(2);
                loader.setController(myMainPageController);
                break;
            }
        }

        Node node = loader.load();
        GridPane.setColumnIndex(node, 1);
        GridPane.setRowIndex(node, 0);
        Template.getRoot().getChildren().add(node);
    }
}


