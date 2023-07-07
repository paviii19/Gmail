class User {
	public static final byte COMPOSE_MAIL = 1;
	public static final byte INBOX = 2;
	public static final byte SENT = 4;
	public static final byte DRAFT = 5;
	public static final byte STARRED = 3;
	public static final byte TRASH = 6;
	public static final byte LABELS = 7;
	public static final byte SEARCH=8;
	public static final byte SIGNOUT = 9;
	public static final byte CREATE_LABEL = 1;
	public static final byte VIEW_LABEL =2;
	public static final byte DELETE_LABEL =3;
	public static final byte BACK = 0;
	
	private String firstName, lastName, dateOfBirth, userName, password, gender;
	Folders folders;
	FileHandler file;

	User(String firstName,String lastName,String dateOfBirth, String gender,String userName,String password){
		this.firstName=firstName;
		this.lastName=lastName;
		this.dateOfBirth=dateOfBirth;
		this.gender=gender;
		this.userName=userName;
		this.password=password;
		folders=new Folders();
		file=new FileHandler();
	}
	String getFirstName() {
		return firstName;
	}
	String getLastName() {
		return lastName;
	}
	String getdateOfBirth() {
		return dateOfBirth;
	}
	String getGender() {
		return gender;
	}
	String getUserName() {
		return userName;
	}
	String getPassword() {
		return password;
	}
	void createAccount(User user) {
		file.writeUserDetails(firstName,lastName,dateOfBirth,gender,userName,password);
	}
	boolean signIn() {
		System.out.print("Enter your Password : ");
		String userPassword=Utility.getInstance().readStringInput();
		return userPassword.equals(password);
	}
	void viewHomePage() {
		boolean signout=false;
		do {
			System.out.print("\n 1. Compose Mail \n 2. Inbox \n 3. Starred \n 4. Sent \n 5. Draft \n 6. Trash \n 7. Labels \n 8. Search \n 9. Signout\n");
			System.out.print("Enter Your Choice : ");
			byte choice=Utility.getInstance().readByteInput();
			switch (choice) {
				case COMPOSE_MAIL -> MailBox.getInstance().composeMail(userName);
				case INBOX -> folders.displayInbox(userName);
				case STARRED -> folders.displayStarred(userName);
				case SENT -> folders.displaySent(userName);
				case DRAFT -> folders.displayDraft(userName);
				case TRASH -> folders.displayTrash(userName);
				case LABELS -> viewLabelsPage();
				case SEARCH -> folders.searchMails(userName);
				case SIGNOUT -> signout = true;
				default -> System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!signout);
	}
	void viewLabelsPage() {
		boolean goBack=false;
		do {
			System.out.print("\n 1. Create New Label \n 2. View Labels \n 3. Delete Label \n Press 0 for Go Back.. \n");
			System.out.print("Enter Your Choice : ");
			byte choice=Utility.getInstance().readByteInput();
			switch (choice) {
				case CREATE_LABEL -> folders.createFolder(userName);
				case VIEW_LABEL -> folders.viewFolders(userName);
				case DELETE_LABEL -> folders.deleteFolder(userName);
				case BACK -> goBack = true;
				default -> System.out.println("\u001B[31m Enter valid choice.. \u001B[0m");
			}
		}while(!goBack);
	}
}
