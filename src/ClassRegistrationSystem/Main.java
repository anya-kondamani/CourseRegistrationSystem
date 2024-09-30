package ClassRegistrationSystem;
import java.io.*;
import java.util.*;

public class Main {
	public static ArrayList<Course> courses = new ArrayList<>(); // Defining course list that can be referenced and updated.
	public static ArrayList<Student> students = new ArrayList<>(); // Defining student list that can be referenced and updated.
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		boolean dataLoaded = false;
		
		// Deserialization
	    try { // First attempting to find serialized data.
	        FileInputStream coursesIn = new FileInputStream("courses.ser");
	        ObjectInputStream cin = new ObjectInputStream(coursesIn);
	        courses = (ArrayList<Course>) cin.readObject(); // Deserializes course list.
	        cin.close();
	        coursesIn.close();
	        
	        FileInputStream studentsIn = new FileInputStream("students.ser");
	        ObjectInputStream sin = new ObjectInputStream(studentsIn);
	        students = (ArrayList<Student>) sin.readObject(); // Deserializes studentt list.
            sin.close();
            studentsIn.close();
	        
	        dataLoaded = true;
	        System.out.println("Previous data loaded from serialization.");
	    } catch (IOException | ClassNotFoundException e) { 
	        System.out.println("No serialized data found, loading data from CSV.");
	    }
	    
		if (!dataLoaded) {  // Data is loaded from the CSV iff serialized data not found.
			String filepath = "MyUniversityCourses.csv";
			String line = "";
			try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
	            br.readLine();
	            while ((line = br.readLine()) != null) {
	            	String[] courseInfo = line.split(",");
	            	String courseName = courseInfo[0];
	            	String courseId = courseInfo[1];
	            	int maxStudents = Integer.parseInt(courseInfo[2]);
	            	int currentStudents = Integer.parseInt(courseInfo[3]);
	            	String instructor = courseInfo[5];
	            	int sectionNumber = Integer.parseInt(courseInfo[6]);
	            	String location = courseInfo[7];
	            	
	            	ArrayList<Student> studentList = new ArrayList<>();
	            	
	            	Course course = new Course(
	            			courseName, // Course name.
	            			courseId, // Course ID.
	            			maxStudents, // Maximum number of students that may enroll.
	            			currentStudents, // Number of students enrolled.
	            			studentList, // List of current students enrolled.
	            			instructor, // Instructor.
	            			sectionNumber, // Section number.
	            			location // Location.
	            			);
	            	courses.add(course); // Adding Course object to list of courses.
	            	
	            }
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Scanner scanner = new Scanner(System.in);
		boolean systemRunning = true;
		
		System.out.println("Welcome to the Course Registration System!");
		
		while (systemRunning) {
			System.out.println("Please enter 1 if you are an administrator and 2 if you are a student. Enter 0 to exit the system.");
			
			while (!scanner.hasNextInt()) {
	            System.out.println("Error: Invalid input. Please enter a number.");
	            scanner.next(); // Clear invalid input
	        }

	        int option = scanner.nextInt();
	        scanner.nextLine(); 
			
			switch (option) {
				case 0: 
					try {
			            FileOutputStream coursesOut = new FileOutputStream("courses.ser");
			            ObjectOutputStream cout = new ObjectOutputStream(coursesOut);
			            cout.writeObject(courses); // Serializes Course List.
			            cout.close();
			            coursesOut.close();
			            
			            FileOutputStream studentsOut = new FileOutputStream("students.ser");
			            ObjectOutputStream sout = new ObjectOutputStream(studentsOut);
			            sout.writeObject(students); // Serializes Student List.
			            sout.close();
			            studentsOut.close();
			            
			            System.out.println("Session data saved successfully.");
			        } catch (IOException i) {
			            i.printStackTrace();
			            return;
			        }
					
					System.out.println("Exiting System");
					systemRunning=false;
					break;
				case 1:
					Admin admin = new Admin("Mr.", "Professor");  
					System.out.println("Enter Admin Username:");
		            String usernameAdmin = scanner.next();
		            System.out.println("Enter Admin Password:");
		            String passwordAdmin = scanner.next();
					if (admin.login(usernameAdmin,passwordAdmin)) {
						System.out.println("Login Successful.");
						Administration(admin,courses,students,scanner);
					}
					else {
						System.out.println("Wrong username or password. Login Unsuccessful.");
					}
					break;
				case 2:
					Student student = null;
					System.out.println("Enter Student Username:");
		            String usernameStudent = scanner.next();
		            System.out.println("Enter Student Password:");
		            String passwordStudent = scanner.next();
		            for (Student thisStudent : students) {
		            	if (thisStudent.getUsername().equals(usernameStudent)) {
		            		student = thisStudent;
		            		break;
		            	}
		            }
		            if (student == null) {
		                System.out.println("These credentials do not exist. Await account creation by Admin.");
		            } else if (student.login(usernameStudent, passwordStudent)) {
		                System.out.println("Login Successful.");
		                UniversityStudent(student, courses, students, scanner);
		            } else {
		                System.out.println("Wrong username or password. Login Unsuccessful.");
		            }
		            break;
		            
				default:
					System.out.println("Invalid Choice.");
			}
		}
	}	


