package ClassRegistrationSystem;

import java.util.*;
import java.io.*;
import java.io.Serializable;

public class Admin extends User implements adminActions {
	private static final String ADMIN_USERNAME = "Admin";  // Set Admin username.
    private static final String ADMIN_PASSWORD = "Admin001"; // Set Admin password.
    private static final long serialVersionUID = 1L;
	
	public Admin(String firstName, String lastName) {
        super(ADMIN_USERNAME, ADMIN_PASSWORD, firstName, lastName); // Calls the User constructor and fixes username + password.
    }
	
	@Override
	public void viewCourses() { // All courses, inherited from User.
        for (Course course : Main.courses) {
            System.out.println("Course Name: "+course.getcourseName());
            System.out.println("Course ID: "+course.getid());
            System.out.println("Max # Students: "+course.getmaxStudents());
            System.out.println("Current # Students: "+course.getcurrentStudents());
            System.out.println("Enrolled Students: ");
            for (Student student : course.getStudents()) {
            	System.out.println(student.getfirstName()+" "+student.getlastName());
            }
            System.out.println("Instructor: "+course.getinstructor());
            System.out.println("Section: "+course.getsection());
            System.out.println("Location: "+course.getlocation());
            System.out.println("------------------------------------");
        }
        return;
	}
	
	@Override 
	public boolean login(String username, String password) {	
		return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
	}
	
	public void createStudent(String username, String password,String firstName, String lastName) { // Standard Student creation method.
		if (!Main.students.isEmpty()) {
			for (Student student : Main.students) {
				if (student.getUsername() != null && student.getUsername().equals(username)) {
					System.out.println("Username is taken.");
					return;  // Exit if username is taken
				}
			}
	    }
	    Student newStudent = new Student(username, password,firstName, lastName);
	    Main.students.add(newStudent);
	}
	
	// Overloaded method for default student. Essentially an unattached account.
	public void createStudent(String username, String password) {
		for (Student student : Main.students) {
	        if (student.getUsername().equals(username)) {
	            System.out.println("Username is taken.");
	            return;  // Exit if username is taken
	        }
	    }
	    Student newStudent = new Student(username, password,"Unknown", "Unknown");
	    System.out.println("Student account created with default names.");
	    Main.students.add(newStudent);
	}
	
	public void createCourse(String courseName, String id, int maxStudents, String instructor, int section, String location) { // Course creation.
		for (Course course : Main.courses) {
			if (course.getid().equals(id)) {
				System.out.println("Course ID is already in System.");
				return;
			}
		}
		Course newCourse = new Course(courseName, id, maxStudents, 0, new ArrayList<>(), instructor, section, location);
		Main.courses.add(newCourse);
	}
	
	public void deleteCourse(String id, String courseName, int section) { // Course deletion.
		Iterator<Course> iterator = Main.courses.iterator();
	    while (iterator.hasNext()) {
	        Course course = iterator.next();
	        if (course.getid().equals(id) && course.getcourseName().equals(courseName) && course.getsection()==section) {
	            iterator.remove();
	            System.out.println("Course deleted successfully.");
	            return; 
	        }
	    }
	}
	
