package board;

public class Cell {
	private ShipTypes shipType;
	private Coordinate coordinate;

	public Cell(int row, int column) {
		setCoordinate(new Coordinate(row, column));
		shipType = ShipTypes.Sea;
	}

	public String getStatus() {
		return shipType.getSimbol();
	}

	public boolean isOccupied() {
		return (shipType == ShipTypes.Submarine || shipType == ShipTypes.Destroyer || shipType == ShipTypes.Cruiser
				|| shipType == ShipTypes.Battleship);
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public ShipTypes getShipType() {
		return shipType;
	}

	public void setShipType(ShipTypes shipType) {
		this.shipType = shipType;
	}
}
