package callOfDuty;

/**
 * This is a subclass Missile of Weapon.
 * @author Yicheng Xia
 */
public class Missile extends Weapon {

    /**
     * The type of the Weapon.
     */
    private static final String TYPE = "missile";

    /**
     * The number of shots left.
     */
    private static final int SHOT_COUNT = 3;

    /**
     * Missile constructor.
     */
    public Missile() {
        super(Missile.SHOT_COUNT);
    }

    /**
     * Missile will return "missile" and it is case insensitive.
     * @return "missile"
     */
    @Override
    public String getWeaponType() {
        return Missile.TYPE;
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
        // shoot 3 x 3 area around (row,column)
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (isValid(i, j)) { // avoid coordinates outside base
                    base.shootAt(i, j);
                }
            }
        }
        this.decrementShotLeft();
        base.incrementShotsCount();
    }

    /**
     * Helper method.
     * Returns true if row and column are valid in base, false otherwise.
     * @param row row to test
     * @param column column to test
     * @return if row and column are valid in base
     */
    private boolean isValid(int row, int column) {
        return row >= 0 && row <= 9 && column >= 0 && column <= 9;
    }
}
