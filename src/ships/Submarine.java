package ships;

import board.ShipTypes;

public class Submarine extends Ship {
	public Submarine() {
		this.setName("Submarine");
		this.setWidth(1);
		this.setType(ShipTypes.Submarine);
	}
}