package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    public static Stage primaryStage;
    
    private BorderPane myWindow = new BorderPane();
    
    public static BorderPane tabletLayout ;
    
    public static Scene scene;

	@Override
	public void start(Stage primaryStage) throws IOException {
		
        MainApp.primaryStage = primaryStage;

        //MainApp.primaryStage.setResizable(false);
        
        MainApp.primaryStage.setMinHeight(1000);
        
        MainApp.primaryStage.setMinWidth(1300);
        
        MainApp.primaryStage.setMaxWidth(1400);
        
        MainApp.primaryStage.setTitle("CookbookApp");
        
        initTabletLayout();
		
	}

	private void initTabletLayout() throws IOException {

        // Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(MainApp.class.getResource("../view/Tablet.fxml"));
        
        tabletLayout = (BorderPane) loader.load();
        
        
        
        myWindow.setCenter(tabletLayout);
        
        // Show the scene containing the root layout.
        
        scene = new Scene(myWindow);
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
		
	}

}

