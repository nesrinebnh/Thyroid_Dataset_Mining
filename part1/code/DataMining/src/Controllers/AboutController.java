package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AboutController implements Initializable {

	@FXML
    private TextField title;

    @FXML
    private Button dashbord;

    @FXML
    private Button statistic;

    @FXML
    private Button about;

    String PATH = "";

    public void setPath(String path){
    	this.PATH = path;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void dashbordAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/Dashbord.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			DashbordController control = loader.getController();
			control.setPath(PATH);
			Scene scene = new Scene(roote);
			scene.getStylesheets().add("/FXML/Dashbord.css");
			rootStage.setScene(scene);
		    rootStage.show();
		    rootStage.setResizable(false);
		    dashbord.getScene().getWindow().hide();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void staticAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/static.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			StaticController control = loader.getController();
			control.setPath(PATH);
			Scene scene = new Scene(roote);
			scene.getStylesheets().add("/FXML/Dashbord.css");
			rootStage.setScene(scene);
		    rootStage.show();
		    rootStage.setResizable(false);
		    dashbord.getScene().getWindow().hide();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
