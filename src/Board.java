import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Board {
	public int n;
	public Tile[][] board;
	public int possibleMoves;
	public int maxByOneMove; 
	public int avlbCaptures;
	
	public Board(){
		buildBoard();
	}
	
	public void buildBoard(){
		Scanner scan = new Scanner(System.in);
		
		try {
			n = scan.nextInt();

			board = new Tile[4*n-1][4*n-1];

			for(int i = 0; i < 4*n-1; i++) {
				for(int j = 0; j < 4*n-1; j++) {
					board[i][j].setCharValue(scan.next().charAt(0));
				}
			}
		} catch (InputMismatchException e) {
			System.err.println("ERROR: File doesn't follow correct syntax!");
			System.exit(1);
		}
		
		scan.close();
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
	
	public void checkError(){
		//any not allowed letters?(R,B,-,+)
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