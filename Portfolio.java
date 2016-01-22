package fiwiGruppeE;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Christian Dienbauer
 * 
 * Contains one or more courses
 *
 */
public class Portfolio {

	List<StockCourse> courses = new ArrayList<StockCourse>();

	public List<StockCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<StockCourse> courses) {
		this.courses = courses;
	}

	/**
	 * Add a new course to portfolio
	 * @param sC
	 */
	public void addStockCourse(StockCourse sC) {

		courses.add(sC);
	}
	
	/**
	 * Prints all courses from the portfolio
	 */
	public void printPortfolio(){
		for(StockCourse sC : courses){
			sC.printStockCourse();
		}
	}
	
	

}
