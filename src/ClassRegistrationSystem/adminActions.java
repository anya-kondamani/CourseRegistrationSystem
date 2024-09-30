package ClassRegistrationSystem;

public interface adminActions {
	void viewCourses();
	boolean login(String username, String password);
	void createStudent(String firstName, String lastName, String username, String password);
	void createCourse(String courseName, String id, int maxStudents, String instructor, int section, String location);
	void deleteCourse(String id, String courseName, int section);
	void editCourse(Course course);
	void courseInfo(String id, int section);
	void registerStudent(String username, String id, int section);
	void viewStudents(String id, int section);
	void fullCourses();
	void viewCoursesByStudent(String username);
	void sortCourses();
	void writeFullCourses(String filepath);
}
