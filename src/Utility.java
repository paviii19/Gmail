import java.util.Scanner;

public class Utility {
	static Scanner sc=new Scanner(System.in);
	private static Utility utility=null;
	private Utility() {

	}
	public static Utility getInstance() {
		if(utility==null) {
			utility=new Utility();
		}
		return utility;
	}
	public byte readByteInput() {
		byte input;
		do {
			if (sc.hasNextByte()) {
				input = sc.nextByte();
				sc.nextLine();
				break;
			} else {
				System.out.println("\u001B[31m Enter Valid Input... \u001B[0m");
				sc.nextLine();
			}
		} while (true);
		return input;
	}

	public int readIntegerInput() {
		int input;
		do {
			if (sc.hasNextInt()) {
				input = sc.nextInt();
				sc.nextLine();
				break;
			} else {
				System.out.println("\u001B[31m Enter Valid Input... \u001B[0m");
				sc.nextLine();
			}
		} while (true);
		return input;
	}

	public String readStringInput() {
		return sc.nextLine();
	}
	String validateName() {
		String name,nameRegex = "[[A-Z][a-z]]{1,20}";
		do {
			name=readStringInput().toLowerCase();
			if(name.matches(nameRegex)) break;
			else System.out.println("\u001B[31m Enter valid Name \u001B[0m");
		}while(true);
		return name;
	}
	String validateDateOfBirth() {
		String dateOfBirth,dateRegex = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(19[0-9]{2}|20[01][0-9]|2022)$";
		do {
			dateOfBirth=readStringInput();
			if(dateOfBirth.matches(dateRegex)) break;
			else System.out.println("\u001B[31m Enter valid Date... Date format must be MM-DD-YYYY \u001B[0m");
		}while(true);
		return dateOfBirth;
	}
	String validateGender() {
		String gender,genderRegex = "male|female|transgender";
		do {
			gender=readStringInput().toLowerCase();
			if(gender.matches(genderRegex)) break;
			else System.out.println("\u001B[31m Enter valid Gender... \u001B[0m");
		}while(true);
		return gender;
	}
	String validateUserName() {
		String userName,gmailRegex = "^[A-Za-z0-9_.-]{3,}@gmail.com$";
		do {
			userName=readStringInput().toLowerCase();
			if(userName.matches(gmailRegex)) {
				if(FileHandler.isUsernameAvailable(userName))
					break;
				else
					System.out.println("\u001B[31m User Name already exists.. Try Different user name... \u001B[0m");
			}
			else System.out.println("\u001B[31m Enter valid username... \u001B[0m");
		}while(true);
		return userName;
	}
	String validatePassword() {
		String password,passRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*.,?]).{8,16}$";
		do {
			password=readStringInput();
			if(password.matches(passRegex)) break;
			else System.out.println("\u001B[31m Enter Strong Password... \n * Password length within 8 to 16 Characters \n * Must contain minimum one Lower Case\n * Must contain minimum one Upper Case\n * Must contain minimum one Special Character  \u001B[0m");
		}while(true);
		return password;
	}
	int validateReceiverCount() {
		int count;
		do {
			count=readIntegerInput();
			if(count>0) break;
			else System.out.println("\u001B[31m Enter valid receiver count.. \u001B[0m");
		}while(true);
		return count;
	}
	int validateCount() {
		int count;
		do {
			count=readIntegerInput();
			if(count>=0) break;
			else System.out.println("\u001B[31m Count must be positive number... \u001B[0m");
		}while(true);
		return count;
	}
	byte validateSendOption() {
		byte option;
		do {
			option=readByteInput();
			if(option>=0&&option<=3) break;
			else System.out.println("\u001B[31m Enter input between 1 to 3... \u001B[0m");
		}while(true);
		return option;
	}
}
