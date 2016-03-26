

public class Tile {
	
	public static final int ZERO_CAPTURE = 0;
	public static final int ONE_CAPTURE = 1;
	public static final int TWO_CAPTURE = 2;
	
	public char charValue;
	public int tileValue;
	
	public Tile(){
		tileValue = Tile.ZERO_CAPTURE;
	}

	public char getCharValue() {
		return charValue;
	}

	public void setCharValue(char charValue) {
		this.charValue = charValue;
	}

	public int getTileValue() {
		return tileValue;
	}

	public void setTileValue(int tileValue) {
		this.tileValue = tileValue;
	}

	
}
