import java.util.LinkedList;
import java.util.Scanner;

import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;

/**
 * This is the Controller class, controlling the operations of the system.
 * @author Yicheng Xia
 */
public class Controller {

    /**
     * Main method of Controller, controlling the operations of the system.
     * @param args not used
     */
    public static void main(String[] args) {

        // set up part
        Scanner sc = new Scanner(System.in);
        FileInfoReader fir = new FileInfoReader();
        fir.setUp("src/courseInfo.txt", "src/studentInfo.txt", "src/profInfo.txt", "src/adminInfo.txt");

        while (true) {
            System.out.println("\n---------------------------------");
            System.out.println(" Penn Students Management System");
            System.out.println("---------------------------------");
            System.out.println(" 1 -- Login as a student");
            System.out.println(" 2 -- Login as a professor");
            System.out.println(" 3 -- Login as an admin");
            System.out.println(" 4 -- Quit the system");

            int option = loginOption(sc);
            // Login as a student
            if (option == 1) {
                Student student = studentLogin(fir, sc);
                while (student != null) {
                    System.out.println("\n---------------------------------");
                    System.out.println(" Welcome, " + student.getName() + "!");
                    System.out.println("---------------------------------");
                    System.out.println(" 1 -- View all courses");
                    System.out.println(" 2 -- Add courses to your list");
                    System.out.println(" 3 -- View enrolled courses");
                    System.out.println(" 4 -- Drop courses in your list");
                    System.out.println(" 5 -- View grades");
                    System.out.println(" 6 -- Return to previous menu");
                    int studentOption = studentOption(sc);
                    if (studentOption == 1) {
                        student.printAllCourse(fir);
                        while (true) {
                            System.out.println("Please enter 'q' to quit.");
                            if (sc.nextLine().strip().equals("q")) {
                                break;
                            }
                        }
                        continue;
                    }
                    if (studentOption == 2) {
                        while (true) {
                            System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590',");
                            System.out.println("or enter 'q' to return to the previous menu.");
                            String newCourseID = sc.nextLine().replaceAll("\\s+", "");
                            if (newCourseID.equals("q")) {
                                break;
                            }
                            student.addCourse(fir, newCourseID);
                        }
                        continue;
                    }
                    if (studentOption == 3) {
                        student.printEnrolledCourse(fir);
                        while (true) {
                            System.out.println("Please enter 'q' to quit.");
                            if (sc.nextLine().strip().equals("q")) {
                                break;
                            }
                        }
                        continue;
                    }
                    if (studentOption == 4) {
                        while (true) {
                            student.printEnrolledCourse(fir);
                            System.out.println("Please select the course ID you want to drop from your list, eg. 'CIT590',");
                            System.out.println("or enter 'q' to return to the previous menu.");
                            String courseID = sc.nextLine().replaceAll("\\s+", "");
                            if (courseID.equals("q")) {
                                break;
                            }
                            student.dropCourse(fir, courseID);
                        }
                        continue;
                    }
                    if (studentOption == 5) {
                        student.printGrade(fir);
                        while (true) {
                            System.out.println("Please enter 'q' to quit.");
                            if (sc.nextLine().strip().equals("q")) {
                                break;
                            }
                        }
                        continue;
                    }
                    if (studentOption == 6) {
                        break;
                    }
                }
                continue;
            }

            // Login as a professor
            if (option == 2) {
                Professor professor = profLogin(fir, sc);
                while (professor != null) {
                    System.out.println("\n---------------------------------");
                    System.out.println(" Welcome, " + professor.getName() + "!");
                    System.out.println("---------------------------------");
                    System.out.println(" 1 -- View given courses");
                    System.out.println(" 2 -- View student list of the given course");
                    System.out.println(" 3 -- Return to previous menu");
                    int profOption = profOption(sc);
                    if (profOption == 1) {
                        professor.printGivenCourse(fir);
                        while (true) {
                            System.out.println("Please enter 'q' to quit.");
                            if (sc.nextLine().strip().equals("q")) {
                                break;
                            }
                        }
                        continue;
                    }
                    if (profOption == 2) {
                        professor.printStudentOfGivenCourse(fir, sc);
                        continue;
                    }
                    if (profOption == 3) {
                        break;
                    }
                }
                continue;
            }

            // Login as an admin
            if (option == 3) {
                Admin admin = adminLogin(fir, sc);
                while (admin != null) {
                    System.out.println("\n---------------------------------");
                    System.out.println(" Welcome, " + admin.getName() + "!");
                    System.out.println("---------------------------------");
                    System.out.println(" 1 -- View all courses");
                    System.out.println(" 2 -- Add new courses");
                    System.out.println(" 3 -- Delete courses");
                    System.out.println(" 4 -- Add new professor");
                    System.out.println(" 5 -- Delete professor");
                    System.out.println(" 6 -- Add new student");
                    System.out.println(" 7 -- Delete student");
                    System.out.println(" 8 -- Return to previous menu");
                    int adminOption = adminOption(sc);
                    if (adminOption == 1) {
                        admin.printAllCourse(fir);
                        while (true) {
                            System.out.println("Please enter 'q' to quit.");
                            if (sc.nextLine().strip().equals("q")) {
                                break;
                            }
                        }
                        continue;
                    }
                    if (adminOption == 2) {
                        while (true) {
                            // enter ID
                            System.out.println("Please enter the course ID, or enter 'q' to quit.");
                            String courseID = sc.nextLine().replaceAll("\\s+", "");
                            if (courseID.equals("q")) {
                                break;
                            }
                            courseID = courseID.toUpperCase();
                            if (fir.getCourseMap().keySet().contains(courseID)) {  // check if ID exists
                                System.out.println("Sorry! The course you entered already exists! :(");
                                continue;
                            }
                            // enter name
                            System.out.println("Please enter the course name, or enter 'q' to quit.");
                            String courseName = sc.nextLine().strip();
                            if (courseName.equals("q")) {
                                break;
                            }
                            // enter start time
                            System.out.println("Please enter the course start time, eg. '19:00', or enter 'q' to quit.");
                            String st = sc.nextLine().strip();
                            if (st.equals("q")) {
                                break;
                            }
                            // enter end time
                            System.out.println("Please enter the course end time, eg. '20:00', or enter 'q' to quit.");
                            String et = sc.nextLine().strip();
                            if (et.equals("q")) {
                                break;
                            }
                            // enter date
                            System.out.println("Please enter the course date, eg. 'MW', or enter 'q' to quit.");
                            String ds = sc.nextLine().strip();
                            if (et.equals("q")) {
                                break;
                            }
                            // enter capacity
                            System.out.println("Please enter the course capacity, eg. '72', or enter 'q' to quit.");
                            String capString = sc.nextLine().strip();
                            if (capString.equals("q")) {
                                break;
                            }
                            int capacity = 0;
                            try { // past capacity string to int
                                capacity = Integer.parseInt(capString);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input format! :(");
                                continue;
                            }
                            if (capacity <= 0) {
                                System.out.println("Please enter a positive integer! :(");
                                continue;
                            }
                            // enter lecturer's ID
                            System.out.println("Please enter the course lecturer's ID, eg. '001', or enter 'q' to quit.");
                            String lecturerID = sc.nextLine().strip();
                            if (lecturerID.equals("q")) {
                                break;
                            }
                            admin.addCourse(fir, courseID, courseName, st, et, ds, capacity, lecturerID);
                        }
                        continue;
                    }
                    if (adminOption == 3) {
                        while (true) {
                            System.out.println("Please select the course ID you want to delete from the list, eg. 'CIT590',");
                            System.out.println("or enter 'q' to return to the previous menu.");
                            String courseID = sc.nextLine().replaceAll("\\s+", "");
                            if (courseID.equals("q")) {
                                break;
                            }
                            admin.deleteCourse(fir, courseID);
                        }
                        continue;
                    }
                    if (adminOption == 4) {
                        while (true) {
                            // enter ID
                            System.out.println("Please enter the professor's ID, or enter 'q' to quit.");
                            String profID = sc.nextLine().replaceAll("\\s+", "");
                            if (profID.equals("q")) {
                                break;
                            }
                            for (Professor professor : fir.getProfMap().values()) {  // check if ID exists
                                if (professor.getID().equals(profID)) {
                                    System.out.println("Sorry! The ID already exists! :(");
                                    continue;
                                }
                            }
                            // enter name
                            System.out.println("Please enter the professor's name, or enter 'q' to quit.");
                            String profName = sc.nextLine().strip();
                            // enter username
                            System.out.println("Please enter a username.");
                            String username = sc.nextLine();
                            for (Professor professor : fir.getProfMap().values()) { // check if username exists
                                if (professor.getUsername().equals(username)) {
                                    System.out.println("Sorry! The username already exists! :(");
                                    continue;
                                }
                            }
                            // enter password
                            System.out.println("Please enter a password.");
                            String password = sc.nextLine();
                            admin.addProf(fir, profID, profName, username, password);
                        }
                        continue;
                    }
                    if (adminOption == 5) {
                        while (true) {
                            System.out.println("Please enter the professor's ID, or enter 'q' to return to the previous menu.");
                            String profID = sc.nextLine();
                            if (profID.equals("q")) {
                                break;
                            }
                            admin.deleteProf(fir, profID);
                        }
                        continue;
                    }
                    if (adminOption == 6) {
                        while (true) {
                            // enter ID
                            System.out.println("Please enter the student's ID, or enter 'q' to quit.");
                            String studentID = sc.nextLine().replaceAll("\\s+", "");
                            if (studentID.equals("q")) {
                                break;
                            }
                            for (Student student : fir.getStudentMap().values()) {
                                if (student.getID().equals(studentID)) { // check if ID exists
                                    System.out.println("Sorry! The ID already exists! :(");
                                    continue;
                                }
                            }
                            // enter name
                            System.out.println("Please enter the student's name, or enter 'q' to quit.");
                            String studentName = sc.nextLine().strip();
                            if (studentName.equals("q")) {
                                break;
                            }
                            // enter username
                            System.out.println("Please enter a username.");
                            String username = sc.nextLine();
                            for (Student student : fir.getStudentMap().values()) {
                                if (student.getUsername().equals(username)) { // check if username exists
                                    System.out.println("Sorry! The username already exists! :(");
                                    continue;
                                }
                            }
                            // enter password
                            System.out.println("Please enter a password.");
                            String password = sc.nextLine();
                            // enter grade
                            LinkedList<LinkedList<String>> list = new LinkedList<>();
                            while (true) {
                                System.out.println("Please enter ID of a course which this student already took, one in a time.");
                                System.out.println("Enter 'q' to quit, 'n' to stop adding");
                                String courseID = sc.nextLine().replaceAll("\\s+", "");
                                if (courseID.equals("q")) {
                                    list = new LinkedList<>();
                                    break;
                                }
                                if (courseID.equals("n")) {
                                    break;
                                }
                                LinkedList<String> subList = new LinkedList<>();
                                courseID = courseID.toUpperCase();
                                subList.add(courseID);
                                System.out.println("Please enter the grade, eg. 'A'.");
                                String grade = sc.nextLine().replaceAll("\\s+", "").toUpperCase();
                                subList.add(grade);
                                list.add(new LinkedList<>(subList));
                            }
                            admin.addStudent(fir, studentID, studentName, username, password, list);
                        }
                        continue;
                    }
                    if (adminOption == 7) {
                        while (true) {
                            System.out.println("Please enter the student's ID, or enter 'q' to return to the previous menu.");
                            String studentID = sc.nextLine();
                            if (studentID.equals("q")) {
                                break;
                            }
                            admin.deleteStudent(fir, studentID);
                        }
                        continue;
                    }
                    if (adminOption == 8) {
                        break;
                    }
                }
                continue;
            }

            // Quit the system
            if (option == 4) {
                break;
            }
        }

        // end part
        System.out.println("\n---------------------------------");
        System.out.println("Goodbye! Have a nice day! :)");
        sc.close();
    }

    /**
     * For user to choose options to login.
     * @param sc to take user inputs
     * @return int from 1 to 4
     */
    private static int loginOption(Scanner sc) {
        System.out.println("Please enter your option, eg. '1'.");
        try {
            int option = Integer.parseInt(sc.nextLine().strip());
            if (option < 1 || option > 4) {
                System.out.println("Invalid input number! :(");
                return loginOption(sc);
            } else {
                return option;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! :(");
            return loginOption(sc); // return itself until getting a valid input
        }
    }

    /**
     * For student to login.
     * @param fir FileInfoReader instance
     * @param sc to take user inputs
     * @return Student instance if successfully logged in, otherwise null
     */
    private static Student studentLogin(FileInfoReader fir, Scanner sc) {
        System.out.println("Please enter your username, or type 'q' to quit.");
        String username = sc.nextLine();
        if (username.equals("q")) {
            return null;
        }
        System.out.println("Please enter your password, or type 'q' to quit.");
        String password = sc.nextLine();
        if (password.equals("q")) {
            return null;
        }
        // check username and password
        for (Student student : fir.getStudentMap().values()) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                return student;
            }
        }
        System.out.println("Incorrect username or password! :(");
        return null;
    }

    /**
     * For student to choose options from 1 to 6.
     * @param sc to take user inputs
     * @return int from 1 to 6
     */
    private static int studentOption(Scanner sc) {
        System.out.println("Please enter your option, eg. '1'.");
        try {
            int option = Integer.parseInt(sc.nextLine().strip());
            if (option < 1 || option > 6) {
                System.out.println("Invalid input number! :(");
                return loginOption(sc);
            } else {
                return option;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! :(");
            return studentOption(sc); // return itself until getting a valid input
        }
    }

    /**
     * For professor to login.
     * @param fir FileInfoReader instance
     * @param sc to take user inputs
     * @return Professor instance if successfully logged in, otherwise null
     */
    private static Professor profLogin(FileInfoReader fir, Scanner sc) {
        System.out.println("Please enter your username, or type 'q' to quit.");
        String username = sc.nextLine();
        if (username.equals("q")) {
            return null;
        }
        System.out.println("Please enter your password, or type 'q' to quit.");
        String password = sc.nextLine();
        if (password.equals("q")) {
            return null;
        }
        // check username and password
        for (Professor professor : fir.getProfMap().values()) {
            if (professor.getUsername().equals(username) && professor.getPassword().equals(password)) {
                return professor;
            }
        }
        System.out.println("Incorrect username or password! :(");
        return null;
    }

    /**
     * For professor to choose options from 1 to 3.
     * @param sc to take user inputs
     * @return int from 1 to 3
     */
    private static int profOption(Scanner sc) {
        System.out.println("Please enter your option, eg. '1'.");
        try {
            int option = Integer.parseInt(sc.nextLine().strip());
            if (option < 1 || option > 3) {
                System.out.println("Invalid input number! :(");
                return loginOption(sc);
            } else {
                return option;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! :(");
            return profOption(sc); // return itself until getting a valid input
        }
    }

    /**
     * For admin to login.
     * @param fir FileInfoReader instance
     * @param sc to take user inputs
     * @return Admin instance if successfully logged in, otherwise null
     */
    private static Admin adminLogin(FileInfoReader fir, Scanner sc) {
        System.out.println("Please enter your username, or type 'q' to quit.");
        String username = sc.nextLine();
        if (username.equals("q")) {
            return null;
        }
        System.out.println("Please enter your password, or type 'q' to quit.");
        String password = sc.nextLine();
        if (password.equals("q")) {
            return null;
        }
        // check username and password
        for (Admin admin : fir.getAdminMap().values()) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        System.out.println("Incorrect username or password! :(");
        return null;
    }

    /**
     * For admin to choose options from 1 to 8.
     * @param sc to take user inputs
     * @return int from 1 to 8
     */
    private static int adminOption(Scanner sc) {
        System.out.println("Please enter your option, eg. '1'.");
        try {
            int option = Integer.parseInt(sc.nextLine().strip());
            if (option < 1 || option > 8) {
                System.out.println("Invalid input number! :(");
                return loginOption(sc);
            } else {
                return option;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! :(");
            return adminOption(sc); // return itself until getting a valid input
        }
    }
}
