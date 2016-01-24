package fiwiGruppeE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public float getMean() {
		Statistics s = new Statistics(this);
		return s.getMean();

	}
	
	public float getVariance(){
		Statistics s = new Statistics(this);
		return s.getVariance();
	}

	public float getVolatility() {
		Statistics s = new Statistics(this);
		return s.getStdDev();
	}

	public float getCovariance(StockCourse sC) {
		Statistics s = new Statistics(this);
		return s.getCovariance(sC);

	}

}
