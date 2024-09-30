package ClassRegistrationSystem;

import java.io.*;

public abstract class User implements Serializable {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private static final long serialVersionUID = 1L;
	
	public User(String username, String password, String firstName,
			String lastName) {
		this.username=username;
		this.password=password;
		this.firstName=firstName;
		this.lastName=lastName;
	}
	
	// The following 2 classes must be used in Student and Admin.
	
	public abstract void viewCourses();
	public abstract boolean login(String username, String password);
	
	// getters & setters
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getfirstName() {
		return firstName;
	}
	public String getlastName() {
		return lastName;
	}
	public void setPassword(String newPassword) {
		this.password=newPassword;
	}
}
