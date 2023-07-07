import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Folders {
	public static final byte SENT_MAIL = 1;
	public static final byte SAVE_MAIL = 2;
	public static final byte READ_MAIL = 1;
	public static final byte ADD_TO_STAR = 2;
	public static final byte DELETE_MAIL = 3;
	public static final byte BACK = 4;
	public static final byte SEND_DRAFT_MAIL = 2;
	public static final byte UNSTAR = 2;
	public static final byte MOVE_TO_INBOX = 2;

	void displayInbox(String user) {

		boolean goBack=false;
		do {
			ArrayList<Mail> inbox=MailBox.getInstance().getInbox(user);
			if(inbox.isEmpty()) {
				System.out.println("\u001B[33m No mails available in inbox.. \u001B[0m");
				goBack=true;
			}
			else {
				int count=1;
				for (Mail mail : inbox) {
					System.out.println("     --------------------------------------------------------------------------------");
					System.out.print("[" + count++ + "]  ");
					mail.displayInboxMail();
					System.out.println("     --------------------------------------------------------------------------------");
				}
				System.out.println("\n 1. Read Mail \n Press 0 for Go Back.. ");
				System.out.println("Enter your option : ");
				byte option = Utility.getInstance().readByteInput();
				if(option==1) {
					System.out.println("Enter Mail Number : ");
					int index=Utility.getInstance().readIntegerInput();
					if(index>0&&index<=inbox.size()) {
						Mail mail=inbox.get(index-1);
						mail.readMail(user);
						System.out.println("\n 1. Starred the Mail \n 2. Delete the Mail \n 3. Move To Label \n Press 0 for go back..");
						byte choice=Utility.getInstance().readByteInput();
						if(choice==1) 
							MailBox.getInstance().starMail(user, mail);
						else if(choice==2)
							MailBox.getInstance().addToTrash(user, mail);
						else if(choice==3)
							MailBox.getInstance().moveToFolder(user, mail);
						else if(choice==0);
						else
							System.out.println("\u001B[31m Your choice is invalid.. \u001B[0m");

					}
					else {
						System.out.println("\u001B[31m Enter valid Choice... \u001B[0m");
					}
				}
				else if(option==0) {
					goBack=true;
				}
				else System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!goBack);
	}
	void displaySent(String user) {

		boolean goBack=false;
		do {
			ArrayList<Mail> sent=MailBox.getInstance().getSentBox(user);
			if(sent.isEmpty()) {
				System.out.println("\u001B[33m No mails available in sent items.. \u001B[0m");
				goBack=true;
			}
			else {
				int count=1;
				for (Mail mail : sent) {
					System.out.println("     --------------------------------------------------------------------------------");
					System.out.print("[" + count++ + "]  ");
					mail.displaySentMail();
					System.out.println("     --------------------------------------------------------------------------------");
				}
				System.out.println("\n 1. Read Mail \n Press 0 for Go Back.. ");
				System.out.println("Enter your option : ");
				byte option = Utility.getInstance().readByteInput();
				if(option==1) {
					System.out.println("Enter Mail Number : ");
					int index=Utility.getInstance().readIntegerInput();
					if(index>0&&index<=sent.size()) {
						Mail mail=sent.get(index-1);
						mail.readMail(user);
						System.out.println("\n 1. Starred the Mail \n 2. Delete the Mail \n 3. Move To Label \n Press 0 for back..");
						byte choice=Utility.getInstance().readByteInput();
						if(choice==1) 
							MailBox.getInstance().starMail(user, mail);
						else if(choice==2)
							MailBox.getInstance().addToTrash(user, mail);
						else if(choice==3)
							MailBox.getInstance().moveToFolder(user, mail);
						else if(choice==0);
						else
							System.out.println("\u001B[31m Your choice is invalid... \u001B[0m");
					}
					else {
						System.out.println("\u001B[31m Enter valid Choice... \u001B[0m");
					}
				}
				else if(option==0) {
					goBack=true;
				}
				else System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!goBack);
	}
	void displayDraft(String user) {

		boolean goBack=false;
		do {
			ArrayList<Mail> draft=MailBox.getInstance().getDraft(user);
			if(draft.isEmpty()) {
				System.out.println("\u001B[33m No mails available in draft.. \u001B[0m");
				goBack=true;
			}
			else {
				int count=1;
				for (Mail mail : draft) {
					System.out.println("     --------------------------------------------------------------------------------");
					System.out.print("[" + count++ + "]  ");
					mail.displaySentMail();
					System.out.println("     --------------------------------------------------------------------------------");
				}
				System.out.println("\n 1. Read Mail \n Press 0 for Go Back.. ");
				System.out.println("Enter your option : ");
				byte option = Utility.getInstance().readByteInput();
				if(option==1) {
					System.out.println("Enter Mail Number : ");
					int index=Utility.getInstance().readIntegerInput();
					if(index>0&&index<=draft.size()) {
						Mail mail=draft.get(index-1);
						mail.readMail(user);
						System.out.println("\n 1. Send the Draft Mail \n 2. Delete the Mail \nPress 0 for back..");
						byte choice=Utility.getInstance().readByteInput();
						if(choice==1) {
							MailBox.getInstance().sendDraftMail(user, mail);
						}
						else if(choice==2)
							MailBox.getInstance().deleteDraftMail(user, mail);
						else if(choice==0);
						else
							System.out.println("\u001B[31m Your choice is invalid... \u001B[0m");
					}
					else {
						System.out.println("\u001B[31m Enter valid Choice... \u001B[0m");
					}
				}
				else if(option==0) {
					goBack=true;
				}
				else System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!goBack);
	}
	void displayStarred(String user) {

		boolean goBack=false;
		do {
			ArrayList<Mail> starred=MailBox.getInstance().getStarred(user);
			if(starred.isEmpty()) {
				System.out.println("\u001B[33m No mails available in starred list.. \u001B[0m");
				goBack=true;
			}
			else {
				int count=1;
				for (Mail mail : starred) {
					System.out.println("     --------------------------------------------------------------------------------");
					System.out.print("[" + count++ + "]  ");
					if(mail.getSender().equals(user))
						mail.displaySentMail();
					else
						mail.displayInboxMail();
					System.out.println("     --------------------------------------------------------------------------------");
				}
				System.out.println("\n 1. Read Mail \n Press 0 for Go Back.. ");
				System.out.println("Enter your option : ");
				byte option = Utility.getInstance().readByteInput();
				if(option==1) {
					System.out.println("Enter Mail Number : ");
					int index=Utility.getInstance().readIntegerInput();
					if(index>0&&index<=starred.size()) {
						Mail mail=starred.get(index-1);
						mail.readMail(user);
						System.out.println("\n 1. Unstar the Mail \n Press 0 for back..");
						byte choice=Utility.getInstance().readByteInput();
						if(choice==1) 
							MailBox.getInstance().unStarMail(user, mail);	
						else if(choice==0);
						else
							System.out.println("\u001B[31m Your choice is invalid... \u001B[0m");
					}
					else {
						System.out.println("\u001B[31m Enter valid Choice... \u001B[0m");
					}
				}
				else if(option==0) {
					goBack=true;
				}
				else System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!goBack);
	}
	void displayTrash(String user) {

		boolean goBack=false;
		do {
			ArrayList<Mail> trash=MailBox.getInstance().getTrash(user);
			if(trash.isEmpty()) {
				System.out.println("\u001B[33m No mails available in trash.. \u001B[0m");
				goBack=true;
			}
			else {
				int count=1;
				for (Mail mail : trash) {
					System.out.println("     --------------------------------------------------------------------------------");
					System.out.print("[" + count++ + "]  ");
					if(mail.getSender().equals(user))
						mail.displaySentMail();
					else
						mail.displayInboxMail();
					System.out.println("     --------------------------------------------------------------------------------");
				}
				System.out.println("\n 1. Read Mail \n Press 0 for Go Back.. ");
				System.out.println("Enter your option : ");
				byte option = Utility.getInstance().readByteInput();
				if(option==1) {
					System.out.println("Enter Mail Number : ");
					int index=Utility.getInstance().readIntegerInput();
					if(index>0&&index<=trash.size()) {
						Mail mail=trash.get(index-1);
						mail.readMail(user);
						System.out.println("\n 1. Restore Mail \n 2. Delete Mail Permanently \n Press 0 for back..");
						byte choice=Utility.getInstance().readByteInput();
						if(choice==1) 
							MailBox.getInstance().restoreMail(user, mail);
						else if(choice==2)
							MailBox.getInstance().removeFromTrash(user, mail);
						else if(choice==0);
						else System.out.println("\u001B[31m Your choice is invalid... \u001B[0m");
					}
					else {
						System.out.println("\u001B[31m Enter valid Choice... \u001B[0m");
					}
				}
				else if(option==0) {
					goBack=true;
				}
				else System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!goBack);
	}
	void createFolder(String userName) {
		System.out.println("Enter Folder Name : ");
		String folder=Utility.getInstance().readStringInput();
		File file=new File("D:\\Gmail Management System\\Users\\"+userName+"\\"+folder);
		if(file.exists()) {
			System.out.println("\u001B[31m"+folder+" name is already exists... \u001B[0m");
		}
		else {
			file.mkdir();
			File path=new File("D:\\Gmail Management System\\Users\\"+userName+"\\"+folder+"\\mails.csv");
			try {
				path.createNewFile();
				FileWriter temp=new FileWriter(path);
				temp.write("ID,Mail Users,Is Draft Mail,Starred Users,Trash Users,Sender,Receivers,CC,BCC,Subject,Mail,Time");
				temp.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("\u001B[33m Folder created Successfully.. \u001B[0m");
		}
	}
	void viewFolders(String userName) {
		String path="D:\\Gmail Management System\\Users\\"+userName;
		File file=new File(path);
		ArrayList<String> folders=new ArrayList<String>(Arrays.asList(file.list()));
		if(folders.size()>0) {
			boolean exit=false;
			do {
				int count=1;
				for(String folder:folders) {
					System.out.println(" "+count++ +"\t----->\t"+folder);
				}
				System.out.println(" Press 0 for Go Back..\n");
				System.out.print("Enter your choice : ");
				int choice=Utility.getInstance().readIntegerInput();
				if(choice>0&&choice<=folders.size()) {
					String folderPath=path+"\\"+folders.get(choice-1);
					displayFolderMails(folderPath,userName);
				}
				else if(choice==0)
					exit=true;
				else
					System.out.println("\u001B[31m Your choice is invalid..!! \u001B[0m");
			}while(!exit);

		}
		else {
			System.out.println("\u001B[33m You are not create any Labels.. \u001B[0m");
		}
	}
	void displayFolderMails(String path,String userName) {
		path+="\\mails.csv";
		boolean goBack=false;
		do {
			ArrayList<Mail> mails=MailBox.getInstance().getFolderMails(path);
			if(mails.isEmpty()) {
				System.out.println("\u001B[33m No mails available in this folder.. \u001B[0m");
				goBack=true;
			}
			else {
				int count=1;
				for (Mail mail : mails) {
					System.out.println("     --------------------------------------------------------------------------------");
					System.out.print("[" + count++ + "]  ");
					if(mail.getSender().equals(userName))
						mail.displaySentMail();
					else
						mail.displayInboxMail();
					System.out.println("     --------------------------------------------------------------------------------");
				}
				System.out.println("\n 1. Read Mail \n Press 0 for Go Back.. ");
				System.out.println("Enter your option : ");
				byte option = Utility.getInstance().readByteInput();
				if(option==1) {
					System.out.println("Enter Mail Number : ");
					int index=Utility.getInstance().readIntegerInput();
					if(index>0&&index<=mails.size()) {
						Mail mail=mails.get(index-1);
						mail.readMail(userName);
						System.out.println("\n 1. Remove mail from folder \n 2. Delete Mail \n Press 0 for go back..");
						byte choice=Utility.getInstance().readByteInput();
						if(choice==1) {
							mail.getMailUsers().add(userName);
							MailBox.getInstance().updateMail(mail);
							MailBox.getInstance().updateMail(mail,path);
						}
						else if(choice==2) {
							mail.getTrash().add(userName);
							MailBox.getInstance().updateMail(mail);
							System.out.println(path);
							MailBox.getInstance().updateMail(mail,path);
						}	
						else if(choice==0);
						else
						System.out.println("\u001B[31m Your choice is invalid... \u001B[0m");
					}
					else {
						System.out.println("\u001B[31m Enter valid Choice... \u001B[0m");
					}
				}
				else if(option==0) {
					goBack=true;
				}
				else System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!goBack);
	}
	void deleteFolder(String userName) {
		File path = new File("D:\\Gmail Management System\\Users\\" + userName);
		ArrayList<String> files = new ArrayList<String>(Arrays.asList(path.list()));
		if (files.isEmpty()) {
			System.out.println("\u001B[33m Your Label List is Empty.. \u001B[0m");
		} else {
			for (String file : files) {
				System.out.println(" * " + file);
			}
			System.out.println("\nEnter Folder Name : ");
			String folder = Utility.getInstance().readStringInput();
			File file = new File(path + "\\" + folder);
			if (!file.exists()) {
				System.out.println("\u001B[31m"+folder + " name not exists...\u001B[0m");
			} else {
				File mailFile = new File(file + "\\mails.csv");
				mailFile.delete();
				File newFile = new File(path + "\\" + folder);
				newFile.delete();
				System.out.println("\u001B[33m Label Deleted Successfully.. \u001B[0m");
			}
		}
	}
	void moveToExistingLable(String userName,ArrayList<Mail> mails) {
		File path = new File("D:\\Gmail Management System\\Users\\" + userName);
		ArrayList<String> files = new ArrayList<String>(Arrays.asList(path.list()));
		if (files.isEmpty()) {
			System.out.println("\u001B[33m Your Label List is Empty.. \u001B[0m");
		} else {
			for (String file : files) {
				System.out.println(" * " + file);
			}
			System.out.print("\nEnter Folder Name : ");
			do {
				String folder = Utility.getInstance().readStringInput();
				File file = new File(path + "\\" + folder);
				if (!file.exists()) {
					System.out.println("\u001B[31m"+folder + " name not exists...\u001B[0m");
					System.out.print("\nEnter valid Folder Name : ");
				} else {
//					File mailFile = new File(file + "\\mails.csv");
//					String mailPath=file.toString();
					MailBox.getInstance().moveToFolder(userName,file.toString(),mails);
//					System.out.println("Label Deleted Successfully..");
					break;
				}
			}while (true);
		}
	}
	void searchMails(String user){
		System.out.print("Enter the word : ");
		String word=Utility.getInstance().readStringInput();
		boolean goBack=false;
		do {
			ArrayList<Mail> mails=MailBox.getInstance().getSearchMails(user,word);
			if(mails.isEmpty()) {
				System.out.println("\u001B[33m No mails available in \""+word+"\"\u001B[0m");
				goBack=true;
			}
			else {
				int count=1;
				for (Mail mail : mails) {
					System.out.println("     --------------------------------------------------------------------------------");
					System.out.print("[" + count++ + "]  ");
					mail.displayInboxMail();
					System.out.println("     --------------------------------------------------------------------------------");
				}
				System.out.println("\n 1. Move To Existing Label\n 2. Move To New Label\n 3. Read Mail \n Press 0 for Go Back.. ");
				System.out.println("Enter your option : ");
				byte option = Utility.getInstance().readByteInput();
				if(option==1){
					moveToExistingLable(user,mails);
				}
				else if(option==2){
					createFolder(user);
					moveToExistingLable(user,mails);
				}
				else if(option==3) {
					System.out.println("Enter Mail Number : ");
					int index=Utility.getInstance().readIntegerInput();
					if(index>0&&index<=mails.size()) {
						Mail mail=mails.get(index-1);
						mail.readMail(user);
						System.out.println("Press 0 for go back..");
						byte choice=Utility.getInstance().readByteInput();
						if(choice==0);
						else
							System.out.println("\u001B[31m Your choice is invalid.. \u001B[0m");

					}
					else {
						System.out.println("\u001B[31m Enter valid Choice... \u001B[0m");
					}
				}
				else if(option==0) {
					goBack=true;
				}
				else System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!goBack);
	}
}
