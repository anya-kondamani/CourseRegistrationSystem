# Course Registration System

### Homework 1; CS102; Anya Kondamani
This Java program offers a simplistic course registration with two user types: Administrator and Student. The Administrator has pre-set credentials for logging in. The username is "Admin" and the password is "Admin001". Currently a Student may only be created by an Administrator and may either have full login credentials such as first name, last name, username, and password or may only have a username and password.

### Operating the Program
To start the program run the Main file. The user is then prompted to choose Admin or User login. If using program for the first time, it may be wise to first login as an Admin, create a Student, and then login as a Student if needed.

Once logged in as a user type, you will be prompted to choose from a list of tasks/actions. Each action is unique and may further prompt you for information which can be inputted via console. To logout as a user, select the listed option. You will then be returned to the Main Menu.

From the Main Menu, you may choose to login again or exit the program. Opting to exit the program will physically stop the program and it must be re-run in order to access again.

This program uses serialization! Data from each run can only be serialized if the user exits the program (input 0 in Main Menu), simply stopping the program will not serialize course or student data. Data is automatically deserialized upon program start (if it exists, of course).

Important note: For writing to a file as an Admin, the file does not need to exist beforehand. However, if running in Eclipse, the new file does not immediately display and the user may need to refresh the project in order to view it. 

### Contact Info
Please do not hesitate to contact me via email at ak8699@nyu.edu with any questions or concerns about this program!!