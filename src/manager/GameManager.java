package manager;

import java.util.Scanner;

import board.Coordinate;
import player.Player;

public class GameManager {
	private Player player1;
	private Player player2;
	private Scanner input = new Scanner(System.in);

	public GameManager(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		player1.placeShips();
		player2.placeShips();
	}

	/* This method is responsible for all the game logic */
	public String startGame() {
		System.out.println("---- WELCOME TO THE BATTLESHIP GAME ---");
		System.out.print("\nEnter player 1 name: ");
		String namePlayer1 = input.nextLine().trim();
		System.out.print("\nEnter player 2 name: ");
		String namePlayer2 = input.nextLine().trim();
		player1.setName(namePlayer1);
		player2.setName(namePlayer2);
		int currentTurn = 1;
		System.out.println("\nPlayer 1 starts the game! ");

		while (true) {
			if (currentTurn == 1) {
				boolean validMove = false;
				System.out.println("Player 1: " + player1.getName() + " it's your turn!\n");
				// To be able to see the hidden board
				// player1.printGameBoard();
				player1.printFiringBoard();
				
				while (!validMove) {
					System.out.print("\nSelect the coordinate for your attack: ");
					String coordinate = input.nextLine().toUpperCase().trim();
					/* Validation for the string introduced */
					if (coordinate.length() == 2 && (int) coordinate.charAt(0) >= 65 && (int) coordinate.charAt(0) <= 74
							&& Character.getNumericValue(coordinate.charAt(1)) >= 0
							&& Character.getNumericValue(coordinate.charAt(1)) <= 9) {
						int column = player1.getNumericalValue(coordinate.charAt(0));
						int row = Character.getNumericValue(coordinate.charAt(1));
						validMove = player1.shotTorpedo(new Coordinate(row, column));
						System.out.println(player1.getMove());
					} else {
						System.out.println("Invalid coordinate to attack. Please provide a valid one.");
					}
				}
				
				if (player1.getGameStatus()) {
					break;
				}
				
				System.out.println("Press enter to continue...");
				input.nextLine();
				currentTurn = 2;
				
			} else if (currentTurn == 2) {
				boolean validMove = false;
				System.out.println("Player 2: " + player2.getName() + " it's your turn!\n");
				player2.printFiringBoard();
				
				while (!validMove) {
					System.out.print("\nSelect the coordinate for your attack: ");
					String coordinate = input.nextLine().toUpperCase().trim();
					
					/* Validation for the string introduced */
					if (coordinate.length() == 2 && (int) coordinate.charAt(0) >= 65 && (int) coordinate.charAt(0) <= 74
							&& Character.getNumericValue(coordinate.charAt(1)) >= 0
							&& Character.getNumericValue(coordinate.charAt(1)) <= 9) {
						int column = player2.getNumericalValue(coordinate.charAt(0));
						int row = Character.getNumericValue(coordinate.charAt(1));
						validMove = player2.shotTorpedo(new Coordinate(row, column));
						System.out.println(player2.getMove());
					} else {
						System.out.println("Invalid coordinate to attack. Please provide a valid one.");
					}
				}
				
				if (player2.getGameStatus()) {
					break;
				}
				
				System.out.println("Press enter to continue...");
				input.nextLine();
				currentTurn = 1;
			} else {
				break;
			}
		}

		/* Sending the message telling who is the winner */
		if (player1.getGameStatus()) {
			return "You have sunk all your opponent's battleships. PLAYER 1 \"" + player1.getName() + "\" win!\n"
					+ "Player 1 wins in " + player1.getShots().size() + " turns";
		} else {
			return "You have sunk all your opponent's battleships. PLAYER 2 \"" + player2.getName() + "\" win!\n"
					+ "Player 2 wins in " + player2.getShots().size() + " turns";
		}
	}
}
