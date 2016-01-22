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

	public StockCourse(String csvPath) {

		course = new ArrayList<DailyValue>();
		File f = new File(csvPath);

		// Set name
		setName(f.getName());

		// Settings for reader.
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(f));
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
	 * Print the stock course
	 */
	public void printStockCourse() {
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("StockCourse: " + getName() + '\n');
		
		for (DailyValue dV : course) {

			System.out.println("Date: " + dV.getDate() + " Value: " + dV.getValue());

		}
	}
	

}
