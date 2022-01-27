package callOfDuty;

/**
 * This is a subclass of Target.
 * @author Yicheng Xia
 */
public class HeadQuarter extends Target {

    /**
     * The name of the Target
     */
    private static final String NAME = "headQuarter";

    /**
     * The length of the Target
     */
    private static final int LENGTH = 6;

    /**
     * The width of the Target (width <= length)
     */
    private static final int WIDTH = 1;

    /**
     * This constructor sets the length, the width and the base of the Target.
     * @param base
     */
    public HeadQuarter(Base base) {
        super(HeadQuarter.LENGTH, HeadQuarter.WIDTH, base);
    }

    /**
     * Define how the target will behave when it is destroyed.
     */
    @Override
    void explode() {}

    /**
     * Returns the string "headQuarter".
     * @return
     */
    @Override
    public String getTargetName() {
        return HeadQuarter.NAME;
    }
}
