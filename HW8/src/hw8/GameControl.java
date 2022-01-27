package hw8;

import java.util.Random;
import java.util.Scanner;

public class GameControl {
	
	/**
	 * Computer player
	 */
	Computer computer;
	
	/**
	 * Human player
	 */
	Human human;
	
	/**
	 * A random number generator to be used for returning random dice result (1-6) for both computer and human player
	 */
	Random random = new Random();
	
	/**
	 * The main method just creates a GameControl object and calls its run method
	 * @param args not used
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		System.out.println("Welcome to Pig!");
		
		GameControl gc = new GameControl();
		while (true) {
			// call run() method to enter the game
			gc.run(sc);
			System.out.println("--------------------");
			System.out.println("Do you want to play again?");
			// ask if human wants one more play
			boolean check = gc.askYesNo(sc);
			// if not, print goodbye message, close scanner and break loop
			if (!check) {
				System.out.println("Goodbye!");
				sc.close();
				break;
			}
		}
	}
	
	/**
     * Calls methods to perform each of the following actions:
     * - Ask user to input the name for human player, and print welcome with the name
     * - Create the players (one human and one computer)
     * - Control the play of the game
     * - Print the final results
	 * @param sc to use for getting user input
	 */
	public void run(Scanner sc) {
		// ask user for the name
		System.out.println("Please input your name:");
		String humanName = sc.nextLine(); // include spaces
		System.out.println("Welcome! " + humanName + "!");

		// initiate Computer and Human instances
		createPlayers(humanName);

		while (true) {
			// get and print the score one this round for computer
			int scoreOneRoundComp = computer.move(human, random);
			System.out.println("Computer's score in this round: " + scoreOneRoundComp);
			// update computer's score and print total score
			computer.setScore(scoreOneRoundComp + computer.getScore());
			System.out.println("Computer's total score: " + computer.getScore());
			System.out.println();

			// get and print the score one this round for human
			int scoreOneRoundHum = human.move(computer, random, sc);
			System.out.println(human.getName() + "'s score in this round: " + scoreOneRoundHum);
			// update human's score and print total score
			human.setScore(scoreOneRoundHum + human.getScore());
			System.out.println(human.getName() + "'s total score: " + human.getScore());
			System.out.println();
			
			// check if winner appears, then print results and winner
			// check after human's turn, since human gets one more turn if computer first reaches 50
			if (checkWinningStatus()) {
				printResults();
				printWinner();
				break;
			}
		}
	}
	
	/**
     * Creates one human player with the given humanName, and one computer player with a name.
     * @param humanName for human player
     */
	public void createPlayers(String humanName) {
		computer = new Computer();
		human = new Human(humanName);
	}
	
	/**
     * Checks if a winning status has been achieved
     * @return true if one player has won the game
     */
	public boolean checkWinningStatus() {
		// winner appears only after any side reaches 50
		// each gets another turn until the tie is broken if both reaches the same score (> 50)
		return (computer.getScore() >= 50 || human.getScore() >= 50) && computer.getScore() != human.getScore();
	}
	
	/**
	 * Prints the final scores of the human player and computer player
	 */
	public void printResults() {
		// call getScore() and getName() for printing result messages
		System.out.println("Computer's total score is: " + computer.getScore());
		System.out.println(human.getName() + "'s total score is: " + human.getScore());
	}
	
	/**
     * Determines who won the game, and prints the results
     */
	public void printWinner() {
		String winner;
		// call getScore() to get scores for comparisons
		if (computer.getScore() > human.getScore()) {
			winner = "Computer";
		} else { // we have already excluded tie cases
			winner = "Human";
		}
		System.out.println(winner + " wins!");
	}
	
	/**
	 * If the user responds with a string "y" or "Y", the function returns True. 
	 * If the user responds with a string "n" or "N", the function returns False. 
	 * Any other response will cause the question to be repeated until the user provides an acceptable response. 
	 * @param sc to use for getting user input
	 * @return true if user responds with "y" or "Y"
	 */
	public boolean askYesNo(Scanner sc) {
		String yesNo = sc.nextLine(); // include spaces
		if (yesNo.length() == 0) { // exclude empty strings and return the method again
			System.out.println("Do you want to play again?");
			return askYesNo(sc);
		} else if (yesNo.charAt(0) == 'y' || yesNo.charAt(0) == 'Y') { // check if first letter is 'y' or 'Y'
			return true;
		} else if (yesNo.charAt(0) == 'n' || yesNo.charAt(0) == 'N') { // check if first letter is 'n' or 'N'
			return false;
		} else { // exclude all other strings and return the method again
			System.out.println("Do you want to play again?");
			return askYesNo(sc);
		}
	}
	
}
