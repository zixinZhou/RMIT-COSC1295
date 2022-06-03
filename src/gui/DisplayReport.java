package gui;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.BadLocationException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import myti.User;
import javax.swing.*;

import javax.swing.text.*;

import java.awt.*;

import java.awt.event.*;

import java.io.*;

import java.util.*;

import java.util.List;


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
		TextArea message = new TextArea();
		showpane.getChildren().add(display);
		showpane.getChildren().add(message);
		message.setPadding(new Insets(1,1,1,1));
		showpane.setPadding(new Insets(50, 12, 13, 60));
		
		// create action handle for display report button
		display.setOnAction(e -> {
			
			try {
				File file = new File("src\\fileio\\report.txt");
				Scanner scanner;
				scanner = new Scanner(file);
			
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] components = line.split(",");
				message.appendText(line+"\r\n");
	
			}    
			} catch (FileNotFoundException e1) {
			
				e1.printStackTrace();

			}
		});
		
		Pane root1 = new Pane(root,showpane);
		Scene scene = new Scene(root1,600,400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Display Report");
		primaryStage.show();
}
		
    public static void main(String[] args) throws FileNotFoundException {
	    Application.launch(args);
		 
		/*File f1 = new File(args[0]);
		File f2 = new File(args[1]);
		
	    InputStream is1 = new FileInputStream(f1);
	    InputStream is2 = new FileInputStream(f2);	
	    int byteReaded;
	    if (args[0].length()==0 || args[1].length()==0) {
	    	System.out.println("The file does not exist!");
	    }else {
	    	  Application.launch(args);
	    }*/
    }

}
