package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Separator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import myti.Account;
import myti.TravelPass;
import myti.User;

public class CreateJourney extends Application{
	static Map<String, String> stationname = new HashMap<>();
	public static Map<String, Account> allJourneyInfo = new HashMap<>();
	public static double remain;
	//public static ArrayList<String> report = new ArrayList<>();
	public static Map<String, Double> checkremain = new HashMap<>();
	public static Map<String, Double> checkprice = new HashMap<>();
	DecimalFormat df = new DecimalFormat("#.00");
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {		

		/* create second grid pane 
		 * for book journey
		 */
		// create new pane object
		GridPane journey = new GridPane();	
		// create label, button and text field
		Label title = new Label("Create A Journey");
		Label user = new Label("User");
		// create user list view
		ListView<String> list = new ListView<String>();
		list.setPrefWidth(30);
		list.setPrefHeight(30);
	    User.readmemberfile("src\\fileio\\output.txt");
		list.getItems().addAll(User.idlist);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		Label from = new Label("From");
		Label to = new Label("To");
		Label day = new Label("Day");
		Label start = new Label("Start");
		Label end = new Label("End");
		
		Button purchase = new Button("PURCHASE");
		TextField textstart = new TextField();
		TextField textend = new TextField();
		
		//create choice bar for from station
	 	ChoiceBox<String> stationstart = new ChoiceBox<String>();
	 	stationstart.getItems().addAll("Central", "Richmond", "Flagstaff","Lilydale","Epping");
	 	
	 	// create choice bar for to station
	 	ChoiceBox<String> stationto = new ChoiceBox<String>();
	 	stationto.getItems().addAll("Central", "Richmond", "Flagstaff","Lilydale","Epping");
	 	
	 	//create choice bar for day
	 	ChoiceBox<String> Daylist = new ChoiceBox<String>();
	 	Daylist.getItems().addAll("mon", "tue", "wed","thu","fri","sat","sun");
		
		//set grind size and position
		journey.setVgap(25);
	    journey.setHgap(5);
		journey.setPadding(new Insets(10));
		journey.setMargin(user, new Insets(2));
		journey.setPadding(new Insets(10,10,10,10));
		
		//put label and text field into grid 
		journey.add(title, 0,0 );
		journey.add(user, 0, 1);
		journey.add(list, 1, 1);
		journey.add(from, 0, 2);
		journey.add(stationstart, 1, 2);
		journey.add(to, 0, 3);
		journey.add(stationto, 1, 3);
		journey.add(day, 0, 4);
		journey.add(Daylist, 1, 4);
		journey.add(start, 0, 5);
		journey.add(textstart, 1, 5);
		journey.add(end, 0, 6);
		journey.add(textend, 1, 6);
		journey.add(purchase, 1, 7);
		
		/*create third pane 
		 * for display message
		 */
		GridPane textpane = new GridPane();
		Label message = new Label("Pusrchase Message");
		TextArea textbox = new TextArea();
		textbox.setPrefWidth(240);
		textbox.setPrefHeight(200);


		Button savebutton = new Button("SAVE");
		Button quitbutton = new Button("QUIT");
		quitbutton.setOnAction(e -> Platform.exit());
		
		//set grind size and position
		textpane.setVgap(15);
	    textpane.setHgap(5);
		textpane.setPadding(new Insets(10));
		textpane.setPadding(new Insets(10,10,10,300));
		
		textpane.add(message, 1, 0);
		textpane.add(textbox, 1, 1);
		textpane.add(savebutton, 2, 7);
		textpane.add(quitbutton, 3, 7);
		
		
		
		// create a separator line 
		Pane pane = new VBox();
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		separator.setPrefHeight(400);
		separator.setPadding(new Insets(1,300,1,300));
		pane.getChildren().add(separator);
		
		

		// create action for purchase button
		User.readmemberfile("src\\fileio\\output.txt");
		purchase.setOnAction(e -> {
			SelectionModel<String> userlist = list.getSelectionModel();
			String ID = userlist.getSelectedItem(); 
			String startstation= stationstart.getValue();
			String endstation= stationto.getValue();
			String inputday= Daylist.getValue();
			String starttime= textstart.getText();
			String arrivaltime=textend.getText();
		    Double durationtime=0.0;
		    //Double starttime1=0.0;
		    //Double arrivaltime1=0.0;
		    String zone1="";
			SingleSelectionModel<String> From = stationstart.getSelectionModel();
			SingleSelectionModel<String> To = stationto.getSelectionModel();
			SingleSelectionModel<String> Day = Daylist.getSelectionModel();
			stationname.put("Central", "1");
			stationname.put("Flagstaff", "1");
			stationname.put("Richmond", "1");
			stationname.put("Lilydale", "2");
			stationname.put("Epping", "2");
			
		    for (String item : User.memberslist.keySet()){		
		    	if (!User.memberslist.containsKey(ID)) {
					textbox.setText("the ID cannot be empty");
		    	}else if (From.isEmpty() || To.isEmpty() || Day.isEmpty() || starttime.trim().length()==0 || arrivaltime.trim().length()==0) {
		    		textbox.setText("Empty! Please enter station, day and time!");
		    	}else if (startstation == endstation) {
		    		textbox.setText("The start station and end station cannot be the same!");
		    	}else if (!starttime.matches("-?[0-9]+.?[0-9]*")) {
		    		textbox.setText("The Start and end time must be number!");
		    	}else if (!arrivaltime.matches("-?[0-9]+.?[0-9]*")) {
		    		textbox.setText("The Start and end time must be number!");
		    		
		    	//}else if(Double.parseDouble(starttime) % 1000 > 359 || Double.parseDouble(starttime) % 100 > 60) {
		    		//textbox.setText("Invalid input of time");
		    	//}//else if(Double.parseDouble(arrivaltime) % 1000 > 359 || Double.parseDouble(arrivaltime) % 100 > 60) {
		    		//textbox.setText("Invalid input of time");
		    	}else {
		    		durationtime = Double.parseDouble(arrivaltime) - Double.parseDouble(starttime);
		    		Math.abs(durationtime);
		    		zone1 = stationname.get(startstation)+stationname.get(endstation);
		    		
		    		break;
		    	}
		    }
		    	
		    	if (zone1.equals("11") || zone1.equals("22")& durationtime <= 200) {
		    	    TravelPass.setprice(TravelPass.twoHourZone1);	

		        } else if (zone1.equals("11") || zone1.equals("22") & durationtime > 200) {
		    		TravelPass.setprice(TravelPass.allDayZone1);
	
		    	}else if (zone1.equals("12") & durationtime <= 200) {
		    		TravelPass.setprice(TravelPass.twoHourZone12);	

		    	}else if (zone1.equals("12") & durationtime > 200) {
		    		TravelPass.setprice(TravelPass.allDayZone12);

		    	}
		    	
		    	Account ac = new Account("zz", "Central", "Epping", "mon", "1200","1300","30.0","3.5");
		    	allJourneyInfo.put(ac.getid(), ac);
		    	allJourneyInfo.put(ac.getstartstation(), ac);
		    	allJourneyInfo.put(ac.getendstation(), ac);
		    	allJourneyInfo.put(ac.getday(), ac);
		    	allJourneyInfo.put(ac.getstarttime(), ac);
		    	allJourneyInfo.put(ac.getarrivaltime(), ac);
		    	allJourneyInfo.put(ac.getremain(), ac);
		    	allJourneyInfo.put(ac.getprice(), ac);
		    	
		    	for(String item : User.memberslist.keySet()){
		            remain = Double.parseDouble(User.memberslist.get(ID).getcredit());
		            
		    	}
		    	if(remain < TravelPass.getprice()) {
		    		textbox.setText("The remain is not enough!");
		    	}else {
		    	    	
		    		for(String item : allJourneyInfo.keySet()){
		    		    	
		    			if(!allJourneyInfo.containsKey(ID) || (allJourneyInfo.containsKey(ID) & !allJourneyInfo.get(ID).getday().equals(inputday))) {	
		    			    TravelPass tp = null;
		                    tp = new TravelPass(zone1, durationtime,ID,inputday);
		                        
		    				String remain3 = String.valueOf(CreateJourney.remain);
		    				String price3 = String.valueOf(TravelPass.getprice());
		    				Account ac1 = new Account(ID, startstation, endstation, inputday, starttime,arrivaltime,remain3,price3);
		    				allJourneyInfo.put(ac1.getid(), ac1);
		    			    allJourneyInfo.put(ac1.getstartstation(),ac1);
		    				allJourneyInfo.put(ac1.getendstation(),ac1);
		    				allJourneyInfo.put(ac1.getday(),ac1);
		    				allJourneyInfo.put(ac1.getstarttime(),ac1);
		    				allJourneyInfo.put(ac1.getarrivaltime(),ac1);
		    				allJourneyInfo.put(ac1.getremain(),ac1);
		    				allJourneyInfo.put(ac1.getprice(),ac1);
		    				double validtime=Double.parseDouble(starttime);
		    				allJourneyInfo.put(ac1.getid(), ac1);
		    				checkremain.put(ID, CreateJourney.remain);
		    				checkprice.put(ID, TravelPass.getprice());
		    				String content = TravelPass.getduration()+" "+TravelPass.getzone()+" Travel Pass purchased for "+ID+" for $"+TravelPass.getprice()+"\n"+"Valid until "+(validtime+200)+"\n"+"Credit remianing for "+ID+": $"+CreateJourney.remain+"\n";
		    				textbox.setText(content);
		    				DisplayReport.report.add("ID:"+ID+","+"starttime:"+starttime+","+"arrivaltime:"+arrivaltime+","+"startstation:"+startstation+","+"endstation:"+endstation);
		    				User.writeReportFile();
		    				String remain1 = String.valueOf(remain);
		    				User.memberslist.get(ID).setcredit(remain1);
		    				break;
		    				
		    			}else if(allJourneyInfo.containsKey(ID) & allJourneyInfo.get(ID).getday().equals(inputday)){
		    				    //TravelPass tp2 = null;
		    				   // tp2 = new TravelPass(durationtime,zone1,starttime, arrivaltime, ID, inputday);
		    			    	
		    			    TravelPass.travelPass(durationtime,zone1,starttime, arrivaltime, ID, inputday);
		    				if (TravelPass.returncontent == "1") {
		    					textbox.setText("You don't need to pay on Sunday!"+"\n"+"Your remain credit is "+CreateJourney.remain);
		    					
		    			  	}else if(TravelPass.returncontent=="2") {
		    					String content1="The travel pass has already update to All Day Pass! The cost is: $"+df.format(TravelPass.price)+" with discount"+"\n"
		    			  	+"Valid until"+TravelPass.validtime+"\n"
		    				+"Your remain credit is "+CreateJourney.remain;
		    				    textbox.setText(content1);
		    				         
		    			  	}else if (TravelPass.returncontent=="3") {
		    				    textbox.setText("The travel pass has already update to All Day Pass! The cost is: $"+TravelPass.price+"\n"
		    			  	+"Your remain credit is "+CreateJourney.remain);
		    			  	    	
		    				}else if(TravelPass.returncontent=="4"){
		    					textbox.setText("The All Day travel pass is still valid! You don't need to pay"+"\n"
		    				+"Your remain credit is "+CreateJourney.remain);
		    				    
		    				}else if(TravelPass.returncontent=="5"){
		    	 	 		 	textbox.setText("The two hour travel pass still valid! You don't need to pay"+"\n"
		    				+"Your remain credit is "+CreateJourney.remain);
		    				    
		    				}else if (TravelPass.returncontent=="6") {
		    					textbox.setText(durationtime+zone1 +"Travel Pass purchased for"+" "+ID+" "+"for"+" "+"$"+df.format(TravelPass.price)+" with discount."+"\n"
		    				+"Valid until"+TravelPass.validtime+"\n"
		    							+"Credit remianing for"+" "+ID+" "+":"+"$"+CreateJourney.remain);
		    				
		    				}else if (TravelPass.returncontent=="7") {
		    					textbox.setText(durationtime+zone1 +"Travel Pass purchased for "+ID+" for $"+df.format(TravelPass.price)+"\n"
		    				+"Valid until"+TravelPass.validtime+"\n"
		    			    +"Credit remianing for "+ID+" :$"+CreateJourney.remain);
		    				}
		
		    		        String remain2 = String.valueOf(CreateJourney.remain);
		    			    String price2 = String.valueOf(TravelPass.getprice());
		    			    Account ac1 = new Account(ID, startstation, endstation, inputday, starttime,arrivaltime,remain2,price2);
		    			    allJourneyInfo.put(ac1.getid(), ac1);
		    				allJourneyInfo.put(ac1.getstartstation(),ac1);
		    				allJourneyInfo.put(ac1.getendstation(),ac1);
		    				allJourneyInfo.put(ac1.getday(),ac1);
		    				allJourneyInfo.put(ac1.getstarttime(),ac1);
		    				allJourneyInfo.put(ac1.getarrivaltime(),ac1);
		    				allJourneyInfo.put(ac1.getremain(),ac1);
		    				allJourneyInfo.put(ac1.getprice(),ac1);
		    				checkremain.put(ID, CreateJourney.remain);
		    				checkprice.put(ID, TravelPass.getprice());
		    				DisplayReport.report.add("ID:"+ID+","+"starttime:"+starttime+","+"arrivaltime:"+arrivaltime+","+"startstation:"+startstation+","+"endstation:"+endstation);
		    				User.writeReportFile();
		    				String remain1 = String.valueOf(remain);
		    				User.memberslist.get(ID).setcredit(remain1);
		    				break;
		    			}	

		    		}
		    }
		    
		});
		
		savebutton.setOnAction(e ->{
			User.rewriteMemberFile("src\\fileio\\input.txt"); // update in input file
			User.rewriteMemberFile("src\\fileio\\output.txt"); // put into output file
		});
	 	
		Pane root = new Pane();
	    root.getChildren().add(pane);
		root.getChildren().add(textpane);
		root.getChildren().add(journey);
		Scene scene = new Scene(root,650,400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Create A Journey");
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