	public void editCourse(Course course) { // Edit course method; to reduce redundancy it prompts the Admin to choose exactly what feature to change.
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Which feature would you like to edit?");
		System.out.println("1. Max # Students");
		System.out.println("2. Student List");
		System.out.println("3. Instructor");
		System.out.println("4. Section Number");
		System.out.println("5. Location");

		int choice = scanner.nextInt();
		scanner.nextLine();  

		switch (choice) {
			case 1:
				System.out.println("Enter new maximum # of students: ");
		        int newMax = scanner.nextInt();
		        course.setmaxStudents(newMax);
		        break;
		    case 2: // This does not update current student list but creates a new one, the former can simply be done with "Register a Student". 
		    	ArrayList<Student> newStudents = new ArrayList<Student>(); 
		    	String addStudents;
		    	System.out.println("Enter new list of enrolled students one at a time.");
	            
		    	while (newStudents.size() < course.getmaxStudents()) {
		    		System.out.println("Enter student's username: ");
		            String username = scanner.next();
		            
		            Student newStudent = null;

	                for (Student student : Main.students) {
	                    if (student.getUsername().equals(username)) {
	                        newStudent = student;
	                        break;
	                    }
	                }

	                if (newStudent == null) {
	                    System.out.println("Student with username " + username + " does not exist.");
	                } else if (newStudents.contains(newStudent)) {
	                    System.out.println("Student with username " + username + " is already enrolled in the course.");
	                } else {
	                    newStudents.add(newStudent);
	                    System.out.println("Student " + newStudent.getfirstName() + " " + newStudent.getlastName() + " added.");
	                }

	                // Cuts off admin additions if maximum student count is reached.
	                if (newStudents.size() >= course.getmaxStudents()) {
	                    System.out.println("Course has reached maximum student enrollment, you cannot add any more students.");
	                    break;
	                }

	                System.out.println("Do you want to add another student? [yes/no]");
	                scanner.nextLine();
	                addStudents = scanner.nextLine();
	                if (!addStudents.equalsIgnoreCase("yes")) {
	                    break;
	                }
	            }

	            course.setStudents(newStudents); // Update course's student list.
	            course.setcurrentStudents(newStudents.size()); // Update current number of students based on new student list.
	            break;
		    case 3:
		        System.out.println("Enter new Course Instructor: ");
		        String newInstructor = scanner.nextLine();
		        course.setInstructor(newInstructor);
		        break;
		    case 4:
		        System.out.println("Enter new section number: ");
		        int newSection = scanner.nextInt();
		        course.setSection(newSection);
		        break;
		    case 5:
		        System.out.println("Enter new location: ");
		        String newLocation = scanner.nextLine();
		        course.setLocation(newLocation);
		        break;
		    default:
		        System.out.println("Invalid choice.");
		        break;
		}

		System.out.println("Course updated successfully!");
	}
	
	public void courseInfo(String id, int section) {
		boolean courseFound = false;  // To track if the course is found.

	    for (Course course : Main.courses) {
	        if (course.getid().equals(id) && course.getsection()==section) {
	            System.out.println("Course Name: " + course.getcourseName());
	            System.out.println("Course ID: " + course.getid());
	            System.out.println("Max # Students: " + course.getmaxStudents());
	            System.out.println("Current # Students: " + course.getcurrentStudents());
	            System.out.println("Enrolled Students: ");
	            
	            for (Student student : course.getStudents()) {
	                System.out.println(student.getfirstName() + " " + student.getlastName());
	            }
	            
	            System.out.println("Instructor: " + course.getinstructor());
	            System.out.println("Section: " + course.getsection());
	            System.out.println("Location: " + course.getlocation());
	            System.out.println("------------------------------------");
	            
	            courseFound = true;  // Course was found.
	            break;  
	        }
	    }

	    if (!courseFound) {
	        System.out.println("Course not found.");
	    }
	}
		
	
	public void registerStudent(String username, String id, int section) { // Registers student in course.
	    Student studentToRegister = null;
	    
	    // These first two blocks find the student.
	    for (Student student : Main.students) {
	        if (student.getUsername().equals(username)) {
	            studentToRegister = student;
	            break;
	        }
	    }
	    
	    if (studentToRegister == null) {
	        System.out.println("Student does not exist.");
	        return;
	    }
	    // These second two block determine the course, if it exists.
	    Course courseToRegister = null;
	    for (Course course : Main.courses) {
	        if (course.getid().equals(id) && course.getsection()==section) {
	            courseToRegister = course;
	            break;
	        }
	    }
	   
	    if (courseToRegister == null) {
	        System.out.println("Course not found.");
	        return;
	    }
	    //Finally, the addStudent method from Course is called.
	    courseToRegister.addStudent(studentToRegister);
	}
	
