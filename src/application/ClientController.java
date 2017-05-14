package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class ClientController implements Runnable{
	

    ObservableList<String> m_names = FXCollections.observableArrayList();	
        
	// define the socket and io streams
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
    
    public String nick;

    @FXML
    public ListView<String> userList;
    
    @FXML
    public TextArea chatLog;
    
    @FXML
    public TextArea chatField;

    public void sendMessage(){
    	
    	//timestamp in this format to help with historical referencing
        String timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date());
    	chatLog.appendText(nick + " (" + timeStamp + "): ");
    	chatLog.appendText(chatField.getText());
    	chatLog.appendText("\n");
    	
		try {
			dos.writeInt(ServerConstants.CHAT_MESSAGE); // determine the type of message to be sent
			dos.writeUTF(chatField.getText()); // message payload
			
			dos.flush(); // force the message to be sent (sometimes data can be buffered)
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
    	chatField.setText(null);

    }
    
	public void initialize(){
		
	    m_names.add(getNick());
        userList.setItems(m_names);
        
        //apply socket and I/O
		while (socket == null) {
			try {
				socket = new Socket("localhost", 5000);
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());

				// define a thread to take care of messages sent from the server
				Thread socketThread = new Thread(this);
				socketThread.start();
				
				dos.writeInt(ServerConstants.REGISTER_CLIENT);
				dos.writeUTF(nick);
				dos.flush();
				
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		System.out.println(socket.toString());


    }
	
	// process messages from the server
	@Override
	public void run()
	{
		while(true)
		{
			try {
				int messageType = dis.readInt(); // receive a message from the server, determine message type based on an integer
				
				// decode message and process
				switch(messageType)
				{
					case ServerConstants.CHAT_BROADCAST:
						chatLog.appendText(dis.readUTF()+"\n");
						break;
					case ServerConstants.REGISTER_CLIENT:
						//Clear the client list
						m_names.clear();
						String data = dis.readUTF();
						//As we are recieving a serialized list, deserialize it
						String[] clients = data.split(":");
						for(String client : clients)
						{
							if(client != null && !client.equals(""))
								m_names.add(client);
						}
						break;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}

	 public String getNick(){

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



	    	Optional<String> result = dialog.showAndWait();
	    	
	    	nick = username.textProperty().get().toString();
	    	
	    	return username.textProperty().get().toString();
	    	
	    }

}
