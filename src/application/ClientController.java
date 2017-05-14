package application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class ClientController implements Runnable{
	//drawer code here references http://java-buddy.blogspot.co.nz/2013/04/free-draw-on-javafx-canvas.html	

    ObservableList<String> m_names = FXCollections.observableArrayList();	
    Pane[] colourPaneList;
    
	// define the socket and io streams
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
    OutputStream outputStream;
    InputStream inputStream;
    
	private Background selectedBackground =new Background(new BackgroundFill(Color.web("#ffba00"), CornerRadii.EMPTY, Insets.EMPTY));
	private Background selectedColourBackground =new Background(new BackgroundFill(Color.web("#008a91"), CornerRadii.EMPTY, Insets.EMPTY));
	
    private ImageView selectedTool;
    private ImageView selectedColour;
    
    public String nick;

    @FXML
    public ListView<String> userList;
    
    @FXML
    public TextArea chatLog;
    
    @FXML
    public TextArea chatField;
    
    @FXML
    public Canvas canvas;
        
    //painter icons
    @FXML
    private Pane pane0_0;
    @FXML
    private ImageView img0_0;
    @FXML
    private Pane pane1_0;
    @FXML
    private ImageView img1_0;
    @FXML
    private Pane pane0_2;
    @FXML
    private ImageView img0_2;
    @FXML
    private Pane pane1_2;
    @FXML
    private ImageView img1_2;
    @FXML
    private Pane pane0_3;
    @FXML
    private ImageView img0_3;
    @FXML
    private Pane pane1_3;
    @FXML
    private ImageView img1_3;
    @FXML
    private Pane pane0_4;
    @FXML
    private ImageView img0_4;
    @FXML
    private Pane pane1_4;
    @FXML
    private ImageView img1_4;
    @FXML
    private Pane pane0_5;
    @FXML
    private ImageView img0_5;
    @FXML
    private Pane pane1_5;
    @FXML
    private ImageView img1_5;
    @FXML
    private Pane pane0_6;
    @FXML
    private ImageView img0_6;
    @FXML
    private Pane pane1_6;
    @FXML
    private ImageView img1_6;
    @FXML
    private Pane pane0_7;
    @FXML
    private ImageView img0_7;
    @FXML
    private Pane pane1_7;
    @FXML
    private ImageView img1_7;
    @FXML
    private Pane pane0_8;
    @FXML
    private ImageView img0_8;
    @FXML
    private Pane pane1_8;
    @FXML
    private ImageView img1_8;
    @FXML
    private Pane pane0_9;
    @FXML
    private ImageView img0_9;
    @FXML
    private Pane pane1_9;
    @FXML
    private ImageView img1_9;
    @FXML
    private Pane pane0_10;
    @FXML
    private ImageView img0_10;
    @FXML
    private Pane pane1_10;
    @FXML
    private ImageView img1_10;
    @FXML
    private Pane pane0_11;
    @FXML
    private ImageView img0_11;
    @FXML
    private Pane pane1_11;
    @FXML
    private ImageView img1_11;
    
    
    //image URLs for control tools
    URL pencilUrl = this.getClass().getClassLoader().getResource("pencil.png");
    Image pencil = new Image("pencil.png");
    URL saveUrl = this.getClass().getClassLoader().getResource("save.png");
    Image save = new Image("save.png");
    URL greyUrl = this.getClass().getClassLoader().getResource("grey.png");
    Image grey = new Image("grey.png");
    URL turquoiseUrl = this.getClass().getClassLoader().getResource("turquoise.png");
    Image turquoise = new Image("turquoise.png");
    URL cyanUrl = this.getClass().getClassLoader().getResource("cyan.png");
    Image cyan = new Image("cyan.png");
    URL redUrl = this.getClass().getClassLoader().getResource("red.png");
    Image red = new Image("red.png");
    URL yellowUrl = this.getClass().getClassLoader().getResource("yellow.png");
    Image yellow = new Image("yellow.png");
    URL roseUrl = this.getClass().getClassLoader().getResource("rose.png");
    Image rose = new Image("rose.png");
    URL greenUrl = this.getClass().getClassLoader().getResource("green.png");
    Image green = new Image("green.png");
    URL navyblueUrl = this.getClass().getClassLoader().getResource("navyblue.png");
    Image navyblue = new Image("navyblue.png");
    URL orangeUrl = this.getClass().getClassLoader().getResource("orange.png");
    Image orange = new Image("orange.png");
    URL darkgreenUrl = this.getClass().getClassLoader().getResource("darkgreen.png");
    Image darkgreen = new Image("darkgreen.png");
    URL darkvioletUrl = this.getClass().getClassLoader().getResource("darkviolet.png");
    Image darkviolet = new Image("darkviolet.png");
    URL violetUrl = this.getClass().getClassLoader().getResource("violet.png");
    Image violet = new Image("violet.png");
    URL brownUrl = this.getClass().getClassLoader().getResource("brown.png");
    Image brown = new Image("brown.png");
    URL amberUrl = this.getClass().getClassLoader().getResource("amber.png");
    Image amber = new Image("amber.png");
    URL lightgreyUrl = this.getClass().getClassLoader().getResource("lightgrey.png");
    Image lightgrey = new Image("lightgrey.png");
    URL lightcyanUrl = this.getClass().getClassLoader().getResource("lightcyan.png");
    Image lightcyan = new Image("lightcyan.png");
    URL darkgreyUrl = this.getClass().getClassLoader().getResource("darkgrey.png");
    Image darkgrey = new Image("darkgrey.png");
    URL darkmagentaUrl = this.getClass().getClassLoader().getResource("darkmagenta.png");
    Image darkmagenta = new Image("darkmagenta.png");
    URL blackUrl = this.getClass().getClassLoader().getResource("black.png");
    Image black = new Image("black.png");
    URL whiteUrl = this.getClass().getClassLoader().getResource("white.png");
    Image white = new Image("white.png");
    
	public void initialize(){
		

		//image settings
        img0_0.setImage(pencil);
        img1_0.setImage(save);
//        img0_1.setImage(pencil);
//        img1_1.setImage(save);
        img0_2.setImage(violet);
        img1_2.setImage(darkviolet);
        img0_3.setImage(navyblue);
        img1_3.setImage(cyan);
        img0_4.setImage(darkmagenta);
        img1_4.setImage(lightcyan);
        img0_5.setImage(darkgreen);
        img1_5.setImage(turquoise);
        img0_6.setImage(green);
        img1_6.setImage(rose);
        img0_7.setImage(yellow);
        img1_7.setImage(amber);
        img0_8.setImage(red);
        img1_8.setImage(orange);
        img0_9.setImage(grey);
        img1_9.setImage(brown);
        img0_10.setImage(darkgrey);
        img1_10.setImage(lightgrey);
        img0_11.setImage(black);
        img1_11.setImage(white);
		
		colourPaneList = new Pane[]{pane0_2,pane1_2,pane0_3,pane1_3,pane0_4,pane1_4,pane0_5,pane1_5,
				pane0_6,pane1_6,pane0_7,pane1_7,pane0_8,pane1_8,pane0_9,pane1_9,
				pane0_10,pane1_10,pane0_11,pane1_11};
		
	    final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
	    initDraw(graphicsContext);
	    
		selectPencil();
		selectColour(graphicsContext, 1);
	    
	    //canvas event handlers
	    
	    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	
            	
            	
                if (selectedTool == img0_0) {
					graphicsContext.beginPath();
					graphicsContext.moveTo(event.getX(), event.getY());
					graphicsContext.stroke();
				}
                
                
            }
            
        });
         
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	
                if (selectedTool == img0_0) {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
                }
                
            }
        });
 
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	sendDraw(graphicsContext);
 
            }
        });
        
        //tool handlers
        img0_0.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectPencil();
            }
        });
        
