package ships;

import board.ShipTypes;

public class Battleship extends Ship {
	public Battleship() {
		this.setName("Battleship");
		this.setWidth(4);
		this.setType(ShipTypes.Battleship);
	}
}
