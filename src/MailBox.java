import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

class MailBox {
	public static final byte SEND_MAIL=1;
	public static final byte SAVE_TO_DRAFT=2;
	public static final byte DELETE_COMPOSED_MAIL=3;
	private LinkedList<Mail> mailbox = new LinkedList<Mail>();
	private static MailBox mails = null;
	private MailBox() {
	}
	public static MailBox getInstance() {
		if(mails==null) {
			mails=new MailBox();
		}
		return mails;
	}
	void addMail(Mail mail) {
		mailbox.add(mail);
	}
	void addMail(String sender,ArrayList<String> receivers,ArrayList<String> cc,ArrayList<String> bcc,String subject,String mail, boolean isDraftMail,ArrayList<String> mailUsers) {
		Mail newMail=new Mail(sender,receivers,cc,bcc,subject,mail,isDraftMail,mailUsers);
		FileHandler file=new FileHandler();
		file.writeMail(newMail,"D:\\Gmail Management System\\mail.csv");
	}
	void composeMail(String userName) {
		ArrayList<String> mailUsers=new ArrayList<String>();
		String sender=userName;
		System.out.print("Enter number of receivers : ");
		int receiverCount=Utility.getInstance().validateReceiverCount();
		System.out.print("Enter Receiver Mail ID(s) : ");
		ArrayList<String> receivers=new ArrayList<String>();
		mailUsers.add(sender);
		for(int i=0;i<receiverCount;i++) {
			String user=Utility.getInstance().readStringInput();
			if(!receivers.contains(user))
				receivers.add(user);
			if(!mailUsers.contains(user))
				mailUsers.add(user);
		}
		System.out.print("Enter CC Count : ");
		int ccCount=Utility.getInstance().validateCount();
		ArrayList<String> cc=new ArrayList<String>();
		if(ccCount>0) {
			System.out.print("Enter CC Mail ID(s) : ");
			for(int i=0;i<ccCount;i++) {
				String user=Utility.getInstance().readStringInput();
				if(!cc.contains(user))
					cc.add(user);
				if(!mailUsers.contains(user))
					mailUsers.add(user);
			}
		}
		System.out.print("Enter BCC Count : ");
		int bccCount=Utility.getInstance().validateCount();
		ArrayList<String> bcc=new ArrayList<String>();
		if(bccCount>0) {
			System.out.print("Enter BCC Mail ID(s) : ");
			for(int i=0;i<bccCount;i++) {
				String user=Utility.getInstance().readStringInput();
				if(!bcc.contains(user))
					bcc.add(user);
				if(!mailUsers.contains(user))
					mailUsers.add(user);
			}
		}
		System.out.print("Enter the Subject : ");
		String subject=Utility.getInstance().readStringInput();
		System.out.println("Enter Mail : ");
		String mail=Utility.getInstance().readStringInput();
		System.out.println("\n 1. Send Mail\n 2. Save to Draft\n 3. Delete Composed Mail ");
		byte choice=Utility.getInstance().validateSendOption();
		if(choice==SEND_MAIL) {
			MailBox.getInstance().addMail(sender,receivers,cc,bcc,subject,mail,false,mailUsers);
			System.out.println("\u001B[32m Mail sended successfully..!! \u001B[0m");
		}
		else if(choice==SAVE_TO_DRAFT) {
			MailBox.getInstance().addMail(sender,receivers,cc,bcc,subject,mail,true,mailUsers);
			System.out.println("\u001B[33m Mail saved to Draft..!! \u001B[0m");
		}
		else if(choice==DELETE_COMPOSED_MAIL) System.out.println("\u001B[33m Composed Mail Deleted..!! \u001B[0m");

	}

