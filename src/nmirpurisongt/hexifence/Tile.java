package nmirpurisongt.hexifence;
/** Represents each character on a board
 * 
 * @author Nihal Mirpuri
 * @author Tessa Song (songt)
 * @version 1.0
 */

public class Tile {
	
	// Character value of this tile (+,-,R or B)	
	public char charValue; 
	// Represents how many cells can be captured by making move on this tile (0,1 or 2) 
	public int captureValue; 
	
	// CaptureValue is initialized as 0 at first
	public Tile(){
		captureValue = 0;
	}
	
	// getter for captureValue
	public int getCaptureValue() {
		return captureValue;
	}

	// setter for captureValue
	public void setCaptureValue(int captureValue) {
		this.captureValue = captureValue;
	}

	// getter for CharValue
	public char getCharValue() {
		return charValue;
	}

	// setter for CharValue
	public void setCharValue(char charValue) {
		this.charValue = charValue;
	}

}
