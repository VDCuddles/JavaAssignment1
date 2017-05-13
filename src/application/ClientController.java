package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ClientController {
	

    ObservableList<String> m_names = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown");	
    
    private SharedModel model ;

    public void setModel(SharedModel model) {
        this.model = model ;
    }
    
    @FXML
    public ListView<String> userList;

    @FXML
    public void getListFromModel(){
    	
    	userList.setItems(model.getUserList());
    	
    }
    
	public void initialize(){
	    m_names.add("test");
        userList.setItems(m_names);
    }

}