	public static void Administration(Admin admin, ArrayList<Course> courses, ArrayList<Student> students, Scanner scanner) {
		boolean isLoggedIn = true;
		
		while (isLoggedIn) {
		// Tasks that an administrator can complete listed below.	
	    System.out.println("Admin Actions:");
	    System.out.println("1. View All Courses");
	    System.out.println("2. View Full Courses");
	    System.out.println("3. Create Course");
	    System.out.println("4. Delete Course");
	    System.out.println("5. Edit Course");
	    System.out.println("6: Create a Student Account");
	    System.out.println("7: Get Course Info");
	    System.out.println("8: Register a Student");
	    System.out.println("9: View Course Classlist");
	    System.out.println("10: View Student's Courses");
	    System.out.println("11: Sort Courses");
	    System.out.println("12: Save Full Course Info to File");
	    System.out.println("13: Logout");
	    
	    int choice = -1; 

        while (!scanner.hasNextInt()) { // Ensures that input is of type int.
            System.out.println("Error: Invalid input. Please enter a number.");
            scanner.next(); 
        }
        
        choice = scanner.nextInt();
        scanner.nextLine(); 
	    
	    switch (choice) {
	        case 1:
	            admin.viewCourses();
	            System.out.println();
	            break;
	        case 2:
	            admin.fullCourses();
	            System.out.println();
	            break;
	        case 3:
	        	System.out.println("Enter Course Name:");
	        	String courseName = scanner.nextLine();
	        	System.out.println("Enter Course ID:");
	        	String id = scanner.next();
	        	System.out.println("Enter Max # of students:");
	        	int maxStudents = scanner.nextInt();
	        	System.out.println("Enter Instructor Name:");
	        	scanner.nextLine();
	        	String instructor = scanner.nextLine();
	        	System.out.println("Enter Section #:");
	        	int section = scanner.nextInt();
	        	scanner.nextLine();
	        	System.out.println("Enter Location:");
	        	String location = scanner.nextLine();
	            admin.createCourse(courseName,id,maxStudents,instructor,section,location);
	            break;
	        case 4:
	        	System.out.println("Enter Course ID: ");
	        	String deleteId = scanner.next();
	        	System.out.println("Enter Course Name: ");
	        	scanner.nextLine();
	        	String deleteCourse = scanner.nextLine();
	        	System.out.println("Enter Course Section #: ");
	        	int deleteSection = scanner.nextInt();
	        	boolean deleteFound = false;
	        	for (Course course : courses) {
	        		if (course.getid().equals(deleteId)&&course.getcourseName().equals(deleteCourse)&&course.getsection()==deleteSection) {
	        			admin.deleteCourse(deleteId,deleteCourse,deleteSection);
	        			deleteFound = true;
	        	        break;
	        		}
	        	}
	        	if (!deleteFound) {
        			System.out.println("Course not found.");
        		}
	            break;
	        case 5:
	        	System.out.println("Enter Course ID: ");
	        	String editID = scanner.next();
	        	System.out.println("Enter Course Name: ");
	        	scanner.nextLine();
	        	String editCourse = scanner.nextLine();
	        	System.out.println("Enter Course Section #: ");
	        	int editSection = scanner.nextInt();
	        	boolean editFound = false;
	        	for (Course course : courses ) {
	        		if (course.getid().equals(editID) && course.getcourseName().equals(editCourse) && course.getsection()==editSection) {
	        			admin.editCourse(course);
	        			editFound=true;
	        			break;
	        		}
	        	}
	        	if (!editFound) System.out.println("Course not found.");
	        	break;
	        case 6:
	        	System.out.println("Enter Student's Username:");
	        	String username = scanner.next();
	        	System.out.println("Enter Student's Password:");
	        	String password = scanner.next();
	        	System.out.println("Do you want to provide the student's first and last name? Enter Y for Yes or N for No:");
	        	String response = scanner.next();
	        	if (response.equalsIgnoreCase("Y")) {
	        		// Admin wants to provide names
	        	    System.out.println("Enter Student's First Name:");
	        	    String firstName = scanner.next();
	        	    System.out.println("Enter Student's Last Name:");
	        	    String lastName = scanner.next();
	        	    admin.createStudent(username, password, firstName, lastName);
	        	} else {
	        	    // Admin does not want to provide names, so calling the overloaded method.
	        	    admin.createStudent(username, password);
	        	}
	        	break;
	        case 7:
	        	System.out.println("Enter Course ID: ");
	        	String infoId = scanner.next();
	        	System.out.println("Enter Course Section #: ");
	        	int infoSection = scanner.nextInt();
	        	admin.courseInfo(infoId, infoSection);
	        	break;
	        case 8:
	        	System.out.println("Enter Student's Username: ");
	        	String registerUsername = scanner.next();
	        	System.out.println("Enter Course ID: ");
	        	String registerId = scanner.next();
	        	System.out.println("Enter Course Section #: ");
	        	int registerSection = scanner.nextInt();
	        	admin.registerStudent(registerUsername, registerId, registerSection);
	        	break;
	        case 9:
	        	System.out.println("Enter Course ID: ");
	        	String listId = scanner.next();
	        	System.out.println("Enter Course Section #: ");
	        	int listSection = scanner.nextInt();
	        	admin.viewStudents(listId,listSection);
	        	break;
	        case 10:
	        	System.out.println("Enter Student's Username: ");
	        	String studentUsername = scanner.next();
	        	admin.viewCoursesByStudent(studentUsername);
	        	break;
	        case 11:
	        	admin.sortCourses();
	        	break;
	        case 12:
	        	System.out.println("Enter Filepath: ");
	        	String fullFilepath = scanner.next();
	        	admin.writeFullCourses(fullFilepath);
	        	break;
	        case 13:
	        	System.out.println("Returning to Main Menu.");
	        	isLoggedIn=false;
	        	break;
	        default:
	            System.out.println("Invalid choice.");
	            break;
	    	}
		}
	}
	
