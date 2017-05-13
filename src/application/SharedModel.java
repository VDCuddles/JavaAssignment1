package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SharedModel {
	
    ObservableList<String> m_names = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown");	
    
    public ListView<String> userList;
	
	private String username = "test";
	
	public String getUsername(){
		
		username = "test";
		return username;
		
	}
	
	public void addUserToList(){
		
		m_names.add(getUsername());
		
	}

	public ObservableList getUserList(){
		
		return m_names;
		
	}

}
