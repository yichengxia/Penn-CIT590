package callOfDuty;

/**
 * This abstract class describes the characteristics common to all targets.
 * @author Yicheng Xia
 */
public abstract class Target {

    /**
     * An array of length 2 that specifies the coordinate of the head of a target.
     */
    private int[] coordinate;

    /**
     * The length of the Target
     */
    private int length;

    /**
     * The width of the Target (width <= length)
     */
    private int width;

    /**
     * Indicates whether the Target is horizontal or not
     */
    private boolean horizontal;

    /**
     * An array of the same size as the target,
     * indicating the number of times a part of the target has been hit.
     */
    private int[][] hit;

    /**
     * An instance of Base that the target is placed in.
     */
    private Base base;

    // Constructor

    /**
     * This constructor sets the length, the width and the base of the Target.
     * @param length length of Target
     * @param width width of Target
     * @param base base the Target is in
     */
    public Target(int length, int width, Base base) {
        this.length = length;
        this.width = width;
        this.base = base;
    }

    // Getters

    /**
     * Returns the coordinate array.
     * @return coordinate array
     */
    public int[] getCoordinate() {
        return this.coordinate;
    }

    /**
     * Returns whether the target is horizontal or not
     * @return whether the target is horizontal
     */
    public boolean getHorizontal() {
        return this.horizontal;
    }

    /**
     * Returns the hit array.
     * @return hit array
     */
    public int[][] getHit() {
        return this.hit;
    }

    /**
     * Returns the base.
     * @return base
     */
    public Base getBase() {
        return this.base;
    }

    /**
     * Returns the length of this target.
     * @return length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Returns the width of this target.
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    // Setters

    /**
     * Sets the coordinate array.
     * @param coordinate coordinate array of the head of Target
     */
    void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Sets the value of horizontal.
     * @param horizontal horizontal value of Target
     */
    void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * Sets the value of hit array.
     * @param hit hit array of Target
     */
    void setHit(int[][] hit) {
        this.hit = hit;
    }

    // Abstract Methods

    /**
     * Defines the behavior when a target is destroyed.
     * Some may explode, while some do nothing.
     */
    abstract void explode();

    /**
     * Returns the type of Target as a String.
     * Every specific type of Target has to override and implement this method
     * and return the corresponding Target type.
     * This method is not case sensitive.
     * @return type of Target as a String
     */
    public abstract String getTargetName();

    // Other Methods

    /**
     * If a part of the Target occupies the given row and column,
     * no matter the target is destroyed or not,
     * mark that part of the Target as "hit" and return true;
     * otherwise return false.
     * @param row row to shot
     * @param column column to shot
     * @return if row,column can be shot
     */
    public boolean getShot(int row, int column) {
        // original destroyed state of Target
        boolean beginState = this.isDestroyed();
        if (this.getHorizontal()) { // horizontal
            // if given row and column are in the range of Target, return true at last
            if (row >= this.getCoordinate()[0] && row < this.getCoordinate()[0] + this.getWidth() &&
                column >= this.getCoordinate()[1] && column < this.getCoordinate()[1] + this.getLength()) {
                // increment value in hit array
                this.getHit()[row - this.getCoordinate()[0]][column - this.getCoordinate()[1]]++;
                // explode only if beginState changes and Target is destroyed
                if (!beginState && this.isDestroyed()) {
                    this.explode();
                }
                return true;
            }
        } else { // vertical
            if (row >= this.getCoordinate()[0] && row < this.getCoordinate()[0] + this.getLength() &&
                column >= this.getCoordinate()[1] && column < this.getCoordinate()[1] + this.getWidth()) {
                this.getHit()[row - this.getCoordinate()[0]][column - this.getCoordinate()[1]]++;
                if (!beginState && this.isDestroyed()) {
                    this.explode();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if every part of the Target has been hit, false otherwise.
     * @return if every part of the Target has been hit
     */
    public boolean isDestroyed() {
        if (this.getHorizontal()) {
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getLength(); j++) {
                    // if any place in hit array is hit, return false
                    if (this.getHit()[i][j] == 0) {
                        return false;
                    }
                }
            }
        } else {
            for (int i = 0; i < this.getLength(); i++) {
                for (int j = 0; j < this.getWidth(); j++) {
                    if (this.getHit()[i][j] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the target has been hit at the given coordinate.
     * This method is used to print the Base.
     * @param row row to test
     * @param column column to test
     * @return if the target has been hit at the given coordinate
     */
    public boolean isHitAt(int row, int column) {
        return this.getHit()[row - this.getCoordinate()[0]][column - this.getCoordinate()[1]] > 0;
    }

    /**
     * Returns a single-character String to use in the Base's print method.
     * This method can be used to print out locations in the Base that have been shot at.
     * @return a single-character String to use in the Base's print method
     */
    public String toString() {
        if (this.isDestroyed()) {
            return new String("X");
        } else {
            return new String("O");
        }
    }
}
