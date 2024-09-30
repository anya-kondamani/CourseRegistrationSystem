package ClassRegistrationSystem;

import java.util.*;
import java.io.Serializable;

public class Student extends User implements Serializable, studentActions {
	
	private ArrayList<Course> myCourses=new ArrayList<>();
	private static final long serialVersionUID = 1L;
	
	public Student(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
	}
	
	public ArrayList<Course> getmyCourses(){
		return myCourses;
	}
	
	@Override
    public boolean login(String username, String password) {
        for (Student student : Main.students) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
            	return true;
            }
        }
		return false;
    }
    
    @Override
    public void viewCourses() { // All courses, inherited from User.
    	for (Course course : Main.courses) {
            System.out.println("Course Name: "+course.getcourseName());
            System.out.println("Course ID: "+course.getid());
            System.out.println("Max # Students: "+course.getmaxStudents());
            System.out.println("Current # Students: "+course.getcurrentStudents());
            System.out.println("Instructor: "+course.getinstructor());
            System.out.println("Section: "+course.getsection());
            System.out.println("Location: "+course.getlocation());
            System.out.println("------------------------------------");
        }
    	return;
	}
    
	
	public void openCourses() { // All courses that have not yet reached the maximum number of students.
		System.out.println("Open courses:");
	    for (Course course : Main.courses) {
	    	if (course.getcurrentStudents() < course.getmaxStudents()) {
	    		System.out.println("Course Name: "+course.getcourseName());
	            System.out.println("Course ID: "+course.getid());
	            System.out.println("Max # Students: "+course.getmaxStudents());
	            System.out.println("Current # Students: "+course.getcurrentStudents());
	            System.out.println("Instructor: "+course.getinstructor());
	            System.out.println("Section: "+course.getsection());
	            System.out.println("Location: "+course.getlocation());
	            System.out.println("------------------------------------");
	    	}
	    }
	    return;
	}
	
	public void addCourse(String id, String courseName, int section) { // A student can add themselves to the course. (if circumstances allow)
		for (Course course : Main.courses) {
	        if (course.getid().equals(id) && course.getcourseName().equals(courseName) && course.getsection()==section) {
	            course.addStudent(this);  
	            return;
	        }
	    }
	    System.out.println("Course not found.");
	}
	
	public void dropCourse(String id, String courseName, int section) { // A student can remove themselves from the course.
		for (Course course : myCourses) {
            if (course.getid().equals(id) && course.getcourseName().equals(courseName) && course.getsection()==section) {
                course.removeStudent(this); 
                return;
            }
        } 
        System.out.println("Course not found or you are not enrolled."); 
	}
	
	public void myCourses() { // A student may view the courses in which they are currently enrolled.
		for (Course course : myCourses) {
			System.out.println("Course Name: "+course.getcourseName());
            System.out.println("Course ID: "+course.getid());
            System.out.println("Max # Students: "+course.getmaxStudents());
            System.out.println("Current # Students: "+course.getcurrentStudents());
            System.out.println("Instructor: "+course.getinstructor());
            System.out.println("Section: "+course.getsection());
            System.out.println("Location: "+course.getlocation());
            System.out.println("------------------------------------");
        }
		if (myCourses.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
		}
	}
	
	public void changePass(String oldPassword, String newPassword) { // Student can change Admin generated password.
        if (getPassword().equals(oldPassword)) {
            setPassword(newPassword);
            System.out.println("Password changed.");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }
	

}
