import java.util.Scanner;

public class Board {
	public int n; // The N value
	public Tile[][] board;
	public int possibleMoves = 0; // Number of possible moves at this state
	public int maxByOneMove = 0;  // Maximum number of hexagonal cells that can be captured by one move (0, 1, 2)
	public int avlbCaptures = 0; // Number of hexagonal cells available for capture by a single move
	
	public Board(){
		buildBoard();
	}
	
	public void buildBoard(){
		
		int i, j;
		Scanner scan = new Scanner(System.in);
		
		n = scan.nextInt();
		board = new Tile[4*n-1][4*n-1];
		
		for(i = 0; i<4*n-1; i++){
			for(j = 0; j<4*n-1; j++){
				board[i][j].setType(scan.next().charAt(0));
			}
		}
		
		System.out.print("Error check: "+checkError());
		printBoard();
	}
	
	
	public void printBoard(){
		System.out.println(n);
		for (int i = 0; i<4*n-1; i++){
			for(int j = 0; j<4*n-1; j++){
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public Boolean checkError(){
		for (int i = 0; i < 4*n-1; i++) { // Iterate through board
			for (int j = 0; j < 4*n-1; j++) {
				int tile = board[i][j];

				//any not allowed letters?(R,B,-,+)
				if (tile != 'R' && tile != 'B' && tile != '-' && tile != '+')
					return false;
				
				
			}
			
		}
		//check the number of lines & the number of chars per line
		//syntax errors not semantic errors
	}
	
	
	
	public int countPossibleMoves(){
		
		
		return possibleMoves;	
	}
	
	
	/** Determine the tile value of each tile in the board
	 */
	public void determineTileValue(){
		int i, j;
		
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





