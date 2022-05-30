package myti;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class User {
	private String id, type, name, email, credit;
	public static Map<String, User> memberslist = new HashMap<>();
	public static ArrayList<String> idlist = new ArrayList<>();
    /* create constructor
    */ 
	public User(String id, String type, String name, String email, String credit){
		this.id = id;
		this.type = type;
		this.name = name;
		this.email = email;
		this.credit = credit;
	}

	/* method to get and set id
    */
	public  String getid() {
		return id;
	}	
	public void setid(String id) {
		this.id = id;
	}

	/* method to get and set type
	 */
	public String gettype() {
		return type;
	}
	public void settype(String type) {
		this.type = type;
	}

	/* method to get and set name
	 */
	public String getname() {
		return name;
	}		
	public void setname(String name) {
		this.name = name;
	}
	
	/* method to get and set email
	 */
	public String getemail() {
		return email;
	}	
	public void setemail(String email) {
		this.email = email;
	}
 
    /* method to get and set credit
    */
	public String getcredit() {
		return credit;
	}
	public void setcredit(String credit) {
		this.credit = credit;
	}

	
	/* read member information from file 
	 * and put into hashmap
	 */
	public static void readmemberfile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] components = line.split(":");
		    User user = new User(components[0], components[1], components[2], components[3], components[4]);
		    memberslist.put(user.getid(), user);
		    idlist.add(components[0]);
		}    
	    }
	
	/* save customer information into file
	 */

	public static void savememberfile(String filename, String content) {
		try {
			RandomAccessFile randomFile = new RandomAccessFile(filename, "rw");
			long fileLength = randomFile.length();
			randomFile.seek(fileLength);
			randomFile.writeBytes("\n"+content);
			randomFile.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*rewrite member txt file with hashmap
	 * for update credit
	 */
	public static void rewriteMemberFile() {
		try {
			FileWriter fw = new FileWriter("src\\fileio\\member.txt",false);
			for(String item : memberslist.keySet()){
		    	String Content = memberslist.get(item).getid()+":"+memberslist.get(item).gettype()+":"+memberslist.get(item).getname()+":"+memberslist.get(item).getemail()+":"+memberslist.get(item).getcredit();
		    	fw.write(Content+"\r\n");
		    	
			}
			fw.close();
			
			try {
				FileChannel open = FileChannel.open(Paths.get("member.txt"), StandardOpenOption.WRITE);
				open.truncate(open.size()-2);
			}catch(IOException e) {
				System.out.println("An IO error occurred.");
				e.printStackTrace();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