	public void viewStudents(String id, int section) { // View all students in a specific course.
		for (Course course : Main.courses) {
			if (course.getid().equals(id) && course.getsection()==section) { 
				for (Student student : course.getStudents()) {
					System.out.println(student.getfirstName()+" "+student.getlastName());
				}
			}
		}
	}
	
	public void fullCourses() { // All courses that have reached the maximum number of students.
	    for (Course course : Main.courses) {
	    	if (course.getcurrentStudents() == course.getmaxStudents()) {
	    		System.out.println("Course Name: "+course.getcourseName());
	            System.out.println("Course ID: "+course.getid());
	            System.out.println("Max # Students: "+course.getmaxStudents());
	            System.out.println("Current # Students: "+course.getcurrentStudents());
	            System.out.println("Enrolled Students: ");
	            for (Student student : course.getStudents()) {
	            	System.out.println(student.getfirstName()+" "+student.getlastName());
	            }
	            System.out.println("Instructor: "+course.getinstructor());
	            System.out.println("Section: "+course.getsection());
	            System.out.println("Location: "+course.getlocation());
	            System.out.println("------------------------------------");
	    	}
	    }
	    return;
	}
	
	public void viewCoursesByStudent(String username) {
		boolean studentFound = false;  // Boolean to determine if student is found.
	    for (Course course : Main.courses) {
	        for (Student student : course.getStudents()) {
	            if (student.getUsername().equals(username)) {
	                studentFound = true;  // Set to true if student is found. Otherwise would remain false.
	                System.out.println("Course Name: " + course.getcourseName());
	                System.out.println("Course ID: " + course.getid());
	                System.out.println("Max # Students: " + course.getmaxStudents());
	                System.out.println("Current # Students: " + course.getcurrentStudents());
	                System.out.println("Enrolled Students: ");
	                for (Student enrolledStudent : course.getStudents()) {
	                    System.out.println(enrolledStudent.getfirstName()+ " " + enrolledStudent.getlastName());
	                }
	                System.out.println("Instructor: " + course.getinstructor());
	                System.out.println("Section: " + course.getsection());
	                System.out.println("Location: " + course.getlocation());
	                System.out.println("------------------------------------");
	                break;  
	            }
	        }
	    }
	    
	    if (!studentFound) {
	        System.out.println("Student with username '" + username + "' not enrolled in any courses.");
	    }
	}
	    
	
	public void sortCourses() {
		Main.courses.sort(Comparator.comparingInt(Course::getcurrentStudents)); // Sorts the ArrayList courses by number of enrolled students.
		System.out.println("Courses sorted by number of enrolled students.");
		for (Course course : Main.courses) {
			System.out.println("Course Name: "+course.getcourseName());
            System.out.println("Course ID: "+course.getid());
            System.out.println("Max # Students: "+course.getmaxStudents());
            System.out.println("Current # Students: "+course.getcurrentStudents());
            System.out.println("Enrolled Students: ");
            for (Student student : course.getStudents()) {
            	System.out.println(student.getfirstName()+" "+student.getlastName());
            }
            System.out.println("Instructor: "+course.getinstructor());
            System.out.println("Section: "+course.getsection());
            System.out.println("Location: "+course.getlocation());
            System.out.println("------------------------------------");
		}
	}
	
	public void writeFullCourses(String filepath) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
	        for (Course course : Main.courses) {
	            if (course.getcurrentStudents() == course.getmaxStudents()) {
	                writer.write("Course Name: " + course.getcourseName() + "\n");
	                writer.write("Course ID: " + course.getid() + "\n");
	                writer.write("Max # Students: " + course.getmaxStudents() + "\n");
	                writer.write("Current # Students: " + course.getcurrentStudents() + "\n");
	                writer.write("Instructor: " + course.getinstructor() + "\n");
	                writer.write("Section: " + course.getsection() + "\n");
	                writer.write("Location: " + course.getlocation() + "\n");
	                writer.write("------------------------------------\n");
	            }
	        }
	        System.out.println("Full courses written to file: " + filepath);
	    } catch (IOException e) {
	        System.out.println("An error occurred while writing to the file.");
	        e.printStackTrace();
	    }
	}

}
