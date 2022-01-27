package callOfDuty;

/**
 * This is a subclass of Target.
 * @author Yicheng Xia
 */
public class Tank extends Target {
    
    /**
     * The name of the Target
     */
    private static final String NAME = "tank";

    /**
     * The length of the Target
     */
    private static final int LENGTH = 1;

    /**
     * The width of the Target (width <= length)
     */
    private static final int WIDTH = 1;

    /**
     * This constructor sets the length, the width and the base of the Target.
     * @param base base the Tank is in
     */
    public Tank(Base base) {
        super(Tank.LENGTH, Tank.WIDTH, base);
    }

    /**
     * Define how the target will behave when it is destroyed.
     */
    @Override
    void explode() {
        if (this.getHorizontal()) { // explode 7 x 6 area
            for (int i = this.getCoordinate()[0] - 2; i < this.getCoordinate()[0] + this.getWidth() + 2; i++) {
                for (int j = this.getCoordinate()[1] - 2; j < this.getCoordinate()[1] + this.getLength() + 2; j++) {
                    if (i >= 0 && i <= 9 && j >= 0 && j <= 9) { // if i and j are valid to be shot
                        this.getBase().shootAt(i, j);
                    }
                }
            }
        } else { // explode 6 x 7 area
            for (int i = this.getCoordinate()[0] - 2; i < this.getCoordinate()[0] + this.getLength() + 2; i++) {
                for (int j = this.getCoordinate()[1] - 2; j < this.getCoordinate()[1] + this.getWidth() + 2; j++) {
                    if (i >= 0 && i <= 9 && j >= 0 && j <= 9) {
                        this.getBase().shootAt(i, j);
                    }
                }
            }
        }
    }

    /**
     * Returns the string "tank".
     * @return type of Target as a String
     */
    @Override
    public String getTargetName() {
        return Tank.NAME;
    }

    /**
     * Returns true if every part of the Target has been hit, false otherwise.
     * For Tank, every part of it should be hit twice.
     * @return if every part of the Target has been hit
     */
    @Override
    public boolean isDestroyed() {
        if (this.getHorizontal()) {
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getLength(); j++) {
                    if (this.getHit()[i][j] < 2) {
                        return false;
                    }
                }
            }
        } else {
            for (int i = 0; i < this.getLength(); i++) {
                for (int j = 0; j < this.getWidth(); j++) {
                    if (this.getHit()[i][j] < 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns a single-character String to use in the Base's print method.
     * This method can be used to print out locations in the Base that have been shot at.
     * @return a single-character String to use in the Base's print method
     */
    @Override
    public String toString() {
        if (this.isDestroyed() == true) {
            return new String("X");
        } else {
            return new String("T"); // Tank returns "T" before explosion instead of "O"
        }
    }
}
