package roles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import courses.Course;
import files.FileInfoReader;

/**
 * This is the Student class, a subclass of User.
 * @author Yicheng Xia
 */
public class Student extends User {
    
    /**
     * The list of grades.
     */
    private LinkedList<LinkedList<String>> grade;

    /**
     * The set of enrolled course ID.
     */
    private Set<String> courseIDSet;

    /**
     * The constructor of Student.
     * @param studentID ID of Student
     * @param studentName name of Student
     * @param username username of Student
     * @param password password of Student
     * @param grade grades of Student
     */
    public Student(String studentID, String studentName, String username, String password, LinkedList<LinkedList<String>> grade) {
        super(studentID, studentName, username, password);
        this.grade = new LinkedList<>(grade); // initiate with empty list
        this.courseIDSet = new HashSet<>(); // initiate with empty set
    }

    /**
     * Get the list of grades.
     * @return list of grades
     */
    public LinkedList<LinkedList<String>> getGrade() {
        return this.grade;
    }

    /**
     * Get the set of course ID.
     * @return set of course ID
     */
    public Set<String> getCourseIDSet() {
        return this.courseIDSet;
    }

    /**
     * Print all courses in FileInfoReader.
     * @param fir FileInfoReader instance
     */
    public void printAllCourse(FileInfoReader fir) {
        System.out.println("\n---------The course list---------");
		for (Course course : fir.getCourseMap().values()) {
			course.printCourse();
		}
	}

    /**
     * Add courses for Student.
     * @param fir FileInfoReader instance
     * @param newCourseID new course ID to add
     */
    public void addCourse(FileInfoReader fir, String newCourseID) {
        newCourseID = newCourseID.toUpperCase();
        // return if new course already contained
        if (this.courseIDSet.contains(newCourseID)) {
            System.out.println("Sorry! The course you selected is already in your list! :(");
            return;
        }
        // return if new course not in key set of courseMap
        if (!fir.getCourseMap().keySet().contains(newCourseID)) {
            System.out.println("Sorry! The course you selected does not exist! :(");
            return;
        }
        // return if course is full
        if (fir.getCourseMap().get(newCourseID).getStudent() >= fir.getCourseMap().get(newCourseID).getCapacity()) {
            System.out.println("Sorry! The course you selected is already full! :(");
            return;
        }
        // create set of new days
        Character[] newDays = fir.getCourseMap().get(newCourseID).getDays().chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        Set<Character> newDaysSet = new HashSet<>(Arrays.asList(newDays));
        // compare with courses already enrolled
        for (String courseID : this.courseIDSet) {
            // create set of days of courses already enrolled
            String[] startTime = fir.getCourseMap().get(courseID).getStartTime().split(":");
            String[] endTime = fir.getCourseMap().get(courseID).getEndTime().split(":");
            Character[] days = fir.getCourseMap().get(courseID).getDays().chars().mapToObj(c -> (char) c).toArray(Character[]::new);
            Set<Character> daysSet = new HashSet<>(Arrays.asList(days));
            for (Character newDay : newDaysSet) {
                // if they are on the same day(s)
                if (daysSet.contains(newDay)) {
                    String[] newStartTime = fir.getCourseMap().get(newCourseID).getStartTime().split(":");
                    String[] newEndTime = fir.getCourseMap().get(newCourseID).getEndTime().split(":");
                    // compare absolute time of startTime, endTime, newStartTime, and newEndTime
                    if ((Integer.parseInt(startTime[0]) * 60 + Integer.parseInt(startTime[1]) <
                        Integer.parseInt(newEndTime[0]) * 60 + Integer.parseInt(newEndTime[1])) &&
                        (Integer.parseInt(endTime[0]) * 60 + Integer.parseInt(endTime[1]) >
                        Integer.parseInt(newStartTime[0]) * 60 + Integer.parseInt(newStartTime[1]))) {
                            System.out.println("Sorry! The course you selected has time conflict with " + courseID +
                                " " + fir.getCourseMap().get(courseID).getName() + "! :(");
                            return; // return if finding any conflicts
                    }
                }
            }
        }
        this.courseIDSet.add(newCourseID);
        fir.getCourseMap().get(newCourseID).addStudentID(this.getID());
        System.out.println("Course added successfully! :)");
    }

    /**
     * Print enrolled courses of Student.
     * @param fir FileInfoReader instance
     */
    public void printEnrolledCourse(FileInfoReader fir) {
        System.out.println("\n---------The course list---------");
        for (String courseID : this.courseIDSet) {
			fir.getCourseMap().get(courseID).printCourse();
		}
    }

    /**
     * Drop courses for Student.
     * @param fir FileInfoReader instance
     * @param courseID course ID to drop
     */
    public void dropCourse(FileInfoReader fir, String courseID) {
        courseID = courseID.toUpperCase();
        if (!this.courseIDSet.contains(courseID)) {
            System.out.println("Sorry! The course you selected is not in your list! :(");
            return;
        }
        // update student
        courseIDSet.remove(courseID);
        // update course
        fir.getCourseMap().get(courseID).removeStudentID(this.getID());
        System.out.println("Course dropped successfully! :)");
    }

    /**
     * Print grades of Student.
     * @param fir FileInfoReader instance
     */
    public void printGrade(FileInfoReader fir) {
        for (int i = 0; i < this.grade.size(); i++) {
            System.out.println("Grade of " + this.grade.get(i).get(0) + " " + fir.getCourseMap().get(this.grade.get(i).get(0)).getName() +
                ": " +  this.grade.get(i).get(1));
        }
    }
}
