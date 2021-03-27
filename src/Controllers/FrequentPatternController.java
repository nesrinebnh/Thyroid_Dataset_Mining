package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import DataStructure.Apriori;
import DataStructure.Data;
import DataStructure.Rule;
import DataStructure.Table;
import DataStructure.TableItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FrequentPatternController implements Initializable{

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
    private Button btn;
    @FXML
    private TextField time;

    @FXML
    private TextField bins;

    @FXML
    private TextField minsupp;
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	bins.setText("10");
    	minsupp.setText("3");

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
	public void staticAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/static.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			StaticController control = loader.getController();
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
	public void extractAction(){

		Data data = new Data();
		String display = "";
		double t1 = System.currentTimeMillis();
        Apriori apriori =new Apriori(data);
        apriori.descritizationUsingBining(Integer.valueOf(bins.getText()));

        Table t = apriori.apriori_algorithm(Integer.valueOf(minsupp.getText()));

        System.out.println("Frequent Pattern: ");
        display +="Frequent Pattern: \n";

        HashMap<String , Table> motifs = apriori.L;
        if(!motifs.isEmpty()){
            for(String key : motifs.keySet()){
                Table tableMotifs = motifs.get(key);
                for(TableItem t2 : tableMotifs.table)
                	display+= t2.getItem()+"\n";
            }
        }

        display += "\nRules\n";
        //t.writeTable();
        if(!t.table.isEmpty()){
            HashMap<Rule , Float> conf = apriori.calculate_confs(t);

            for(Rule r : conf.keySet()){
                //if(conf.get(r)==1.0)
            	display += r.left.toString()+"->"+r.right+" Conf="+conf.get(r) +"\n";
                //System.out.println(r.left.toString()+"->"+r.right+" Conf="+conf.get(r));
            }
        }
        double t2 = System.currentTimeMillis();
        this.rules.setText(display);

        this.time.setText(String.valueOf((t2-t1)/1000)+" sec ");
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

		//System.out.println(table2.getSelectionModel().getSelectedItem().getmode());
	}

	@FXML
	public void miningAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/Mining.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			Scene scene = new Scene(roote);
			MiningController control = loader.getController();
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
