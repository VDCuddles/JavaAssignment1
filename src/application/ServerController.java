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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

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
    	
    	confirmNick();

    	URL fxmlUrl = this.getClass()
    			.getClassLoader()
    			.getResource("Client.fxml");
        Pane mainPane = FXMLLoader.<Pane>load(fxmlUrl);
        Stage newStage = new Stage();
        newStage.setTitle("Client Window");
        newStage.setScene(new Scene(mainPane));
        newStage.show();   
    }
    
    void confirmNick() throws Exception{

    	//code here references http://code.makery.ch/blog/javafx-dialogs-official/

    	// Create the custom dialog.
    	Dialog<String> dialog = new Dialog<>();
    	dialog.setTitle("Nickname dialog");
    	dialog.setHeaderText("Nickname Confirmation");

    	// Set the button types.
    	ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    	// Create the username and password labels and fields.
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));

    	TextField username = new TextField();
    	username.setPromptText("Username");

    	grid.add(new Label("Username:"), 0, 0);
    	grid.add(username, 1, 0);

    	// Enable/Disable login button depending on whether a username was entered.
    	Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    	loginButton.setDisable(true);

    	// Do some validation (using the Java 8 lambda syntax).
    	username.textProperty().addListener((observable, oldValue, newValue) -> {
    	    loginButton.setDisable(newValue.trim().isEmpty());
    	});

    	dialog.getDialogPane().setContent(grid);

    	// Request focus on the username field by default.
    	Platform.runLater(() -> username.requestFocus());

    	// Convert the result to a username-password-pair when the login button is clicked.
    	dialog.setResultConverter(dialogButton -> {
    	    if (dialogButton == loginButtonType) {
    	        return new String(username.getText());
    	    }
    	    return null;
    	});

    	Optional<String> result = dialog.showAndWait();

    	result.ifPresent(name -> System.out.println("Your name: " + name));
    }

    	
}
