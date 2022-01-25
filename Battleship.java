/*
 * Name:Mohammed Mohsen Al-Shuyukh
 * Student ID: 991349088
 * CS 337 Homework 1: Battleship
 */
import java.util.Scanner;

public class Battleship {
	final static int boardSize = 10;
	char[][] gameBoard = new char[boardSize][boardSize];

	public static void main(String[] args) {
		// Create an instance of the class
		Battleship b1 = new Battleship();
		// Create a scanner input
		Scanner stdin = new Scanner(System.in);
		// Create 4 ints to place the ship
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

		//Number of hits, tries and guesses
		int numhits = 4, numtries = 5, numGuesses = 8;

		//Check when the match is over or not
		boolean match = false;
		// Create two ints for the guesses for player 2
		int guessX = 0, guessY = 0;

		/*
		 * In this process, we're filling the board with periods at every position So
		 * that the other player can choose what they want to do
		 */
		System.out.println("The Battleship game has started" + "\n");
		{
			System.out.print(" ");
			for (int i = 0; i < boardSize; i++) {
				System.out.print(i);
			}
			System.out.println();
			// Printing the numbers in the Y-axis
			for (int i = 0; i < boardSize; i++) {
				System.out.print(i);

				for (int j = 0; j < boardSize; j++) {
					b1.gameBoard[i][j] = '.';
					System.out.print(b1.gameBoard[i][j]);
				}
				System.out.println();
			}
		}

		/*
		 * A do while loop where you place your ship and it will keep looping if you
		 * input invalid coordinates
		 */
		do {
			// Input the valid numbers for your ship
			System.out.print("Enter the x coordinate of the bow:");
			x1 = b1.numCheck(x1, stdin);
			System.out.print("\nEnter the y coordinate of the bow:");
			y1 = b1.numCheck(y1, stdin);
			System.out.print("\nEnter the x coordinate of the Stern:");
			x2 = b1.numCheck(x2, stdin);
			System.out.print("\nEnter the y coordinate of the Stern:");
			y2 = b1.numCheck(y2, stdin);

		} while (b1.isValid(x1, y1, x2, y2) == false); // This would keep looping up until you have

		System.out.println("\033[2J");

		// Print the board again so that the other player can choose which position to hit the target
		b1.printBoard(b1.gameBoard, match);

		// This would keep looping until the number of guesses are 0
		while (numGuesses > 0) {
			// A do while loop to check if player 2's guesses are valid
			do {
				System.out.println("Enter the x value of the guess:");
				guessX = b1.numCheck(guessX, stdin);

				System.out.println("Enter the y value of the guess:");
				guessY = b1.numCheck(guessY, stdin);
			} while (b1.isValidGuess(guessX, guessY, b1.gameBoard) == false);

			// Check if it hit the target
			if (b1.gameBoard[guessX][guessY] == 'X') {
				--numGuesses;
				--numhits;
				// If the number of hits is 0, break out of the loop since the game ended
				if (numhits == 0) {
					break;
				}
				System.out.println(numhits + " parts of the ship remaining");
				// Check if it missed the target
			} else {
				--numGuesses;
				--numtries;
				// If the number of tries is 0, break out of the loop since the game ended
				if (numtries == 0) {
					break;
				}
				System.out.println(numtries + " tries remaining");
			}

			b1.printBoard(b1.gameBoard, match);
		}
		/* The match has been concluded, so we set our boolean match to true and we print all the
		 * visible positions
		 */
		match = true;
		if (numtries > 0) {
			b1.printBoard(b1.gameBoard, match);
			System.out.println("Player 2 wins the Match!");
		} else {
			b1.printBoard(b1.gameBoard, match);
			System.out.println("Player 1 wins the Match!");
		}

	}

	/*
	 * A helper method that would check if the input was an int and not anything
	 * else and it would also check if the number was between [0-9] If not, it would
	 * keep looping until you have a valid number
	 */
	public int numCheck(int giveNum, Scanner check) {
		// Check if there already is an input
		while (check.hasNext()) {
			// Check if it has an int
			if (check.hasNextInt()) {
				giveNum = check.nextInt();
				// Check if the number is between [0-9], if not, continue the loop again
				if (giveNum < 0 || giveNum > 9) {
					System.out.println("Please input a number from [0-9]");
					continue;
					// Continue going through the loop if the number is invalid
				} else {
					// Break out of the loop and return the valid number
					break;
				}
			} else {
				check.next();
				System.out.println("Please input a proper number and not a string");
			}
		}

		return giveNum;
	}

	// This is to check if the placement of the ship is valid
	// Returns true if coordinates describe a valid ship, false otherwise
	public boolean isValid(int x1, int y1, int x2, int y2) {
		// Both Xs are the same, and it's from 0 to 9
		if (x1 == x2) {
			// Check which one is the max Y and which one is the minimum Y
			int maxY = Math.max(y1, y2);
			int minY = Math.min(y1, y2);
			if (maxY - minY != 3) {
				System.out.println("The difference between them isn't 4 spaces");
				return false;
			}
			// Once it's valid, place them on the map;
			// Go from minimum Y to maximum Y
			for (int i = minY; i <= maxY; i++) {
				gameBoard[x1][i] = 'x';
			}
			System.out.println("Valid coordinate inputs" + "\n");
			return true;

		} else if (y1 == y2) {
			int maxX = Math.max(x1, x2);
			int minX = Math.min(x1, x2);
			if (maxX - minX != 3) {
				System.out.println("The difference between them isn't 4 spaces" + "\n");
				return false;
			}
			// Once it's valid, place them on the map;
			// Go from minimum X to maximum X
			for (int i = minX; i <= maxX; i++) {
				gameBoard[i][y1] = 'x';
			}
			System.out.println("Valid coordinate inputs" + "\n");

			return true;
		} else {
			System.out.println("Invalid coordinates. Bow & stern must have the same value for either x or y." + "\n");
			return false;
		}

	}

//	Returns true if the given location is valid and
//	has not previously been guessed, false otherwise.
	public boolean isValidGuess(int x, int y, char[][] board) {

		// If you hit the target
		if (board[x][y] == 'x') {
			// Update the gameboard
			board[x][y] = 'X';
			return true;
		}
		// If you missed the target
		else if (board[x][y] == '.') {
			// Update the gameBoard
			board[x][y] = 'O';
			return true;
		}
		// If you already have input that same position again
		else {
			System.out.println("That position has already been guessed");
			return false;
		}
	}

//	Prints the board as shown in the examples above. If final (result) is false,
//	unguessed ship locations are displayed as periods.
//	If final is true, unguessed ship locations are displayed as lowercase x's.
	public void printBoard(char[][] board, boolean result) {
		{
			System.out.print(" ");
			// Printing the numbers in the Y-axis
			for (int i = 0; i < board.length; i++) {
				System.out.print(i);
			}
			System.out.println();
			for (int i = 0; i < board.length; i++) {
				System.out.print(i);

				for (int j = 0; j < board.length; j++) {
					/*
					 * Game isn't over yet, so you have to hide the ship's position from the other
					 * player
					 */
					if (result == false) {
						// Hide the ship's position from the opponent by printing a period
						if (board[i][j] == 'x') {
							System.out.print('.');
						} else {
							System.out.print(board[i][j]);
						}
					}
					// if result is true, reveal everything since the match has ended
					else {
						System.out.print(board[i][j]);
					}
				}
				System.out.println();
			}
		}
	}

}
