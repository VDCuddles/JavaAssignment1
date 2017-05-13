package application;

import java.net.URL;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ServerController {
	
/*    private ClientController clientController;*/
	
    private SharedModel model;
    
    public void setModel(SharedModel model) {
        this.model = model ;
    }
    
    public void addModelUser(){
    	model.addUserToList();
    }
    
    /*public ListView<String> userList;*/

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
    
/*    public void setClientController(ClientController clientController) {
        this.clientController = clientController ;
    }*/
    
    void startNewClient() throws Exception{
    	
    	URL fxmlUrl = this.getClass()
    			.getClassLoader()
    			.getResource("Client.fxml");
        Pane mainPane = FXMLLoader.<Pane>load(fxmlUrl);
        Stage newStage = new Stage();
        newStage.setTitle("Client Window");
        newStage.setScene(new Scene(mainPane));
        newStage.show();  
        
/*        setClientController(clientController);
        Client client = new Client();
        client.getUsername();
        clientController.addUserToList();*/
    }
    
}
