import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Mail {
	private String sender,subject,mail,time,ID;
	private ArrayList<String> receiver,cc,bcc,starred,trash,mailUsers;
	private boolean isDraftMail;
	SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	SimpleDateFormat formatters=new SimpleDateFormat("yyyyMMddHHmmss");
	Date date=new Date();
	Mail(String sender,ArrayList<String> receiver,ArrayList<String> cc,ArrayList<String> bcc,String subject, String mail, boolean isDraftMail,ArrayList<String> mailUsers){
		this.ID=sender+formatters.format(date);
		this.sender=sender;
		this.receiver=receiver;
		this.cc=cc;
		this.bcc=bcc;
		this.subject=subject;
		this.mail=mail;
		this.mailUsers=mailUsers;
		this.time=formatter.format(date);
		this.isDraftMail=isDraftMail;
		this.starred=new ArrayList<String>();
		this.trash=new ArrayList<String>();
	}
	Mail(String ID,ArrayList<String> mailUsers,boolean isDraftMail,ArrayList<String> starred,ArrayList<String> trash,String sender,ArrayList<String> receiver,ArrayList<String> cc,ArrayList<String> bcc,String subject,String mail,String time){
		this.ID=ID;
		this.sender=sender;
		this.receiver=receiver;
		this.cc=cc;
		this.bcc=bcc;
		this.subject=subject;
		this.mail=mail;
		this.mailUsers=mailUsers;
		this.time=time;
		this.isDraftMail=isDraftMail;
		this.starred=starred;
		this.trash=trash;
	}
	String getID() {
		return ID;
	}
	String getSender() {
		return sender;
	}
	void setSender(String user) {
		sender=user;
	}
	void setTime(String time) {
		this.time=time;
	}
	void setID(String ID) {
		this.ID=ID;
	}
	ArrayList<String> getReceiver() {
		return receiver;
	}
	ArrayList<String> getCc() {
		return cc;
	}
	ArrayList<String> getBcc() {
		return bcc;
	}
	String getSubject() {
		return subject;
	}
	String getMail() {
		return mail;
	}
	String getTime() {
		return time;
	}
	boolean getIsDraftMail() {
		return isDraftMail;
	}
	void setIsDraftMail(boolean isDraftMail) {
		this.isDraftMail=isDraftMail;
	}
	ArrayList<String> getStarred(){
		return starred;
	}
	ArrayList<String> getTrash(){
		return trash;
	}
	ArrayList<String> getMailUsers(){
		return mailUsers;
	}
	void displaySentMail() {
		if(receiver.size()==1)
			System.out.println(receiver.get(0) + "\t:\t" + subject + "\t\t\t" + time);
		else
			System.out.println(receiver.get(0) + "...\t:\t" + subject + "\t\t\t" + time);
	}
	void displayInboxMail() {
		System.out.println(sender + "\t:\t" + subject + "\t\t\t" + time);
	}
	void readMail(String user) {
		System.out.print("_________________________________________________________");
		System.out.print("\n|\tFrom\t:\t" + sender);
		System.out.print("\n|\tTo\t\t:\t");
		for(String receiver:receiver) {
			System.out.print("\n|\t\t\t\t" + receiver);
//			System.out.println();
		}
		System.out.print("\n|\tCC\t\t:\t");
		for(String cc:cc) {
			System.out.print("\n|\t\t\t\t" + cc);
//			System.out.println();
		}
		System.out.print("\n|\tBCC\t\t:\t");
		for(String bcc:bcc) {
			if(sender.equals(user)){
				System.out.print("\n|\t\t\t\t" + bcc);
//				System.out.println();
			}
			if(bcc.equals(user)&&!bcc.equals(sender)) {
				System.out.print("\n|\t\t\t\t" + bcc);
//				System.out.println();
			}
		}	
		System.out.print("\n|\tTime\t:\t" + time );
		System.out.print("\n|\tSubject\t:\t" + subject);
		System.out.print("\n|\tMail\t:\t" + mail);
		System.out.print("\n|_________________________________________________________");
	}
}
