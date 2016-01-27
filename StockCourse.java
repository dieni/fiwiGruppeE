package fiwiGruppeE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * 
 * @author Christian Dienbauer
 * 
 *         Imports a stock course from a CSV-File.
 *
 */
public class StockCourse {

	private String name;
	private List<DailyValue> course;
	private Portfolio portfolio = Portfolio.getInstance();

	public StockCourse(File csvFile) {

		course = new ArrayList<DailyValue>();

		// Set name
		setName(csvFile.getName());

		// Settings for reader.
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			// Skip the first line.
			br.readLine();

			while ((line = br.readLine()) != null) {

				String[] dailyValue = line.split(csvSplitBy);
				course.add(new DailyValue(dailyValue[0], dailyValue[6]));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DailyValue> getCourse() {
		return course;
	}

	public void setCourse(List<DailyValue> course) {
		this.course = course;
	}

	/**
	 * Print the stock course with all its statistical informations.
	 */
	public void printStockCourse() {

		System.out.println("--------------------------------------------------------------");
		System.out.println("StockCourse: " + getName() + '\n');
		System.out.println("Mean: " + getMean());
		System.out.println("Volatility: " + getVolatility());

		for (DailyValue dV : course) {

			System.out.println("Date: " + dV.getDate() + " Value: " + dV.getValue());

		}
	}

	/**
	 * Calculate the mean of the course.
	 */
	public double getMean() {
		Statistics s = new Statistics(this);
		return s.getMean();

	}

	public double getVariance() {
		Statistics s = new Statistics(this);
		return s.getVariance();
	}

	public double getVolatility() {
		Statistics s = new Statistics(this);
		return s.getStdDev();
	}

	public double getCovariance(StockCourse sC) {
		Statistics s = new Statistics(this);
		return s.getCovariance(sC);

	}

	public double getDWValue() {
		int n = course.size();

		double numerator = 0;
		double denumerator = 0;

		double residual;
		double previousResidual;

		for (int i = 0; i < n; i++) {
			residual = course.get(i).getValue();

			if (i > 0) {
				previousResidual = course.get(i - 1).getValue();
				numerator += Math.pow(residual - previousResidual, 2);
			}

			denumerator += residual * residual;
		}

		double dwValue = numerator / denumerator;
		return dwValue;
	}

	public double getDailyMeanR() {
		double r = 0;

		for (int i = 1; i < course.size(); i++) {
			r += (course.get(i).getValue() - course.get(i - 1).getValue());
		}

		return r / (course.size() - 1);
	}

	public double getCorrelation() {

		PearsonsCorrelation pc = new PearsonsCorrelation();
		double courseArray[] = new double[course.size()];

		for (int i = 0; i < course.size(); i++) {
			courseArray[i] = course.get(i).getValue();
		}

		return pc.correlation(courseArray, portfolio.getPortfolioZeitreihe());

	}

	public double getBeta() {
		double courseArray[] = new double[course.size()];

		for (int i = 0; i < course.size(); i++) {
			courseArray[i] = course.get(i).getValue();
		}
		//System.out.println(StatUtils.populationVariance(portfolio.getPortfolioZeitreihe()));

		Covariance covariance = new Covariance();
		double cov = covariance.covariance(courseArray, portfolio.getPortfolioZeitreihe());
		return cov / StatUtils.variance(portfolio.getPortfolioZeitreihe());
	}

	public double[] getRenditen() {
		double[] renditen = new double[course.size() - 1];
		for (int i = 1; i < course.size(); i++) {
			renditen[i - 1] = course.get(i).getValue() - course.get(i - 1).getValue();
		}
		return renditen;
	}

}