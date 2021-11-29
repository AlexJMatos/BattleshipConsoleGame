package ships;

import board.ShipTypes;

public class Cruiser extends Ship {
	public Cruiser() {
		this.setName("Cruiser");
		this.setWidth(3);
		this.setType(ShipTypes.Cruiser);
	}
}
