package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Template extends Application {
	
    public static Stage primaryStage;
    
    //private BorderPane rootBP = new BorderPane();
    
    private static GridPane rootGP ;
    
    public static Scene scene;

	@Override
	public void start(Stage primaryStage) throws IOException {
		
        Template.primaryStage = primaryStage;

        Template.primaryStage.getIcons().add(new Image(new File("src/resources/COOKING_NAVIGATOR.png").toURI().toString(), 80, 80, false, false));
        Template.primaryStage.setTitle("Cooking Navigator");


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Template.class.getResource("../view/Template.fxml"));
        rootGP = loader.load();

        //loadContent("");

        // Show the scene containing the root layout.

        scene = new Scene(rootGP, 1200, 750);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1218);
        primaryStage.setMinHeight(797);
        primaryStage.show();

//        Template.primaryStage.setMinHeight(1000);
//
//        Template.primaryStage.setMinWidth(1300);
//
//        Template.primaryStage.setMaxWidth(1400);
        

		
	}


    public static GridPane getRoot() {
	    return rootGP;
    }
}

