package fiwiGruppeE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

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

		Collections.reverse(sC.getCourse());
		
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
			r += ((portfolioZeitreihe[i] / portfolioZeitreihe[i - 1])) -1;
		}

		return r /portfolioZeitreihe.length *100; //in %

	}

	public double getVolatility() {
		StandardDeviation sd = new StandardDeviation(false);
		return sd.evaluate(portfolioZeitreihe);
	}

	public double[] getPortfolioZeitreihe() {
		return portfolioZeitreihe;
	}

	public double[] getRenditen() {
		double[] renditen = new double[portfolioZeitreihe.length - 1];
		for (int i = 1; i < portfolioZeitreihe.length; i++) {
			renditen[i - 1] = (portfolioZeitreihe[i] / portfolioZeitreihe[i - 1]) - 1;
		}
		return renditen;
	}

}
