package myti;
import java.text.DecimalFormat;
import java.util.ArrayList;

import gui.CreateJourney;

public class TravelPass{

	public static final double twoHourZone1 = 2.50;
	public static final double twoHourZone12 = 3.50;
 	public static final double allDayZone1 = 4.90;
    public static final double allDayZone12 = 6.80;
    public static final double discountrate = 0.50;
 	private static String duration;
	private static String zone;
 	public static double price;
	private static double lastcost=0.00;
	private static double rm;
	DecimalFormat df = new DecimalFormat("#.00");
	public static Double validtime;
    public static String returncontent;
 	
 	/*get method to get zone, duration and price
 	 */
 	public static String getzone() {
 		return zone;
 	}
 	
 	public static String getduration() {
 		return duration;
 	}
 	public static double getprice() {
 		return price;
 	}
 	public static void setprice(double price) {
	    TravelPass.price = price;
 		
 	}
 	
 	
 	/* this loop is check the cost for user create journey at first time,
 	 * with different zone, duration and time.
 	 */
 	
 	public TravelPass(String zone1, double duration1, String ID, String day) {
 		if (zone1.equals("11") & duration1<=200 || zone1.equals("22")& duration1 <= 200) {
 		    TravelPass.zone = "zone 1"; // check for zone and duration
 			TravelPass.duration = "2 hours"; 			
 			if (User.memberslist.get(ID).gettype().equals("senior") & day.equals("sun")) {
 				price = 0;
 				CreateJourney.remain = MyTiSystem.remain - price;
 			}else if (User.memberslist.get(ID).gettype().equals("junior") || User.memberslist.get(ID).gettype().equals("senior") ) {
 				price = twoHourZone1 * discountrate;
 				CreateJourney.remain = CreateJourney.remain - price;
 				
 			}else {
 				price = twoHourZone1;
 				CreateJourney.remain = CreateJourney.remain - price; // calculate the remain
 			}
  
 			
 		} else if (zone1.equals("11") & duration1 >200 || zone1.equals("22") & duration1 > 200) {
 			TravelPass.zone = "zone 1";
		    TravelPass.duration = "all day";
			if (User.memberslist.get(ID).gettype().equals("senior") & day.equals("sun")) {
 				price = 0;
 				CreateJourney.remain = CreateJourney.remain - price;
 			}else if (User.memberslist.get(ID).gettype().equals("junior") || User.memberslist.get(ID).gettype().equals("senior") ) {
 				price = allDayZone1 * discountrate;
 				CreateJourney.remain = CreateJourney.remain - price;
 				
 			}else {
 				price = allDayZone1;
 				CreateJourney.remain = CreateJourney.remain - price; // calculate the remain
 			}


		}else if (zone1.equals("12") & duration1 <= 200) {
			TravelPass.zone = "zone 1 and 2";
			TravelPass.duration = "2 hours";
			if (User.memberslist.get(ID).gettype().equals("senior") & day.equals("sun")) {
 				price = 0;
 				CreateJourney.remain = CreateJourney.remain - price;
 			}else if (User.memberslist.get(ID).gettype().equals("junior") || User.memberslist.get(ID).gettype().equals("senior") ) {
 				price = twoHourZone12 * discountrate;
 				CreateJourney.remain = CreateJourney.remain - price;
 				
 			}else {
 				price = twoHourZone12;
 				CreateJourney.remain = CreateJourney.remain - price; // calculate the remain
 			}

 			
		}else if (zone1.equals("12") & duration1 > 200) {
			TravelPass.zone = "zone 1 and 2";
			TravelPass.duration = "all day";
			if (User.memberslist.get(ID).gettype().equals("senior") & day.equals("sun")) {
 				price = 0;
 				CreateJourney.remain = CreateJourney.remain - price;
 			}else if (User.memberslist.get(ID).gettype().equals("junior") || User.memberslist.get(ID).gettype().equals("senior")) {
 				price = allDayZone12 * discountrate;
 				CreateJourney.remain = CreateJourney.remain - price;
 				
 			}else {
 				price = allDayZone12;
 				CreateJourney.remain = CreateJourney.remain - price; // calculate the remain
 			}
		}	
 	}
 	
 	
 	/*this loop is for user to pay the journey for second or third journey,
 	 * or pay for new journey in different day.
 	 */
 	
