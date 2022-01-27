package courses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseTest {

    Course course1;
    Course course2;
    Course course3;
    
    @BeforeEach
    void setUp() throws Exception {
        course1 = new Course("CIT590", "Programming Languages and Techniques", "Brandon L Krakowsky", "MW", "16:30", "18:00", 110);
        course2 = new Course("CIT591", "Introduction to Software Development", "Arvind Bhusnurmath", "MW", "12:00", "13:30", 120);
        course3 = new Course("CIT592", "Mathematical Foundations of Computer Science", "Clayton Greenberg", "TR", "10:00", "11:00", 72);
    }

    @Test
    void testAddStudentID() {
        assertFalse(course1.getStudentIDSet().contains("001"));
        assertEquals(0, course1.getStudent());
        course1.addStudentID("001");
        assertTrue(course1.getStudentIDSet().contains("001"));
        assertEquals(1, course1.getStudent());

        assertFalse(course2.getStudentIDSet().contains("002"));
        assertFalse(course2.getStudentIDSet().contains("003"));
        assertEquals(0, course2.getStudent());
        course2.addStudentID("002");
        course2.addStudentID("003");
        assertTrue(course2.getStudentIDSet().contains("002"));
        assertTrue(course2.getStudentIDSet().contains("003"));
        assertEquals(2, course2.getStudent());

        assertFalse(course3.getStudentIDSet().contains("002"));
        assertFalse(course3.getStudentIDSet().contains("003"));
        assertEquals(0, course3.getStudent());
        course3.addStudentID("002");
        course3.addStudentID("002");
        course3.addStudentID("003");
        assertTrue(course3.getStudentIDSet().contains("002"));
        assertTrue(course3.getStudentIDSet().contains("002"));
        assertTrue(course3.getStudentIDSet().contains("003"));
        assertEquals(2, course3.getStudent());
    }

    @Test
    void testRemoveStudentID() {
        assertFalse(course1.getStudentIDSet().contains("001"));
        assertEquals(0, course1.getStudent());
        course1.addStudentID("001");
        assertTrue(course1.getStudentIDSet().contains("001"));
        assertEquals(1, course1.getStudent());
        course1.removeStudentID("001");
        assertFalse(course1.getStudentIDSet().contains("001"));
        assertEquals(0, course1.getStudent());

        assertFalse(course2.getStudentIDSet().contains("002"));
        assertFalse(course2.getStudentIDSet().contains("003"));
        assertEquals(0, course2.getStudent());
        course2.addStudentID("002");
        course2.addStudentID("003");
        assertTrue(course2.getStudentIDSet().contains("002"));
        assertTrue(course2.getStudentIDSet().contains("003"));
        assertEquals(2, course2.getStudent());
        course2.removeStudentID("002");
        course2.removeStudentID("002");
        assertFalse(course2.getStudentIDSet().contains("002"));
        assertEquals(1, course2.getStudent());

        assertFalse(course3.getStudentIDSet().contains("001"));
        assertEquals(0, course3.getStudent());
        course3.addStudentID("001");
        assertTrue(course3.getStudentIDSet().contains("001"));
        assertEquals(1, course3.getStudent());
        course3.removeStudentID("001");
        course3.removeStudentID("001");
        assertFalse(course3.getStudentIDSet().contains("001"));
        assertEquals(0, course3.getStudent());
    }
}
