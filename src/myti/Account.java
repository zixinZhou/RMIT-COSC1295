package myti;

public class Account {
	private String id, startstation1, endstation1, day, starttime1, arrivaltime1;
	private static String price1;
	String remain1;


    public Account (String id, String startstation, String endstation, String Day, String starttime, String arrivaltime, String remain, String price){     
    	this.id=id;
    	this.startstation1 = startstation;
    	this.endstation1 = endstation;
    	this.day = Day;
    	this.starttime1 = starttime;
    	this.arrivaltime1 = arrivaltime;
    	this.remain1 = remain;
    	Account.price1 = price;
    }


	public String getprice() {
    	return price1;
    }
	public void setprice(String price) {
		Account.price1=price;
	}
    public String getid() {
    	return id;
    }
    
    public String getstartstation() {
    	return startstation1;
    }
    
    public String getendstation() {
    	return endstation1;
    }
    public String getday() {
    	return day;
    }
    public String getstarttime() {
    	return starttime1;
    }
    public String getarrivaltime() {
    	return arrivaltime1;
    }
    public String getremain() {
    	return remain1;
    }

    
 
}