package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Algorithms.Clarans;
import Algorithms.Kmeans;
import Algorithms.Kmedoids;
import DataStructure.Thyroid;
import DataStructure.Data;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MiningController implements Initializable {

    @FXML
    private TextField title;

    @FXML
    private Button dashbord;

    @FXML
    private Button statistic;

    @FXML
    private Button about;

    @FXML
    private Button pattern;

    @FXML
    private Button mining;

    @FXML
    private TextArea rules;

    @FXML
    private TextField time;

    @FXML
    private TextField param2;

    @FXML
    private TextField param1, param3, param4, param5;

    @FXML
    private Button btn;

    @FXML
    private ComboBox<String> method;

    @FXML
    private Text t1,t2, t3, t4, t5;

    String display = "";
    String miningMethod = "";

    @FXML
	public void staticAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/static.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			Scene scene = new Scene(roote);
			StaticController control = loader.getController();
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
	public void aboutAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/about.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			AboutController control = loader.getController();
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
	public void dashbordAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/Dashbord.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			DashbordController control = loader.getController();
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
	public void patternAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/FrequentPattern.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			FrequentPatternController control = loader.getController();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		method.setItems(FXCollections.observableArrayList("KMeans", "KMeans with GA","Clarans","Kmedoids"));
		t1.setText("");
		t2.setText("");
		t3.setText("");
		t4.setText("");
		t5.setText("");
		param1.setDisable(true);
		param2.setDisable(true);
		param3.setDisable(true);
		param4.setDisable(true);
		param5.setDisable(true);
		btn.setDisable(true);



	}

	@FXML
	public void activate(){
		miningMethod = method.getValue();
		btn.setDisable(false);

		if(miningMethod.equals("KMeans")){
			t1.setText("Max iteration");
			t2.setText("K");
			t3.setText("");
			t5.setText("");
			t4.setText("");
			param1.setDisable(false);
			param1.setText("500");
			param2.setDisable(false);
			param2.setText("3");
			param3.setDisable(true);
			param4.setDisable(true);
			param5.setDisable(true);
		}else if (miningMethod.equals("KMeans with GA")){
			t1.setText("Max iteration");
			t2.setText("K");
			t3.setText("population Length");
			t4.setText("mutation Rate");
			t5.setText("maxiter GA");
			param1.setDisable(false);
			param1.setText("500");
			param2.setDisable(false);
			param2.setText("5");
			param3.setDisable(false);
			param3.setText("6");
			param4.setDisable(false);
			param4.setText("0.5");
			param5.setDisable(false);
			param5.setText("160");
		}else if (miningMethod.equals("Clarans")){
			t1.setText("Number of clusters");
			t2.setText("Max neighbors");
			t3.setText("Num local");
			t5.setText("");
			t4.setText("");
			param1.setDisable(false);
			param1.setText("4");
			param2.setDisable(false);
			param2.setText("5");
			param3.setDisable(false);
			param3.setText("6");
			param4.setDisable(true);
			param5.setDisable(true);
		}else if (miningMethod.equals("Kmedoids")){
			t1.setText("Number of clusters");
			t2.setText("");
			t3.setText("");
			t5.setText("");
			t4.setText("");
			param1.setDisable(false);
			param1.setText("10");
			param2.setDisable(true);
			param3.setDisable(true);
			param4.setDisable(true);
			param5.setDisable(true);
		}
	}

	@FXML
	public void extractAction(){
		Data dataset = new Data();
		if(miningMethod.equals("KMeans")){
			display = "";
			double t1 = System.currentTimeMillis();
	        Kmeans kmeans = new Kmeans(dataset);

	        // calling fit kmeans with no GA :
	        HashMap<Integer, ArrayList<Thyroid>> clusters = kmeans.fit(Integer.valueOf(this.param1.getText()), Integer.valueOf(this.param2.getText()));

	        for (int k=1; k<= kmeans.centroids.size(); k++){
	            display += "-------------------------- CLUSTER ----------------------------" + "\n";
	            display += String.valueOf(k) + "\n";
	            display+= "Centroid : "+kmeans.centroids.get(k).toString()+"\n";
	            if (clusters.containsKey(k)){

	                display += "the cluster size = "+kmeans.clusters.get(k).size()+"\n";
	                display += "Members: \n";
	                String members = String.join(", ", kmeans.clusters.get(k).toString());
	                display += "["+kmeans.clusters.get(k).get(0)+"\n";
	                //display += kmeans.clusters.get(k).toString()+",\n";
	                for (int l =1; l< kmeans.clusters.get(k).size(); l++){
	                	display += ","+kmeans.clusters.get(k).get(l)+"\n";
	                }
	                display += "]\n";
	            }else
	            	display += "Empty cluster !";

	            display += "\n\n";

	        }
	        double t2 = System.currentTimeMillis();
	        display += "fmeasure: "+kmeans.fmeasure()+"\n";
	        display += "emeasure: "+kmeans.emeasure()+"\n";

	        this.rules.setText(display);

	        this.time.setText(String.valueOf((t2-t1)/1000)+" sec ");
		}else if(miningMethod.equals("KMeans with GA")){
			double t1 = System.currentTimeMillis();
	        Kmeans kmeans = new Kmeans(dataset);

	        display = "";
	        // These are the best parameters of GA =
	        // Nombre d’itérations :160
	        //•	Taille de la population : 6
	        //•	Taux de mutation : 0.5
	        HashMap<Integer, ArrayList<Thyroid>> clusters = kmeans.fit(Integer.valueOf(this.param1.getText()),
	        		Integer.valueOf(this.param2.getText()),
	        		Integer.valueOf(this.param3.getText()),
	        		Float.valueOf(this.param4.getText()),
	        		Integer.valueOf(this.param5.getText()));



	        for (int k=1; k<= kmeans.centroids.size(); k++){
	            display += "-------------------------- CLUSTER ----------------------------" + "\n";
	            display += String.valueOf(k) + "\n";
	            display+= "Centroid : "+kmeans.centroids.get(k).toString()+"\n";
	            if (clusters.containsKey(k)){

	                display += "the cluster size = "+kmeans.clusters.get(k).size()+"\n";
	                display += "Members: \n";
	                String members = String.join(", ", kmeans.clusters.get(k).toString());
	                display += "["+kmeans.clusters.get(k).get(0)+"\n";
	                //display += kmeans.clusters.get(k).toString()+",\n";
	                for (int l =1; l< kmeans.clusters.get(k).size(); l++){
	                	display += ","+kmeans.clusters.get(k).get(l)+"\n";
	                }
	                display += "]\n";
	            }else
	            	display += "Empty cluster !";

	            display += "\n\n";

	        }
	        double t2 = System.currentTimeMillis();
	        display += "fmeasure: "+kmeans.fmeasure()+"\n";
	        display += "emeasure: "+kmeans.emeasure()+"\n";

	        this.rules.setText(display);

	        this.time.setText(String.valueOf((t2-t1)/1000)+" sec ");

		}else if(miningMethod.equals("Clarans")){
			display = "";
			double t1 = System.currentTimeMillis();
			Clarans k = new Clarans(dataset);

			HashMap<Integer, ArrayList<Integer>> clusters =k.Clarans(dataset,Integer.valueOf(this.param1.getText()),Integer.valueOf(this.param2.getText()),Integer.valueOf(this.param3.getText()));
			HashMap<Integer, ArrayList<Thyroid>> cl = k.clustersClarans(dataset, clusters);
	        double t2 = System.currentTimeMillis();

	        cl.forEach((key, value) -> {
	        	display += "\n-------------------------- CLUSTER ----------------------------\n";
	            // Sorting the coordinates to see the most significant tags first.
	            display += key+"\n";
	            String members = String.join(", ", value.toString())+"\n";
	            display += members+"\n";
	        });






	        display += "fmeasure: "+k.fmeasure(cl)+"\n";
	        display += "emeasure: "+k.emeasure(dataset, clusters)+"\n";
	        this.rules.setText(display);


	        this.time.setText(String.valueOf((t2-t1)/1000)+" sec ");
		}else if(miningMethod.equals("Kmedoids")){
			display = "";
			double t1 = System.currentTimeMillis();
			Kmedoids k2 = new Kmedoids(dataset);
	        HashMap<Integer, ArrayList<Integer>> clusters = k2.fit(dataset, Integer.valueOf(this.param1.getText()));
	        //HashMap<Integer, ArrayList<Thyroid>> cl = k.clustersClarans(dataset, clusters);
	        HashMap<Integer, ArrayList<Thyroid>> cl = k2.clusters(dataset, clusters);
	        double t2 = System.currentTimeMillis();
	        cl.forEach((key, value) -> {
	        	display += "\n-------------------------- CLUSTER ----------------------------\n";
	            // Sorting the coordinates to see the most significant tags first.
	            display += key+"\n";
	            String members = String.join(", ", value.toString())+"\n";
	            display += members+"\n";
	        });

	        display += "fmeasure: "+k2.fmeasure(cl)+"\n";
	        display += "emeasure: "+k2.emeasure(dataset, clusters)+"\n";
	        this.rules.setText(display);


	        this.time.setText(String.valueOf((t2-t1)/1000)+" sec ");
		}




	}
}
