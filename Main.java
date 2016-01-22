package fiwiGruppeE;

public class Main {

	public static void main(String[] args) {

		String filePath;
		
		// Create an empty portfolio
		Portfolio portfolio = new Portfolio();

		// Add a stock course to portfolio
		filePath = "C:/Users/Dieni/Documents/eclipseWorkspace/Zeitreihenanalyse/src/apple0914.csv";
		portfolio.addStockCourse(new StockCourse(filePath));

		//Add another stock course to portfolio
		filePath = "C:/Users/Dieni/Documents/eclipseWorkspace/Zeitreihenanalyse/src/msft0914.csv";
		portfolio.addStockCourse(new StockCourse(filePath));

		
		//Print all stock courses from portfolio
		portfolio.printPortfolio();

	}

}
