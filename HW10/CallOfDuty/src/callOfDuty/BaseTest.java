package callOfDuty;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for Base class.
 * @author Yicheng Xia
 */
class BaseTest {

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
    void testBase() {
        assertEquals(10, base.getTargetsArray().length);

        assertEquals(10, base.getTargetsArray()[0].length);

        assertEquals(0, base.getShotsCount());
        assertEquals(0, base.getDestroyedTargetCount());

        assertEquals("armory", base.getTargetsArray()[0][2].getTargetName().toLowerCase());
        assertEquals("ground", base.getTargetsArray()[0][3].getTargetName().toLowerCase());
    }

    @Test
    void testPlaceAllTargetRandomly() {
        this.base = new Base();
        base.placeAllTargetRandomly();
        List<Target> list = new ArrayList<Target>();
        int headQuarterCount = 0;
        int armoryCount = 0;
        int barracksCount = 0;
        int sentryCount = 0;
        int tanksCount = 0;
        int odCount = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (base.getTargetsArray()[i][j].getTargetName() != "ground") {
                    if (!list.contains(base.getTargetsArray()[i][j])) {
                        list.add(base.getTargetsArray()[i][j]);
                        switch (base.getTargetsArray()[i][j].getTargetName().toLowerCase()) {
                        case "armory": {
                            armoryCount++;
                            break;
                        }
                        case "headquarter": {
                            headQuarterCount++;
                            break;
                        }
                        case "barrack": {
                            barracksCount++;
                            break;
                        }
                        case "sentrytower": {
                            sentryCount++;
                            break;
                        }
                        case "tank": {
                            tanksCount++;
                            break;
                        }
                        case "oildrum": {
                            odCount++;
                            break;
                        }
                        }
                    }
                }
            }
        }
        assertEquals(list.size(), 18);

        assertEquals(1, headQuarterCount);
        assertEquals(2, armoryCount);
        assertEquals(3, barracksCount);
        assertEquals(4, sentryCount);
        assertEquals(4, tanksCount);
        assertEquals(4, odCount);
    }

    @Test
    void testOkToPlaceTargetAt() {
        assertFalse(this.base.okToPlaceTargetAt(new Armory(this.base), 1, 7, false));
        assertFalse(this.base.okToPlaceTargetAt(new Armory(this.base), 1, 8, true));
        assertTrue(this.base.okToPlaceTargetAt(new Armory(this.base), 1, 8, false));

        assertTrue(this.base.okToPlaceTargetAt(new SentryTower(this.base), 3, 0, true));
        assertTrue(this.base.okToPlaceTargetAt(new SentryTower(this.base), 3, 0, false));
        assertFalse(this.base.okToPlaceTargetAt(new SentryTower(this.base), 3, 3, true));
        assertFalse(this.base.okToPlaceTargetAt(new SentryTower(this.base), 3, 3, false));

        assertFalse(this.base.okToPlaceTargetAt(new Armory(this.base), 3, 1, true));
        assertTrue(this.base.okToPlaceTargetAt(new Armory(this.base), 3, 1, false));
        assertFalse(this.base.okToPlaceTargetAt(new Armory(this.base), 2, 3, false));
    }
    
    

    @Test
    void testPlaceTargetAt() {
        Target armory = new Armory(base);
        this.base.placeTargetAt(armory, 5, 5, false);
        assertEquals(5, armory.getCoordinate()[0]);
        assertEquals(5, armory.getCoordinate()[1]);
        assertEquals(3, armory.getHit().length);
        assertEquals(2, armory.getHit()[0].length);
        
        Target headquarter = new HeadQuarter(base);
        this.base.placeTargetAt(headquarter, 0, 8, false);
        assertEquals(0, headquarter.getCoordinate()[0]);
        assertEquals(8, headquarter.getCoordinate()[1]);
        assertEquals(6, headquarter.getHit().length);
        assertEquals(1, headquarter.getHit()[0].length);

        Target barrack2 = new Barrack(base);
        this.base.placeTargetAt(barrack2, 3, 0, true);
        assertEquals(3, barrack2.getCoordinate()[0]);
        assertEquals(0, barrack2.getCoordinate()[1]);
        assertEquals(1, barrack2.getHit().length);
        assertEquals(3, barrack2.getHit()[0].length);
    }
    
    
    @Test
    void testIsOccupied() {

        Armory arm = new Armory(base);
        this.base.placeTargetAt(arm, 0, 0, true);
        assertTrue(base.isOccupied(0, 0));

        Target headquarter = new HeadQuarter(base);
        this.base.placeTargetAt(headquarter, 0, 8, false);
        assertTrue(base.isOccupied(0, 8));
        assertTrue(base.isOccupied(5, 8));

        Target tank2 = new Tank(base);
        this.base.placeTargetAt(tank2, 0, 9, true);
        assertTrue(base.isOccupied(0, 9));
    }

    @Test
    void testShootAt() {

        Armory arm = new Armory(base);
        this.base.placeTargetAt(arm, 5, 5, true);
        base.shootAt(5, 5);
        assertTrue(arm.isHitAt(5, 5));

        Target headquarter = new HeadQuarter(base);
        this.base.placeTargetAt(headquarter, 0, 8, false);
        base.shootAt(0, 8);
        assertTrue(base.isOccupied(0, 8));

        Target tank2 = new Tank(base);
        this.base.placeTargetAt(tank2, 0, 9, true);
        base.shootAt(0, 9);
        assertTrue(base.isOccupied(0, 9));
    }

    @Test
    void testIsGameOver() {

        assertFalse(base.isGameOver(new RocketLauncher(), new Missile()));

        base.setDestroyedTargetCount(18);
        assertTrue(base.isGameOver(new RocketLauncher(), new Missile()));

        base.setDestroyedTargetCount(0);
        assertFalse(base.isGameOver(new RocketLauncher(), new Missile()));
        RocketLauncher rocketLauncher = new RocketLauncher();
        Missile missile = new Missile();
        for (int i = 0; i < 20; i++) {
            rocketLauncher.shootAt(0, 0, base);
            if (i < 3) {
                missile.shootAt(9, 9, base);
            }
        }
        assertTrue(base.isGameOver(rocketLauncher, missile));
    }

    @Test
    void testWin() {

        assertFalse(this.base.win());

        base.setDestroyedTargetCount(18);
        assertTrue(this.base.win());

        base.setDestroyedTargetCount(17);
        assertFalse(this.base.win());

        base.setDestroyedTargetCount(0);
        assertFalse(this.base.win());
    }

    @Test
    void testIncrementAndSetShotsCount() {

        assertEquals(0, base.getShotsCount());
        base.incrementShotsCount();
        assertEquals(1, base.getShotsCount());

        base.incrementShotsCount();
        assertEquals(2, base.getShotsCount());

        base.incrementShotsCount();
        base.incrementShotsCount();
        assertEquals(4, base.getShotsCount());
    }

    @Test
    void testSetAndGetDestroyedTargetCount() {

        base.setDestroyedTargetCount(10);
        assertEquals(10, base.getDestroyedTargetCount());

        base.setDestroyedTargetCount(0);
        assertEquals(0, base.getDestroyedTargetCount());

        base.setDestroyedTargetCount(18);
        assertEquals(18, base.getDestroyedTargetCount());
    }

    @Test
    void testGetDestroyedTargetCount() {
        
        assertEquals(0, base.getDestroyedTargetCount());
                
        base.setDestroyedTargetCount(base.getDestroyedTargetCount() + 1);
        assertEquals(1, base.getDestroyedTargetCount());

        base.setDestroyedTargetCount(base.getDestroyedTargetCount() + 10);
        assertEquals(11, base.getDestroyedTargetCount());
    }


    @Test
    void testGetTargetsArray() {

        assertEquals(10, base.getTargetsArray().length);

        assertEquals(10, base.getTargetsArray()[0].length);

        assertEquals("tank", base.getTargetsArray()[1][3].getTargetName().toLowerCase());
        assertEquals("oildrum", base.getTargetsArray()[2][1].getTargetName().toLowerCase());
    }

}
