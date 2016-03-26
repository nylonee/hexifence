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
		printBoard();
	}
	
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
	
	public int countPossibleMoves(){
		
		
		return possibleMoves;	
	}
	
	
	
	
	/** Determine the tile value of each tile in the board
	 */
	public void determineTileValue(){
		int i, j;
		
		
	}
	
	
	public void determineTileValue(int i, int j){
		boolean increase = true;
		if (board[i][j].getCharValue() == '+'){
			if(board[i+1][j].getCharValue() != 'R' && board[i+1][j].getCharValue() != 'B'){
				increase = false;
			}
			
			if(board[i][j+1].getCharValue() != 'R' && board[i][j+1].getCharValue() != 'B'){
				increase = false;
			}
			
			if(board[i+2][j+1].getCharValue() != 'R' && board[i+2][j+1].getCharValue() != 'B'){
				increase = false;
			}
			
			if(board[i+1][j+2].getCharValue() != 'R' && board[i+2][j+1].getCharValue() != 'B'){
				increase = false;
			}
			
		}
		
	}
	
		
	/** Count available cells for capture
	 * @return Number of hexagonal cells available for capture by a single move
	 */
	public int countAvailableCaptures(){
		int i, j;
		for(i = 0; i<4*n-1; i++){
			for(j = 0; j<4*n-1; j++){
				if(board[i][j].getTileValue() > 0){
					avlbCaptures += board[i][j].getTileValue();
				}
				
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
				if (board[i][j].getTileValue() > maxByOneMove){
					maxByOneMove = board[i][j].getTileValue();
				}
			}
		}
		return maxByOneMove; 
	}
	
	
	
	
	
	
}
