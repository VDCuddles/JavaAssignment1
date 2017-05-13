package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Server {
	
	final long serialVersionUID = -2291453973624020582L;
	ServerSocket serverSocket;
	ArrayList <ServerThread> connectedClients = new ArrayList<ServerThread>();
	
	public Server()
	{
		
		// construct ServerSocket
		try {
			serverSocket = new ServerSocket(5800);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println(serverSocket.toString());
		
	}
		
    @FXML
    private TextArea systemLog;

	public TextArea getSystemLog() {
		return systemLog;
	}
	
	public void setSystemLog(TextArea systemLog) {
		this.systemLog = systemLog;
	}
	
	public void start()
	{
		try
		{
			while(true) // keep accepting new clients
			{
				Socket remoteClient = serverSocket.accept(); // block and wait for a connection from a client
								
				// construct a new server thread, to handle each client socket
				ServerThread st = new ServerThread(remoteClient,this,connectedClients);
				st.start();
				connectedClients.add(st);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
