package callOfDuty;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for Target class.
 * @author Yicheng Xia
 */
class TargetTest {

    Base base;
    Armory armory;
    Barrack barrack;
    SentryTower st;
    Tank tank;
    OilDrum od;

    @BeforeEach
    void setUp() throws Exception {

        base = new Base();

        armory = new Armory(base);
        base.placeTargetAt(armory, 0, 0, true);

        barrack = new Barrack(base);
        base.placeTargetAt(barrack, 0, 4, true);

        st = new SentryTower(base);
        base.placeTargetAt(st, 2, 4, true);

        tank = new Tank(base);
        base.placeTargetAt(tank, 1, 3, true);

        od = new OilDrum(base);
        base.placeTargetAt(od, 2, 1, true);
    }

    @Test
    void testTarget() {

        // Armory
        assertEquals(2, armory.getHit().length);
        assertEquals(3, armory.getHit()[0].length);

        // Barrack
        assertEquals(1, barrack.getHit().length);
        assertEquals(3, barrack.getHit()[0].length);

        // Sentry Tower
        assertEquals(1, st.getHit().length);
        assertEquals(1, st.getHit()[0].length);

        // Tank
        assertEquals(1, tank.getHit().length);
        assertEquals(1, tank.getHit()[0].length);

        // Head Quater
        HeadQuarter hq = new HeadQuarter(base);
        base.placeTargetAt(hq, 0, 8, false);
        assertEquals(6, hq.getHit().length);
        assertEquals(1, hq.getHit()[0].length);
    }

    @Test
    void testToString() {
        assertEquals("O", st.toString());
        assertEquals("T", tank.toString());

        assertEquals("-", new Ground(base).toString());

        base.shootAt(2, 4);
        assertEquals("X", st.toString());

        base.shootAt(1, 3);
        assertEquals("T", tank.toString());
        base.shootAt(1, 3);
        assertEquals("X", tank.toString());
    }

    @Test
    void testGetTargetName() {
        assertEquals("tank", tank.getTargetName().toLowerCase());
        assertEquals("sentrytower", st.getTargetName().toLowerCase());
        assertEquals("oildrum", od.getTargetName().toLowerCase());

        assertEquals("armory", armory.getTargetName().toLowerCase());
        assertEquals("barrack", barrack.getTargetName().toLowerCase());
        HeadQuarter hq = new HeadQuarter(base);
        assertEquals("headquarter", hq.getTargetName().toLowerCase());
    }

    @Test
    void testExplode() {
        assertFalse(armory.isDestroyed());
        od.explode();
        assertTrue(armory.isDestroyed());

        assertTrue(od.isDestroyed());
        assertTrue(tank.isDestroyed());

        OilDrum od2 = new OilDrum(base);
        base.placeTargetAt(od2, 0, 9, true);
        assertFalse(od2.isDestroyed());
        od2.explode();
        assertTrue(od2.isDestroyed());

        Tank tank2 = new Tank(base);
        base.placeTargetAt(tank2, 5, 0, false);
        assertFalse(tank2.isDestroyed());
        base.shootAt(5, 0);
        tank2.explode();
        assertTrue(tank2.isDestroyed());
    }


    @Test
    void testGetShot() {
        Target am = new Armory(this.base);
        assertTrue(this.base.okToPlaceTargetAt(am, 5, 5, false));
        this.base.placeTargetAt(am, 5, 5, false);
        am.getShot(5, 6);
        assertEquals(1, am.getHit()[0][1]);

        Tank tank2 = new Tank(base);
        assertTrue(this.base.okToPlaceTargetAt(tank2, 0, 8, false));
        base.placeTargetAt(tank2, 0, 8, false);
        tank2.getShot(0, 8);
        assertEquals(1, tank2.getHit()[0][0]);
        tank2.getShot(0, 8);
        assertTrue(tank2.isDestroyed());

        st.getShot(2, 4);
        assertEquals(1, st.getHit()[0][0]);
        assertTrue(st.isDestroyed());
    }

    @Test
    void testIsDestroyed() {
        assertFalse(od.isDestroyed());
        od.getShot(2, 1);
        assertTrue(od.isDestroyed());
        assertTrue(tank.isDestroyed());

        assertFalse(barrack.isDestroyed());
        barrack.getShot(0, 6);
        assertTrue(barrack.isDestroyed());

        Tank tank2 = new Tank(base);
        base.placeTargetAt(tank2, 0, 8, false);
        assertFalse(tank2.isDestroyed());
        tank2.getShot(0, 8);
        assertFalse(tank2.isDestroyed());
        tank2.getShot(0, 8);
        assertTrue(tank2.isDestroyed());
    }

    @Test
    void testIsHitAt() {
        Target am = new Armory(this.base);
        assertTrue(this.base.okToPlaceTargetAt(am, 5, 5, false));
        this.base.placeTargetAt(am, 5, 5, true);
        assertFalse(am.isHitAt(5, 5));
        am.getShot(5, 5);
        assertTrue(am.isHitAt(5, 5));

        assertFalse(st.isHitAt(2, 4));
        st.getShot(2, 4);
        assertTrue(st.isHitAt(2, 4));

        assertFalse(od.isHitAt(2, 1));
        od.getShot(2, 1);
        assertTrue(od.isHitAt(2, 1));
    }

}
