package callOfDuty;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for Weapon class.
 * @author Yicheng Xia
 */
class WeaponTest {

    Base base;
    Missile mis;
    RocketLauncher rl;

    @BeforeEach
    void setUp() throws Exception {

        base = new Base();

        mis = new Missile();
        rl = new RocketLauncher();
    }

    @Test
    void testWeapon() {
        assertEquals(3, mis.getShotLeft());

        assertEquals(20, rl.getShotLeft());

        RocketLauncher rl2 = new RocketLauncher();
        assertEquals(20, rl2.getShotLeft());
    }

    @Test
    void testGetWeaponType() {
        assertEquals("missile", mis.getWeaponType().toLowerCase());

        assertEquals("rocketlauncher", rl.getWeaponType().toLowerCase());

        RocketLauncher rl2 = new RocketLauncher();
        assertEquals("rocketLauncher", rl2.getWeaponType());
    }

    
    @Test
    void testGetShotLeft() {
        mis.shootAt(0, 0, this.base);
        assertEquals(2, mis.getShotLeft());

        rl.shootAt(9, 9, this.base);
        assertEquals(19, rl.getShotLeft());

        RocketLauncher rl2 = new RocketLauncher();
        assertEquals(20, rl2.getShotLeft());
        rl2.shootAt(9, 9, this.base);
        assertEquals(19, rl2.getShotLeft());
    }

    @Test
    void testDecrementShotleft() {
        mis.decrementShotLeft();
        assertEquals(2, mis.getShotLeft());

        rl.decrementShotLeft();
        assertEquals(19, rl.getShotLeft());
        rl.decrementShotLeft();
        assertEquals(18, rl.getShotLeft());

        RocketLauncher rl2 = new RocketLauncher();
        assertEquals(20, rl2.getShotLeft());
        rl2.decrementShotLeft();
        assertEquals(19, rl2.getShotLeft());
    }

    @Test
    void testShootAt() {
        mis.shootAt(0, 0, this.base);
        assertTrue(base.getTargetsArray()[0][0].isHitAt(0, 0));
        assertEquals(1, base.getShotsCount());
        
        rl.shootAt(9, 9, this.base);
        assertTrue(base.getTargetsArray()[9][9].isHitAt(9, 9));
        assertEquals(2, base.getShotsCount());

        RocketLauncher rl2 = new RocketLauncher();
        rl2.shootAt(5, 5, this.base);
        assertTrue(base.getTargetsArray()[5][5].isHitAt(5, 5));
        assertEquals(3, base.getShotsCount());
    }

}
