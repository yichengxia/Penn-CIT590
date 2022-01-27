/**
 * Name: Yicheng Xia
 * Penn ID: 65349745
 * Statement of work: I worked alone without help.
 */
package hw7;

import java.util.Scanner;

class HelloWorld {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); // initiate a scanner to take input from the keyboard

        /*
         * Say Hello
         * Ask the user to enter their full name.
         * The user should type in their first name and last name, separated by a space.
         * Print “Hello, <full name>!” where <full name> gets replaced by the full name of the user.
         */
        System.out.println("Please enter your full name:");
        String name = scan.nextLine(); // read in the entire next line to ignore spaces
        System.out.println("Hello, " + name + "!");

        /*
         * Add Five Numbers
         * Ask the user to enter a total of 5 numbers (ints or doubles), and hit enter after each.
         * Assume each number is an int or a double.
         * Print the sum (as a double) of all the numbers each time.
         */
        System.out.println();
        System.out.println("----------");
        System.out.println();
        System.out.println("Please enter 5 numbers (ints or doubles), and hit enter after each:");
        int count = 5; // initiate count number
        double sum = 0.0; // initiate sum value
        while (count > 0) {
            String number = scan.next(); // read in the next String
            // add the double by the specified String, done by the method under class Double
            sum += Double.parseDouble(number); 
            System.out.println("Current sum is: " + sum);
            count--;
        }

        /* 
         * Even or Odd
         * Ask the user to enter an integer.
         * Check if the number is even or odd.
         * Assume this will be a positive integer.
         * If it is even, print “<number> is even”, where <number> gets replaced by the number.
         * If it is odd, print “<number> is odd”, where <number> gets replaced by the number.
         */
        System.out.println();
        System.out.println("----------");
        System.out.println();
        System.out.println("Please enter an integer: ");
        int integer = scan.nextInt(); // read in the next int
        if (integer % 2 == 0) { // if integer % 2 == 0, then integer is even
            System.out.println(integer + " is even");
        } else { // else integer is odd
            System.out.println(integer + " is odd");
        }

        /*
         * Prime or Composite
         * Ask the user to enter a positive integer.
         * Assume this will be a positive integer.
         * If it is prime, print “<number> is prime”, where <number> gets replaced by the number.
         * If it is composite, print “<number> is composite”, where <number> gets replaced by the number.
         * If the number is 1, print 1.
         */
        System.out.println();
        System.out.println("----------");
        System.out.println();
        System.out.println("Please enter a positive integer: ");
        int positiveInt = scan.nextInt(); // read in the next int
        if (positiveInt == 1) {
            System.out.println(1);
        } else {
            int i;
            // check if positiveInt is composite with number from 2 to Math.sqrt(positiveInt)
            for (i = 2; i <= Math.sqrt(positiveInt); i++) {
                if (positiveInt % i == 0) {
                    System.out.println(positiveInt + " is composite");
                    break;
                }
            }
            // after the for loop, if every possible i is examined, then positiveInt is prime
            if (i > Math.sqrt(positiveInt)) {
                System.out.println(positiveInt + " is prime");
            }
        }

        /*
         * Convert Seconds to Time
         * Ask the user to enter some number of seconds (as an int) and convert it to hours:minutes:seconds.
         * For example, if input seconds is 1432, print output in the format: 0:23:52
         * If input seconds is 0, print output in the format: 0:0:0
         * If input seconds is negative, print output in the format: -1:-1:-1
         */
        System.out.println();
        System.out.println("----------");
        System.out.println();
        System.out.println("Please enter some number of seconds: ");
        int numSeconds = scan.nextInt(); // read in the next int
        if (numSeconds < 0) {
            System.out.println("-1:-1:-1");
        } else {
            // get seconds by % 60, then / 60
            int seconds = numSeconds % 60;
            numSeconds /= 60;
            // get minutes by % 60, then / 60
            int minutes = numSeconds % 60;
            numSeconds /= 60;
            // the remaining value in numSeconds is hours
            int hours = numSeconds;
            System.out.println(hours + ":" + minutes + ":" + seconds);
        }

        /*
         * Say Goodbye
         * Print “Goodbye, <full name>!” where <full name> gets replaced by the full name of the user.
         */
        System.out.println();
        System.out.println("----------");
        System.out.println();
        System.out.println("Goodbye! " + name + "!");

        scan.close(); // close the scanner
    }
}