	public static void UniversityStudent(Student student, ArrayList<Course> courses, ArrayList<Student> students, Scanner scanner) {
		boolean isLoggedIn = true;
		
		while (isLoggedIn) {
		// Tasks that a student can complete listed below.
		System.out.println("Student Actions:");
	    System.out.println("1. View All Courses");
	    System.out.println("2. View Open Courses");
	    System.out.println("3. View My Courses");
	    System.out.println("4. Add Course");
	    System.out.println("5. Drop Course");
	    System.out.println("6: Change Password");
	    System.out.println("7: Logout");
	    
	    int choice = -1;

        while (!scanner.hasNextInt()) { // Ensures that input is of type int.
            System.out.println("Error: Invalid input. Please enter a number.");
            scanner.next(); 
        }
        
        choice = scanner.nextInt();
        scanner.nextLine(); 

	    switch (choice) {
	        case 1:
	            student.viewCourses();
	            System.out.println();
	            break;
	        case 2:
	            student.openCourses();
	            System.out.println();
	            break;
	        case 3:
	        	student.myCourses();
	        	System.out.println();
	            break;
	        case 4:
	        	System.out.println("Enter Course ID: ");
	        	String addId = scanner.nextLine();
	        	System.out.println("Enter Course Name: ");
	        	String addCourse = scanner.nextLine();
	        	System.out.println("Enter Course Section #");
	        	int addSection = scanner.nextInt();
	        	student.addCourse(addId,addCourse,addSection);
	            break;
	        case 5:
	        	System.out.println("Enter Course ID: ");
	        	String dropId = scanner.nextLine();
	        	System.out.println("Enter Course Name: ");
	        	String dropCourse = scanner.nextLine();
	        	System.out.println("Enter Course Section #");
	        	int dropSection = scanner.nextInt();
	        	student.dropCourse(dropId,dropCourse,dropSection);
	        	break;
	        case 6:
	        	System.out.println("Enter Old Password: ");
	        	String oldPass = scanner.nextLine();
	        	System.out.println("Enter New Password: ");
	        	String newPass = scanner.nextLine();
	        	student.changePass(oldPass, newPass);
	        	break;
	        case 7:
	        	System.out.println("Returning to Main Menu.");
	        	isLoggedIn=false;
	        	break;
	        default:
	            System.out.println("Invalid choice.");
	            break;
	    	}
	    }
	}
}
