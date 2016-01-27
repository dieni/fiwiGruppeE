package fiwiGruppeE;

import java.io.File;
import java.text.DecimalFormat;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	static private Portfolio portfolio = Portfolio.getInstance();
	static private VBox vboxLeft = new VBox();
	static private VBox vboxCenter = new VBox();
	Button portfolioButton;
	Button csvButton;
	Button covMatrixButton;
	Button plotNormvButton;
	DecimalFormat df = new DecimalFormat("#0.0000");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryWindow) throws Exception {

		final FileChooser fileChooser = new FileChooser();

		// Define layout
		vboxLeft.setPadding(new Insets(10));
		vboxLeft.setSpacing(8);

		vboxCenter.setPadding(new Insets(10));
		vboxCenter.setSpacing(8);

		// Define portfolio list
		ObservableList<String> coursesNames = FXCollections.observableArrayList();
		ListView<String> list = new ListView<String>();
		list.setPrefWidth(100);
		list.setPrefHeight(1000);
		list.setItems(coursesNames);

		// Listens for the selected POSITION in the list
		list.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldIndex, Object newIndex) {
				showDetailsCourse(newIndex);
			}
		});

		BorderPane border = new BorderPane();
		border.setLeft(vboxLeft);
		border.setCenter(vboxCenter);

		// Define button for portfolio view
		portfolioButton = new Button();
		portfolioButton.setText("Portfolio");
		portfolioButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		portfolioButton.setPrefWidth(100);
		vboxLeft.getChildren().add(portfolioButton);
		vboxLeft.getChildren().add(list);

		portfolioButton.setOnAction(e -> { // Lambda expression FREAKIN AWESOME
			showDetailsPortfolio();

		});

		// Define button for covariance matrix
		covMatrixButton = new Button();
		covMatrixButton.setText("Kovarianzmatrix");
		covMatrixButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		covMatrixButton.setPrefWidth(100);
		covMatrixButton.setOnAction(e -> {
			portfolio.displayCovarianceMatrix();
		});

		// Define button for CSV-Import
		csvButton = new Button();
		csvButton.setText("Import CSV");
		csvButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		csvButton.setPrefWidth(100);
		csvButton.setOnAction(e -> {

			File file = fileChooser.showOpenDialog(primaryWindow);
			if (file != null) {
				portfolio.addStockCourse(new StockCourse(file));

				for (StockCourse sC : portfolio.getCourses()) {
					if (!coursesNames.contains(sC.getName()))
						coursesNames.add(sC.getName());
				}

				list.setItems(coursesNames);
			}

		});

		// Window preferences
		Scene scene = new Scene(border, 600, 480);
		primaryWindow.setTitle("Zeitreihenanalyse");
		primaryWindow.setScene(scene);
		primaryWindow.show();
	}

	/**
	 * Refreshes the center of the BorderPane in stock course view
	 * 
	 * @param index
	 */
	public void showDetailsCourse(Object index) {
		StockCourse sc = portfolio.getCourses().get(Integer.parseInt(index.toString()));

		String drendite = df.format(sc.getDailyMeanR());
		String volatili = df.format(sc.getVolatility());
		String mrWatson = df.format(sc.getDWValue());
		String correlat = df.format(sc.getCorrelation());
		String betafakt = df.format(sc.getBeta());
		//String kolmogor = df.format(new KolmogorovSmirnovTest().kolmogorovSmirnovTest(new NormalDistribution(), sc.getRenditen()));
		
		Text courseName = new Text(sc.getName());
		courseName.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		Text durchschnittsrendite = new Text("Durchschnittsrendite pro Tag: " + drendite);
		Text volatilitaet = new Text("Volatilität: " + volatili);

		Text autokorrelation = new Text("Durbin-Watson-Wert: " + mrWatson);
		Text korrelation = new Text("Korrelationkoeffizient zu Portfolio: " + correlat);
		Text beta = new Text("Beta-Faktor zu Portfolio: " + betafakt);

		Text ks = new Text("p-Wert K-S-Test: "
				+ new KolmogorovSmirnovTest().kolmogorovSmirnovTest(new NormalDistribution(), sc.getRenditen()));

		vboxCenter.getChildren().clear();
		vboxCenter.getChildren().add(courseName);
		vboxCenter.getChildren().add(durchschnittsrendite);
		vboxCenter.getChildren().add(volatilitaet);
		vboxCenter.getChildren().add(autokorrelation);
		vboxCenter.getChildren().add(korrelation);
		vboxCenter.getChildren().add(beta);
		vboxCenter.getChildren().add(ks);
		vboxCenter.getChildren().add(Distribution.getDistributionChart(sc.getRenditen(), 50));

	}

	/**
	 * Refreshes the center of the BoderPane in portfolio view
	 */
	public void showDetailsPortfolio() {
		Text name = new Text("Portfolio");
		name.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		Text durchschnittsrendite = null;
		Text volatilitaet = null;

		String drendite = "";
		String volatili = "";

		if (!portfolio.getCourses().isEmpty()) {
			drendite = df.format(portfolio.getDailyMeanR());
			
			volatili = df.format(portfolio.getVolatility());
		}
		durchschnittsrendite = new Text("Durchschnittsrendite pro Tag: " + drendite);
		volatilitaet = new Text("Volatilität: " + volatili);

		vboxCenter.getChildren().clear();
		vboxCenter.getChildren().add(csvButton);
		vboxCenter.getChildren().add(name);

		vboxCenter.getChildren().add(durchschnittsrendite);
		vboxCenter.getChildren().add(volatilitaet);

		vboxCenter.getChildren().add(covMatrixButton);
		if (!portfolio.getCourses().isEmpty())
			vboxCenter.getChildren().add(Distribution.getDistributionChart(portfolio.getRenditen(), 50));
	}

}
