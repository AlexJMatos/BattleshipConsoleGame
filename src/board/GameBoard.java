package board;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
	private List<Cell> board;
	
	public GameBoard() {
		this.board = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board.add(new Cell(i, j));
			}
		}
	}

	public List<Cell> getBoard() {
		return board;
	}

	public void setBoard(List<Cell> board) {
		this.board = board;
	}
}
