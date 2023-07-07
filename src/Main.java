public class Main {
public static final byte USER_SIGNIN = 1;
	public static final byte CREATE_ACCOUNT = 2;
	public static final byte EXIT = 3;

	public static void main(String args[]) {
		System.out.println(
				"\t@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ \n\t@    GMAIL MANAGEMENT SYSTEM\t@\n\t@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		boolean exitCode=false;
		do {
			System.out.println("\n 1. Sign in to Gmail\n 2. Create a Gmail Account \n 3. Exit\n");
			System.out.print("Enter Your Choice : ");
			byte choice = Utility.getInstance().readByteInput();
			switch (choice) {
				case USER_SIGNIN -> {
					User user = UserUtility.getInstance().findUser();
					if (user != null) {
						if (user.signIn()) {
							System.out.println("\u001B[32m Signin Successfully.. \u001B[0m");
							user.viewHomePage();
						} else
							System.out.println("\u001B[31m Incorrect Password..!! \u001B[0m");
					} else
						System.out.println("\u001B[31m User not found.. Please try again or Create new account.. \u001B[0m");
				}
				case CREATE_ACCOUNT -> {
					User newUser = UserUtility.getInstance().readNewUserDetails();
					newUser.createAccount(newUser);
					System.out.println("\u001B[33m Account Created Successfully...!!! Sign in to continue.... \u001B[0m");
				}
				case EXIT -> exitCode = true;
				default -> System.out.println("\u001B[31m Enter valid Choice \u001B[0m");
			}
		} while (!exitCode);
	}
}
