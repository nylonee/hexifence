

public class Tile {
	
	public static final int ZERO_CAPTURE = 0;
	public static final int ONE_CAPTURE = 1;
	public static final int TWO_CAPTURE = 2;
	
	public char charValue;
	public int captureValue;
	
	public Tile(){
		captureValue = Tile.ZERO_CAPTURE;
	}

	public int getCaptureValue() {
		return captureValue;
	}

	public void setCaptureValue(int captureValue) {
		this.captureValue = captureValue;
	}

	public char getCharValue() {
		return charValue;
	}

	public void setCharValue(char charValue) {
		this.charValue = charValue;
	}


	
}
