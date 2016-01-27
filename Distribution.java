package fiwiGruppeE;

import org.apache.commons.math3.random.EmpiricalDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Distribution {

	public static LineChart<Number, Number> getDistributionChart(StockCourse sc, int quantityClasses) {
		
		double[] temp = new double[sc.getCourse().size()];
		
		int i = 0;
		for(DailyValue dv : sc.getCourse()){
			temp[i++] = dv.getValue();
		}
		
		return getDistributionChart(temp, quantityClasses);
		
	}
	
	public static LineChart<Number, Number> getDistributionChart(double[] zeitreihe, int quantityClasses) {

		
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();

		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Verteilung der Renditen");
		lineChart.setCreateSymbols(false);

		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		
		
		//Create histogram
		//long[] histogram = new long[quantityClasses];
		EmpiricalDistribution distribution = new EmpiricalDistribution(quantityClasses);
		distribution.load(zeitreihe);
		
		
		//Feed table
		int k = 0;
		for (SummaryStatistics stats : distribution.getBinStats()) {
			series.getData().add(new XYChart.Data<Number, Number>(k++, stats.getN()));
		}

		lineChart.getData().add(series);

		return lineChart;
	}

}
