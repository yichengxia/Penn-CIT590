package courses;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the Course class.
 * @author Yicheng Xia
 */
public class Course {

    /**
     * The ID of Course.
     */
    private String ID;

    /**
     * The name of Course.
     */
    private String name;

    /**
     * The lecturer name of Course.
     */
    private String lecturer;

    /**
     * The days of Course.
     */
    private String days;

    /**
     * The start time of Course.
     */
    private String startTime;

    /**
     * The end time of Course.
     */
    private String endTime;

    /**
     * The capacity of Course.
     */
    private int capacity;

    /**
     * The number of student of Course.
     */
    private int student;

    /**
     * The student ID Set of Course.
     */
    private Set<String> studentIDSet;

    /**
     * The constructor of Course.
     * @param ID ID of Course
     * @param name name of Course
     * @param lecturer lecturer name of Course
     * @param days  days of Course
     * @param startTime start time of Course
     * @param endTime end time of Course
     * @param capacity capacity of Course
     */
    public Course(String ID, String name, String lecturer, String days, String startTime, String endTime, int capacity) {
        this.ID = ID;
        this.name = name;
        this.lecturer = lecturer;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.student = 0; // initiate with 0 students enrolled
        this.studentIDSet = new HashSet<>(); // initiate with empty set
    }

    /**
     * Gets ID of Course.
     * @return ID of Course
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Gets name of Course.
     * @return name of Course
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets lecturer name of Course.
     * @return lecturer name of Course
     */
    public String getLecturer() {
        return this.lecturer;
    }

    /**
     * Gets days of Course.
     * @return days of Course
     */
    public String getDays() {
        return this.days;
    }

    /**
     * Gets start time of Course.
     * @return start time of Course
     */
    public String getStartTime() {
        return this.startTime;
    } 

    /**
     * Gets end time of Course.
     * @return end time of Course
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * Gets capacity of Course.
     * @return capacity of Course
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Gets enrolled number of students of Course.
     * @return enrolled number of students of Course
     */
    public int getStudent() {
        return this.student;
    }

    /**
     * Gets student ID set of Course.
     * @return student ID set of Course
     */
    public Set<String> getStudentIDSet() {
        return this.studentIDSet;
    }

    /**
     * Add student ID to Course.
     * @param studentID student ID to add
     */
    public void addStudentID(String studentID) {
        if (this.studentIDSet.add(studentID)) {
            this.student++;
        }
    }

    /**
     * Remove student ID from Course.
     * @param studentID student ID to remove
     */
    public void removeStudentID(String studentID) {
        if (this.studentIDSet.remove(studentID)) {
            this.student--;
        }
    }

    /**
     * Print the course.
     */
    public void printCourse() {
        System.out.println(this.ID + "|" + this.name + ", " + this.startTime + "-" + this.endTime + " on " +
            this.days + ", with course capacity: " + this.capacity + ", students: " +
            this.student + ", lecturer: Professor " + this.lecturer);
	}
}
