package callOfDuty;

/**
 * This is a subclass of Target.
 * @author Yicheng Xia
 */
public class Armory extends Target {
    
    /**
     * The name of the Target
     */
    private static final String NAME = "armory";

    /**
     * The length of the Target
     */
    private static final int LENGTH = 3;

    /**
     * The width of the Target (width <= length)
     */
    private static final int WIDTH = 2;

    /**
     * This constructor sets the length, the width and the base of the Target.
     * @param base base the Armory is in
     */
    public Armory(Base base) {
        super(Armory.LENGTH, Armory.WIDTH, base);
    }

    /**
     * Define how the target will behave when it is destroyed.
     */
    @Override
    void explode() {
        if (this.getHorizontal()) {
            // explode 5 x 5 area
            for (int i = this.getCoordinate()[0] - 2; i < this.getCoordinate()[0] + this.getWidth() + 2; i++) {
                for (int j = this.getCoordinate()[1] - 2; j < this.getCoordinate()[1] + this.getLength() + 2; j++) {
                    if (i >= 0 && i <= 9 && j >= 0 && j <= 9) { // if i and j are valid to be shot
                        this.getBase().shootAt(i, j);
                    }
                }
            }
        } else {
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
     * Returns the string "armory".
     * @return string "armory"
     */
    @Override
    public String getTargetName() {
        return Armory.NAME;
    }
}
