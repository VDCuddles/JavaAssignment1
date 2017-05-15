package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
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
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;

public class ClientController implements Runnable{
	//drawer code here references http://java-buddy.blogspot.co.nz/2013/04/free-draw-on-javafx-canvas.html	
    ObservableList<String> m_names = FXCollections.observableArrayList();	
    Pane[] colourPaneList;
    Pane[] toolPaneList;
    
	// define the socket and io streams
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
    OutputStream outputStream;
    InputStream inputStream;
    
	private Background selectedBackground =new Background(new BackgroundFill(Color.web("#ffba00"), CornerRadii.EMPTY, Insets.EMPTY));
	private Background selectedColourBackground =new Background(new BackgroundFill(Color.web("#008a91"), CornerRadii.EMPTY, Insets.EMPTY));
	
    private ImageView selectedTool;
    
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
    private Pane pane0_1;
    @FXML
    private ImageView img0_1;
    @FXML
    private Pane pane1_1;
    @FXML
    private ImageView img1_1;
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
    double sx;
    double sy;
    double ex;
    double ey;
    SVGPath svg = new SVGPath();

	public void initialize(){
		
		colourPaneList = new Pane[]{pane0_2,pane1_2,pane0_3,pane1_3,pane0_4,pane1_4,pane0_5,pane1_5,
				pane0_6,pane1_6,pane0_7,pane1_7,pane0_8,pane1_8,pane0_9,pane1_9,
				pane0_10,pane1_10,pane0_11,pane1_11};
		toolPaneList = new Pane[]{pane0_0,pane0_1}; 
		
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
//					graphicsContext.moveTo(event.getX(), event.getY());
//					graphicsContext.stroke();
			        SVGPath path = new SVGPath();
			        path.setContent("M"+(float)event.getX()+","+(float)event.getY());
			        graphicsContext.appendSVGPath(path.getContent());
					sx = event.getX();
					sy = event.getY();
				}
                
                
            }
            
        });
         
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	
                if (selectedTool == img0_0) {
//                graphicsContext.lineTo(event.getX(), event.getY());
//                graphicsContext.stroke();

				ex = event.getX();
				ey = event.getY();
				svg = drawSVG(sx,sy,ex,ey,graphicsContext);
				System.out.println(svg.toString());
            	graphicsContext.appendSVGPath(svg.getContent());
//				graphicsContext.fill();
                if(sx != event.getX() || sy != event.getY()){
                	sx = event.getX();
                	sy = event.getY();
                }
                if(ex != event.getX() || ey != event.getY()){
                	ex = event.getX();
                	ey = event.getY();
                }
//                graphicsContext.closePath();

                }
                
            }
        });
 
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
            	sendDraw(graphicsContext);
				graphicsContext.stroke();

            }
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                if (selectedTool == img0_1) {
                	Canvas can = new Canvas();
//                	can.set
                }
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
        
        img0_1.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        	      new EventHandler<MouseEvent>(){

        	  @Override
        	  public void handle(MouseEvent event) {
        	  	selectZoom(graphicsContext, canvas);

        	  }
        	});
        img1_1.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        	      new EventHandler<MouseEvent>(){

        	  @Override
        	  public void handle(MouseEvent event) {
        	  	rotate(graphicsContext, canvas);

        	  }
        	});
        
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
					try {
						//Clear the client list
						m_names.clear();
						String data = dis.readUTF();
						//As we are recieving a serialized list, deserialize it
						String[] clients = data.split(":");
						for (String client : clients) {
							if (client != null && !client.equals(""))
								m_names.add(client);
						} 
					} catch (Exception ex) {
		                ex.printStackTrace();
					}
					break;
					case ServerConstants.DRAW_BROADCAST:
		            try {
//			                ByteArrayInputStream byteInput= new ByteArrayInputStream(null);
//			                byte[] size = ByteBuffer.allocate(4).putInt(inputStream.available()).array();
//
//			            	byteInput.read(size);
//			                otherClient.getOutputStream() = byteOutput;
//			                socket.getInputStream().read();//(byteOutput.toByteArray());	
//			                try {
//								System.out.println("image = " + SwingFXUtils.toFXImage(image,null).getHeight());
//								System.out.println("image = " + SwingFXUtils.toFXImage(image,null).getWidth());
//								System.out.println("image = " + SwingFXUtils.toFXImage(image,null).toString());
//							} catch (Exception ex) {
//				                ex.printStackTrace();
//							}
//						
//		            	
//		            	
		            	BufferedImage image = ImageIO.read(inputStream);
		            	canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(image,null), (double)image.getWidth(), (double)image.getHeight());
		            	}
			             catch (IOException ex) {
			                ex.printStackTrace();
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
		
//		try {
////			 try {
//			
//			//first write the image to disk
//                 WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
//                 canvas.snapshot(null, writableImage);
//                 
////                 File file = new File("temp.png");
////                 try {
////         	          ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
////                 } catch (IOException ex) {
////                     Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
////                 }
////                 BufferedImage bImage = ImageIO.read(new File("temp.png"));
//				   dos.writeInt(ServerConstants.DRAW_IMAGE); // determine the type of message to be sent
//                   ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", outputStream);
//                   dos.flush(); // force the message to be sent (sometimes data can be buffered)
//                   outputStream.flush();
//                   
//		}
//		catch (IOException e){
//			e.printStackTrace();
//		}
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
	    
	    private void setBackgroundForTool(Pane aPane){
	    	for (Pane pane : toolPaneList){
	    		if (pane == aPane) {
	    			pane.setBackground(selectedBackground);
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
		    	setBackgroundForTool(pane0_0);
	    }

	    private void selectZoom(GraphicsContext gc, Canvas can){
	    	System.out.println("zoom tool selected");
    		selectedTool = img0_1;
	    	setBackgroundForTool(pane0_1);
	    	can.setScaleX(2);
	    	can.setScaleY(2);

	    }

	    private void rotate(GraphicsContext gc, Canvas can){
            try {
            	can.setRotate(can.getRotate() + 90);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    private void selectColour(GraphicsContext gc, int iv){
	    	switch (iv){
	    	
	    	case 1:
	    	System.out.println("black colour selected");
	    	setBackgroundForColour(pane0_11);
	        gc.setFill(Color.BLACK);
	        gc.setStroke(Color.BLACK);
	        break;
	        
	    	case 2:
	    	System.out.println("white colour selected");
	    	setBackgroundForColour(pane1_11);
	        gc.setFill(Color.WHITE);
	        gc.setStroke(Color.WHITE);
	        break;
	        
	    	case 3:
	    	System.out.println("white colour selected");
	    	setBackgroundForColour(pane1_10);
	        gc.setFill(Color.web("e1e1e1"));
	        gc.setStroke(Color.web("e1e1e1"));
	        break;
	        
	    	case 4:
	    	System.out.println("darkgrey colour selected");
	    	setBackgroundForColour(pane0_10);
	        gc.setFill(Color.web("363636"));
	        gc.setStroke(Color.web("363636"));
	        break;
	        
	    	case 5:
	    	System.out.println("brown colour selected");
	    	setBackgroundForColour(pane1_9);
	        gc.setFill(Color.web("603913"));
	        gc.setStroke(Color.web("603913"));
	        break;
	        
	    	case 6:
	    	System.out.println("grey colour selected");
	    	setBackgroundForColour(pane0_9);
	        gc.setFill(Color.web("898989"));
	        gc.setStroke(Color.web("898989"));
	        break;
	        
	    	case 7:
	    	System.out.println("orange colour selected");
	    	setBackgroundForColour(pane1_8);
	        gc.setFill(Color.web("f26522"));
	        gc.setStroke(Color.web("f26522"));
	        break;
	        
	    	case 8:
	    	System.out.println("red colour selected");
	    	setBackgroundForColour(pane0_8);
	        gc.setFill(Color.web("ff0000"));
	        gc.setStroke(Color.web("ff0000"));
	        break;
	        
	    	case 9:
	    	System.out.println("amber colour selected");
	    	setBackgroundForColour(pane1_7);
	        gc.setFill(Color.web("f9ad81"));
	        gc.setStroke(Color.web("f9ad81"));
	        break;
	        
	    	case 10:
	    	System.out.println("yellow colour selected");
	    	setBackgroundForColour(pane0_7);
	        gc.setFill(Color.web("ffff00"));
	        gc.setStroke(Color.web("ffff00"));
	        break;
	        
	    	case 11:
	    	System.out.println("rose colour selected");
	    	setBackgroundForColour(pane1_6);
	        gc.setFill(Color.web("ed145b"));
	        gc.setStroke(Color.web("ed145b"));
	        break;
	        
	    	case 12:
	    	System.out.println("green colour selected");
	    	setBackgroundForColour(pane0_6);
	        gc.setFill(Color.web("00ff00"));
	        gc.setStroke(Color.web("00ff00"));
	        break;
	        
	    	case 13:
	    	System.out.println("turquoise colour selected");
	    	setBackgroundForColour(pane1_5);
	        gc.setFill(Color.web("287175"));
	        gc.setStroke(Color.web("287175"));
	        break;
	        
	    	case 14:
	    	System.out.println("darkgreen colour selected");
	    	setBackgroundForColour(pane0_5);
	        gc.setFill(Color.web("005826"));
	        gc.setStroke(Color.web("005826"));
	        break;
	        
	    	case 15:
	    	System.out.println("lightcyan colour selected");
	    	setBackgroundForColour(pane1_4);
	        gc.setFill(Color.web("00bff3"));
	        gc.setStroke(Color.web("00bff3"));
	        break;
	        
	    	case 16:
	    	System.out.println("darkmagenta colour selected");
	    	setBackgroundForColour(pane0_4);
	        gc.setFill(Color.web("9e005d"));
	        gc.setStroke(Color.web("9e005d"));
	        break;
	        
	    	case 17:
	    	System.out.println("cyan colour selected");
	    	setBackgroundForColour(pane1_3);
	        gc.setFill(Color.web("00ffff"));
	        gc.setStroke(Color.web("00ffff"));
	        break;
	        
	    	case 18:
	    	System.out.println("navyblue colour selected");
	    	setBackgroundForColour(pane0_3);
	        gc.setFill(Color.web("0054a6"));
	        gc.setStroke(Color.web("0054a6"));
	        break;
	        
	    	case 19:
	    	System.out.println("darkviolet colour selected");
	    	setBackgroundForColour(pane1_2);
	        gc.setFill(Color.web("1b1464"));
	        gc.setStroke(Color.web("1b1464"));
	        break;
	        
	    	case 20:
	    	System.out.println("violet colour selected");
	    	setBackgroundForColour(pane0_2);
	        gc.setFill(Color.web("2e3192"));
	        gc.setStroke(Color.web("2e3192"));
	        break;
	        
	    	}
	    }
	    
	    public static SVGPath drawSVG(double startX, double startY, double endX, double endY, GraphicsContext gc)
	    {
	        SVGPath path = new SVGPath();
	        path.setContent("L"+(float)startX+","+(float)startY+" L "+(float)endX+","+(float)endY);
	        path.setFill(gc.getFill());
	        path.setStroke(gc.getStroke());
	        path.setStrokeWidth(3);
	        return path;    
	    }

}
