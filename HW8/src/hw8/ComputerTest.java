package hw8;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComputerTest {
	
	Computer c;
	
	@BeforeEach
	void setUp() throws Exception {
		this.c = new Computer();
	}

	@Test
	void testGetScore() {
		assertEquals(0, this.c.getScore());
	}
	
	@Test
	void testSetScore() {
		this.c.setScore(10);
		assertEquals(10, this.c.getScore());
		
		this.c.setScore(11);
		assertEquals(11, this.c.getScore());
		
		this.c.setScore(21);
		assertEquals(21, this.c.getScore());
	}

}
