
public class Tile {
	public Point location;
	public char type;
	
	public Tile(Point tileLocation, char typeOfTile){
		type = typeOfTile;
		location = tileLocation;
	}
	
	public Point getTileLocation() {
		return location;
	}
	
	public char getTileValue() {
		return type;
	}
	
	public int isAvailable(int boardSize) {
		return 0;
	}
}
