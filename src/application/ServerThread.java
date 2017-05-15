package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;


public class ServerThread extends Thread {

	DataInputStream dis;
	DataOutputStream dos;
	OutputStream outputStream;
	InputStream inputStream;
	
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
			this.inputStream = remoteClient.getInputStream();
			this.outputStream = remoteClient.getOutputStream();
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
						server.getSystemLog().appendText(remoteClient.getInetAddress()+":"+remoteClient.getPort()+" >"+ nick + ": "+data+"\n");
						
						for(ServerThread otherClient: connectedClients)
						{
							if(!otherClient.equals(this)) // don't send the message to the client that sent the message in the first place
							{
								//timestamp in this format to help with historical referencing
							    String timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date());
							    otherClient.getDos().writeInt(ServerConstants.CHAT_BROADCAST);
								otherClient.getDos().writeUTF(nick + "> " + "(" + timeStamp + "): " + data );
							}
						}
						
						break;
						
					case ServerConstants.DRAW_IMAGE:
						server.getSystemLog().appendText(remoteClient.getInetAddress()+": (image data sent)\n");
						
						for(ServerThread otherClient: connectedClients)
						{
							if(!otherClient.equals(this)) // don't send the message to the client that sent the message in the first place
							{
								
							    otherClient.getDos().writeInt(ServerConstants.DRAW_BROADCAST);

				                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
				                byte[] size = ByteBuffer.allocate(4).putInt(byteOutput.size()).array();
				                otherClient.getOutputStream().write(size);			                 
				                otherClient.getOutputStream().write(byteOutput.toByteArray());			                 
							}
						}

						break;
						
					case ServerConstants.REGISTER_CLIENT:
						
						nick = dis.readUTF();
						
						String clients = "";
						
						//Serialize the connected clients
						server.getSystemLog().appendText(remoteClient.getInetAddress()+":"+remoteClient.getPort()+">"+nick+" has connected\n");

						for(ServerThread otherClient: connectedClients)
						{
							
							otherClient.getDos().writeInt(ServerConstants.CHAT_BROADCAST);
							otherClient.getDos().writeUTF(remoteClient.getPort()+">"+nick+" connected");
							
							clients += otherClient.getNick() + ":";
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
	
	public OutputStream getOutputStream() {
		
		return outputStream;
	}

}
