package fiwiGruppeE;



public class Main {

	public static void main(String[] args) {
		
		Portfolio portfolio = new Portfolio();

		String filePath = "C:/Users/rockw/Documents/eclipseWorkspace/FiwiZeitreihenanalyse/src/apple0914.csv";
		
		portfolio.importStockCourse(filePath);
		
		filePath = "C:/Users/rockw/Documents/eclipseWorkspace/FiwiZeitreihenanalyse/src/msft.csv";
		portfolio.importStockCourse(filePath);
		
		portfolio.printStockCourse(0);
		System.out.println("--------------------------------------------------------------");
		portfolio.printStockCourse(1);
		
	}
	
	
}

