package ships;

import board.ShipTypes;

public class Destroyer extends Ship {
	public Destroyer() {
		this.setName("Destroyer");
		this.setWidth(2);
		this.setType(ShipTypes.Destroyer);
	}
}
