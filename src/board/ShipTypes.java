package board;

public enum ShipTypes {
	Submarine("S"),
	Destroyer("D"),
	Cruiser("C"),
	Battleship("B"),
	Hit("X"),
	Miss("O"),
	Sea("*");
	
	public final String simbol;

	ShipTypes(String simbol){
		this.simbol = simbol;
	}
	
	public String getSimbol() {
		return this.simbol;
	}
}
