package hw8;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControlTest {
	
	GameControl gc;
	
	@BeforeEach
	void setUp() throws Exception {
		this.gc = new GameControl();
	}

	@Test
	void testCheckWinningStatus() {
		
		this.gc.createPlayers("test");
		
		this.gc.human.setScore(49);
		this.gc.computer.setScore(41);
		assertFalse(this.gc.checkWinningStatus());
		
		this.gc.human.setScore(41);
		this.gc.computer.setScore(49);
		assertFalse(this.gc.checkWinningStatus());
		
		this.gc.human.setScore(49);
		this.gc.computer.setScore(49);
		assertFalse(this.gc.checkWinningStatus());
		
		this.gc.human.setScore(50);
		this.gc.computer.setScore(49);
		assertTrue(this.gc.checkWinningStatus());
		
		this.gc.human.setScore(41);
		this.gc.computer.setScore(53);
		assertTrue(this.gc.checkWinningStatus());
		
		this.gc.human.setScore(52);
		this.gc.computer.setScore(52);
		assertFalse(this.gc.checkWinningStatus());
		
		this.gc.human.setScore(55);
		this.gc.computer.setScore(54);
		assertTrue(this.gc.checkWinningStatus());
		
		this.gc.human.setScore(51);
		this.gc.computer.setScore(59);
		assertTrue(this.gc.checkWinningStatus());
		
	}

}
