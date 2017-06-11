package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    public static Stage primaryStage;
    
    public static BorderPane tabletLayout;
    
    public static Scene scene;

	@Override
	public void start(Stage primaryStage) throws IOException {
		
        MainApp.primaryStage = primaryStage;

        
        MainApp.primaryStage.setTitle("CookbookApp");
        
        initTabletLayout();
		
	}

	private void initTabletLayout() throws IOException {

        // Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(MainApp.class.getResource("../view/Tablet.fxml"));
        
        tabletLayout = (BorderPane) loader.load();
        
        // Show the scene containing the root layout.
        
        scene = new Scene(tabletLayout);
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
		
	}

}
