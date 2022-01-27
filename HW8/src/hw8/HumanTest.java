package hw8;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HumanTest {
	
	Human h;
	
	@BeforeEach
	void setUp() throws Exception {
		this.h = new Human("test");
	}

	@Test
	void testGetName() {
		assertEquals("test", this.h.getName());
	}
	
	@Test
	void testGetScore() {
		assertEquals(0, this.h.getScore());
	}
	
	@Test
	void testSetScore() {
		this.h.setScore(10);
		assertEquals(10, this.h.getScore());
		
		this.h.setScore(11);
		assertEquals(11, this.h.getScore());
		
		this.h.setScore(21);
		assertEquals(21, this.h.getScore());
	}

}
