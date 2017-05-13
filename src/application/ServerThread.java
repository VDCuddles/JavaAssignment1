package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ServerThread extends Thread {

	DataInputStream dis;
	DataOutputStream dos;
	
	Socket remoteClient;
	ServerController server;
	
	String nick;
	
	ArrayList<ServerThread> connectedClients; // keep track of all the other clients connected to the Server
	
	public ServerThread(Socket remoteClient, ServerController server, ArrayList<ServerThread> connectedClients)
	{
		this.remoteClient = remoteClient;
		this.connectedClients = connectedClients;
		try {
			this.dis = new DataInputStream(remoteClient.getInputStream());
			this.dos = new DataOutputStream(remoteClient.getOutputStream());
			this.server = server;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		while(true) // main protocol decode loop
		{
			try {
				int mesgType = dis.readInt(); // read the type of message from the client (must be an integer)
				System.err.println(mesgType);
				
				// decode the message type based on the integer sent from the client
				switch(mesgType)
				{
					case ServerConstants.CHAT_MESSAGE:
						String data = dis.readUTF();
						System.err.println(data);
						server.getSystemLog().appendText(remoteClient.getInetAddress()+":"+remoteClient.getPort()+">"+ nick + ": "+data+"\n");
						
						for(ServerThread otherClient: connectedClients)
						{
							if(!otherClient.equals(this)) // don't send the message to the client that sent the message in the first place
							{
								otherClient.getDos().writeInt(ServerConstants.CHAT_BROADCAST);
								otherClient.getDos().writeUTF(nick + "> " + data);
							}
						}
						
						break;
					case ServerConstants.REGISTER_CLIENT:
						
						nick = dis.readUTF();
						
						String clients = "";
						
						//Serialize the connected clients
						for(ServerThread otherClient: connectedClients)
						{
							server.getSystemLog().appendText(remoteClient.getInetAddress()+":"+remoteClient.getPort()+">"+nick+" has connected\n");
							
							otherClient.getDos().writeInt(ServerConstants.CHAT_BROADCAST);
							otherClient.getDos().writeUTF(remoteClient.getInetAddress()+":"+remoteClient.getPort()+">"+nick+" has connected");
							
							clients += otherClient.remoteClient.getInetAddress() + "/" + otherClient.remoteClient.getPort() + "/" + otherClient.getNick() + ":";
						}
						
						//Push the clients to each connected client
						for(ServerThread otherClient: connectedClients)
						{
							otherClient.getDos().writeInt(ServerConstants.REGISTER_CLIENT);
							otherClient.getDos().writeUTF(clients);
						}
						
						break;	
					case ServerConstants.PRIVATE_MESSAGE:
						for(ServerThread otherClient: connectedClients)
						{
							//The client thats sending the message
							String clientComparation = otherClient.remoteClient.getInetAddress() + "/" + otherClient.remoteClient.getPort() + "/" + otherClient.getNick();
							//Deserialize the string
							String[] deserialized = dis.readUTF().split("~");
							/**
							 * @todo sometimes theres a bug with deserialization
							 */
							if(deserialized.length > 1)
							{
								if(clientComparation.equals(deserialized[0]))
								{
									otherClient.getDos().writeInt(ServerConstants.CHAT_BROADCAST);
									otherClient.getDos().writeUTF(remoteClient.getInetAddress() + "/" + remoteClient.getPort() + "/" + getNick() + " -> you: " + deserialized[1]);
								}
							}
						}
						break;
				}				
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
	}

	public String getNick()
	{
		return nick;
	}
	
	public DataOutputStream getDos() {
		return dos;
	}
}
