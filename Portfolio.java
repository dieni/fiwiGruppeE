package fiwiGruppeE;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import javafx.scene.control.TableView;

/**
 * 
 * @author Christian Dienbauer
 * 
 *         Contains one or more courses
 *
 */
public class Portfolio {

	List<StockCourse> courses = new ArrayList<StockCourse>();
	double[] portfolioZeitreihe;

	private static Portfolio instance = null;

	private Portfolio() {

	}

	public static Portfolio getInstance() {
		if (instance == null) {
			instance = new Portfolio();
		}
		return instance;
	}

	public List<StockCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<StockCourse> courses) {
		this.courses = courses;
	}

	/**
	 * Add a new course to portfolio
	 * 
	 * @param sC
	 */
	public void addStockCourse(StockCourse sC) {

		courses.add(sC);
		berechnePortfolioZeitreihe();
	}

	/**
	 * Prints all courses from the portfolio
	 */
	public void printPortfolio() {
		for (StockCourse sC : courses) {
			sC.printStockCourse();
		}
	}

	public void displayCovarianceMatrix() {

		// Create as much columns as there are courses + one
		String[] columns = new String[courses.size() + 1];

		// Create Table without rows
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		JTable table = new JTable(tableModel);

		// Create an object for inserting data
		Object[] data = new Object[courses.size() + 1];

		// Name columns
		int i = 1;
		for (StockCourse sC : courses) {

			data[i++] = sC.getName();
			// Insert data

		}

		tableModel.addRow(data);

		// Generate covariance-matrix

		for (StockCourse sC : courses) {
			i = 0; // reset counter

			data[i++] = sC.getName();
			for (StockCourse sC2 : courses) {

				data[i++] = sC.getCovariance(sC2);
			}
			// Insert data
			tableModel.addRow(data);
		}

		// Show table within frame
		JFrame jf = new JFrame();
		jf.setTitle("Kovarianz-Matrix des Portfolios");
		jf.setSize(500, 500);
		jf.setVisible(true);
		// jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(table);
	}

	public void berechnePortfolioZeitreihe() {
		portfolioZeitreihe = new double[courses.get(0).getCourse().size()];

		for (StockCourse sc : courses) {
			int index = 0;
			for (DailyValue dv : sc.getCourse()) {

				portfolioZeitreihe[index++] += dv.getValue();
			}
		}

		for (double d : portfolioZeitreihe) {
			d = d / (courses.get(0).getCourse().size() + 1);
		}

	}

	public double getDailyMeanR() {
		double r = 0;

		for (int i = 1; i < portfolioZeitreihe.length; i++) {
			r += (portfolioZeitreihe[i] - portfolioZeitreihe[i - 1]);
		}

		return r / (portfolioZeitreihe.length - 1);
	}

	public double getVolatility() {
		StandardDeviation sd = new StandardDeviation(false);
		return sd.evaluate(portfolioZeitreihe);
	}

	public double[] getPortfolioZeitreihe() {
		return portfolioZeitreihe;
	}
	
	public ArrayList<Count> getDate(double[] stockCourse){
		double[] stockCourseRounded=new double[stockCourse.length];
		ArrayList<Count> count=new ArrayList<Count>();
		
		
		// Round stockCourse array
		for( int i=0; i<stockCourse.length; i++){
			stockCourseRounded[i]=Math.round(stockCourse[i]*100)/100.0;
			System.out.println(i + " " + stockCourseRounded[i]);
		}
			
		// distribute values in classes
		for(int i=0; i<stockCourseRounded.length; i++){
			count.add(new Count(stockCourseRounded[i]));
			for(int j=0; j<stockCourseRounded.length; j++){
				if(count.get(i).getValue()==stockCourseRounded[j]){
					count.get(i).countUp();
				}
			}
		}
		
		//System.out.println("value: " + count.get(613).getValue() + " Wie oft: " + count.get(613).getCount());
		
		// Delete duplicated entries
		for(int i=0; i<count.size()-1; i++){
			if(count.get(i).getValue()==count.get(i+1).getValue()){
				count.remove(i);
				i--;			
			}
		}
		
		for(int i=0; i<count.size(); i++){
			System.out.println("Value: " + count.get(i).getValue() + " Anzahl: " +  count.get(i).getCount());
		}
		return count;
	}
	
	

}
