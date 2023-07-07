import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class FileHandler {

	String path="D:\\Gmail Management System";
	void writeUserDetails(String firstName,String lastName,String dateOfBirth,String gender,String userName,String password) {
		try {
			FileWriter file=new FileWriter("D:\\Gmail Management System\\Users.csv",true);
			BufferedWriter writer=new BufferedWriter(file);
			writer.write(firstName+","+lastName+","+dateOfBirth+","+gender+","+userName+","+password);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File dir=new File("D:\\Gmail Management System\\Users\\"+userName);
		dir.mkdir();
	}
	static String[] readUserDetails(String userName) {
		String[] userDetails=null;
		try {
			FileReader file=new FileReader("D:\\Gmail Management System\\Users.csv");
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				while(line!=null) {
					String[] user=line.split(",");
					if(user[4].equals(userName)) {
						userDetails=user;
						break;
					}
					line=reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return userDetails;		
	}
	static boolean isUsernameAvailable(String userName) {
		boolean flag=true;
		File file=new File("D:\\Gmail Management System\\Users");
		String[] files=file.list();
		for(String f : files) {
			if(f.equals(userName)) {
				flag=false;
				break;
			}
		}
		return flag;
	}
	void writeMail(Mail mail,String path) {
		ArrayList<String> userNotFound=new ArrayList<>();
		try {
			FileWriter file=new FileWriter(path,true);
			BufferedWriter writer=new BufferedWriter(file);
			writer.newLine();
			writer.write(mail.getID()+",");
			for(String user:mail.getMailUsers()) {
				writer.write(user+"-");
			}
			writer.write(","+mail.getIsDraftMail()+",");
			for(String user:mail.getStarred()) {
				writer.write(user+"-");
			}
			writer.write(",");
			for(String user:mail.getTrash()) {
				writer.write(user+"-");
			}
			writer.write(","+mail.getSender()+",");
			for(String user:mail.getReceiver()) {
				writer.write(user+"-");
				if(isUsernameAvailable(user))
					userNotFound.add(user);
			}
			writer.write(",");
			for(String user:mail.getCc()) {
				writer.write(user+"-");
				if(isUsernameAvailable(user))
					userNotFound.add(user);
			}
			writer.write(",");
			for(String user:mail.getBcc()) {
				writer.write(user+"-");
				if(isUsernameAvailable(user))
					userNotFound.add(user);
			}
			writer.write(","+mail.getSubject()+","+mail.getMail()+","+mail.getTime());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(userNotFound);
		if(!userNotFound.isEmpty()) {
			System.out.println("1");
			if (path.equals("D:\\Gmail Management System\\mail.csv")) {
				System.out.println("called");
				String tempMail = mail.getID() + 1 + "," + mail.getSender() + "," + mail.getIsDraftMail() + ",,,mailer@googlemail.com," + mail.getSender() + ",,," + mail.getSubject() + ",Your message wasn't delivered to ";
				for (String user : userNotFound) {
					tempMail += user + " ";
				}
				tempMail += "because the address couldn't be found or is unable to receive mail." + mail.getMail() +"," +mail.getTime();
				writeMail(tempMail,path);
			}
		}

	}
	void writeMail(String mail,String path){
		try {
			FileWriter file = new FileWriter(path, true);
			BufferedWriter writer = new BufferedWriter(file);
			writer.newLine();
			writer.write(mail);
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
