package Controllers;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private AnchorPane chartArea;// for injecting the XChart

    @FXML
    private AnchorPane hist;

    @FXML
    private Button dashbord;

    @FXML
    private Button statistic;

    @FXML
    private Button about;

    @FXML
    private ChoiceBox<String> attribute;

    @FXML
    private ChoiceBox<String> type;

    String choice_attribute, choice_type;



    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

    	attribute.setItems(FXCollections.observableArrayList("t3","tri","tsh","tys","dtsh"));
    	type.setItems(FXCollections.observableArrayList("Histograme chart","Scatter chart","Box chart"));



    	//hist.setVisible(false);





    	//buildScatterChart();
    	buildHistChart();
    	//buildBoxChart();

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

    public void buildScatterChart() {
            	// Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(16);

        // Series
        List<Double> xData = new LinkedList<Double>();
        List<Double> yData = new LinkedList<Double>();
        Random random = new Random();
        int size = 1000;
        for (int i = 0; i < size; i++) {
          xData.add(random.nextGaussian() / 1000);
          yData.add(-1000000 + random.nextGaussian());
        }
        chart.addSeries("Gaussian Blob", xData, yData);

        //showChart((Chart) chart, scatter);
    }


    public void buildHistChart() {
    	// Create Chart

        Data data = new Data();
        ArrayList<Point> d= data.makeHistogram(attribute.getValue(),10);

        List<Double> y = new ArrayList<Double>();
        List<Double> x = new ArrayList<Double>();
        for(int i=0; i<d.size(); i++){
        	y.add(Double.valueOf(d.get(i).getY()));
        	x.add(Double.valueOf(Math.round(d.get(i).getX())));
        }

        // Create Chart
        CategoryChart chart =
            new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Variation of "+attribute.getValue()+"")
                .xAxisTitle("Score")
                .yAxisTitle("Total")
                .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setPlotGridLinesVisible(false);

        // Series
        chart.addSeries(choice_attribute, x, y);
        //chart.addSeries("test 1", Arrays.asList(0, 1, 2, 3, 4), Arrays.asList(4, 5, 9, 6, 5));
        showChart((Chart) chart, hist);
    }



    private List<Double> getGaussianData(int count) {

      List<Double> data = new ArrayList<Double>(count);
      Random r = new Random();
      for (int i = 0; i < count; i++) {
        data.add(r.nextGaussian() * 10);
      }
      return data;
    }


    public void buildBoxChart() {
    	// Create Chart
    	BoxChart chart =
    			new BoxChartBuilder().title("box plot demo").build();

    		// Choose a calculation method
    		chart.getStyler().setBoxplotCalCulationMethod(BoxplotCalCulationMethod.N_LESS_1_PLUS_1);
    		chart.getStyler().setToolTipsEnabled(true);

    		// Series
    		chart.addSeries("boxOne", Arrays.asList(-1,20,22,23,0,-100,1,2,3,4));
    		    //showChart((Chart) chart, box);
    }



}