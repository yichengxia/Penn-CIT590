package roles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import files.FileInfoReader;

class StudentTest {

    FileInfoReader fir;
    Student student1;
    Student student2;

    @BeforeEach
    void setUp() throws Exception {
        fir = new FileInfoReader();
        fir.setUp("src/courseInfo.txt", "src/studentInfo.txt", "src/profInfo.txt", "src/adminInfo.txt");
        student1 = fir.getStudentMap().get("001");
        student2 = fir.getStudentMap().get("002");
    }
    
    @Test
    void testAddCourse() {
        assertFalse(student1.getCourseIDSet().contains("CIT590"));
        assertEquals(0, student1.getCourseIDSet().size());
        assertEquals(0, fir.getCourseMap().get("CIT590").getStudent());

        student1.addCourse(fir, "CIT599");
        assertFalse(student1.getCourseIDSet().contains("CIT599"));
        assertEquals(0, student1.getCourseIDSet().size());

        student1.addCourse(fir, "CIT590");
        assertTrue(student1.getCourseIDSet().contains("CIT590"));
        assertEquals(1, student1.getCourseIDSet().size());
        assertEquals(1, fir.getCourseMap().get("CIT590").getStudent());

        assertEquals(0, student2.getCourseIDSet().size());
        student2.addCourse(fir, "CIT590");
        student2.addCourse(fir, "CIS553");
        assertTrue(student2.getCourseIDSet().contains("CIT590"));
        assertFalse(student2.getCourseIDSet().contains("CIS553"));
        assertEquals(1, student2.getCourseIDSet().size());
        assertEquals(2, fir.getCourseMap().get("CIT590").getStudent());
        assertEquals(0, fir.getCourseMap().get("CIS553").getStudent());
    }

    @Test
    void testDropCourse() {
        assertFalse(student1.getCourseIDSet().contains("CIT590"));
        assertEquals(0, student1.getCourseIDSet().size());
        assertEquals(0, fir.getCourseMap().get("CIT590").getStudent());

        student1.addCourse(fir, "CIT590");
        student1.addCourse(fir, "CIT591");
        assertEquals(2, student1.getCourseIDSet().size());
        student1.dropCourse(fir, "CIT592");

        assertEquals(2, student1.getCourseIDSet().size());
        student1.dropCourse(fir, "CIT591");
        assertEquals(1, student1.getCourseIDSet().size());
        student1.dropCourse(fir, "CIT591");
        assertEquals(1, student1.getCourseIDSet().size());
        assertTrue(student1.getCourseIDSet().contains("CIT590"));

        assertFalse(student2.getCourseIDSet().contains("CIT591"));
        assertEquals(0, student2.getCourseIDSet().size());
        assertEquals(0, fir.getCourseMap().get("CIT591").getStudent());
        student2.dropCourse(fir, "CIT591");
        assertEquals(0, student2.getCourseIDSet().size());
    }
}
