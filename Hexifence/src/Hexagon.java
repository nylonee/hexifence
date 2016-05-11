/** Represents each hexagon on a board
 * 
 * @author Nihal Mirpuri (nmirpuri)
 * @author Tessa Song (songt)
 * @version 1.0
 */

public class Hexagon {
	
	// references to the 6 tiles which this hex consists of
	public Tile[] tileArr;

	// the centre tile reference
	// the char value of this tile is either r or b
	public Tile captureTile; 

	
	// Constructor
	public Hexagon(){
		tileArr = new Tile[6];
	}


}
