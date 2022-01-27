package files;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FileInfoReaderTest {

    FileInfoReader fir;

    @Test
    void testSetUp() {

        fir = new FileInfoReader();
        fir.setUp("src/courseInfo.txt", "src/studentInfo.txt", "src/profInfo.txt", "src/adminInfo.txt");

        assertEquals(2, fir.getStudentMap().size());
        assertEquals("StudentName1", fir.getStudentMap().get("001").getName());
        assertEquals("A", fir.getStudentMap().get("002").getGrade().getFirst().get(1));
        assertEquals("StudentName2", fir.getStudentMap().get("002").getName());
        assertEquals("CIT593", fir.getStudentMap().get("002").getGrade().getLast().get(0));

        assertEquals(32, fir.getProfMap().size());
        assertEquals("001", fir.getProfMap().get("Clayton Greenberg").getID());
        assertEquals("002", fir.getProfMap().get("Harry Smith").getID());
        assertEquals("password590", fir.getProfMap().get("Stephanie Weirich").getPassword());

        assertEquals(3, fir.getAdminMap().size());
        assertEquals("admin", fir.getAdminMap().get("001").getName());
        assertEquals("admin", fir.getAdminMap().get("002").getName());
        assertEquals("admin03", fir.getAdminMap().get("003").getUsername());

        assertEquals(50, fir.getCourseMap().size());
        assertEquals("Programming Languages and Techniques", fir.getCourseMap().get("CIT590").getName());
        assertEquals("MW", fir.getCourseMap().get("CIT591").getDays());
        assertEquals(120, fir.getCourseMap().get("CIT591").getCapacity());
        assertEquals("10:00", fir.getCourseMap().get("CIT592").getStartTime());
        assertEquals("11:00", fir.getCourseMap().get("CIT592").getEndTime());
    }
}
