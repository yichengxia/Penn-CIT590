package callOfDuty;

/**
 * This is a subclass of Target.
 * Ground describes a part of the base that doesn't have a target on it.
 * @author Yicheng Xia
 */
public class Ground extends Target {

    /**
     * The name of the Target
     */
    private static final String NAME = "ground";

    /**
     * The length of the Target
     */
    private static final int LENGTH = 1;

    /**
     * The width of the Target (width <= length)
     */
    private static final int WIDTH = 1;

    /**
     * This constructor sets the width and length variables to 1 and the Base variable by calling the constructor in the super class.
     * @param base base the Ground is in
     */
    public Ground(Base base) {
        super(Ground.LENGTH, Ground.WIDTH, base);
    }

    /**
     * Define how the target will behave when it is destroyed.
     */
    @Override
    void explode() {}

    /**
     * Returns the string "tank".
     * @return string "tank"
     */
    @Override
    public String getTargetName() {
        return Ground.NAME;
    }

    /**
     * This method overrides isDestroyed() that is inherited from Target,
     * and always returns false to indicate that you didn't destroy anything.
     * @return always false to indicate that you didn't destroy anything
     */
    @Override
    public boolean isDestroyed() {
        return false;
    }

    /**
     * Returns the single-character "-"" String to use in the Base's print method. 
     * @return single-character "-"" String
     */
    @Override
    public String toString() {
        return new String("-");
    }
}
