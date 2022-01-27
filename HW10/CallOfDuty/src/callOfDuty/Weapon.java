package callOfDuty;

/**
 * This abstract class describes the characteristics common to all weapons.
 * @author Yicheng Xia
 */
public abstract class Weapon {

    // Instance Variables
    
    /**
     * The number of shots left.
     * Initially, it's 20 for RocketLauncher and 3 for Missile.
     */
    private int shotleft;

    // Constructor

    /**
     * Weapon constructor.
     * @param shotCount
     */
    public Weapon(int shotCount) {
        this.shotleft = shotCount;
    }

    // Getters

    /**
     * Returns the number of shots left.
     * @return number of shots left
     */
    public int getShotLeft() {
        return this.shotleft;
    }

    // Abstract Methods

    /**
     * RocketLauncher will return "rocketLauncher",
     * and Missile will return "missile" and it is case insensitive.
     * @return "rocketLauncher" or "missile", depending on its implementation
     */
    public abstract String getWeaponType();

    /**
     * RocketLauncher will only shoot at one square,
     * while Missile will attack a 3x3 area.
     * @param row row to shoot at
     * @param column column to shoot at
     * @param base base the Weapon is in
     */
    public abstract void shootAt(int row, int column, Base base);

    // Other Method

    /**
     * Decrement the number of shots left.
     */
    public void decrementShotLeft() {
        this.shotleft--;
    }
}
