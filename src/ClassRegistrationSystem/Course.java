package ClassRegistrationSystem;
import java.util.*;
import java.io.Serializable;

public class Course implements Serializable {
	private String courseName;
	private String id;
	private int maxStudents;
	private int currentStudents;
	private ArrayList<Student> students;
	private String instructor;
	private int section;
	private String location;
	private static final long serialVersionUID = 1L;
	
	public Course(String courseName, String id, int maxStudents, int currentStudents, ArrayList<Student> students,
			String instructor, int section, String location) {
		this.courseName=courseName;
		this.id=id;
		this.maxStudents=maxStudents;
		this.currentStudents=currentStudents;
		this.students=students;
		this.instructor=instructor;
		this.section=section;
		this.location=location;
	}
	
	public void addStudent(Student student) {  // Adds a student to course's enrollment.
		if (students.size() < maxStudents) {
            if (!students.contains(student)) { 
                students.add(student);
                student.getmyCourses().add(this); 
                currentStudents = students.size();
                System.out.println("Student successfully registered.");
            } else {
                System.out.println("Student is already enrolled in this course.");
            }
        } else {
            System.out.println("Course is full, can't add more students.");
        }
    }
	public void removeStudent(Student student) { // Removes a student from course's enrollement.
		if (students.contains(student)) { // Check if student is actually enrolled
            students.remove(student);
            student.getmyCourses().remove(this); // Update the student's course list as well
            currentStudents = students.size(); // Sets currentStudents to the actual number of students enrolled.
            System.out.println("Student successfully removed from the course.");
        } else {
            System.out.println("Student is not enrolled in this course.");
        }
	}
	
	// getter & setters
	
	public String getcourseName() {
		return courseName;
	}
	public String getid() {
		return id;
	}
	public int getmaxStudents() {
		return maxStudents;
	}
	public int getcurrentStudents() {
		return currentStudents;
	}
	public ArrayList<Student> getStudents() {
		return students;
	}
	public String getinstructor() {
		return instructor;
	}
	public int getsection() {
		return section;
	}
	public String getlocation() {
		return location;
	}
	public void setmaxStudents(int newmaxStudents) {
		this.maxStudents = newmaxStudents;
	}
	public void setcurrentStudents(int newcurrentStudents) {
		this.currentStudents = newcurrentStudents;
	} 
	public void setStudents(ArrayList<Student> newStudents) {
		this.students = newStudents;
	}
	public void setInstructor(String newInstructor) {
		this.instructor = newInstructor;
	}
	public void setSection(int newSection) {
		this.section = newSection;
	}
	public void setLocation(String newLocation) {
		this.location = newLocation;
	}

}
