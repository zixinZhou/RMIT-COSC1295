package myti;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

public class MyTiSystem {
	static Map<String, User> memberslist = new HashMap<>();
	static Map<String, String> stationname = new HashMap<>();
	static Map<String, Double> checkremain = new HashMap<>();
	static Map<String, Double> checkprice = new HashMap<>();
	static Map<String, Account> allJourneyInfo = new HashMap<>();
	final static double creditLimit =100.0;
	final static int multipleLimit = 5;
	//private static final double twoHourZone1 = 0;	
	static double remain;
	static ArrayList<String> report = new ArrayList<>();
	static Map<String, Integer> lastcal = new HashMap<>();
	static Map<String, Integer> caltimes = new HashMap<>();



	
//main function 	
	public static void main(String[] args) {
		// loop structure for main menu
		Scanner MenuOption = new Scanner(System.in);
		boolean invalidoption = true;
		do {
			Menu();			
			System.out.println();
			// define the option
		    try {
		    	int option = Integer.parseInt(MenuOption.next());
			if (option == 1) {
				creatjourney();
			}
				
			else if (option == 2) {
				rechargecredit();
			}
			
			else if (option == 3) {
				showRemainCredit();
			}
				
			else if (option == 4) {
				printreport();
			}
			
			else if (option == 5) {
				showupdatepricing();
			}
				
			else if (option == 6) {
				showstation();
			}
				
			else if (option == 7) {
				registeruser();
			}
				
			else if (option == 8) {
				System.out.println("Goodbye!");
				break;
			}
			else {
				System.out.println("Please enter int valid number!");
			}
		    
		    }catch (Exception e) {System.out.println("Invalid input! Please enter again!");}
		}while (invalidoption);
	    MenuOption.close();

	}
	
/* print out main menu 
 * information with option
 */
static void Menu() {
	System.out.println();
	System.out.println("Welcome to MyTiSystem!\n1. Buy a Journey for a User\n2. Recharge a MyTi card for a User\n3. Show remaining credit for a User\n4. Print User Reports\n5. Update pricing\n6. Show Station statistics\n7. Add a new User\n8. Quit");
	System.out.println("Please make a choice!");
	stationname.put("Central", "1");
	stationname.put("Flagstaff", "1");
	stationname.put("Richmond", "1");
	stationname.put("Lilydale", "2");
	stationname.put("Epping", "2");
	Account ac = new Account("zz", "Central", "Epping", "mon", "1200","1300","30.0","3.5");
	allJourneyInfo.put(ac.getid(), ac);
	allJourneyInfo.put(ac.getstartstation(), ac);
	allJourneyInfo.put(ac.getendstation(), ac);
	allJourneyInfo.put(ac.getday(), ac);
	allJourneyInfo.put(ac.getstarttime(), ac);
	allJourneyInfo.put(ac.getarrivaltime(), ac);
	allJourneyInfo.put(ac.getremain(), ac);
	allJourneyInfo.put(ac.getprice(), ac);
}

    
/* create a new user with the ID check from member file
 */
public static void registeruser() throws FileNotFoundException {	
	Scanner input = new Scanner(System.in);
	String inputid="";
	String inputtype="";
	String inputname="";
	String inputemail="";
    User.readmemberfile("src\\fileio\\member.txt");	 
    for (String item: memberslist.keySet()){
    	System.out.println("Enter an ID: ");
    	inputid = input.next();
    	if(memberslist.containsKey(inputid)) {
    		System.out.println("The ID has already exist! ");	
    	}else { 		
    		break;
    	}   	
    }	
	for(;;) {
		System.out.println("Please enter card type: ");
		inputtype = input.next();
		if (!inputtype.equals("adult") & !inputtype.equals("senior") & !inputtype.equals("junior")) {
			System.out.println("The type must enter Adult, Junior or Senior! ");		
		}
		else {
			break;		     
		}
	}	
	for(;;) {
		System.out.println("Please enter your name: ");
	    inputname = input.next();
		if (inputname.isEmpty()) {
			System.out.println("The name cannot be empty! ");		
		}
		else {
			break;		     
		}
	}	
	for(;;) {
		System.out.println("Please enter your email: ");
		inputemail = input.next();
		if (inputemail.isBlank()) {
			System.out.println("The name cannot be empty! ");		
		}
		else {
			break;		     
		}
	}       
		String content =inputid+","+inputtype+","+inputname+","+inputemail+","+"0";	
		User.savememberfile("member.txt",content);
	}

/*recharge for card credit
 */
public static void rechargecredit() throws IOException {
	Scanner input = new Scanner(System.in);
	String ID ;
	double inputcredit;
	User.readmemberfile("src\\fileio\\member.txt");
    for (String item : memberslist.keySet()){
    	System.out.println("Enter an ID to recharge: ");
    	ID = input.next();
    	if (memberslist.containsKey(ID)) {
    	    System.out.println("Please enter value for credit: ");
    	    inputcredit = input.nextDouble();
    	    String remaincredit = memberslist.get(ID).getcredit();
    	    Double credit1 = Double.parseDouble(remaincredit);
    	    if((inputcredit + credit1) > creditLimit) {
    	      	System.out.println("The credit cannot charge over 100!");    
    	      	
    	    }else if (inputcredit % multipleLimit !=0) {
    		System.out.println("The charge should be multiple of 5!"); 	
    		
    	    }else {
    	     	System.out.println("Charge successfully!");
    		    credit1 = credit1+inputcredit;
    		    String credit2 = String.valueOf(credit1);
    		    memberslist.get(ID).setcredit(credit2);
    		    break;
    	    }
    	}
    	else{
    		System.out.println("The ID does not exit!");
    	}
    	}
    	User.rewriteMemberFile("src\\fileio\\input.txt");
    } 



/*Show remain credit for user
 */
static void showRemainCredit() throws IDnotExistException, FileNotFoundException {
	Scanner sc = new Scanner(System.in);
	String ID;
	User.readmemberfile("src\\fileio\\member.txt");
    for (String item : memberslist.keySet()){
    	System.out.println("Enter an ID: ");
    	ID = sc.next();
    	if(allJourneyInfo.containsKey(ID)) {
    		System.out.println(checkremain.get(ID));
    		break;
    	}else if (!allJourneyInfo.containsKey(ID)) {
    		System.out.println(memberslist.get(ID).getcredit());
    		break;
    	}else {
    		System.out.println("The ID does not exit!");
    		continue;
    	}
    	}
}

/*show update price
 */
static void showupdatepricing() throws IOException {
	Scanner sc = new Scanner(System.in);
	String ID;
	User.readmemberfile("src\\fileio\\member.txt");
    for (String item : memberslist.keySet()){
    	System.out.println("Enter an ID: ");
    	ID = sc.next();
    	if(allJourneyInfo.containsKey(ID)) {
    		System.out.println(checkprice.get(ID));
    		break;
    	}else {
    		System.out.println("The ID does not exist!");
    		continue;
    	}
    	}
}

/*print report for user and sorted by ID
 */
public static void printreport() throws IOException {
	Scanner sc = new Scanner(System.in);
	String ID;
	for (;;) {
		System.out.println("Enter an ID: ");
		ID = sc.next();
		if(allJourneyInfo.containsKey(ID)) {
			for (String x : report) { 
				System.out.println(x);
			}
			break;
		}else {
			System.out.println("The ID does not exist!");
			continue;
		}
	}	
}



/*calculate for the times of each start station
 * and end station
 */
public static void showstation() {
	Scanner sc = new Scanner(System.in);
	String ID;
	for (String item : allJourneyInfo.keySet()) {
		int startcount=0;
		int endcount=0;
		System.out.println("Enter an ID: ");
		ID = sc.next();
		if(allJourneyInfo.containsKey(ID)) {
			System.out.println(caltimes);
			break;

		}else {
			System.out.println("There is no record for this ID!");
			continue;
		}
	}
}


/* create a new journey 
 */
public static void creatjourney() throws IOException,RemainNotenoughException{
	Scanner input = new Scanner(System.in);
	String ID="";
	String startstation="";
	String endstation="";
	String inputday="";
	String starttime="";
	String arrivaltime="";
    Double durationtime=0.0;
    Double starttime1=0.0;
    Double arrivaltime1=0.0;
    String zone1="";
	User.readmemberfile("src\\fileio\\member.txt");
	int count=0;
	for (String item : memberslist.keySet()){		
	    System.out.println("Which user: ");
	    ID = input.next();
	    if (memberslist.containsKey(ID)) {
	    	break;
	    }else {
	    	System.out.println("The ID does not exit!");
	    }
	}
	
	for(;;) {
		System.out.println("From what Station: ");
		startstation = input.next();
		if (stationname.containsKey(startstation)) {
	    	int stcount = count+1;
	    	lastcal.put(startstation, stcount);
			break;
		}else {
			System.out.println("The station does not valid! Please enter Central,Flagstaff,Richmond,Lilydale and Epping!");
		}
	}
	
	for(;;) {
	    System.out.println("To what station: ");
	    endstation = input.next();
	    if(stationname.containsKey(endstation)) {
	    	zone1 = stationname.get(startstation)+stationname.get(endstation);
	    	int endcount = count+1;
	    	lastcal.put(endstation, endcount);
			TravelPass tp3 = new TravelPass(startstation,endstation,ID);
			
	    	break;
	    }else {
	    	System.out.println("The station does not valid! Please enter Central,Flagstaff,Richmond,Lilydale and Epping!");
	    }
	}
	
	for(;;) {
		System.out.println("What day: ");
		inputday = input.next();
		if(inputday.equals("mon") || inputday.equals("tue") || inputday.equals("wed") || inputday.equals("thu") || inputday.equals("fri") || inputday.equals("sat") || inputday.equals("sun")) {
			break;
		}else {
		    System.out.println("Invalid input! It should be in same day! "
		    		+ "Please enter like 'mon','tus','wed'and so on.");
		}
	}
	
	for(;;) {
		System.out.println("Start time: ");
		starttime = input.next();
		String strstart = starttime.replaceAll("\\p{Punct}", "");
		starttime1 = Double.parseDouble(strstart);
		if(starttime1 % 1000 == 0 || starttime1 % 1000 <= 359 & starttime1 % 100 == 0 || starttime1 % 100 < 60) {
			break;
		}else {
			System.out.println("Input invalid!");
		}
	}
	
	for(;;) {
		System.out.println("Arrival time: ");
		arrivaltime = input.next();
		String strarrival = arrivaltime.replaceAll("\\p{Punct}", "");
		arrivaltime1 = Double.parseDouble(strarrival);
		if (arrivaltime1 % 1000 == 0 || arrivaltime1 % 1000 <= 359 & arrivaltime1 % 100 == 0 || arrivaltime1 % 100 < 60) {

			durationtime = arrivaltime1 - starttime1;
			Math.abs(durationtime);
			break;
		}else {
			System.out.println("Input invalid!");
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
   
    for(String item : memberslist.keySet()){
        remain = Double.parseDouble(memberslist.get(ID).getcredit());
    }
	if(remain < TravelPass.getprice()) {
		System.out.println("The credit is not enough! Please charge your credit!");
	}else {
		for(String item : allJourneyInfo.keySet()){
			
			if(!allJourneyInfo.containsKey(ID) || allJourneyInfo.containsKey(ID) & !allJourneyInfo.get(ID).getday().equals(inputday)) {	
                TravelPass tp = null;
                tp = new TravelPass(zone1, durationtime,ID,inputday);
				String remain2 = String.valueOf(remain);
				String price2 = String.valueOf(TravelPass.getprice());
				String remain3 = String.valueOf(remain);
				String price3 = String.valueOf(TravelPass.getprice());
				Account ac = new Account(ID, startstation, endstation, inputday, starttime,arrivaltime,remain3,price3);
				allJourneyInfo.put(ac.getid(), ac);
				allJourneyInfo.put(ac.getstartstation(),ac);
				allJourneyInfo.put(ac.getendstation(),ac);
				allJourneyInfo.put(ac.getday(),ac);
				allJourneyInfo.put(ac.getstarttime(),ac);
				allJourneyInfo.put(ac.getarrivaltime(),ac);
				allJourneyInfo.put(ac.getremain(),ac);
				allJourneyInfo.put(ac.getprice(),ac);
				double validtime=starttime1;;
				allJourneyInfo.put(ac.getid(), ac);
				checkremain.put(ID, remain);
				checkprice.put(ID, TravelPass.getprice());
				System.out.println(TravelPass.getduration()+" "+TravelPass.getzone()+" "+"Travel Pass purchased for"+" "+ID+" "+"for"+" "+"$"+TravelPass.getprice());
				System.out.println("Valid until"+" "+(validtime+200));
				System.out.println("Credit remianing for"+" "+ID+":"+"$"+remain);
				report.add("ID:"+ID+","+"starttime:"+starttime+","+"arrivaltime:"+arrivaltime+","+"startstation:"+startstation+","+"endstation:"+endstation);
				
				break;
				
			}else if(allJourneyInfo.containsKey(ID) & allJourneyInfo.get(ID).getday().equals(inputday)){
				//TravelPass tp2 = null;
				TravelPass.travelPass(durationtime, zone1, starttime, arrivaltime, ID, inputday);
				String remain2 = String.valueOf(remain);
				String price2 = String.valueOf(TravelPass.getprice());
				Account ac = new Account(ID, startstation, endstation, inputday, starttime,arrivaltime,remain2,price2);
				allJourneyInfo.put(ac.getid(), ac);
				allJourneyInfo.put(ac.getstartstation(),ac);
				allJourneyInfo.put(ac.getendstation(),ac);
				allJourneyInfo.put(ac.getday(),ac);
				allJourneyInfo.put(ac.getstarttime(),ac);
				allJourneyInfo.put(ac.getarrivaltime(),ac);
				allJourneyInfo.put(ac.getremain(),ac);
				allJourneyInfo.put(ac.getprice(),ac);
				checkremain.put(ID, remain);
				checkprice.put(ID, TravelPass.getprice());
				report.add("ID:"+ID+","+"starttime:"+starttime+","+"arrivaltime:"+arrivaltime+","+"startstation:"+startstation+","+"endstation:"+endstation);
				
				break;
			}	

		}
	}   
	
}

}







