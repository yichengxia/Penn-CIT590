package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import files.FileInfoReader;

class AdminTest {

    FileInfoReader fir;
    Admin admin1;
    Admin admin2;
    Admin admin3;
    
    @BeforeEach
    void setUp() throws Exception {
        fir = new FileInfoReader();
        fir.setUp("src/courseInfo.txt", "src/studentInfo.txt", "src/profInfo.txt", "src/adminInfo.txt");
        admin1 = fir.getAdminMap().get("001");
        admin2 = fir.getAdminMap().get("002");
        admin3 = fir.getAdminMap().get("003");
    }

    @Test
    void testAddCourse() {
        assertFalse(fir.getCourseMap().containsKey("CIT500"));
        admin1.addCourse(fir, "CIT500", "Introduction to Winnie the Pooh", "16:00", "17:00", "MW", 100, "029");
        assertFalse(fir.getCourseMap().containsKey("CIT500"));
        assertEquals(1, fir.getProfMap().get("Brandon L Krakowsky").getCourseIDSet().size());

        assertFalse(fir.getCourseMap().containsKey("CIT500"));
        admin2.addCourse(fir, "CIT500", "Introduction to Winnie the Pooh", "16:00", "17:00", "TR", 100, "029");
        assertTrue(fir.getCourseMap().containsKey("CIT500"));
        assertEquals(2, fir.getProfMap().get("Brandon L Krakowsky").getCourseIDSet().size());

        assertFalse(fir.getCourseMap().containsKey("CIT600"));
        admin3.addCourse(fir, "CIT600", "Advanced Topics of Winnie the Pooh", "10:00", "12:00", "TR", 100, "029");
        assertTrue(fir.getCourseMap().containsKey("CIT600"));
        assertEquals(3, fir.getProfMap().get("Brandon L Krakowsky").getCourseIDSet().size());
    }

    @Test
    void testDeleteCourse() {
        assertTrue(fir.getCourseMap().containsKey("CIT590"));
        admin1.deleteCourse(fir, "CIT590");
        assertFalse(fir.getCourseMap().containsKey("CIT590"));

        assertTrue(fir.getCourseMap().containsKey("CIT591"));
        admin2.deleteCourse(fir, "cit591");
        assertFalse(fir.getCourseMap().containsKey("CIT591"));

        assertEquals(0, fir.getCourseMap().get("CIT592").getStudent());
        Student student1 = fir.getStudentMap().get("001");
        student1.addCourse(fir, "CIT592");
        assertTrue(student1.getCourseIDSet().contains("CIT592"));
        assertEquals(1, fir.getCourseMap().get("CIT592").getStudent());
        admin3.deleteCourse(fir, "CIT592");
        assertFalse(student1.getCourseIDSet().contains("CIT592"));
        assertEquals(0, student1.getCourseIDSet().size());
    }

    @Test
    void testAddProf() {
        assertEquals("001", fir.getProfMap().get("Clayton Greenberg").getID());
        admin1.addProf(fir, "000", "Clayton Greenberg", "cg", "password590");
        assertEquals("000", fir.getProfMap().get("Clayton Greenberg").getID());

        assertEquals(null, fir.getProfMap().get("Tigger"));
        admin2.addProf(fir, "033", "Tigger", "tigger", "password590");
        assertEquals("033", fir.getProfMap().get("Tigger").getID());

        assertEquals(null, fir.getProfMap().get("Winnie the Pooh"));
        admin3.addProf(fir, "034", "Winnie the Pooh", "pooh", "password590");
        assertEquals("034", fir.getProfMap().get("Winnie the Pooh").getID());
    }

    @Test
    void testDeleteProf() {
        assertEquals("001", fir.getProfMap().get("Clayton Greenberg").getID());
        admin1.deleteProf(fir, "001");
        assertEquals(null, fir.getProfMap().get("Clayton Greenberg"));

        assertEquals(31, fir.getProfMap().size());
        admin2.deleteProf(fir, "000");
        assertEquals(31, fir.getProfMap().size());

        assertEquals(null, fir.getProfMap().get("Winnie the Pooh"));
        admin3.addProf(fir, "034", "Winnie the Pooh", "pooh", "password590");
        admin3.deleteProf(fir, "034");
        assertEquals(31, fir.getProfMap().size());
    }

    @Test
    void testAddStuent() {
        assertFalse(fir.getStudentMap().containsKey("000"));
        admin1.addStudent(fir, "000", "Winnie the Pooh", "pooh", "password590", new LinkedList<>());
        assertTrue(fir.getStudentMap().containsKey("000"));

        assertFalse(fir.getStudentMap().containsKey("003"));
        admin2.addStudent(fir, "003", "Tigger", "tigger", "password590", new LinkedList<>(new LinkedList<>()));
        assertTrue(fir.getStudentMap().containsKey("003"));

        assertFalse(fir.getStudentMap().containsKey("004"));
        LinkedList<String> subList = new LinkedList<>();
        subList.add("CIT590");
        subList.add("A");
        LinkedList<LinkedList<String>> list = new LinkedList<>();
        list.add(subList);
        admin2.addStudent(fir, "004", "Yicheng Xia", "yicheng", "password590", list);
        assertEquals("CIT590", fir.getStudentMap().get("004").getGrade().get(0).get(0));
    }

    @Test
    void testDeleteStuent() {
        assertTrue(fir.getStudentMap().containsKey("001"));
        admin1.deleteStudent(fir, "001");
        assertFalse(fir.getStudentMap().containsKey("001"));

        assertFalse(fir.getStudentMap().containsKey("001"));
        admin2.deleteStudent(fir, "001");
        assertFalse(fir.getStudentMap().containsKey("001"));

        assertEquals(0, fir.getCourseMap().get("CIT590").getStudent());
        Student student2 = fir.getStudentMap().get("002");
        student2.addCourse(fir, "CIT590");
        assertEquals(1, fir.getCourseMap().get("CIT590").getStudent());
        admin3.deleteStudent(fir, "002");
        assertFalse(fir.getStudentMap().containsKey("002"));
        assertEquals(0, fir.getCourseMap().get("CIT590").getStudent());
    }
}
