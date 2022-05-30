package gui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DisplayReport extends Application{
	public static ArrayList<String> report = new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		HBox root = new HBox(10);
		//create button and set padding
		Button savebutton = new Button("SAVE");
		Button quitbutton = new Button("QUIT");
		root.getChildren().add(savebutton);
		root.getChildren().add(quitbutton);
		root.setPadding(new Insets(350, 12, 13, 430));
		// set ont action for quit button to quit
		quitbutton.setOnAction(e -> Platform.exit());

		
		// create new button at top
		VBox showpane = new VBox(10);
		Button display = new Button("DISPLAY JOURNEYS");
		TextField message = new TextField();
		showpane.getChildren().add(display);
		showpane.getChildren().add(message);
		message.setPadding(new Insets(100,300,100,100));
		showpane.setPadding(new Insets(50, 12, 13, 30));
		
		// create action handle for display report button
		display.setOnAction(e -> {
					for (String x : report) { 
						message.setText(x);
					}
				
		});
		
		Pane root1 = new Pane(root,showpane);
		Scene scene = new Scene(root1,600,400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Display Report");
		primaryStage.show();
}
		
    public static void main(String[] args) {
	    Application.launch(args);
    }

}
