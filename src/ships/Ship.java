package ships;

import board.ShipTypes;

public class Ship {
	private String name;
	private int hits = 0;
	private int width;
	private ShipTypes type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}
	
	public void setOneHit() {
		hits++;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public ShipTypes getType() {
		return type;
	}

	public void setType(ShipTypes type) {
		this.type = type;
	}
	
	public boolean isSunk() {
		return hits >= width;
	}
}
