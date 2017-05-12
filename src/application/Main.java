package application;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    public void start(Stage primaryStage) throws Exception {
    	
    	URL fxmlUrl = this.getClass()
    			.getClassLoader()
    			.getResource("Server.fxml");
        Pane mainPane = FXMLLoader.<Pane>load(fxmlUrl);
        
        primaryStage.setTitle("Assignment 1");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();        
        
/*    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
    	Parent root = loader.load();
    	Scene scene = new Scene(root, 300, 275);
    	ServerController c = loader.getController();
        primaryStage.setTitle("Assignment 1");
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}