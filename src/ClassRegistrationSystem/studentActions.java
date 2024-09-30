package ClassRegistrationSystem;

public interface studentActions {
	boolean login(String username, String password);
	void viewCourses();
	void openCourses();
	void addCourse(String id, String courseName, int section);
	void dropCourse(String id, String courseName, int section);
	void myCourses();
	void changePass(String oldPassword, String newPassword);
	
}
