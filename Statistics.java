package fiwiGruppeE;

import java.util.List;

/**
 * 
 * This class is for statistical computation of a stock course.
 * 
 * @author Christian Dienbauer
 *
 */
public class Statistics {

	List<DailyValue> course;
	int size;

	public Statistics(StockCourse sC) {
		setCourse(sC.getCourse());
		setSize(sC.getCourse().size());
	}

	float getMean() {

		float sum = 0;

		for (DailyValue dV : course) {
			sum += dV.getValue();
		}

		return sum / size;
	}

	float getVariance() {
		float mean = getMean();
		float temp = 0;

		for (DailyValue dV : course) {
			temp += (mean - dV.getValue()) * (mean - dV.getValue());
		}

		return temp / size;
	}

	/**
	 * 
	 * @return Volatility of a stock course.
	 */
	float getStdDev() {
		return (float) Math.sqrt(getVariance());
	}

	float getCovariance(StockCourse sC) {
		Statistics sCS = new Statistics(sC);

		float mean1 = getMean();
		float mean2 = sCS.getMean();
		float temp = 0;
		int count = 0;

		for (DailyValue dV : course) {
			for (DailyValue dV2 : sC.getCourse()) {
				if (dV.getDate().equals(dV2.getDate())){
					temp += (mean1 - dV.getValue()) * (mean2 - dV2.getValue());
					count++;
				}
			}
		}

		return temp/count;
	}

	public void setCourse(List<DailyValue> c) {
		this.course = c;
	}

	public List<DailyValue> getCourse() {
		return course;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
