package myti;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readfile {
	private String id, type, name, email, credit;
	
    /* create constructor
    */ 
	public readfile(String id, String type, String name, String email, String credit){
		this.id = id;
		this.type = type;
		this.name = name;
		this.email = email;
		this.credit = credit;
	}
	public static void readmemberfile() throws FileNotFoundException {
		File file = new File("member.txt");
		Scanner scanner = new Scanner(file);
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] components = line.split(",");
		    User user = new User(components[0], components[1], components[2], components[3], components[4]);
		    MyTiSystem.memberslist.put(user.getid(), user);
		}    
	    }
}
