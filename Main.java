package fiwiGruppeE;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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

	

	static private Portfolio portfolio = new Portfolio();
	Button portfolioButton;
	Button csvButton;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryWindow) throws Exception {
		
		final FileChooser fileChooser = new FileChooser();

		// Define layout
		VBox vboxLeft = new VBox();
		vboxLeft.setPadding(new Insets(10));
		vboxLeft.setSpacing(8);

		VBox vboxCenter = new VBox();
		vboxCenter.setPadding(new Insets(10));
		vboxCenter.setSpacing(8);

		// Populate portfolio list
		
		ObservableList<String> coursesNames = FXCollections.observableArrayList();
		ObservableList<StockCourse> coursesData = FXCollections.observableArrayList();
		ListView<String> list = new ListView<String>();
		list.setPrefWidth(100);
		list.setPrefHeight(1000);
		list.setItems(coursesNames);
		//list.setCellFactory(ComboBoxListCell.forListView(coursesData.));

		//delete this!!
		File f = new File("C:/Users/Dieni/Documents/eclipseWorkspace/Zeitreihenanalyse/src/apple0914.csv");
		portfolio.addStockCourse(new StockCourse(f));
		
		for(StockCourse sC : portfolio.getCourses()){
			coursesNames.add(sC.getName());
		}
		
		// list.getSelectionModel().getSelectedItem();

		BorderPane border = new BorderPane();
		border.setLeft(vboxLeft);

		// Define button for portfolio view
		portfolioButton = new Button();
		portfolioButton.setText("Portfolio");
		portfolioButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		portfolioButton.setPrefWidth(100);
		vboxLeft.getChildren().add(portfolioButton);
		vboxLeft.getChildren().add(list);
		
		

		portfolioButton.setOnAction(e -> { // Lambda expression FREAKIN AWESOME
			border.setCenter(vboxCenter);
			System.out.println("Links to portfolio view");
			
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
				
			}

			
		});

		vboxCenter.getChildren().add(csvButton);
		

		// ################################################################################
		// Auswahl

		// ################################################################################

		// Window preferences
		Scene scene = new Scene(border, 600, 480);
		primaryWindow.setTitle("Zeitreihenanalyse");
		primaryWindow.setScene(scene);
		primaryWindow.show();
	}


	
}