	ArrayList<Mail> getInbox(String userName) {
		ArrayList<Mail> inbox=new ArrayList<Mail>();
		try {
			FileReader file=new FileReader("D:\\Gmail Management System\\Mail.csv");
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				while(line!=null) {
					String mail[]=line.split(",");
					ArrayList<String> mailUsers=new ArrayList<String>(Arrays.asList(mail[1].split("-")));
					if(mailUsers.contains(userName)) {
						if(mail[2].equalsIgnoreCase("FALSE")) {
							ArrayList<String> trash=new ArrayList<String>(Arrays.asList(mail[4].split("-")));
							if(!trash.contains(userName)) {
								if(!mail[5].equalsIgnoreCase(userName)) {
									String ID=mail[0];
									boolean isDraftMail=Boolean.parseBoolean(mail[2]);
									ArrayList<String> starred=new ArrayList<String>(Arrays.asList(mail[3].split("-")));
									String sender=mail[5];	
									ArrayList<String> receivers=new ArrayList<String>(Arrays.asList(mail[6].split("-")));
									ArrayList<String> cc=new ArrayList<String>(Arrays.asList(mail[7].split("-")));
									ArrayList<String> bcc=new ArrayList<String>(Arrays.asList(mail[8].split("-")));
									String subject=mail[9];
									String mailBody=mail[10];
									String time=mail[11];
									inbox.add(0,new Mail(ID,mailUsers,isDraftMail,starred,trash,sender,receivers,cc,bcc,subject,mailBody,time));
								}
							}
						}
					}
					line=reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return inbox;
	}
	ArrayList<Mail> getSentBox(String userName) {
		ArrayList<Mail> sent=new ArrayList<Mail>();
		try {
			FileReader file=new FileReader("D:\\Gmail Management System\\Mail.csv");
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				while(line!=null) {
					String mail[]=line.split(",");
					ArrayList<String> mailUsers=new ArrayList<String>(Arrays.asList(mail[1].split("-")));
					if(mailUsers.contains(userName)) {
						if(mail[2].equalsIgnoreCase("FALSE")) {
							ArrayList<String> trash=new ArrayList<String>(Arrays.asList(mail[4].split("-")));
							if(!trash.contains(userName)) {
								if(mail[5].equalsIgnoreCase(userName)) {
									String ID=mail[0];
									boolean isDraftMail=Boolean.parseBoolean(mail[2]);
									ArrayList<String> starred=new ArrayList<String>(Arrays.asList(mail[3].split("-")));

									String sender=mail[5];	
									ArrayList<String> receivers=new ArrayList<String>(Arrays.asList(mail[6].split("-")));
									ArrayList<String> cc=new ArrayList<String>(Arrays.asList(mail[7].split("-")));
									ArrayList<String> bcc=new ArrayList<String>(Arrays.asList(mail[8].split("-")));
									String subject=mail[9];
									String mailBody=mail[10];
									String time=mail[11];
									sent.add(0,new Mail(ID,mailUsers,isDraftMail,starred,trash,sender,receivers,cc,bcc,subject,mailBody,time));
								}
							}
						}
					}
					line=reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sent;
	}
	ArrayList<Mail> getDraft(String userName) {
		ArrayList<Mail> draft=new ArrayList<Mail>();
		try {
			FileReader file=new FileReader("D:\\Gmail Management System\\Mail.csv");
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				while(line!=null) {
					String mail[]=line.split(",");
					ArrayList<String> mailUsers=new ArrayList<String>(Arrays.asList(mail[1].split("-")));
					if(mailUsers.contains(userName)) {
						if(mail[2].equalsIgnoreCase("TRUE")) {
							String ID=mail[0];
							boolean isDraftMail=Boolean.parseBoolean(mail[2]);
							ArrayList<String> starred=new ArrayList<String>(Arrays.asList(mail[3].split("-")));
							ArrayList<String> trash=new ArrayList<String>(Arrays.asList(mail[4].split("-")));
							String sender=mail[5];	
							ArrayList<String> receivers=new ArrayList<String>(Arrays.asList(mail[6].split("-")));
							ArrayList<String> cc=new ArrayList<String>(Arrays.asList(mail[7].split("-")));
							ArrayList<String> bcc=new ArrayList<String>(Arrays.asList(mail[8].split("-")));
							String subject=mail[9];
							String mailBody=mail[10];
							String time=mail[11];
							draft.add(0,new Mail(ID,mailUsers,isDraftMail,starred,trash,sender,receivers,cc,bcc,subject,mailBody,time));
						}
					}
					line=reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return draft;
	}
	ArrayList<Mail> getStarred(String userName) {
		ArrayList<Mail> starred=new ArrayList<Mail>();
		try {
			FileReader file=new FileReader("D:\\Gmail Management System\\Mail.csv");
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				while(line!=null) {
					String mail[]=line.split(",");
					ArrayList<String> mailUsers=new ArrayList<String>(Arrays.asList(mail[1].split("-")));

					if(mailUsers.contains(userName)) {
						ArrayList<String> starredUsers=new ArrayList<String>(Arrays.asList(mail[3].split("-")));
						if(starredUsers.contains(userName)) {
							ArrayList<String> trash=new ArrayList<String>(Arrays.asList(mail[4].split("-")));
							if(!trash.contains(userName)) {
								String ID=mail[0];
								boolean isDraftMail=Boolean.parseBoolean(mail[2]);
								String sender=mail[5];	
								ArrayList<String> receivers=new ArrayList<String>(Arrays.asList(mail[6].split("-")));
								ArrayList<String> cc=new ArrayList<String>(Arrays.asList(mail[7].split("-")));
								ArrayList<String> bcc=new ArrayList<String>(Arrays.asList(mail[8].split("-")));
								String subject=mail[9];
								String mailBody=mail[10];
								String time=mail[11];
								starred.add(0,new Mail(ID,mailUsers,isDraftMail,starredUsers,trash,sender,receivers,cc,bcc,subject,mailBody,time));
							}
						}
					}
					line=reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return starred;
	}
	ArrayList<Mail> getTrash(String userName) {
		ArrayList<Mail> trash=new ArrayList<Mail>();
		try {
			FileReader file=new FileReader("D:\\Gmail Management System\\Mail.csv");
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				while(line!=null) {
					String mail[]=line.split(",");
					ArrayList<String> mailUsers=new ArrayList<String>(Arrays.asList(mail[1].split("-")));
					if(mailUsers.contains(userName)) {
						ArrayList<String> trashUsers=new ArrayList<String>(Arrays.asList(mail[4].split("-")));
						if(trashUsers.contains(userName)) {
							String ID=mail[0];
							boolean isDraftMail=Boolean.parseBoolean(mail[2]);
							ArrayList<String> starredUsers=new ArrayList<String>(Arrays.asList(mail[3].split("-")));

							String sender=mail[5];	
							ArrayList<String> receivers=new ArrayList<String>(Arrays.asList(mail[6].split("-")));
							ArrayList<String> cc=new ArrayList<String>(Arrays.asList(mail[7].split("-")));
							ArrayList<String> bcc=new ArrayList<String>(Arrays.asList(mail[8].split("-")));
							String subject=mail[9];
							String mailBody=mail[10];
							String time=mail[11];
							trash.add(0,new Mail(ID,mailUsers,isDraftMail,starredUsers,trashUsers,sender,receivers,cc,bcc,subject,mailBody,time));
						}
					}
					line=reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return trash;
	}
	ArrayList<Mail> getFolderMails(String path) {
		ArrayList<Mail> mails=new ArrayList<Mail>();
		try {
			FileReader file=new FileReader(path);
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				line=reader.readLine();
				while(line!=null) {
					String mail[]=line.split(",");
					String ID=mail[0];
					ArrayList<String> mailUsers=new ArrayList<String>(Arrays.asList(mail[1].split("-")));
					boolean isDraftMail=Boolean.parseBoolean(mail[2]);
					ArrayList<String> starred=new ArrayList<String>(Arrays.asList(mail[3].split("-")));
					ArrayList<String> trash=new ArrayList<String>(Arrays.asList(mail[4].split("-")));
					String sender=mail[5];	
					ArrayList<String> receivers=new ArrayList<String>(Arrays.asList(mail[6].split("-")));
					ArrayList<String> cc=new ArrayList<String>(Arrays.asList(mail[7].split("-")));
					ArrayList<String> bcc=new ArrayList<String>(Arrays.asList(mail[8].split("-")));
					String subject=mail[9];
					String mailBody=mail[10];
					String time=mail[11];
					mails.add(0,new Mail(ID,mailUsers,isDraftMail,starred,trash,sender,receivers,cc,bcc,subject,mailBody,time));
					line=reader.readLine();
				}
			} catch (IOException e) {
//				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
//			e.printStackTrace();
		}
		return mails;
	}
	ArrayList<Mail> getSearchMails(String userName,String word) {
		ArrayList<Mail> search=new ArrayList<Mail>();
		try {
			FileReader file=new FileReader("D:\\Gmail Management System\\Mail.csv");
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				while(line!=null) {
					String mail[]=line.split(",");
					ArrayList<String> mailUsers=new ArrayList<String>(Arrays.asList(mail[1].split("-")));
					if(mailUsers.contains(userName)) {
						if(mail[2].equalsIgnoreCase("FALSE")) {
							if (line.contains(word)) {
								ArrayList<String> trash = new ArrayList<String>(Arrays.asList(mail[4].split("-")));
								String ID = mail[0];
								boolean isDraftMail = Boolean.parseBoolean(mail[2]);
								ArrayList<String> starred = new ArrayList<String>(Arrays.asList(mail[3].split("-")));
								String sender = mail[5];
								ArrayList<String> receivers = new ArrayList<String>(Arrays.asList(mail[6].split("-")));
								ArrayList<String> cc = new ArrayList<String>(Arrays.asList(mail[7].split("-")));
								ArrayList<String> bcc = new ArrayList<String>(Arrays.asList(mail[8].split("-")));
								String subject = mail[9];
								String mailBody = mail[10];
								String time = mail[11];
								search.add(0, new Mail(ID, mailUsers, isDraftMail, starred, trash, sender, receivers, cc, bcc, subject, mailBody, time));
							}
						}
					}
					line=reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return search;
	}
	void sendDraftMail(String user,Mail mail) {
		mail.setIsDraftMail(false);
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date=new Date();
		mail.setTime(formatter.format(date));
		updateMail(mail);
	}
	void deleteDraftMail(String user,Mail mail) {
		mail.getMailUsers().clear();
		updateMail(mail);
		System.out.println("\u001B[33m Mail Deleted Successfully..!! \u001B[0m");

	}
	void starMail(String user,Mail mail) {
		if(mail.getStarred().contains(user)) {
			System.out.println("\u001B[33m You are also star the mail..!! \u001B[0m");
		}
		else {
			mail.getStarred().add(user);
			updateMail(mail);
			System.out.println("\u001B[33m Mail Starred \u001B[0m");
		}
	}
	void unStarMail(String user,Mail mail) {
		mail.getStarred().remove(user);
		updateMail(mail);
	}
	void addToTrash(String user,Mail mail) {
		mail.getTrash().add(user);
		updateMail(mail);
		System.out.println("\u001B[33m Mail Deleted Successfully \u001B[0m");
	}
	void restoreMail(String user,Mail mail) {
		mail.getTrash().remove(user);
		updateMail(mail);
		System.out.println("\u001B[33m Mail Restored Successfully \u001B[0m");
	}
	void removeFromTrash(String user,Mail mail) {
		mail.getTrash().remove(user);
		mail.getMailUsers().remove(user);
		updateMail(mail);
		System.out.println("\u001B[33m Mail Deleted Successfully.. \u001B[0m");
	}
	void updateMail(Mail mail) {
		ArrayList<String> data=new ArrayList<String>();
		StringBuilder updatedLine= new StringBuilder();
		updatedLine.append(mail.getID()).append(",");
		for(String user:mail.getMailUsers()) {
			if(user!=null&&!user.equals("-"))
				updatedLine.append(user).append("-");
		}
		updatedLine.append(",").append(mail.getIsDraftMail()).append(",");
		for(String user:mail.getStarred()) {
			if(user!=null&&!user.equals("-"))
				updatedLine.append(user).append("-");
		}
		updatedLine.append(",");
		for(String user:mail.getTrash()) {
			if(user!=null&&!user.equals("-"))
				updatedLine.append(user).append("-");
		}
		updatedLine.append(",").append(mail.getSender()).append(",");
		for(String user:mail.getReceiver()) {
			if(user!=null&&!user.equals("-"))
				updatedLine.append(user).append("-");
		}
		updatedLine.append(",");
		for(String user:mail.getCc()) {
			if(user!=null&&!user.equals("-"))
				updatedLine.append(user).append("-");
		}
		updatedLine.append(",");
		for(String user:mail.getBcc()) {
			if(user!=null&&!user.equals("-"))
				updatedLine.append(user).append("-");
		}
		updatedLine.append(",").append(mail.getSubject()).append(",").append(mail.getMail()).append(",").append(mail.getTime()).append(",");
		try {
			BufferedReader reader = new BufferedReader(new FileReader("D:\\Gmail Management System\\Mail.csv"));
			try {

				String line=reader.readLine();
				line=reader.readLine();
				while(line!=null) {
					String[] mailDetails=line.split(",");
					if(mail.getID().equals(mailDetails[0])) {
						data.add(updatedLine.toString());
					}
					else
						data.add(line);
					line=reader.readLine();
				}
				FileWriter temp=new FileWriter("D:\\Gmail Management System\\Mail.csv");
				temp.write("ID,Mail Users,Is Draft Mail,Starred Users,Trash Users,Sender,Receivers,CC,BCC,Subject,Mail,Time");
				temp.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Gmail Management System\\Mail.csv",true));
				for(String mailData:data) {
					writer.newLine();
					writer.write(mailData);
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e) {
		}
	}
	void updateMail(Mail mail,String path) {
		ArrayList<String> data=new ArrayList<String>();
		try {
			FileReader file = new FileReader(path);
			BufferedReader reader=new BufferedReader(file);
			try {
				String line=reader.readLine();
				line=reader.readLine();
				while(line!=null) {
					String[] mailDetails=line.split(",");
					if(!mail.getID().equals(mailDetails[0])) {
						data.add(line);
					}
					line=reader.readLine();
				}
				FileWriter temp=new FileWriter(path);

				temp.write("ID,Mail Users,Is Draft Mail,Starred Users,Trash Users,Sender,Receivers,CC,BCC,Subject,Mail,Time");
				temp.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(path,true));
				for(String mailData:data) {
					writer.newLine();
					writer.write(mailData);
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
//				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
		}	
	}
	void moveToFolder(String user,Mail mail) {
		File folderPath = new File("D:\\Gmail Management System\\Users\\" + user);
		ArrayList<String> files = new ArrayList<String>(Arrays.asList(folderPath.list()));
		if (files.isEmpty()) {
			System.out.println("\u001B[33m Your Label List is Empty.. \u001B[0m");
		} else {
			for (String file : files) {
				System.out.println(" * " + file);
			}

			System.out.println("Enter the folder name : ");
			String folder = Utility.getInstance().readStringInput();
//		String folderPath=path+"\\"+folder;
			File path = new File(folderPath + "\\" + folder);
			FileHandler write = new FileHandler();
			if (path.exists()) {
				write.writeMail(mail, path + "\\mails.csv");
				mail.getMailUsers().remove(user);
				updateMail(mail);
			} else {
				System.out.println("\u001B[31m" + folder + "is not Found.. Create Label then add mail to this label \u001B[0m");
			}
		}
	}
	void moveToFolder(String user,String path,ArrayList<Mail> mails){
		FileHandler write=new FileHandler();
		for(Mail mail:mails){
			write.writeMail(mail, path+"\\mails.csv");
			mail.getMailUsers().remove(user);
			updateMail(mail);
		}
		System.out.println("\u001B[33m Mails moved successfully... \u001B[0m");
	}
}
