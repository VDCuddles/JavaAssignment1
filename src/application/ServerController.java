package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerController {
	
	final long serialVersionUID = -2291453973624020582L;
	ServerSocket serverSocket;
	ArrayList <ServerThread> connectedClients = new ArrayList<ServerThread>();
	

    @FXML
    public Button initialiseServerButton;
    
    @FXML
    public Button initialiseClientButton;
    
    @FXML
    private TextArea systemLog;

	public void initialize(){
	
		// construct ServerSocket
		try {
			serverSocket = new ServerSocket(5000);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println(serverSocket.toString());
		
		//accept new clients ever 100ms through start()
		java.util.Timer t = new java.util.Timer();
		t.schedule(new TimerTask() {

		            @Override
		            public void run() {
/*		                System.out.println("This will run every 100ms");
*/						start();
		            }
		        }, 100, 100);
/*		
*/		
	}
	
	public void start()
	{
		try
		{
/*			while(true) // keep accepting new clients
			{*/
				Socket remoteClient = serverSocket.accept(); // block and wait for a connection from a client
								
				// construct a new server thread, to handle each client socket
				ServerThread st = new ServerThread(remoteClient,this,connectedClients);
				st.start();
				connectedClients.add(st);
//				System.out.println("test");
			/*}*/
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public TextArea getSystemLog() {
		return systemLog;
	}
	
	public void setSystemLog(TextArea systemLog) {
		this.systemLog = systemLog;
	}
    
    @FXML
    void initialiseClientButtonClick(ActionEvent event)  throws Exception {
    	
    	System.out.println("Initialising client...");
    	startNewClient();
    	
    }

    @FXML
    void closeButtonClick(ActionEvent event) {

		System.exit(0);

    }
 
    void startNewClient() throws Exception{
    	
    	URL fxmlUrl = this.getClass().getClassLoader().getResource("Client.fxml");
        Pane mainPane = FXMLLoader.<Pane>load(fxmlUrl);
        Stage newStage = new Stage();
        newStage.setTitle("Client Window");
        newStage.setScene(new Scene(mainPane));
        newStage.show();  

    }
    
}
