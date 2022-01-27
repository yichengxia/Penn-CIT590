package roles;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import files.FileInfoReader;

/**
 * This is the Professor class, a subclass of User.
 * @author Yicheng Xia
 */
public class Professor extends User {

    /**
     * The set of given course ID.
     */
    private Set<String> courseIDSet;

    /**
     * The constructor of Professor.
     * @param profID ID of Professor
     * @param profName name of Professor
     * @param username username of Professor
     * @param password password of Professor
     */
    public Professor(String profID, String profName, String username, String password) {
        super(profID, profName, username, password);
        this.courseIDSet = new HashSet<>(); // initiate with empty set
    }

    /**
     * Get the set of course ID.
     * @return set of course ID
     */
    public Set<String> getCourseIDSet() {
        return this.courseIDSet;
    }

    /**
     * Print given courses of Professor.
     * @param fir FileInfoReader instance
     */
    public void printGivenCourse(FileInfoReader fir) {
        System.out.println("\n---------The course list---------");
        for (String courseID : this.courseIDSet) {
			fir.getCourseMap().get(courseID).printCourse();
		}
    }

    /**
     * Print given courses of a Student.
     * @param fir FileInfoReader instance
     * @param sc to take user inputs
     */
    public void printStudentOfGivenCourse(FileInfoReader fir, Scanner sc) {
        printGivenCourse(fir);
        System.out.println("Please enter the courseID, eg. 'CIS519', or type 'q' to quit.");
        String courseID = sc.nextLine().replaceAll("\\s+", "");
        if (courseID.equals("q")) {
            return;
        }
        courseID = courseID.toUpperCase();
        // return if the course is not taught by the professor
        if (!this.courseIDSet.contains(courseID)) {
            System.out.println("Sorry! The course you selected is not in your list! :(");
            printStudentOfGivenCourse(fir, sc);
            return;
        }
        System.out.println("Students in your course " + courseID + " " + fir.getCourseMap().get(courseID).getName() + ":");
        for (String studentID : fir.getCourseMap().get(courseID).getStudentIDSet()) {
            System.out.println(studentID + " " + fir.getStudentMap().get(studentID).getName());
        }
        // continue to view students of given courses
        printStudentOfGivenCourse(fir, sc);
    }
}
