import java.util.InputMismatchException;
import java.util.Scanner;

public class Board {
	public int n; // The N value
	public int size; // The size of the board (4*n-1)
	public Tile[][] board;
	public int possibleMoves = 0; // Number of possible moves at this state
	public int maxByOneMove = 0;  // Maximum number of hexagonal cells that can be captured by one move (0, 1, 2)
	public int avlbCaptures = 0; // Number of hexagonal cells available for capture by a single move
	
	public Board(){
		buildBoard();
	}
	
	/** Build a board by scanning the given input
	 * 
	 */
	public void buildBoard(){
		Scanner scan = new Scanner(System.in);
		
		try {
			n = scan.nextInt();
			size = 4*n-1;

			board = new Tile[size][size];

			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					board[i][j] = new Tile();
					
					//TODO: Check row and column length = 4*n-1
					char value = scan.next().charAt(0);

					// any not allowed letters?(R,B,-,+)
					if (value != 'R' && value != 'B' && value != '-' && value != '+')
						throw new IllegalArgumentException();
					
					board[i][j].setCharValue(value);
				}
			}
		} catch (InputMismatchException|IllegalArgumentException e) {
			System.err.println("SYNTAX ERROR: Exiting!");
			System.exit(1);
		}
		
		scan.close();
		
		determineCaptureValues();
		
		printBoard();
	}
	
	
	/**
	 * Print this board
	 */
	public void printBoard(){
		System.out.println(n);
		for (int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				System.out.print(board[i][j].getCharValue()+" ");
			}
			System.out.println();
		}
		System.out.println();
	}	
	
	
	
	/** Count the possible moves
	 * 
	 * @return the number of possible Moves
	 */
	public int countPossibleMoves(){
		int i, j;
		possibleMoves = 0;
		
		for(i = 0; i< 4*n-1; i++){
			for(j = 0; j< 4*n-1; j++){
				if (board[i][j].getCharValue() == '+'){
					possibleMoves++;
				}
			}
		}
		return possibleMoves;	
	}
	
	
	
	
	/** Determine the capture value of every tile in the board
	 */
	public void determineCaptureValues(){
		int i, j;
		for (i = 0; i< 4*n-1; i+=2){
			for(j =0; j< 4*n-1; j+=2){
				if (board[i][j].getCharValue() != '-'){
					determineCaptureValue(i, j);
				}	
			}
		}
	}
	
	
	// fixed it. there should be at most only one tile whose tile value increases by 1
	// do boundary check as well
	// if more than one + is detected stop it
	public void determineCaptureValue(int i, int j){
		int numPlus = 0;
		boolean isOutOfBound = false;
		int[] iValues = {i, i+1, i, i+2, i+1, i+2};
		int[] jValues = {j, j, j+1, j+1, j+2, j+2};
		int k;
		int iIncrease = 0, jIncrease = 0;
		
		for(k = 0; k<jValues.length; k++){
			if (iValues[k] >= 4*n-1 || jValues[k] >= 4*n-1){
				isOutOfBound = true;
				break;
			}
			if(board[iValues[k]][jValues[k]].getCharValue() == '+'){
				iIncrease = iValues[k];
				jIncrease = jValues[k];
				numPlus++;
			}
			if(numPlus > 1){
				break;
			}
		}
		
		if (numPlus == 1 && !isOutOfBound){
			board[iIncrease][jIncrease].setCaptureValue(board[iIncrease][jIncrease].getCaptureValue()+1);
		}	
	}
	
		
	
	/** Count available cells for capture
	 * @return Number of hexagonal cells available for capture by a single move
	 */
	public int countAvailableCaptures(){
		int i, j;
		for(i = 0; i<4*n-1; i++){
			for(j = 0; j<4*n-1; j++){
					avlbCaptures += board[i][j].getCaptureValue();
				
			}
		}
		return avlbCaptures;
	}
	
	
	/** Count the maximum number of cells which can be captured by one move
	 * @return maximum number of hexagonal cells which can be captured by one move
	 */
	public int countMaxByOneMove(){
		int i, j;
		for(i = 0; i<4*n-1; i++){
			for(j = 0; j<4*n-1; j++){
				if (board[i][j].getCaptureValue() > maxByOneMove){
					maxByOneMove = board[i][j].getCaptureValue();
				}
			}
		}
		return maxByOneMove; 
	}
	
	
	
	
	
	
}
