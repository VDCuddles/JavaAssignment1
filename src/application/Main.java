package application;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
	

	
    public void start(Stage primaryStage) throws Exception {
    	
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = this.getClass()
        			.getClassLoader()
        			.getResource("Server.fxml");
            Pane mainPane = loader.<Pane>load(fxmlUrl);

/*            ServerController controller = loader.getController();
*/
            // Show the scene containing the root layout.
            Scene scene = new Scene(mainPane);
            primaryStage.setTitle("Assignment 1");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}