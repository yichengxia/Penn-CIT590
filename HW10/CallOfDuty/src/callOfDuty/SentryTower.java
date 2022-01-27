package callOfDuty;

/**
 * This is a subclass of Target.
 * @author Yicheng Xia
 */
public class SentryTower extends Target {
    
    /**
     * The name of the Target
     */
    private static final String NAME = "sentryTower";

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
     * @param base base the SentryTower is in
     */
    public SentryTower(Base base) {
        super(SentryTower.LENGTH, SentryTower.WIDTH, base);
    }

    /**
     * Define how the target will behave when it is destroyed.
     */
    @Override
    void explode() {}

    /**
     * Returns the string "sentryTower".
     * @return string "sentryTower"
     */
    @Override
    public String getTargetName() {
        return SentryTower.NAME;
    }
}
