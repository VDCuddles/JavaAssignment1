package application;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerController {
	
    @FXML
    public Button initialiseServerButton;
    
    @FXML
    public Button initialiseClientButton;
		
    @FXML
    void initialiseServerButtonClick(ActionEvent event)  throws Exception {
    	
		
    	System.out.println("Initialising server...");
    	Server server = new Server();
    	initialiseClientButton.setDisable(false);
    	initialiseServerButton.setDisable(true);

    }
    
    @FXML
    void initialiseClientButtonClick(ActionEvent event)  throws Exception {
    	
		
    	System.out.println("Initialising client...");
    	startNewClient();
    	
   
    }

    @FXML
    void closeButtonClick(ActionEvent event) {
/*		try {
			server.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		System.exit(0);

    }
    
    void startNewClient() throws Exception{
    	URL fxmlUrl = this.getClass()
    			.getClassLoader()
    			.getResource("Client.fxml");
        Pane mainPane = FXMLLoader.<Pane>load(fxmlUrl);
        Stage newStage = new Stage();
        newStage.setTitle("Client Window");
        newStage.setScene(new Scene(mainPane));
        newStage.show();   
    }
    
}
