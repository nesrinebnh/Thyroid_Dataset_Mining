package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import DataStructure.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TestController implements Initializable {

    @FXML
    private Button btn;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
    void clickAction() {
    	FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
        	String pathFile = file.getPath();
        	Data data = new Data(pathFile);

        	Stage rootStage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(this.getClass().getResource("/FXML/Dashbord.fxml"));
    		Parent roote;
    		try {
    			roote = loader.load();
    			Scene scene = new Scene(roote);
    			DashbordController control = loader.getController();
    			control.setPath(pathFile);
    			scene.getStylesheets().add("/FXML/Dashbord.css");
    			rootStage.setScene(scene);
    		    rootStage.show();
    		    rootStage.setResizable(false);
    		    btn.getScene().getWindow().hide();


    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}


        }else{
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("File selection");
        	alert.setHeaderText("File");
        	alert.setContentText("You have cancled the selection operation");
        	alert.showAndWait();
        }

    }


}
