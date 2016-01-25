package fiwiGruppeE;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
		ObservableList<StockCourse> coursesData = FXCollections.observableArrayList();
		ListView<String> list = new ListView<String>();
		list.setPrefWidth(100);
		list.setPrefHeight(1000);
		list.setItems(coursesNames);

		// Listens for the selected ITEM in the list
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
				// System.out.println(new_val);
				// vboxCenter.getChildren().clear();
				// vboxCenter.getChildren().add(new Text(new_val));
			}
		});

		// Listens for the selected POSITION in the list
		list.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldIndex, Object newIndex) {
				// System.out.println(newValue);
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

		covMatrixButton = new Button();
		covMatrixButton.setText("Kovarianzmatrix");
		covMatrixButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		covMatrixButton.setPrefWidth(100);
		covMatrixButton.setOnAction(e->{
			
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

				System.out.println("CSV has been added.");

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
	 * @param index
	 */
	public void showDetailsCourse(Object index){
		StockCourse sc = portfolio.getCourses().get(Integer.parseInt( index.toString() ));
		
		Text courseName = new Text(sc.getName());
		Text durchschnittsrendite = new Text("Durchschnittsrendite pro Tag: " + sc.getDailyMeanR());
		Text volatilität = new Text ("Volatilität: " + sc.getVolatility());
		
		Text autokorrelation = new Text("Durbin-Watson-Wert: " + sc.getDWValue());
		Text korrelation = new Text("Korrelationkoeffizient zu Portfolio: " + sc.getCorrelation());
		Text beta = new Text("Beta-Faktor zu Portfolio: " + sc.getBeta());
		
		
		vboxCenter.getChildren().clear();
		vboxCenter.getChildren().add(courseName);	
		vboxCenter.getChildren().add(durchschnittsrendite);
		vboxCenter.getChildren().add(volatilität);
		vboxCenter.getChildren().add(autokorrelation);
		vboxCenter.getChildren().add(korrelation);
		vboxCenter.getChildren().add(beta);
		
	}

	/**
	 * Refreshes the center of the BoderPane in portfolio view
	 */
	public void showDetailsPortfolio() {
		String name = "PORTFOLIO";

		vboxCenter.getChildren().clear();
		vboxCenter.getChildren().add(csvButton);
		vboxCenter.getChildren().add(new Text(name));
		vboxCenter.getChildren().add(covMatrixButton);
	}

	
}
