/** Board class for holding a 'snapshot' of a board
 *
 * @author Nihal Mirpuri (nmirpuri)
 * @author Tessa Song (songt)
 * @version 1.0
 */

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import aiproj.hexifence.*;

public class Board {
	private int n; // The N value (either 2 or 3)
	public int size; // The size of the board (4*n-1)
	public Tile[][] board; // Represents the entire board
	private int possibleMoves = 0; // Number of possible moves at this state
	private int maxByOneMove = 0;  // Maximum number of hexagonal cells that can be captured by one move (0, 1, 2)
	private int avlbCaptures = 0; // Number of hexagonal cells available for capture by a single move

	public int blueHex = 0; // Number of hexagons captured by blue player
	public int redHex = 0; // Number of hexagons captured by red player
	
	/** constructor 
	 */
	public Board(int n){
		this.n = n;
		this.size = 4*n-1;
		buildBoard();
	}
	
	/** Build a board by filling it with + and -
	 */
	public void buildBoard(){
		
		// create board object
		board = new Tile[size][size];
		
		// fill the board with either + or -
		for(int i = 0; i<size ; i++){
			for(int j = 0; j<size ; j++){
				Tile temp = new Tile();
				if(checkTile(i, j)){
					temp.setCharValue('+');
				}else{
					temp.setCharValue('-');
				}
				board[i][j] = temp;
			}
		}
	}
	
	
	/**
	 * Print this board
	 */
	public void printBoard(PrintStream output){
		for(int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				output.print(board[i][j].getCharValue()+" ");
			}
			output.println();
		}
		output.println();
	}
	
	
	/**
	 * Given a tile point using i, j, returns true if the tile coordinate is valid
	 * Valid tiles are tiles that can have +, R or G on it.
	 * 
	 * Hexagon centers and boundaries therefore return false
	 */
	public Boolean checkTile(int i, int j){
		
		// Check for array boundary
		if (i >= size || j >= size)
			return false;
		
		// Check for hexagon boundary
		// Note: Center of hexagons only occur on odd i, j
		if (i%2==1 && j%2==1)
			return false;
			
		// Check for game boundary
		// If j < 2n-1, then for tile to be valid i <= (2n-1)+j
		// If j > 2n-1, then for tile to be valid i >= j-(2n-1)
		// If j = 2n-1, then tile is valid
		int boundary = 2*n-1;	
		if(!((j < boundary && i <= boundary+j)
				||(j > boundary && i >= j-boundary)
				||(j == boundary)))
			return false;
		
		return true;
	}

	
	/** initialise capture value of all tiles as 0
	*/
	public void initialiseCaptureValue(){
		for(int i = 0 ;i<size ; i++){
			for(int j = 0;j<size; j++){
				board[i][j].setCaptureValue(0);
			}
		}

	}

	/** Determine captureValue of each tile on the board.
	 * Simultaneously uses the values to populate avlbCaptures
	 */
	public void determineCaptureValues(){
		// initialise capture values as 0 first
		initialiseCaptureValue();

		// pass the top left tile of each hexagonal cell
		// to check if this cell can be captured by single move
		for(int i=0; i<size; i+=2)
			for(int j=0; j<size ; j+=2)
				// increase avlbCaptures if this cell is available for capture by single move 
				avlbCaptures += determineCaptureValue(i, j);
	}
	
	
	
	/** Determine if the given hexagon by x,y coordinate can be captured by single move or not
	 *  and increase the capture value of the available tile by 1
	 *  MaxByOneMove is updated every time this function is called
	 * @param i x coordinate of the hexagon
	 * @param j y coordinate of the hexagon
	 * @return  Returns 1 if hex can be captured, otherwise 0 (used for counter)
	 */
	public int determineCaptureValue(int i, int j){
		// number or plus character in the corresponding hex cell to the given Tile
		// there should be only one plus character within this cell to capture it
		int numPlus = 0;
		
		// boundary checking boolean variable
		boolean isOutOfBound = false;
		
		// int Arrays which contain x,y coordinates of the 5 adjacent tiles 
		int[] iValues = {i, i+1, i, i+2, i+1, i+2};
		int[] jValues = {j, j, j+1, j+1, j+2, j+2};
		
		// x,y coordinate of a tile whose capture value will increase by 1
		// if this cell can be captured by one move
		int iIncrease = 0, jIncrease = 0;
		
		
		for(int k=0; k<jValues.length && numPlus<=1; k++){
			// check if this tile is within the available boundary or not
			if(!checkTile(iValues[k], jValues[k])){
				isOutOfBound = true;
				break;
			}

			// find out the tile whose char value is '+'
			if(board[iValues[k]][jValues[k]].getCharValue() == '+'){
				iIncrease = iValues[k];
				jIncrease = jValues[k];
				numPlus++;
			}
		}
		
		// if there is only one plus '+' character and within boundary 
		// increase the capture value of the '+' tile object
		if (numPlus == 1 && !isOutOfBound){
			board[iIncrease][jIncrease].setCaptureValue(board[iIncrease][jIncrease].getCaptureValue()+1);
			
			// Use capture value to now determine the max you can capture in one move
			if (board[iIncrease][jIncrease].getCaptureValue() > maxByOneMove)
				maxByOneMove = board[iIncrease][jIncrease].getCaptureValue();
			
			// Can capture hex
			return 1;
		}
		
		// Can't capture hex
		return 0;
	}
	
	
	/** Update this board by applying the newly-made move
	 *  If there is any hexagon captured by this move, put either r or b 
	 *  at the centre tile 
	 * @param Move move
	 * @return 1 if there is any hexagon captured by this move
	 * 			 otherwise, return 0 
	 */
	public int setBoard(Move move, int p){
		int hexCapted; // number of hexagons captured by this move
		int counter;

		// make move
		if(p == Piece.BLUE)
			board[move.Row][move.Col].setCharValue('B');
		else
			board[move.Row][move.Col].setCharValue('R');
		
		// check how many hexagons are captured by this move
		hexCapted = board[move.Row][move.Col].getCaptureValue();
		counter = hexCapted;

		// put either b or r at the centre of the hexagons captured
		if (hexCapted > 0) {
			outerloop:
			for(int i = 0; i<size; i+=2){
				for(int j= 0; j<size; j+=2){
					// decrease counter by 1 if this cell has been captured
					if(checkTile(i,j)){
						counter -= isCaptured(i, j, p);
					}
					// if there is no cell to capture for this move anymore, break
					if (counter == 0)
						break outerloop;
				}
			}
		}
	
		// update capture values of tiles
		determineCaptureValues();
		
		// return value
		if (hexCapted > 0)
			return 1;
		else
			return 0;
					

	}
	
	
	/**
	 * check if this hexagon has been captured
	 *  if captured, put either 'r' or 'b' at the centre tile 
	 * @return 1 if captured
	 * 		   otherwise, 0
	 */
	public int isCaptured(int i, int j, int p){
		int captured = 1;
		
		// int Arrays which contain x,y coordinates of the 5 adjacent tiles 
		int[] iValues = {i, i+1, i, i+2, i+1, i+2};
		int[] jValues = {j, j, j+1, j+1, j+2, j+2};
		
		// check if this cell has been captured
		for(int k = 0; k < jValues.length; k++){
			if( !checkTile(iValues[k], jValues[k]) ||
				board[iValues[k]][jValues[k]].getCharValue() == '+' ){
				captured = 0;
				break;
			}
		}

		// if this has been already captured before,
		if(captured == 1 && board[i+1][j+1].getCharValue() != '-' ){
			captured = 0;

		}

		
		// if capture, set character of the centre tile as either 'b' or 'r'
		if(captured == 1){
			if(p == Piece.BLUE){
				board[i+1][j+1].setCharValue('b');
				blueHex++;
			}
			else{
				board[i+1][j+1].setCharValue('r');
				redHex++;
			}
		}

		return captured;
	}
	
	
	
	/** Return the possible moves
	 * @return the number of possible Moves
	 */
	public int getPossibleMoves(){
		possibleMoves = 0;
		for(int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				if (board[i][j].getCharValue() == '+'){
					possibleMoves++;
				}
			}
		}

		return possibleMoves;
	}
	
	
	/** Return available cells for capture
	 * @return Number of hexagonal cells available for capture by a single move
	 */
	public int getAvailableCaptures(){
		return avlbCaptures;
	}
	
	
	/** Return maximum number of cells which can be captured by one move
	 * @return maximum number of hexagonal cells which can be captured by one move
	 */
	public int getMaxByOneMove(){
		return maxByOneMove;
	}
}

