package Controllers;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import org.knowm.xchart.BoxChart;
import org.knowm.xchart.BoxChartBuilder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.BoxStyler.BoxplotCalCulationMethod;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import DataStructure.Data;
import DataStructure.Point;

/**
 * JavaFX App
 */
public class StaticController implements Initializable {

    @FXML
    private AnchorPane hist;

    @FXML
    private Button dashbord;

    @FXML
    private Button statistic;

    @FXML
    private Button about;

    @FXML
    private ChoiceBox<String> attribute, attribute1;

    @FXML
    private ComboBox<String> type;

    @FXML
    private Button btn;

    @FXML
    private ImageView infos;

    String PATH  = "";

    Data data ;

    public void setPath(String path){
    	this.PATH = path;
    	/** Initialize Variables*/
    	attribute.setItems(FXCollections.observableArrayList("t3","tri","tsh","tys","dtsh"));
    	attribute1.setItems(FXCollections.observableArrayList("t3","tri","tsh","tys","dtsh"));
    	type.setItems(FXCollections.observableArrayList("Histograme chart","Scatter chart","Box chart"));
    	hist.setVisible(false);
    	data = new Data(PATH);
    	infos.setVisible(false);
    }


    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}


    /**FXML functions*/

    @FXML
	public void aboutAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/about.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			AboutController control = loader.getController();
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
    public void clickAction(){
    	//Check if no attribute is selected*/
    	infos.setVisible(false);
    	if(attribute.getValue() == null | type.getValue() == null){
    		System.out.println("null value");
    		AlertDialog("You have not selected a chart type or chart attribute!");
    	}else{
    		/**which chart to display?*/
    		hist.setVisible(true);
    		if(type.getValue().equals("Scatter chart")){
    			//Scatter chart
    			if(attribute1.getValue() == null){
    				AlertDialog("Scatter chart needs two attribute! Please select them");
    			}else
    				buildScatterChart();
    		}else if(type.getValue().equals("Histograme chart")){
    			//Histogram chart
    			buildHistChart();
    		}else if(type.getValue().equals("Box chart")){
    			//Box chart
    			buildBoxChart();
    		}
    	}
    }

    @FXML
    public void attribute1Disable(){
    	//If histogram or box chart selected disable second attribute
    	if(!type.getValue().equals("Scatter chart")){
    		attribute1.setDisable(true);
    	}else{
    		attribute1.setDisable(false);
    	}
    }

    @FXML
    public void plusInfos(){
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Information Dialog");
    	alert.setHeaderText("Box detail");
    	alert.setContentText("Q1 : "+String.valueOf(data.Q1(attribute.getValue()))
    			+"\n"
    			+"Q3 : "+ String.valueOf(data.Q3(attribute.getValue()))+"\n"
    			+"Min : "+ String.valueOf(data.Min(attribute.getValue()))+"\n"
    			+"Max : "+String.valueOf(data.Max(attribute.getValue()))+"\n"
    			+"Mean : "+String.valueOf(data.mean(attribute.getValue()))
    	);

    	alert.showAndWait();
    }


    /** Chart methods*/

    public void buildScatterChart() {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setMarkerSize(16);

        // Get Data
        List<Double> xData = new LinkedList<Double>();
        List<Double> yData = new LinkedList<Double>();

		if(attribute1.getValue() != attribute.getValue()){
			ArrayList<Point> liste= data.makeDigramme(attribute.getValue(),attribute1.getValue());
			for(int i=0; i<liste.size(); i++){
				xData.add(Double.valueOf(liste.get(i).getX()));
				yData.add(Double.valueOf(liste.get(i).gety1()));
			}
			//Series
	        chart.addSeries("Gaussian Blob", xData, yData);

	        //Display chart
	        showChart((Chart) chart, hist);


		}else{
			AlertDialog("You can not select the same attribute!");
		}


    }


    public void buildHistChart() {
    	// Get Data
        ArrayList<Point> d= data.makeHistogram(attribute.getValue(),10);
        List<Double> y = new ArrayList<Double>();
        List<Double> x = new ArrayList<Double>();
        for(int i=0; i<d.size(); i++){
        	y.add(Double.valueOf(d.get(i).getY()));
        	x.add(Double.valueOf(Math.round(d.get(i).getX())));
        }

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Histogram of "+attribute.getValue())
                .xAxisTitle(attribute.getValue())
                .yAxisTitle("Total")
                .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setPlotGridLinesVisible(false);
        chart.getStyler().setStacked(true);

        // Series
        chart.addSeries(attribute.getValue(), x, y);

        //Display chart
        showChart((Chart) chart, hist);
    }

    public void buildBoxChart() {
    	//Get Data
         ArrayList<Point> d= data.makeHistogram(attribute.getValue(),10);
         List<Double> y = new ArrayList<Double>();
         for(int i=0; i<d.size(); i++){
         	y.add(Double.valueOf(d.get(i).getY()));
         }

    	// Create Chart
    	BoxChart chart = new BoxChartBuilder().title(attribute.getValue()+" box plot").build();

		// Choose a calculation method
		chart.getStyler().setBoxplotCalCulationMethod(BoxplotCalCulationMethod.NP);
		chart.getStyler().setToolTipsEnabled(true);

		// Series
		chart.addSeries(attribute.getValue(), y);

		//Display chart
		showChart((Chart) chart, hist);

		infos.setVisible(true);
    }

    /**Helper methods*/
    private void showChart(Chart chart, AnchorPane pane) {
        JPanel chartPanel = new XChartPanel<Chart>(chart);

        // for embeding swing in javafx
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(chartPanel);

        // for resizing plot to window
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);

        // add chart to the chart area
        pane.getChildren().add(swingNode);

    }

    private void AlertDialog(String errorMsg){
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Selection Error");
		alert.setContentText(errorMsg);
		alert.showAndWait();
    }




}