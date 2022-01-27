package callOfDuty;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * This is the "main" class, containing the main method, which starts by creating an instance of Base.
 * @author Yicheng Xia
 */
public class CallOfDutyGame {
    
    /**
     * Creates Base, RocketLauncher, and Missile instances and plays the game.
     * @param args not used
     */
    public static void main(String[] args) {
        // welcome message
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Call of Duty!");
        System.out.println("Please enter your name:");
        String username = sc.nextLine().strip();
        System.out.println("Hi, " + username + "! Game starts now!");
        System.out.println("------------------------------");

        // initiate Base, RocketLauncher, and Missile instances
        // call placeAllTargetRandomly() to generate all targets
        Base base = new Base();
        base.placeAllTargetRandomly();
        RocketLauncher rocketLauncher = new RocketLauncher();
        Missile missile = new Missile();
        // set defaultCount to be 0
        int defaultCount = 0;

        // game loop starts
        while (true) {
            // print base and shots information
            base.print();
            System.out.println("RPG: " + rocketLauncher.getShotLeft() + ", Missile: " + missile.getShotLeft());
            
            // get defaultWeapon based on defaultCount
            Weapon defaultWeapon = getDefaultWeapon(rocketLauncher, missile, defaultCount);

            int row = 0, column = 0;
            while (true) {
                // check if game is over in every loop
                if (base.isGameOver(rocketLauncher, missile)) {
                    System.out.println("Sorry! You are out of ammo!");
                    break;
                }
                System.out.println("Your current weapon is: " + defaultWeapon.getWeaponType());
                System.out.print("Enter row,column or q to switch a weapon: ");
                // strip all whitespaces in user's action and change it to lower case
                String action = sc.nextLine().replaceAll("\\s+", "").toLowerCase();
                if (action.equals("q")) { // change weapon
                    defaultCount++;
                    defaultWeapon = getDefaultWeapon(rocketLauncher, missile, defaultCount);
                    // check if defaultWeapon has shot left
                    if (defaultWeapon.getShotLeft() == 0) {
                        System.out.println("Sorry! You are out of " + defaultWeapon.getWeaponType() + "!");
                        continue; // loop again for user to choose weapon again
                    }
                } else { // not change weapon
                    if (defaultWeapon.getShotLeft() == 0) {
                        System.out.println("Sorry! You are out of " + defaultWeapon.getWeaponType() + "!");
                        continue;
                    }
                    // turn coordinate string into array
                    String[] coordinate = action.split(",");
                    if (coordinate.length >= 2) {
                        try {
                            row = Integer.parseInt(coordinate[0]);
                            column = Integer.parseInt(coordinate[1]);
                        } catch (NumberFormatException e) { // catch incorrect format
                            System.out.println("Invalid coordinate!");
                            continue;
                        }
                        if (isValid(row, column)) { // not accept invalid coordinate outside of base
                            break;
                        } else {
                            System.out.println("Invalid coordinate!");
                        }
                    }
                }
            }

            defaultWeapon.shootAt(row, column, base);

            // take every target into a set
            Set<Target> targetSet = new HashSet<>();
            int destroyedTargetCount = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    targetSet.add(base.getTargetsArray()[i][j]);
                }
            }
            // check the destroyed status of every target in the set
            for (Target t : targetSet) {
                if (t.isDestroyed()) {
                    destroyedTargetCount++;
                }
            }
            // update destroyedTargetCount
            base.setDestroyedTargetCount(destroyedTargetCount);

            // win
            if (base.win()) {
                base.print();
                System.out.println("Congratulations! You win! :)");
                System.out.println("------------------------------");
                if (playAgain(sc)) {
                    // initiate base, rocketLauncher, missile, and defaultCount again
                    // call placeAllTargetRandomly() again
                    base = new Base();
                    base.placeAllTargetRandomly();
                    rocketLauncher = new RocketLauncher();
                    missile = new Missile();
                    defaultCount = 0;
                } else {
                    break;
                }
            }

            // not win but game is over
            if (base.isGameOver(rocketLauncher, missile)) {
                base.print();
                System.out.println("Sorry, you failed the game. :(");
                System.out.println("------------------------------");
                if (playAgain(sc)) {
                    base = new Base();
                    base.placeAllTargetRandomly();
                    rocketLauncher = new RocketLauncher();
                    missile = new Missile();
                    defaultCount = 0;
                } else {
                    break;
                }
            }
        }

        // goodbye message
        System.out.println("Goodbye! " + username + "!");
        sc.close();
    }

    /**
     * Calculate for weapon change.
     * Returns by judging if the defaultCount is even or not.
     * If defaultCount is even, return rocketLauncher.
     * If defaultCount is odd, return missile.
     * @param rocketLauncher rocketLauncher Weapon instance
     * @param missile missile instance Weapon instance
     * @param defaultCount count weapon change from 0
     * @return rocketLauncher or missile by judging if the defaultCount is even or not
     */
    private static Weapon getDefaultWeapon(RocketLauncher rocketLauncher, Missile missile, int defaultCount) {
        return defaultCount % 2 == 0 ? rocketLauncher : missile;
    }

    /**
     * Helper method.
     * Returns true if row and column are valid in base, false otherwise.
     * @param row row to test
     * @param column column to test
     * @return if row and column are valid in base
     */
    private static boolean isValid(int row, int column) {
        return row >= 0 && row <= 9 && column >= 0 && column <= 9;
    }

    /**
     * Helper method.
     * Returns true if user wants to play again, false if not. Otherwise return itself again.
     * @param sc scanner
     * @return if user wants to play again
     */
    private static boolean playAgain(Scanner sc) {
        System.out.println("Would you like to play again? (y/n)");
        String ans = sc.nextLine().strip().toLowerCase();
        if (ans.length() > 0 && ans.charAt(0) == 'y') { // if the first char exist and is 'y'
            return true;
        } else if (ans.length() > 0 && ans.charAt(0) == 'n') { // if the first char exist and is 'n'
            return false;
        } else { // else return itself until getting a valid input
            System.out.println("Sorry! Invalid input!");
            return playAgain(sc);
        }
    }
}
