package player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import board.Cell;
import board.Coordinate;
import board.GameBoard;
import board.ShipTypes;
import ships.Battleship;
import ships.Cruiser;
import ships.Destroyer;
import ships.Ship;
import ships.Submarine;

public class Player {
	private String name;
	private GameBoard shipsBoard;
	private GameBoard firingBoard;
	private List<Ship> ships;
	private List<Cell> shots;
	private List<ShipTypes> shotsLegend;
	private String move = "";

	public Player() {
		/* Adding the ships */
		this.ships = new ArrayList<>();
		ships.add(new Submarine());
		ships.add(new Destroyer());
		ships.add(new Destroyer());
		ships.add(new Cruiser());
		ships.add(new Cruiser());
		ships.add(new Battleship());
		
		/* Creating board */ 
		shipsBoard = new GameBoard();
		firingBoard = new GameBoard();
		shots = new ArrayList<>();
		shotsLegend = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameBoard getShipsBoard() {
		return shipsBoard;
	}

	public void setShipsBoard(GameBoard shipsBoard) {
		this.shipsBoard = shipsBoard;
	}

	public GameBoard getFiringBoard() {
		return firingBoard;
	}

	public void setFiringBoard(GameBoard firingBoard) {
		this.firingBoard = firingBoard;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public List<Cell> getShots() {
		return shots;
	}

	public void setShots(List<Cell> shots) {
		this.shots = shots;
	}

	public List<ShipTypes> getShotsLegend() {
		return shotsLegend;
	}

	public void setShotsLegend(List<ShipTypes> shotsLegend) {
		this.shotsLegend = shotsLegend;
	}

	public String getMove() {
		return move;
	}

	private void setMove(String move) {
		this.move = move;
	}

	/* Method for placing the ships in a random way and with different orientation */
	public void placeShips() {
		/*
		 * Select a random row/column combination, then select a random orientation. If
		 * none of the proposed panels are occupied, place the ship Do this for all
		 * ships
		 */
		for (Ship ship : ships) {
			boolean isFree = true;
			Random random = new Random();

			while (isFree) {
				int startColumn = random.nextInt(9) + 1;
				int startRow = random.nextInt(9) + 1;
				int endColumn = startColumn;
				int endRow = startRow;
				int orientation = (random.nextInt(100) + 1) % 2; // 0 for horizontal, 1 for vertical

				if (orientation == 0) {
					for (int i = 1; i < ship.getWidth(); i++) {
						endRow++;
					}
				} else {
					for (int i = 1; i < ship.getWidth(); i++) {
						endColumn++;
					}
				}

				/* Avoid placing ships beyond the boundaries of the board */
				if (endRow > 9 || endColumn > 9) {
					isFree = true;
					continue;
				}

				final int startC = startColumn;
				final int endC = endColumn;
				final int startR = startRow;
				final int endR = endRow;

				var affectedCells = shipsBoard.getBoard().stream()
						.filter(cell -> cell.getCoordinate().getColumn() >= startC
								&& cell.getCoordinate().getColumn() <= endC && cell.getCoordinate().getRow() >= startR
								&& cell.getCoordinate().getRow() <= endR)
						.collect(Collectors.toList());

				if (affectedCells.stream().anyMatch(cell -> cell.isOccupied())) {
					isFree = true;
					continue;
				}

				for (Cell cell : affectedCells) {
					cell.setShipType(ship.getType());
				}

				isFree = false;
			}
		}
	}

	/* Method for printing the game board with the ships revealed */
	/* Use for testing purposes */
	public void printGameBoard() {
		System.out.println("    A   B   C   D   E   F   G   H   I   J");
		System.out.println("  -----------------------------------------");
		int rows = 0;
		System.out.print("" + rows + " | ");
		rows++;
		for (Cell cell : shipsBoard.getBoard()) {
			if (cell.getCoordinate().getColumn() == 9) {
				System.out.println(cell.getStatus() + " | ");
				if (cell.getCoordinate().getRow() < 9) {
					System.out.println("  -----------------------------------------");
					System.out.print("" + rows + " | ");
					rows++;
				} else {
					System.out.println("  -----------------------------------------");
				}
			} else {
				System.out.print(cell.getStatus() + " | ");
			}
		}
	}

	/* Method for printing the game board with ships hidden */
	public void printFiringBoard() {
		System.out.println("    A   B   C   D   E   F   G   H   I   J");
		System.out.println("  -----------------------------------------");
		int rows = 0;
		System.out.print("" + rows + " | ");
		rows++;
		for (Cell cell : firingBoard.getBoard()) {
			if (cell.getCoordinate().getColumn() == 9) {
				System.out.println(cell.getStatus() + " | ");
				if (cell.getCoordinate().getRow() < 9) {
					System.out.println("  -----------------------------------------");
					System.out.print("" + rows + " | ");
					rows++;
				} else {
					System.out.println("  -----------------------------------------");
				}
			} else {
				System.out.print(cell.getStatus() + " | ");
			}
		}
	}

	public boolean shotTorpedo(Coordinate coordinate) {
		ShipTypes shot = ShipTypes.Hit;
		boolean isValidMove = false;

		/* Check if the coordinate hasn't been shoot */
		boolean alreadyShot = shots.stream()
				.filter(cell -> cell.getCoordinate().getColumn() == coordinate.getColumn()
						&& cell.getCoordinate().getRow() == coordinate.getRow())
				.findAny().orElse(null) == null ? false : true;

		if (!alreadyShot) {
			/* Check if coordinate hit or miss coordinate status */
			Cell choosenCell = shipsBoard.getBoard().stream()
					.filter(cell -> cell.getCoordinate().getColumn() == coordinate.getColumn()
							&& cell.getCoordinate().getRow() == coordinate.getRow())
					.findAny().orElse(null);

			if (choosenCell.isOccupied()) {
				shot = ShipTypes.Hit;
				hitTorpedo(choosenCell);
				setMove("Successful attack!\n");
			} else {
				shot = ShipTypes.Miss;
				missTorpedo(choosenCell);
				setMove("Missed attack!\n");
			}

			/* Setting the status to the firing board */
			for (Cell cell : firingBoard.getBoard()) {
				if (cell.getCoordinate().getColumn() == coordinate.getColumn()
						&& cell.getCoordinate().getRow() == coordinate.getRow()) {
					cell.setShipType(shot);
				}
			}
			isValidMove = true;
		} else {
			setMove("You have already attack in this cell!");
			isValidMove = false;
		}
		return isValidMove;
	}

	/* In case there is a hit */
	private void hitTorpedo(Cell hitCell) {
		/* Adding the shot to the player shots */
		shots.add(hitCell);
		/* Adding the hit or miss legend to the legendShots */
		shotsLegend.add(ShipTypes.Hit);

		/* Increasing the hits to the corresponding ship */
		for (Ship ship : ships) {
			if (ship.getType() == hitCell.getShipType() && !ship.isSunk()) {
				ship.setOneHit();
				break;
			}
		}
	}

	/* In case there is a miss */
	private void missTorpedo(Cell missedCell) {
		/* Adding the shot to the player shots */
		shots.add(missedCell);
		/* Adding the hit or miss legend to the legendShots */
		shotsLegend.add(ShipTypes.Miss);
	}

	/* Check whether all the ships are sunk */
	public boolean getGameStatus() {
		return ships.stream().allMatch(ship -> ship.isSunk());
	}

	/* Method for getting the numerical value from column char */
	public int getNumericalValue(char column) {
		switch (column) {
		case 'A':
			return 0;
		case 'B':
			return 1;
		case 'C':
			return 2;
		case 'D':
			return 3;
		case 'E':
			return 4;
		case 'F':
			return 5;
		case 'G':
			return 6;
		case 'H':
			return 7;
		case 'I':
			return 8;
		case 'J':
			return 9;
		default:
			return -1;
		}
	}
}
