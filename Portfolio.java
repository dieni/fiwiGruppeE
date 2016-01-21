package fiwiGruppeE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {

	List<ArrayList<DailyValue>> portfolio = new ArrayList<ArrayList<DailyValue>>();

	public void importStockCourse(String filePathCSV) {

		List<DailyValue> stockCourse = new ArrayList<DailyValue>();

		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(filePathCSV));

			br.readLine();

			while ((line = br.readLine()) != null) {

				String[] dailyValue = line.split(csvSplitBy);
				stockCourse.add(new DailyValue(dailyValue[0], dailyValue[6]));

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

		portfolio.add((ArrayList<DailyValue>) stockCourse);
	}

	public void printStockCourse(int index) {

		List<DailyValue> stockCourse = portfolio.get(index);

		for (DailyValue dv : stockCourse) {

			System.out.println("Date: " + dv.getDate() + " Value: " + dv.getValue());

		}
	}

}
