package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import myti.User;

public class ManageUsers extends Application{
	String content;
	final static double creditLimit =100.0;
	final static int multipleLimit = 5;
	String contentinput;

	@Override
	public void start(Stage primaryStage) throws Exception {
	    
	    /* create pane for 
	     * create user function
	     */
	    GridPane user = new GridPane();
	 	ChoiceBox<String> type = new ChoiceBox<String>();
	 	type.getItems().addAll("adult", "senior", "junoir");
	 	
	    //create grid pand for register
	 	Label lefttitle = new Label("Create New User");
		Label textid = new Label("ID");//create label 
	    Label textname = new Label("Name");
		Label textemail = new Label("Email");
		Label texttype = new Label("Type");
		Button createbutton = new Button("CREATE USER");
	
		TextField fieldid = new TextField();
		TextField fieldname = new TextField();
		TextField fieldemail = new TextField();
		TextField message = new TextField();
		
		//set grind size and position
		user.setVgap(25);
		user.setHgap(5);
		user.setPadding(new Insets(10));
		user.setMargin(textid, new Insets(2));
		user.setPadding(new Insets(10,10,10,10));
		
		//put label and text field into grid 
		user.add(lefttitle, 0,0 );
		user.add(textid, 0, 1);
		user.add(fieldid, 1, 1);
		user.add(textname, 0, 2);
		user.add(fieldname, 1, 2);
		user.add(textemail, 0, 3);
		user.add(fieldemail, 1, 3);
		user.add(texttype, 0, 4);
		user.add(type, 1, 4);
		user.add(createbutton, 1, 5);
		user.add(message, 1, 6);

		
		/*create third pane 
		 * for recharge
		 */
		// create new pane object
		GridPane creditpane = new GridPane();
		//create label and text field for third pane
		Label title = new Label("Charge Credit");
		Label choose = new Label("Choose User");
		Button savebutton = new Button("SAVE");
		Button quitbutton = new Button("QUIT");
	    quitbutton.setOnAction(e -> Platform.exit());
	    
		// create user list view
		ListView<String> list = new ListView<String>();
		list.setPrefWidth(30);
		list.setPrefHeight(30);
		User.readmemberfile("src\\fileio\\input.txt");
		list.getItems().addAll(User.idlist);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	    
	//	list.setItems(items);		
		Label remain = new Label("Remain");
		Label remainbox = new Label();		
		Label enter = new Label("Enter credit");
		TextField creditbox = new TextField();		
		Button add = new Button("ADD CREDIT");
		TextArea message1 = new TextArea();
		message1.setPrefWidth(30);
		message1.setPrefHeight(30);
		
		//set size of grid pane
		creditpane.setVgap(25);
		creditpane.setHgap(5);
		creditpane.setPadding(new Insets(10));
		creditpane.setMargin(textid, new Insets(10));
	    creditpane.setPadding(new Insets(10,10,10,300));
	    
	    // put each label and text field into grid
	    creditpane.add(title, 1,0);
		creditpane.add(choose, 1, 1);
		creditpane.add(list, 2, 1);
		creditpane.add(remain, 1, 2);
		creditpane.add(remainbox, 2, 2);
		creditpane.add(enter, 1, 3);
		creditpane.add(creditbox, 2, 3);
		creditpane.add(add, 2, 4);
		creditpane.add(message1, 2, 5);
		creditpane.add(savebutton, 2, 6);
		creditpane.add(quitbutton, 3, 6);
		
		// create H separator line
		Pane pane = new VBox();
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		separator.setPrefHeight(450);
		separator.setPadding(new Insets(1,300,55,280));
		pane.getChildren().add(separator);
		
		
		//create action on list click to show credit
		list.setOnMouseClicked(e ->{
			String click = list.getSelectionModel().getSelectedItem();
			if (click!=null) {
			remainbox.setText("The credit for "+click+" is $"+User.memberslist.get(click).getcredit());
			}
		});
		
		// read member information form text file		
		User.readmemberfile("src\\fileio\\input.txt");
		
		
		// create new user button action
		createbutton.setOnAction(e -> {
				String ID = fieldid.getText();
				String name = fieldname.getText();
				String email = fieldemail.getText();
				SingleSelectionModel<String> Type = type.getSelectionModel();
				for (String item: User.memberslist.keySet()){
					if(User.memberslist.containsKey(ID)) {
						message.setText("The ID exist!");
					}else if (ID.trim().length() ==0 ||name.trim().length() == 0 || email.trim().length()==0 || Type.isEmpty()) {
					message.setText("Cannot enter empty!");
				}else {
					
					User user1 = new User(ID, type.getValue(),name, email, "0");
					User.memberslist.put(user1.getid(), user1);
					message.setText("create succed!");
					User.idlist.add(ID);
					list.getItems().add(ID);
					list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			        break;
				}
				}
				// add new id immedately into list view
				//User.idlist.add(ID);
				//list.getItems().add(ID);
				//list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				
		});
		
        // create action handler for add button 
		add.setOnAction(e ->{	
			// create variable to save enter 
			SelectionModel<String> userlist = list.getSelectionModel();
			String ID = userlist.getSelectedItem(); 
			String creditinput = creditbox.getText();
			   // check the enter is valid or not
			   for (String item : User.memberslist.keySet()){
				   if(User.memberslist.containsKey(ID)) {
						Double credit1 = Double.parseDouble(User.memberslist.get(ID).getcredit());
						Double creditput = Double.parseDouble(creditinput);
				    if((credit1+creditput)>creditLimit) {
						message1.setText("cannot over $100");
					}else if (creditput % multipleLimit !=0) {
						message1.setText("should be multiple of 5");
					}else if (creditinput.trim().length()==0) {
						message1.setText("cannot enter empty");
					}else {
						String totalcredit = String.valueOf(credit1+creditput);
						User.memberslist.get(ID).setcredit(totalcredit);	// update remain credit
						message1.setText("The remain for "+User.memberslist.get(ID).getid()+" is $"+totalcredit);				
						break;
					}
			   }else {
				   message1.setText("ID cannot be empty");
			   }
			}
			   
		});
		
        // create action handler for save button to create output file
		savebutton.setOnAction(e ->{
			User.rewriteMemberFile("src\\fileio\\input.txt"); // update in input file
			User.rewriteMemberFile("src\\fileio\\output.txt"); // put into output file
		});
		
		// create stage scene
		Pane root = new Pane();
		root.getChildren().add(pane);
		root.getChildren().add(creditpane);
		root.getChildren().add(user);
		Scene scene = new Scene(root, 620, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Manage User");
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
