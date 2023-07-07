public class UserUtility {
	private static UserUtility userUtility=null;
	private UserUtility() {
	}
	public static UserUtility getInstance() {
		if(userUtility==null) {
			userUtility=new UserUtility();
		}
		return userUtility;
	} 
	User readNewUserDetails() {
		System.out.print("Enter Your First Name : ");
		String firstName=Utility.getInstance().validateName();
		System.out.print("Enter Your Last Name : ");
		String lastName=Utility.getInstance().validateName();
		System.out.print("Enter Your DOB (MM-DD-YYYY): ");
		String dateOfBirth=Utility.getInstance().validateDateOfBirth();
		System.out.print("Enter Your Gender Male\\Female\\Transgender : ");
		String gender=Utility.getInstance().validateGender();
		System.out.print("Enter User Name (Must ends with @gmail.com) : ");
		String userName=Utility.getInstance().validateUserName();
		System.out.print("Enter Your Password : ");
		String password=Utility.getInstance().validatePassword();
		return new User(firstName,lastName,dateOfBirth,gender,userName,password);
	}
	User findUser() {
		User user=null;
		System.out.print("Enter your Mail ID : ");
		String userName=Utility.getInstance().readStringInput();
		String[] userDetails =FileHandler.readUserDetails(userName);
		if(userDetails!=null) {
			user=new User(userDetails[0],userDetails[1],userDetails[2],userDetails[3],userDetails[4],userDetails[5]);
		}
		return user;
	}
}
