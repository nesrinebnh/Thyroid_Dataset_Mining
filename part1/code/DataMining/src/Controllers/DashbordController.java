package Controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DashbordController implements Initializable {

   @FXML
   private TextField title;
   @FXML
   private PieChart chart;

   @FXML
   private TableView<Attribute> table;

   @FXML
   private TableColumn<Attribute, String> attr1;

   @FXML
   private TableColumn<Attribute, String> attr2;

   @FXML
   private TableColumn<Attribute, String> attr3;

   @FXML
   private TableColumn<Attribute, String> attr4;

   @FXML
   private TableColumn<Attribute, String> attr5;

   @FXML
   private TableColumn<Attribute, String> target;

   @FXML
   private TableView<Attribute> table2;

   @FXML
   private TableColumn<Attribute, String> name;

   @FXML
   private TableColumn<Attribute, String> min;

   @FXML
   private TableColumn<Attribute, String> max;

   @FXML
   private TableColumn<Attribute, String> mode;

   @FXML
   private TableColumn<Attribute, String> mediane;

   private ObservableList<Attribute> attributeData;
   private ObservableList<Attribute> statData;

   @FXML
   private Button dashbord;

   @FXML
   private Button statistic;

   @FXML
   private Button about;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		attributeData = FXCollections.observableArrayList();
		statData = FXCollections.observableArrayList();
		title.setEditable(false);
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                		new PieChart.Data("1. normal", 150),
                		new PieChart.Data("2. hyper", 35),
                		new PieChart.Data("3. hypo", 30));
		this.chart.getData().addAll(pieChartData);

		BufferedReader txtReader;
		try {
			txtReader = new BufferedReader(new FileReader("src/FXML/Thyroid_Dataset.txt"));
			String row;
			try {
				while ((row = txtReader.readLine()) != null) {
				    String[] data = row.split(",");
				    String target = data[0];
				    String attr1 = data[1];
				    String attr2 = data[2];
				    String attr3 = data[3];
				    String attr4 = data[4];
				    String attr5 = data[5];
				    attributeData.add(new Attribute(attr1, attr2, attr3, attr4, attr5, target));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txtReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		attr1.setCellValueFactory(cellData -> cellData.getValue().attr1Property());
		attr2.setCellValueFactory(cellData -> cellData.getValue().attr2Property());
		attr3.setCellValueFactory(cellData -> cellData.getValue().attr3Property());
		attr4.setCellValueFactory(cellData -> cellData.getValue().attr4Property());
		attr5.setCellValueFactory(cellData -> cellData.getValue().attr5Property());
		target.setCellValueFactory(cellData -> cellData.getValue().targetProperty());
		System.out.println(this.attributeData.size());
		table.setItems(attributeData);

		for(int i=1; i<=5;i++){
			statData.add(new Attribute(String.valueOf(i), "25.5", "35", "117", "15"));
		}

		name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		min.setCellValueFactory(cellData -> cellData.getValue().minProperty());
		max.setCellValueFactory(cellData -> cellData.getValue().maxProperty());
		mode.setCellValueFactory(cellData -> cellData.getValue().modeProperty());
		mediane.setCellValueFactory(cellData -> cellData.getValue().medianeProperty());
		table2.setItems(statData);

	}

	@FXML
	public void aboutAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/about.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			AboutController control = loader.getController();
			Scene scene = new Scene(roote);
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
	public void statAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/static.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			StaticController control = loader.getController();
			Scene scene = new Scene(roote);
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
