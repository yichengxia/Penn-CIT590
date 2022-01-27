package callOfDuty;

/**
 * This is a subclass of Target.
 * @author Yicheng Xia
 */
public class Barrack extends Target {

    /**
     * The name of the Target
     */
    private static final String NAME = "barrack";

    /**
     * The length of the Target
     */
    private static final int LENGTH = 3;

    /**
     * The width of the Target (width <= length)
     */
    private static final int WIDTH = 1;

    /**
     * This constructor sets the length, the width and the base of the Target.
     * @param base base the Barrack is in
     */
    public Barrack(Base base) {
        super(Barrack.LENGTH, Barrack.WIDTH, base);
    }

    /**
     * Define how the target will behave when it is destroyed.
     */
    @Override
    void explode() {}

    /**
     * Returns the string "barrack".
     * @return string "barrack"
     */
    @Override
    public String getTargetName() {
        return Barrack.NAME;
    }
}
