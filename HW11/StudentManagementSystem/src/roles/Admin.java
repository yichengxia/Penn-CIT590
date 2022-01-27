package roles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import courses.Course;
import files.FileInfoReader;

/**
 * This is the Admin class, a subclass of User.
 * @author Yicheng Xia
 */
public class Admin extends User {
    
    /**
     * The constructor of Admin.
     * @param adminID ID of Admin
     * @param adminName name of Admin
     * @param username username of Admin
     * @param password password of Admin
     */
    public Admin(String adminID, String adminName, String username, String password) {
        super(adminID, adminName, username, password);
    }

    /**
     * Print all courses.
     * @param fir FileInfoReader instance
     */
    public void printAllCourse(FileInfoReader fir) {
        System.out.println("\n---------The course list---------");
		for (Course course : fir.getCourseMap().values()) {
			course.printCourse();
		}
	}

    /**
     * Add courses to FileInfoReader.
     * @param fir FileInfoReader instance
     * @param courseID course ID to add
     * @param courseName course name to add
     * @param st start time to add
     * @param et end time to add
     * @param ds dates to add
     * @param capacity int capacity to add
     * @param lecturerID lecturer ID of the course to add
     */
    public void addCourse(FileInfoReader fir, String courseID, String courseName, String st, String et, String ds,
        int capacity, String lecturerID) {
        String[] startTime = st.split(":");
        String[] endTime = et.split(":");
        for (Professor professor : fir.getProfMap().values()) {
            if (professor.getID().equals(lecturerID)) {
                Character[] days = ds.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
                Set<Character> daysSet = new HashSet<>(Arrays.asList(days));
                for (String newCourseID : professor.getCourseIDSet()) {
                    String[] newStartTime = fir.getCourseMap().get(newCourseID).getStartTime().split(":");
                    String[] newEndTime = fir.getCourseMap().get(newCourseID).getEndTime().split(":");
                    Character[] NewDays = fir.getCourseMap().get(newCourseID).getDays().chars().mapToObj(c -> (char) c).
                        toArray(Character[]::new);
                    Set<Character> newDaysSet = new HashSet<>(Arrays.asList(NewDays));
                    for (Character newDay : newDaysSet) {
                        if (daysSet.contains(newDay)) {
                            if ((Integer.parseInt(startTime[0]) * 60 + Integer.parseInt(startTime[1]) <
                                Integer.parseInt(newEndTime[0]) * 60 + Integer.parseInt(newEndTime[1])) &&
                                (Integer.parseInt(endTime[0]) * 60 + Integer.parseInt(endTime[1]) >
                                Integer.parseInt(newStartTime[0]) * 60 + Integer.parseInt(newStartTime[1]))) {
                                    System.out.println("Sorry! The course you selected has time conflict with " +
                                        newCourseID + " " + fir.getCourseMap().get(newCourseID).getName() + "! :(");
                                    return;
                            }
                        }
                    }
                }
                Course newCourse = new Course(courseID, courseName, professor.getName(), ds, st, et, capacity);
                professor.getCourseIDSet().add(courseID);
                fir.getCourseMap().put(courseID, newCourse);
                System.out.print("Successfully added course: ");
                newCourse.printCourse();
                return;
            }
        }
        System.out.println("Sorry! This lecturer does not exist! Please add first!");
    }

    /**
     * Delete courses from FileInfoReader.
     * @param fir FileInfoReader instance
     * @param courseID course ID to delete
     */
    public void deleteCourse(FileInfoReader fir, String courseID) {
        courseID = courseID.toUpperCase();
        if (!fir.getCourseMap().keySet().contains(courseID)) {
            System.out.println("Sorry! The course you selected does not exist! :(");
            return;
        }
        fir.getProfMap().get(fir.getCourseMap().get(courseID).getLecturer()).getCourseIDSet().remove(courseID);
        fir.getCourseMap().remove(courseID);
        for (Student student : fir.getStudentMap().values()) {
            student.getCourseIDSet().remove(courseID);
        }
        System.out.println("Course deleted successfully! :)");
    }

    /**
     * Add professors to FileInfoReader.
     * @param fir FileInfoReader instance
     * @param profID professor ID to add
     * @param profName professor name to add
     * @param username username to add
     * @param password password to add
     */
    public void addProf(FileInfoReader fir, String profID, String profName, String username, String password) {
        Professor newProfessor = new Professor(profID, profName, username, password);
        fir.getProfMap().put(profName, newProfessor);
        System.out.println("Successfully added new professor: " + newProfessor.getID() + " " + newProfessor.getName());
    }

    /**
     * Delete professors from FileInfoReader.
     * @param fir FileInfoReader instance
     * @param profID professor ID to delete
     */
    public void deleteProf(FileInfoReader fir, String profID) {
        for (Professor professor : fir.getProfMap().values()) {
            if (professor.getID().equals(profID)) {
                for (String courseID : professor.getCourseIDSet()) {
                    for (Student student : fir.getStudentMap().values()) {
                        student.getCourseIDSet().remove(courseID);
                    }
                    fir.getCourseMap().remove(courseID);
                }
                fir.getProfMap().remove(professor.getName());
                System.out.println("Professor deleted successfully! :)");
                return;
            }
        }
        System.out.println("Sorry! The professor you entered does not exist! :(");
    }

    /**
     * Add students to FileInfoReader.
     * @param fir FileInfoReader instance
     * @param studentID student ID to add
     * @param studentName student name to add
     * @param username username to add
     * @param password password to add
     * @param list list of grades to add
     */
    public void addStudent(FileInfoReader fir, String studentID, String studentName, String username, String password,
        LinkedList<LinkedList<String>> list) {
        Student newStudent = new Student(studentID, studentName, username, password, list);
        fir.getStudentMap().put(studentID, newStudent);
        System.out.println("Successfully added new student: " + newStudent.getID() + " " + newStudent.getName());
    }

    /**
     * Delete students from FileInfoReader.
     * @param fir FileInfoReader instance
     * @param studentID student ID to delete
     */
    public void deleteStudent(FileInfoReader fir, String studentID) {
        for (String ID : fir.getStudentMap().keySet()) {
            if (ID.equals(studentID)) {
                for (Course course : fir.getCourseMap().values()) {
                    course.removeStudentID(ID);
                }
                fir.getStudentMap().remove(ID);
                System.out.println("Student deleted successfully! :)");
                return;
            }
        }
        System.out.println("Sorry! The student you entered does not exist! :(");
    }
}
