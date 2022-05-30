package myti;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TravelPassTest {
    static Map<String, User> memberslist = new HashMap<>();
    static Map<String, Account> allJourneyInfo = new HashMap<>();
    static Map<String, Double> checkremain = new HashMap<>();
    static Map<String, Double> checkprice = new HashMap<>();
    
	@Before
	public void setUp() {
		User testuser = new User("zz","adult","zixin","123456@qq.com","30");
		MyTiSystem.memberslist.put(testuser.getid(), testuser);
		MyTiSystem.remain= Double.parseDouble(testuser.getcredit());

		Account testac = new Account("zz", "Richmond", "Central", "mon", "1100","1200","27.5","2.5");
		MyTiSystem.allJourneyInfo.put(testac.getid(), testac);
		Double remain= Double.parseDouble(testac.getremain());
		Double cost= Double.parseDouble(testac.getprice());
		MyTiSystem.checkremain.put("zz", remain);
		MyTiSystem.checkprice.put("zz", cost);
		
		
		
	}
	@Test
	public void testPriceCost() throws RemainNotenoughException{
		TravelPass test1 = new TravelPass("11",200,"zz","mon");
		double expectedcost = 2.50;
		double actualcost = TravelPass.getprice();
		double expectedremain = 27.5;
		double actualremain = MyTiSystem.remain;
		
		assertEquals(expectedcost, actualcost,0.01);
		assertEquals(expectedremain, actualremain,0.1);
	}
	
	@Test
	public void testCostwithoutChange() throws RemainNotenoughException{
		//TravelPass test2 = new TravelPass(200,"22", "1200", "1300", "zz","mon");
		TravelPass.travelPass(200.0,"22", "1200", "1300", "zz","mon");
		double expectedcost = 2.50;
		double actualcost = TravelPass.getprice();
		double expectedremain = 27.5;
		double actualremain = MyTiSystem.remain;
		
		assertEquals(expectedcost, actualcost,0.01);
		assertEquals(expectedremain, actualremain,0.1);
		System.out.println("The two Hour travel pass cost: "+actualcost+",and remain: "+actualremain);
	}
	
	@Test
	public void testTravelUpdate() throws RemainNotenoughException{
		//TravelPass test3 = new TravelPass(600,"22", "1200", "1800", "zz","mon");
		TravelPass.travelPass(600.0,"22", "1200", "1800", "zz","mon");
		double expectedcost = 2.40;
		double actualcost = TravelPass.getprice();
		double expectedremain = 25.1;
		double actualremain = MyTiSystem.remain;
		
		assertEquals(expectedcost, actualcost,0.01);
		assertEquals(expectedremain, actualremain,0.1);
	}
	
	@Test
	public void testdiscount() throws RemainNotenoughException{
		User testuser1 = new User("mm","senior","zixin","123456@qq.com","30");
		MyTiSystem.memberslist.put(testuser1.getid(), testuser1);
		MyTiSystem.remain= Double.parseDouble(testuser1.getcredit());
		
		Account testac1 = new Account("mm", "Richmond", "Central", "mon", "1100","1200","28.75","1.25");
		MyTiSystem.allJourneyInfo.put(testac1.getid(), testac1);
		Double remain1= Double.parseDouble(testac1.getremain());
		Double cost1= Double.parseDouble(testac1.getprice());
		MyTiSystem.checkremain.put("mm", remain1);
		MyTiSystem.checkprice.put("mm", cost1);
		//TravelPass test4 = new TravelPass(600,"22", "1200", "1800", "mm","mon");
		TravelPass.travelPass(600.0,"22", "1200", "1800", "mm","mon");
		double expectedcost = 1.20;
		double actualcost = TravelPass.getprice();
		double expectedremain = 27.55;
		double actualremain = MyTiSystem.remain;
		
		assertEquals(expectedcost, actualcost,0.01);
		assertEquals(expectedremain, actualremain,0.01);
	}

}
