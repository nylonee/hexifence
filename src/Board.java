import java.util.Scanner;


public class Board {
	public int n;
	public char[][] board;
	public int[][] captureBoard;
	public int possibleMoves;
	public int maxByOneMove; 
	public int avlbCaptures;
	
	public Board(){
		buildBoard();
	}
	
	public void buildBoard(){
		
		int i, j;
		Scanner scan = new Scanner(System.in);
		
		n = scan.nextInt();
		board = new char[4*n-1][4*n-1];
		
		captureBoard = new int[4*n-1][4*n-1];
		
		for(i = 0; i<4*n-1; i++){
			for(j = 0; j<4*n-1; j++){
				board[i][j] = scan.next().charAt(0);
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
	
	public int isAvailableForCapture(int i, int j){
		int available = 0;
		
		
		return available;
		
	}

	
	public int countMaxByOneMove(){
		
		return maxByOneMove; 
	}
	
	
	public int countAvailableCaptures(){
		
		return avlbCaptures;
	}

}