 	public static String travelPass(Double durationtime,String zone1, String starttime, String arrivaltime, String ID, String day) {
 	    Double thisst=0.0;
 		Double thisar=0.0;
 	    Double lastst=0.0;
 		Double lastar=0.0;

 		for(String item : CreateJourney.allJourneyInfo.keySet()){
 			String start = CreateJourney.allJourneyInfo.get(ID).getstarttime();
 			String arrivel = CreateJourney.allJourneyInfo.get(ID).getarrivaltime();
 			lastar = Double.parseDouble(arrivel);
 			lastst = Double.parseDouble(start);
 			thisar = Double.parseDouble(arrivaltime);
 			thisst = Double.parseDouble(starttime);
 		}
 		rm = CreateJourney.checkremain.get(ID);
 	 	 /* pay for All Day zone 1 journey more than one times 
 	 	  * during the different period
 	 	  */
 		if (zone1.equals("11") & durationtime > 200 || zone1.equals("22")& durationtime > 200){	

 			lastcost = CreateJourney.checkprice.get(ID);
 			if(lastcost > allDayZone1) {
 	 			if (User.memberslist.get(ID).gettype().equals("Senior") & day.equals("sun")) {
 	 				price = lastcost;
 	 				CreateJourney.remain = rm- 0;
 	 				//CreateJourney.textbox.setText("You don't need to pay on Sunday!");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "1";
 	 				
 	 			}else if (User.memberslist.get(ID).gettype().equals("Junior") || User.memberslist.get(ID).gettype().equals("Senior") ) {
 	 				price = lastcost-(allDayZone1 * discountrate);
 	 				CreateJourney.remain = rm - price;
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+df.format(price)+" "+"with discount");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "2";
 	 			}else {
 	 				price = lastcost - allDayZone1;
 	 				CreateJourney.remain = rm - price; // calculate the remain
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+price);
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "3";
 	 			}
 			}else if (lastcost < allDayZone1 ) {//update travel to all day and pay for difference between price
 	 			if (User.memberslist.get(ID).gettype().equals("Senior") & day.equals("sun")) {
 	 				price = lastcost;
 	 				CreateJourney.remain = rm -0;
 	 				//System.out.println("You don't need to pay on Sunday!");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "1";
 	 				
 	 			}else if (User.memberslist.get(ID).gettype().equals("Junior") || User.memberslist.get(ID).gettype().equals("Senior") ) {
 	 				
 	 				price = (allDayZone1 * discountrate) - lastcost;
 	 				CreateJourney.remain = rm - price;
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+df.format(price)+" with discount");
 	 			    //System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "2";
 	 				
 	 			}else {
 	 				price = allDayZone1 - lastcost;
 	 				CreateJourney.remain = rm - price; // calculate the remain
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+price);
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "3";
 	 			}

 			}else if (lastcost == allDayZone1) {// keep the same price and remian with all day travel pass
 				price = lastcost;
 				CreateJourney.remain =rm - 0;
 				//System.out.println("The All Day travel pass is still valid! You don't need to pay");	
 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 				returncontent = "4";
 			}
 			
        
 	     /* pay for two hour zone 1 journey more than one times 
 	 	  * during the different period
 	 	  */ 	
 		}else if (zone1.equals("11") & durationtime <=200 || zone1.equals("22") & durationtime <=200) {

 			lastcost = CreateJourney.checkprice.get(ID);
 			if ((lastcost+twoHourZone1)>allDayZone1 & thisar<=(lastst +200.0) || (lastcost+twoHourZone1)<allDayZone1 & thisar<=(lastst +200.0)) {
		 			price = lastcost;
		 			CreateJourney.remain= rm - 0;
		 			//System.out.println("Two hour travel pass still valid! You don't need to pay");
		 			//System.out.println("Your remain credit is "+CreateJourney.remain);
		 			returncontent ="5";
		 			
 			}else if((lastcost+twoHourZone1)>allDayZone1 & thisar>(lastst +200)) { //update to all day travel and pay for difference
 	 			if (User.memberslist.get(ID).gettype().equals("Senior") & day.equals("sun")) {
 	 				price = lastcost;
 	 				CreateJourney.remain= rm - 0;
 	 				//System.out.println("You don't need to pay on Sunday!");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "1";
 	 				
 	 			}else if (User.memberslist.get(ID).gettype().equals("Junior") || User.memberslist.get(ID).gettype().equals("Senior") ) {
 	 				price = (lastcost+twoHourZone1)-(allDayZone1 * discountrate);
 	 				
 	 				CreateJourney.remain = rm - price;
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+df.format(price)+" with discount.");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "2";
 	 				
 	 			}else {
 	 				price = (lastcost+twoHourZone1)-allDayZone1;
 	 				CreateJourney.remain = rm- price; // calculate the remain
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+df.format(price));
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "3";
 	 				
 	 			}
 			
 			}else if ((lastcost+twoHourZone1)<allDayZone1) { // pay for 2 twohour travel
 	 			if (User.memberslist.get(ID).gettype().equals("Senior") & day.equals("sun")) {
 	 				price = lastcost;
 	 				CreateJourney.remain = rm - 0;
 	 				//System.out.println("You don't need to pay on Sunday!");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "1";
 	 				
 	 			}else if (User.memberslist.get(ID).gettype().equals("Junior") || User.memberslist.get(ID).gettype().equals("Senior") ) {
 	 				price = twoHourZone1 * discountrate;
 	 				CreateJourney.remain= rm - price;
 	 				validtime = thisst + 200;
 					//System.out.println(durationtime +zone1 +"Travel Pass purchased for"+" "+ID+" "+"for"+" "+"$"+df.format(price)+" with discount.");
 					//System.out.println("Valid until"+validtime);
 					//System.out.println("Credit remianing for"+" "+ID+" "+":"+"$"+CreateJourney.remain);
 	 				returncontent = "2";
 	 				
 	 			}else {
 	 				price = twoHourZone1;
 	 				CreateJourney.remain = rm - twoHourZone1; // calculate the remain
 	 				validtime = thisst + 200;
 					//System.out.println(durationtime +zone1 +"Travel Pass purchased for "+ID+" for $"+df.format(price));
 					//System.out.println("Valid until"+validtime);
 					//System.out.println("Credit remianing for "+ID+" :$"+CreateJourney.remain);
 	 				returncontent = "3";
 	 			}

 			}else if(lastcost == allDayZone1) {// keep the same price and remian with all day travel pass
 	 				price = lastcost;
 	 				CreateJourney.remain = rm - 0;
 	 			//	System.out.println("The All Day travel pass is still valid! You don't need to pay");	
 	 			   // System.out.println("Your remain credit is "+CreateJourney.remain);
 	 			    returncontent = "4";
 			}
 			
 	        
 	 	 /* pay for all day zone 1 and 2 journey more than one times 
 	 	  * during the different period
 	 	  */ 			
 		}else if (zone1.equals("12") & durationtime > 200 ){

 			lastcost = CreateJourney.checkprice.get(ID);
 			if (lastcost < allDayZone12 ) {//update travel to all day and pay for difference between price
 	 			if (User.memberslist.get(ID).gettype().equals("Senior") & day.equals("sun")) {
 	 				price = lastcost;
 	 				CreateJourney.remain = rm- 0;
 	 				//System.out.println("You don't need to pay on Sunday!");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "1";
 	 				
 	 			}else if (User.memberslist.get(ID).gettype().equals("Junior") || User.memberslist.get(ID).gettype().equals("Senior") ) {
 	 				price = (allDayZone12 * discountrate) - lastcost;
 	 				CreateJourney.remain = rm - price;
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+df.format(price)+" with discount.");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "2";
 	 				
 	 			}else {
 	 				price = allDayZone12 - lastcost;
 	 				CreateJourney.remain = rm- price; // calculate the remain
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+df.format(price));
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "3";
 	 				
 	 			}
 			}else if(lastcost == allDayZone12) {// keep the same price and remian with all day travel pass
 	 				price = lastcost;
 	 				CreateJourney.remain = rm - 0;
 	 				//System.out.println("The All Day travel pass is still valid! You don't need to pay");	
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "4";
 	 			}
 			
 			
 	 	/* pay for two hour zone 1 and 2 journey more than one times 
 	 	 * during the different period
 	 	 */ 					
 		}else if (zone1.equals("12") & durationtime <=200) {

 			lastcost = CreateJourney.checkprice.get(ID);
 			price =  lastcost + twoHourZone12;
 			if ((lastcost+twoHourZone12)>allDayZone12 & thisar<=(lastst +200.0) || (lastcost+twoHourZone12)<allDayZone12 & thisar<=(lastst +200.0)) {
 	 		 	price = lastcost;
 	 		 	CreateJourney.remain = rm - 0;
 	 		 	//System.out.println("The two hour travel pass still valid! You don't need to pay");
 	 		   // System.out.println("Your remain credit is "+CreateJourney.remain);
 	 		 	    returncontent ="5";
 				
 			}else if ((lastcost+twoHourZone12) > allDayZone12 & thisar > (lastst+200.0)) { //update to all day travel and pay for difference
 	 			if (User.memberslist.get(ID).gettype().equals("Senior") & day.equals("sun")) {
 	 				price = lastcost;
 	 				CreateJourney.remain = rm - 0;
 	 				//System.out.println("You don't need to pay on Sunday!");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "1";
 	 				
 	 			}else if (User.memberslist.get(ID).gettype().equals("Junior") || User.memberslist.get(ID).gettype().equals("Senior") ) {
 	 				price = (lastcost+twoHourZone12)-(allDayZone12 * discountrate);
 	 				CreateJourney.remain = rm - price;
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+df.format(price)+" with discount");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "2";
 	 				
 	 			}else {
 	 				price = (lastcost+twoHourZone12)-allDayZone1;
 	 				CreateJourney.remain = rm - price; // calculate the remain
 	 				//System.out.println("The travel pass has already update to All Day Pass! The cost is: $"+df.format(price));
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "3";
 	 			} 			
 
 			}else if ((lastcost+twoHourZone12)<allDayZone12) {
 	 			if (User.memberslist.get(ID).gettype().equals("Senior") & day.equals("sun")) {
 	 				price = lastcost;
 	 				CreateJourney.remain = rm - 0;
 	 				//System.out.println("You don't need to pay on Sunday!");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 	 				returncontent = "1";
 	 				
 	 			}else if (User.memberslist.get(ID).gettype().equals("Junior") || User.memberslist.get(ID).gettype().equals("Senior") ) {
 	 				price = twoHourZone12 * discountrate;
 	 				CreateJourney.remain = rm - price;
 	 				validtime = thisst + 200;
 					//System.out.println(durationtime +zone1 +"Travel Pass purchased for"+" "+ID+" "+"for"+" "+"$"+df.format(price));
 					//System.out.println("Valid until"+validtime);
 					//System.out.println("Credit remianing for"+" "+ID+" "+":"+"$"+CreateJourney.remain);
 	 				returncontent = "2";
 	 				
 	 			}else {
 	 				price = twoHourZone12;
 	 				CreateJourney.remain = rm - twoHourZone12; // calculate the remain
 	 				Double validtime = thisst + 200;
 					//System.out.println(durationtime +zone1 +"Travel Pass purchased for"+" "+ID+" "+"for"+" "+"$"+df.format(price));
 					//System.out.println("Valid until"+validtime);
 					//System.out.println("Credit remianing for"+" "+ID+" "+":"+"$"+CreateJourney.remain);
 	 				returncontent = "3";
 	 			}


 			}else if (lastcost == allDayZone12) {// keep the same price and remian with all day travel pass
 	 				price = lastcost;
 	 				CreateJourney.remain =rm - 0;
 	 				//System.out.println("The All Day travel pass is still valid! You don't need to pay");
 	 				//System.out.println("Your remain credit is "+CreateJourney.remain);
 			}
 		}
		return returncontent;
 	}	
 	
 	

 	/* method to calculate times for each station
 	 */
 	public TravelPass(String startstation, String endstation, String ID) {
 		int endtimes=0;
 		int sttimes=0;
 		for(String item: MyTiSystem.allJourneyInfo.keySet()) {
 			
 			if(!MyTiSystem.allJourneyInfo.containsKey(ID)|| !MyTiSystem.allJourneyInfo.get(ID).getstartstation().contains(startstation) & !MyTiSystem.allJourneyInfo.get(ID).getendstation().contains(endstation)){
 	 			
 	 		    MyTiSystem.caltimes.put("Start station: "+startstation, MyTiSystem.lastcal.get(startstation));
 	 		    MyTiSystem.caltimes.put("End station: "+endstation, MyTiSystem.lastcal.get(endstation));
 	 			break;
 	 			
 	 		}else if(MyTiSystem.allJourneyInfo.containsKey(ID) & MyTiSystem.allJourneyInfo.get(ID).getstartstation().contains(startstation)) {
 	 			
 	 			sttimes = MyTiSystem.caltimes.get("Start station: "+MyTiSystem.allJourneyInfo.get(ID).getstartstation());
 	 			MyTiSystem.caltimes.put("Start station: "+startstation, sttimes+1);
 	 			
 	 			if(MyTiSystem.allJourneyInfo.get(ID).getendstation().contains(endstation)) {
 	 				
 	 	 			endtimes = MyTiSystem.caltimes.get("End station: "+MyTiSystem.allJourneyInfo.get(ID).getendstation());
 	 	 			MyTiSystem.caltimes.put("End station: "+endstation, endtimes+1);
 	 	 			break;
 	 			}else {
 	 				
 	 				MyTiSystem.caltimes.put("End station: "+endstation, MyTiSystem.lastcal.get(endstation));
 	 				break;
 	 			}
 	 			
 	 			
 	 		}else if(MyTiSystem.allJourneyInfo.containsKey(ID) & MyTiSystem.allJourneyInfo.get(ID).getendstation().contains(endstation)) {
 	 			
 	 			endtimes = MyTiSystem.caltimes.get("End station: "+MyTiSystem.allJourneyInfo.get(ID).getendstation());
 	 			MyTiSystem.caltimes.put("End station: "+endstation, endtimes+1);
 	 			
 	 			if(MyTiSystem.allJourneyInfo.get(ID).getstartstation().contains(startstation)) {
 	 				
 	 				sttimes = MyTiSystem.caltimes.get("Start station: "+MyTiSystem.allJourneyInfo.get(ID).getstartstation());
 	 	 			MyTiSystem.caltimes.put("Start station: "+startstation, sttimes+1);
 	 	 			break;
 	 			}else {
 	 				
 	 				MyTiSystem.caltimes.put("Start station: "+startstation,MyTiSystem.lastcal.get(startstation));
 	 				break;
 	 			}
 	 		}			
 		}
 	}
 	
 	/*public double discount() {
 		final double discountrate = 0.50;
		return discountrate;
 	}*/




 	}


