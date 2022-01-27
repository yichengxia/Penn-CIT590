package hw8;

import java.util.Random;
import java.util.Scanner;

public class Human {
	
	/**
	 * The name of the player
	 */
	private String name;
	
	/**
	 * The score of the current player
	 */
	private int score = 0;
	
	
	/**
	 * Constructs a human player with the given name
	 * @param name of human
	 */
	public Human(String name) {
		this.name = name;
	}

	/**
	 * Controls human player to roll the dice, and prints the related information for each roll.
	 * First of all, this method will automatically roll one time for the human player, 
	 * if the result is 6, the player will have no right to roll again and this method should return 0; 
	 * else, this roll will be added to the total score for this turn.
	 * User should answer Y or N (y or n) to determine whether they want to roll the dice again 
	 * or stop with the current score.
	 * 
	 * This method will also update the human's total score.
	 * -- You can either add the total of all the rolls to the human's total score, within this move method
	 * e.g. this.score += scoreOneRound;
	 *  
	 * -- or you can call the setScore method from outside of this class, after calling this move method 
	 * e.g. int scoreOneRound = human.move(computer, random, sc);
	 *      human.setScore(scoreOneRound + human.getScore());
	 * 
	 * @param computer player
	 * @param random number generator
	 * @param sc to get input from user
	 * @return the score this round (for example, 
	 * 1. the player rolls: 2, 6, then this method should return 0
	 * 2. the player rolls: 5, 5, 2, then this method should return 12
	 * )
	 */
	public int move(Computer computer, Random random, Scanner sc) {
		// intiate the score for one round
		int scoreOneRound = 0;
		while (true) {
			// generate random dice number
			int scoreOneRoll = random.nextInt(6) + 1;
			if (scoreOneRoll == 6) {
				System.out.println(this.name + "'s roll: 6");
				return 0;
			} else {
				System.out.println(this.name + "'s roll: " + scoreOneRoll);
				// update the score for one round
				scoreOneRound += scoreOneRoll;
				System.out.println("Do you want to roll again?");
				// call askYesNo() to ask human
				// if the condition is false, return the score for one round to exit loop
				if (!askYesNo(sc)) {
					return scoreOneRound;
				}
			}
		}
	}
	
	/**
	 * Get the name of human
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the score of human
	 * @return score
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Set the score of human
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * If the user responds with a string "y" or "Y", the function returns True. 
	 * If the user responds with a string "n" or "N", the function returns False. 
	 * Any other response will cause the question to be repeated until the user provides an acceptable response. 
	 * @param sc to use for getting user input
	 * @return true if user responds with "y" or "Y"
	 */
	private boolean askYesNo(Scanner sc) {
		String yesNo = sc.nextLine(); // include spaces
		if (yesNo.length() == 0) { // exclude empty strings and return the method again
			System.out.println("Do you want to roll again?");
			return askYesNo(sc);
		} else if (yesNo.charAt(0) == 'y' || yesNo.charAt(0) == 'Y') { // check if first letter is 'y' or 'Y'
			return true;
		} else if (yesNo.charAt(0) == 'n' || yesNo.charAt(0) == 'N') { // check if first letter is 'n' or 'N'
			return false;
		} else { // exclude all other strings and return the method again
			System.out.println("Do you want to roll again?");
			return askYesNo(sc);
		}
	}
	
}
