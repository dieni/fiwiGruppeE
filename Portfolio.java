package fiwiGruppeE;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
	
	public void displayCovarianceMatrix(){
		
		//Create as much columns as there are courses + one
		String[] columns = new String[courses.size()+1];
		
		
		//Create Table without rows
		DefaultTableModel tableModel = new DefaultTableModel(columns,0);
		JTable table = new JTable(tableModel);
		
		//Create an object for inserting data
		Object[] data = new Object[courses.size()+1];
		
		//Name columns
		int i = 1;
		for(StockCourse sC : courses){
			
			data[i++] = sC.getName();
			//Insert data
			tableModel.addRow(data);
		}
		
		//Generate covariance-matrix 
		for(StockCourse sC : courses){
			i=0; //reset counter
			data[i++] = sC.getName();
			for(StockCourse sC2 : courses){
				data[i++] = sC.getCovariance(sC2);
			}
			//Insert data
			tableModel.addRow(data);
		}
		
		//Show table within frame
		JFrame jf = new JFrame();
		jf.setTitle("Kovarianz-Matrix des Portfolios");
		jf.setSize(500, 500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(table);
		
		
	}
	

}
