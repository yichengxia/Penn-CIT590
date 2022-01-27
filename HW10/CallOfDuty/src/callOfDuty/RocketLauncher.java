package callOfDuty;

/**
 * This is a subclass RocketLauncher of Weapon.
 * @author Yicheng Xia
 */
public class RocketLauncher extends Weapon {

    /**
     * The type of the Weapon.
     */
    private static final String TYPE = "rocketLauncher";

    /**
     * The number of shots left.
     */
    private static final int SHOT_COUNT = 20;

    /**
     * RocketLauncher constructor.
     */
    public RocketLauncher() {
        super(RocketLauncher.SHOT_COUNT);
    }

    /**
     * RocketLauncher will return "rocketLauncher" and it is case insensitive.
     * @return "rocketLauncher"
     */
    @Override
    public String getWeaponType() {
        return RocketLauncher.TYPE;
    }

    /**
     * RocketLauncher will only shoot at one square,
     * while Missile will attack a 3x3 area.
     * @param row row to shoot at
     * @param column column to shoot at
     * @param base base the Weapon is in
     */
    @Override
    public void shootAt(int row, int column, Base base) {
        base.shootAt(row, column);
        this.decrementShotLeft();
        base.incrementShotsCount();
    }
}
