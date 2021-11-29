package maingame;

import player.Player;
import manager.GameManager;

public class BattleshipGame {
	
	public static void main(String[] args) {
		Player player1 = new Player();
		Player player2 = new Player();
		
		GameManager manager = new GameManager(player1, player2);
		String winner = manager.startGame();
		System.out.println(winner);
		
		/* Discover the location of the ships */
		System.out.println("\n---- Final winning board ships locations ----\n");
		if (player1.getGameStatus()) {
			player1.printGameBoard();
		} else if (player2.getGameStatus()) {
			player2.printGameBoard();
		}
	}
}