//        img0_1.addEventHandler(MouseEvent.MOUSE_CLICKED, 
//        	      new EventHandler<MouseEvent>(){
//
//        	  @Override
//        	  public void handle(MouseEvent event) {
//        	  	selectColour(graphicsContext, 1);
//
//        	  }
//        	});
//        img1_1.addEventHandler(MouseEvent.MOUSE_CLICKED, 
//        	      new EventHandler<MouseEvent>(){
//
//        	  @Override
//        	  public void handle(MouseEvent event) {
//        	  	selectColour(graphicsContext, 2);
//
//        	  }
//        	});
        
        //save handler
        img1_0.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	saveImage(graphicsContext);
 
            }
        });
        
        //colour handlers
        

        
        img0_2.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 20);
 
            }
        });
        img1_2.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 19);
 
            }
        });
        img0_3.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 18);
 
            }
        });
        img1_3.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 17);
 
            }
        });
        img0_4.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 16);
 
            }
        });
        img1_4.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 15);
 
            }
        });
        img0_5.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 14);
 
            }
        });
        img1_5.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 13);
 
            }
        });
        img0_6.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 12);
 
            }
        });
        img1_6.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 11);
 
            }
        });
        img0_7.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 10);
 
            }
        });
        img1_7.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 9);
 
            }
        });
        img0_8.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 8);
 
            }
        });
        img1_8.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 7);
 
            }
        });
        img0_9.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 6);
 
            }
        });
        img1_9.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 5);
 
            }
        });
        img0_10.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 4);
 
            }
        });
        img1_10.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 3);
 
            }
        });
        img0_11.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 1);
 
            }
        });
        img1_11.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	selectColour(graphicsContext, 2);
 
            }
        });
		
	    m_names.add(getNick());
        userList.setItems(m_names);
        
        //apply socket and I/O
		while (socket == null) {
			try {
				socket = new Socket("localhost", 5000);
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				
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
					case ServerConstants.DRAW_BROADCAST:
			            try {
			            	InputStream resourceBuff = inputStream;
			                BufferedImage image = ImageIO.read(resourceBuff);
//			                Graphics2D g2 = image.createGraphics();
			                canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(image,null), (double)image.getWidth(), (double)image.getHeight());

			                
			            } catch (IOException ex) {
			                ex.printStackTrace();
			            }
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
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
	
	public void sendDraw(GraphicsContext gc){
		
		try {
			dos.writeInt(ServerConstants.DRAW_IMAGE); // determine the type of message to be sent
//			 try {
			
			//first write the image to disk
                 WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                 File file = new File("temp.png");
                 canvas.snapshot(null, writableImage);
                 try {
                     ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                     System.out.println(file.toString() + " saved to temp.png");
                 } catch (IOException ex) {
                     Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                 }
//                 
                 BufferedImage bImage = ImageIO.read(new File("temp.png"));
//                 System.out.println("bImage = " + bImage.toString());
//                 RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                 ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
//                 ImageIO.write( SwingFXUtils.fromFXImage( writableImage, bImage ), "png", byteOutput );
//                 ImageIO.write( bImage , "png", outputStream );
//                 ImageIO.write( bImage , "png", outputStream );
//                 System.out.println("byteOutput after write = " + byteOutput.toString());
                 byte[] size = ByteBuffer.allocate(4).putInt(byteOutput.size()).array();
//                 for (byte member : size){
//                     System.out.println("members = " + member);
//                 }

                 try {
                	 outputStream = new ByteArrayOutputStream();
                     outputStream.write(size);
                     outputStream.write(byteOutput.toByteArray());
                     outputStream.flush();
                 } catch (IOException ex) {
                     Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 
//             } catch (IOException ex) {
//                 Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
//             }
			dos.flush(); // force the message to be sent (sometimes data can be buffered)
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	 private String getNick(){

	    	//code here references http://code.makery.ch/blog/javafx-dialogs-official/

	    	// Create the custom dialog.
	    	Dialog<String> dialog = new Dialog<>();
	    	dialog.setTitle("Nickname dialog");
	    	dialog.setHeaderText("Nickname Confirmation");

	    	// Set the button types.
	    	ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
	    	dialog.getDialogPane().getButtonTypes().add(loginButtonType);

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

	    	dialog.showAndWait();
	    	
	    	nick = username.textProperty().get().toString();
	    	
	    	return username.textProperty().get().toString();
	    	
	    }
	 
	    private void initDraw(GraphicsContext gc){
	    	
	        double canvasWidth = gc.getCanvas().getWidth();
	        double canvasHeight = gc.getCanvas().getHeight();

	        gc.strokeRect(
	                0,              //x of the upper left corner
	                0,              //y of the upper left corner
	                canvasWidth,    //width of the rectangle
	                canvasHeight);  //height of the rectangle
	         
	        gc.setFill(Color.RED);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	         
	    }
	    
	    private void setBackgroundForColour(Pane aPane){
	    	for (Pane pane : colourPaneList){
	    		if (pane == aPane) {
	    			pane.setBackground(selectedColourBackground);
				}
	    		else pane.setBackground(null);
	    	}
	    }
	    
	    @FXML
	    private void saveImage(GraphicsContext gc){
	    	
	    	//code here references http://java-buddy.blogspot.co.nz/2013/04/save-canvas-to-png-file.html	    	

	    	 FileChooser fileChooser = new FileChooser();
             
             //Set extension filter
             FileChooser.ExtensionFilter extFilter = 
                     new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
             fileChooser.getExtensionFilters().add(extFilter);
            
             //Show save file dialog
             File file = fileChooser.showSaveDialog(null);
              
             if(file != null){
                 try {
                     WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                     canvas.snapshot(null, writableImage);
                     ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                 } catch (IOException ex) {
                   Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
		 }
	    
	    private void selectPencil(){
	    	System.out.println("pencil tool selected");
	    		selectedTool = img0_0;
	    		pane0_0.setBackground(selectedBackground);
	    }
	    
	    private void selectColour(GraphicsContext gc, int iv){
	    	switch (iv){
	    	
	    	case 1:
	    	System.out.println("black colour selected");
	    	selectedColour = img0_11;
	    	setBackgroundForColour(pane0_11);
	        gc.setFill(Color.BLACK);
	        gc.setStroke(Color.BLACK);
	        break;
	        
	    	case 2:
	    	System.out.println("white colour selected");
	    	selectedColour = img1_11;
	    	setBackgroundForColour(pane1_11);
	        gc.setFill(Color.WHITE);
	        gc.setStroke(Color.WHITE);
	        break;
	        
	    	case 3:
	    	System.out.println("white colour selected");
	    	selectedColour = img1_10;
	    	setBackgroundForColour(pane1_10);
	        gc.setFill(Color.web("e1e1e1"));
	        gc.setStroke(Color.web("e1e1e1"));
	        break;
	        
	    	case 4:
	    	System.out.println("darkgrey colour selected");
	    	selectedColour = img0_10;
	    	setBackgroundForColour(pane0_10);
	        gc.setFill(Color.web("363636"));
	        gc.setStroke(Color.web("363636"));
	        break;
	        
	    	case 5:
	    	System.out.println("brown colour selected");
	    	selectedColour = img1_9;
	    	setBackgroundForColour(pane1_9);
	        gc.setFill(Color.web("603913"));
	        gc.setStroke(Color.web("603913"));
	        break;
	        
	    	case 6:
	    	System.out.println("grey colour selected");
	    	selectedColour = img0_9;
	    	setBackgroundForColour(pane0_9);
	        gc.setFill(Color.web("898989"));
	        gc.setStroke(Color.web("898989"));
	        break;
	        
	    	case 7:
	    	System.out.println("orange colour selected");
	    	selectedColour = img1_8;
	    	setBackgroundForColour(pane1_8);
	        gc.setFill(Color.web("f26522"));
	        gc.setStroke(Color.web("f26522"));
	        break;
	        
	    	case 8:
	    	System.out.println("red colour selected");
	    	selectedColour = img0_8;
	    	setBackgroundForColour(pane0_8);
	        gc.setFill(Color.web("ff0000"));
	        gc.setStroke(Color.web("ff0000"));
	        break;
	        
	    	case 9:
	    	System.out.println("amber colour selected");
	    	selectedColour = img1_7;
	    	setBackgroundForColour(pane1_7);
	        gc.setFill(Color.web("f9ad81"));
	        gc.setStroke(Color.web("f9ad81"));
	        break;
	        
	    	case 10:
	    	System.out.println("yellow colour selected");
	    	selectedColour = img0_7;
	    	setBackgroundForColour(pane0_7);
	        gc.setFill(Color.web("ffff00"));
	        gc.setStroke(Color.web("ffff00"));
	        break;
	        
	    	case 11:
	    	System.out.println("rose colour selected");
	    	selectedColour = img1_6;
	    	setBackgroundForColour(pane1_6);
	        gc.setFill(Color.web("ed145b"));
	        gc.setStroke(Color.web("ed145b"));
	        break;
	        
	    	case 12:
	    	System.out.println("green colour selected");
	    	selectedColour = img0_6;
	    	setBackgroundForColour(pane0_6);
	        gc.setFill(Color.web("00ff00"));
	        gc.setStroke(Color.web("00ff00"));
	        break;
	        
	    	case 13:
	    	System.out.println("turquoise colour selected");
	    	selectedColour = img1_5;
	    	setBackgroundForColour(pane1_5);
	        gc.setFill(Color.web("287175"));
	        gc.setStroke(Color.web("287175"));
	        break;
	        
	    	case 14:
	    	System.out.println("darkgreen colour selected");
	    	selectedColour = img0_5;
	    	setBackgroundForColour(pane0_5);
	        gc.setFill(Color.web("005826"));
	        gc.setStroke(Color.web("005826"));
	        break;
	        
	    	case 15:
	    	System.out.println("lightcyan colour selected");
	    	selectedColour = img1_4;
	    	setBackgroundForColour(pane1_4);
	        gc.setFill(Color.web("00bff3"));
	        gc.setStroke(Color.web("00bff3"));
	        break;
	        
	    	case 16:
	    	System.out.println("darkmagenta colour selected");
	    	selectedColour = img0_4;
	    	setBackgroundForColour(pane0_4);
	        gc.setFill(Color.web("9e005d"));
	        gc.setStroke(Color.web("9e005d"));
	        break;
	        
	    	case 17:
	    	System.out.println("cyan colour selected");
	    	selectedColour = img1_3;
	    	setBackgroundForColour(pane1_3);
	        gc.setFill(Color.web("00ffff"));
	        gc.setStroke(Color.web("00ffff"));
	        break;
	        
	    	case 18:
	    	System.out.println("navyblue colour selected");
	    	selectedColour = img0_3;
	    	setBackgroundForColour(pane0_3);
	        gc.setFill(Color.web("0054a6"));
	        gc.setStroke(Color.web("0054a6"));
	        break;
	        
	    	case 19:
	    	System.out.println("darkviolet colour selected");
	    	selectedColour = img1_2;
	    	setBackgroundForColour(pane1_2);
	        gc.setFill(Color.web("1b1464"));
	        gc.setStroke(Color.web("1b1464"));
	        break;
	        
	    	case 20:
	    	System.out.println("violet colour selected");
	    	selectedColour = img0_2;
	    	setBackgroundForColour(pane0_2);
	        gc.setFill(Color.web("2e3192"));
	        gc.setStroke(Color.web("2e3192"));
	        break;
	        
	    	}
	    }

}
