

public class Tile {
	
	public static final int NOT_AVAILBLE = -1;
	public static final int AVAILABLE = 0;
	public static final int ONE_CAPTURE = 1;
	public static final int TWO_CAPTURE = 2;
	
	public char type;
	public int available;  
	
	public Tile(){
		available = Tile.NOT_AVAILBLE;
	}
	
	public char getTileValue() {
		return type;
	}
	
	public void setTile(char type){
		this.type = type;
	}
	
	public int isAvailable() {
		return available;
	}
	
	public void setAvailable(int available){
		this.available = available;
	}
	
	
}
