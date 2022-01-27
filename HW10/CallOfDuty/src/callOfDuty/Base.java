package callOfDuty;

import java.util.Random;

/**
 * This contains a 10x10 array of Targets, representing a "base", and some methods to manipulate it.
 * Think of it as the map in this game.
 * @author Yicheng Xia
 */
public class Base {
    
    // Instance variables

    /**
     * Keeps a reference to the location of every Target in the game.
     * Every location in this array points to a Target,
     * specifically, an instance of a subclass of Target.
     */
    private Target[][] targets;

    /**
     * The total number of shots fired by the user.
     */
    private int shotsCount;

    /**
     * The number of targets destroyed.
     */
    private int destroyedTargetCount;

    // Constructor

    /**
     * Creates an 10x10 "empty" Base (and fills the Targets array with Ground objects).
     */
    public Base() {
        this.shotsCount = 0;
        this.destroyedTargetCount = 0;
        this.targets = new Target[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // initiate targets by calling placeTargetAt() and pointing every cell to a new Ground instance
                placeTargetAt(new Ground(this), i, j, true);
            }
        }
    }

    // Methods

    /**
     * Create and place all Targets randomly on the Base (initially filled with Ground).
     */
    public void placeAllTargetRandomly() {
        int row, column, i = 0; // use i to indicate the number of set targets
        boolean horizontal;
        // set 1 headquater
        while (i == 0) { // loop until i is above the threshold
            // generate random row and column with int from 0 to 9
            row = new Random().nextInt(10);
            column = new Random().nextInt(10);
            // if random number is from 0 to 0.5, then horizontal is true
            // if random number is from 0.5 to 1, then horizontal is false
            horizontal = Math.random() < 0.5 ? true : false;
            // generate a HeadQuarter instance to test if it is ok to place target at
            if (okToPlaceTargetAt(new HeadQuarter(this), row, column, horizontal)) {
                // if so, put a new HeadQuarter with valid row, column, and horizontal
                placeTargetAt(new HeadQuarter(this), row, column, horizontal);
                // increment i after a successful placement
                i++;
            }
        }
        // set 2 armories
        while (i < 3) {
            row = new Random().nextInt(10);
            column = new Random().nextInt(10);
            horizontal = Math.random() < 0.5 ? true : false;
            if (okToPlaceTargetAt(new Armory(this), row, column, horizontal)) {
                placeTargetAt(new Armory(this), row, column, horizontal);
                i++;
            }
        }
        // set 3 barracks
        while (i < 6) {
            row = new Random().nextInt(10);
            column = new Random().nextInt(10);
            horizontal = Math.random() < 0.5 ? true : false;
            if (okToPlaceTargetAt(new Barrack(this), row, column, horizontal)) {
                placeTargetAt(new Barrack(this), row, column, horizontal);
                i++;
            }
        }
        // set 4 sentry towers
        while (i < 10) {
            row = new Random().nextInt(10);
            column = new Random().nextInt(10);
            horizontal = Math.random() < 0.5 ? true : false;
            if (okToPlaceTargetAt(new SentryTower(this), row, column, horizontal)) {
                placeTargetAt(new SentryTower(this), row, column, horizontal);
                i++;
            }
        }
        // set 4 tanks
        while (i < 14) {
            row = new Random().nextInt(10);
            column = new Random().nextInt(10);
            horizontal = Math.random() < 0.5 ? true : false;
            if (okToPlaceTargetAt(new Tank(this), row, column, horizontal)) {
                placeTargetAt(new Tank(this), row, column, horizontal);
                i++;
            }
        }
        // set 4 oil drums
        while (i < 18) {
            row = new Random().nextInt(10);
            column = new Random().nextInt(10);
            horizontal = Math.random() < 0.5 ? true : false;
            if (okToPlaceTargetAt(new OilDrum(this), row, column, horizontal)) {
                placeTargetAt(new OilDrum(this), row, column, horizontal);
                i++;
            }
        }
    }

    /**
     * Based on the given row, column, and orientation,
     * returns true if it is okay to put the Target with its head at this location; false otherwise.
     * @param target target to put
     * @param row row to put target in
     * @param column column to put target in
     * @param horizontal horizontal of target
     * @return if it is okay to put the Target
     */
    public boolean okToPlaceTargetAt(Target target, int row, int column, boolean horizontal) {
        // if target is tank or oil drum, test if the corresponding row and column is occupied by another target
        if (target.getTargetName().toLowerCase().equals("tank") || target.getTargetName().toLowerCase().equals("oildrum")) {
            return !isOccupied(row, column);
        }
        if (horizontal) {
            // horizontal target: increment row number in the range of target.getWidth() + 2
            // increment column number in the range of target.getLength() + 2
            for (int i = row - 1; i <= row + target.getWidth(); i++) {
                for (int j = column - 1; j <= column + target.getLength(); j++) {
                    if (!isInTarget(target, i, j, row, column, horizontal)) {
                        // if (i, j) is not in target (testing adjacent buildings)
                        if (!isValid(i, j)) { // if (i, j) is not valid in base, continue test others
                            continue;
                        }
                        if (isBuilding(i, j)) { // return false when finding adjacent buildings
                            return false;
                        }
                    } else if (!isValid(i, j) || isOccupied(i, j)) {
                        // if (i, j) is in target (testing places to put targets)
                        // return false if (i, j) is not valid in base, or occupied by other targets
                        return false;
                    }
                }
            }
            // all other cases are true
            return true;
        } else {
            // vertical target: increment row number in the range of target.getLength() + 2
            // increment column number in the range of target.getWidth() + 2
            for (int i = row - 1; i <= row + target.getLength(); i++) {
                for (int j = column - 1; j <= column + target.getWidth(); j++) {
                    if (!isInTarget(target, i, j, row, column, horizontal)) {
                        if (!isValid(i, j)) {
                            continue;
                        }
                        if (isBuilding(i, j)) {
                            return false;
                        }
                    } else if (!isValid(i, j) || isOccupied(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
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

    /**
     * Helper method.
     * Returns true if row i and column j are in target, false otherwise.
     * @param target target to test
     * @param i row to test
     * @param j column to test
     * @param row total row of target
     * @param column total column of target
     * @param horizontal horizontal of target
     * @return if row i and column j are in target
     */
    private boolean isInTarget(Target target, int i, int j, int row, int column, boolean horizontal) {
        return i >= row && i < row + target.getWidth() && j >= column && j < column + target.getLength() ? horizontal :
            i >= row && i < row + target.getLength() && j >= column && j < column + target.getWidth();
    }

    /**
     * Helper method.
     * Returns true if row and column have building in targets, false otherwise.
     * @param row row to test
     * @param column column to test
     * @return if row and column have building in targets
     */
    private boolean isBuilding(int row, int column) {
        String name = this.targets[row][column].getTargetName().toLowerCase();
        return name.equals("headquarter") || name.equals("armory") || name.equals("barrack") || name.equals("sentrytower");
    }

    /**
     * Sets the value of the "hit" array, "coordinate" array, and "horizontal" boolean value of the target.
     * @param target target to put
     * @param row row to place target at
     * @param column column to place target at
     * @param horizontal horizontal of target
     */
    public void placeTargetAt(Target target, int row, int column, boolean horizontal) {
        target.setCoordinate(new int[]{row, column});
        target.setHorizontal(horizontal);
        if (horizontal) {
            // horizontal target: increment row number in the range of target.getWidth()
            // increment column number in the range of target.getLength()
            target.setHit(new int[target.getWidth()][target.getLength()]);
            for (int i = row; i < row + target.getWidth(); i++) {
                for (int j = column; j < column + target.getLength(); j++) {
                    this.targets[i][j] = target;
                }
            }
        } else {
            // vertical target: increment row number in the range of target.getLength()
            // increment column number in the range of target.getWidth()
            target.setHit(new int[target.getLength()][target.getWidth()]);
            for (int i = row; i < row + target.getLength(); i++) {
                for (int j = column; j < column + target.getWidth(); j++) {
                    this.targets[i][j] = target;
                }
            }
        }
    }

    /**
     * Returns true if the given location contains a Target (not a Ground), false if it does not.
     * @param row row to test
     * @param column column to test
     * @return if the given location contains a Target (not a Ground)
     */
    public boolean isOccupied(int row, int column) {
        return !this.targets[row][column].getTargetName().toLowerCase().equals("ground");
    }

    /**
     * Attack the position specified by the row and the column.
     * @param row row to shoot at
     * @param column column to shoot at
     */
    public void shootAt(int row, int column) {
        this.targets[row][column].getShot(row, column);
    }

    /**
     * Returns true if run out of ammunition or if all targets have been destroyed.
     * Otherwise return false.
     * @param weapon1 first weapon to test
     * @param weapon2 second weapon to test
     * @return if run out of ammunition or all targets have been destroyed
     */
    public boolean isGameOver(Weapon weapon1, Weapon weapon2) {
        return (weapon1.getShotLeft() == 0 && weapon2.getShotLeft() == 0) || this.win();
    }

    /**
     * Returns true if all targets have been destroyed.
     * @return if all targets have been destroyed
     */
    public Boolean win() {
        return this.destroyedTargetCount == 18;
    }

    /**
     * Prints the Base.
     */
    public void print() {
        System.out.print("  "); // start with 2 whitespaces
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " "); // column number
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " "); // start with row number
            for (int j = 0; j < 10; j++) {
                if (this.targets[i][j].isHitAt(i, j)) {
                    // if hit, print by calling toString() in Target
                    System.out.print(this.targets[i][j].toString() + " ");
                } else {
                    // other wise just print period
                    System.out.print(new String(". "));
                }
            }
            System.out.println();
        }
    }

    /**
     * Returns the number of shots fired.
     * @return number of shots fired
     */
    public int getShotsCount() {
        return this.shotsCount;
    }

    /**
     * Returns the targets array.
     * @return targets array
     */
    public Target[][] getTargetsArray() {
        return this.targets;
    }

    /**
     * This method will be called from shootAt(int row, int column) from Weapon class.
     */
    public void incrementShotsCount() {
        this.shotsCount++;
    }

    /**
     * Returns the count of destroyed targets.
     * @return count of destroyed targets
     */
    public int getDestroyedTargetCount() {
        return this.destroyedTargetCount;
    }

    /**
     * Sets the count of destroyed targets.
     * @param destroyedTargetCount count of destroyed targets
     */
    public void setDestroyedTargetCount(int destroyedTargetCount) {
        this.destroyedTargetCount = destroyedTargetCount;
    }
}
