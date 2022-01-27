package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import courses.Course;
import roles.Admin;
import roles.Professor;
import roles.Student;

/**
 * This is the FileInfoReader class, reading and saving file information.
 * @author Yicheng Xia
 */
public class FileInfoReader {

	/**
	 * Define a map for courses (String ID -> Course).
	 */
	private Map<String, Course> courseMap = new HashMap<>();
	/**
	 * Define a Map for students (String ID -> Course).
	 */
	private Map<String, Student> studentMap = new HashMap<>();
	/**
	 * Define a Map for professors (String name -> Professor).
	 */
	private Map<String, Professor> profMap = new HashMap<>();
	/**
	 * Define a Map for admins (String ID -> Admin).
	 */
	private Map<String, Admin> adminMap = new HashMap<>();
    
	/**
	 * Loads course, student, professor, and admin information from files.
	 * @param courseInfo path to courseInfo.txt
	 * @param studentInfo path to studentInfo.txt
	 * @param profInfo path to profInfo.txt
	 * @param adminInfo path to adminInfo.txt
	 */
    public void setUp(String courseInfo, String studentInfo, String profInfo, String adminInfo) {
		
		// load courseInfo
		try {
			File f = new File(courseInfo);
			FileReader fd = new FileReader(f);
			BufferedReader br = new BufferedReader(fd);
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] array = line.strip().split("; ");
                if (array.length == 7) {
					this.courseMap.put(array[0], new Course(array[0], array[1], array[2], array[3], array[4], array[5],
						Integer.parseInt(array[6]))); // course capacity needs to parse to int
                }
			}
			fd.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// load studentInfo
		try {
			File f = new File(studentInfo);
			FileReader fd = new FileReader(f);
			BufferedReader br = new BufferedReader(fd);
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] array = line.strip().split("; ");
                if (array.length == 5) {
					String[] item = array[4].strip().split(", ");
					LinkedList<LinkedList<String>> list = new LinkedList<>();
					for (int i = 0; i < item.length; i++) {
						LinkedList<String> subList = new LinkedList<>(); // subList for each course
						subList.add(item[i].strip().split(": ")[0]);
						subList.add(item[i].strip().split(": ")[1]);
						list.add(new LinkedList<>(subList));
					}
                    this.studentMap.put(array[0], new Student(array[0], array[1], array[2], array[3], list));
                }
			}
			fd.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// load profInfo
		try {
			File f = new File(profInfo);
			FileReader fd = new FileReader(f);
			BufferedReader br = new BufferedReader(fd);
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] array = line.strip().split("; ");
                if (array.length == 4) {
					this.profMap.put(array[0], new Professor(array[1], array[0], array[2], array[3]));
					for (Course course : this.getCourseMap().values()) {
						if (course.getLecturer().equals(array[0])) {
							this.getProfMap().get(array[0]).getCourseIDSet().add(course.getID());
						}
					}
                }
			}
			fd.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// load adminInfo
		try {
			File f = new File(adminInfo);
			FileReader fd = new FileReader(f);
			BufferedReader br = new BufferedReader(fd);
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] array = line.strip().split("; ");
                if (array.length == 4) {
                    this.adminMap.put(array[0], new Admin(array[0], array[1], array[2], array[3]));
                }
			}
			fd.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	/**
	 * Get the map of courses
	 * @return courseMap
	 */
	public Map<String, Course> getCourseMap() {
        return this.courseMap;
    }

	/**
	 * Get the map of students
	 * @return studentMap
	 */
    public Map<String, Student> getStudentMap() {
        return this.studentMap;
    }

	/**
	 * Get the map of professors
	 * @return profMap
	 */
	public Map<String, Professor> getProfMap() {
        return this.profMap;
    }

	/**
	 * Get the map of admins
	 * @return adminMap
	 */
	public Map<String, Admin> getAdminMap() {
        return this.adminMap;
    }
}
