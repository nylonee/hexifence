import java.io.PrintStream;
import java.util.Random;
import aiproj.hexifence.*;

public class SongtNmirpuriFirstFive implements Player, Piece {
	
	public Board gameBoard; // the board to put pieces on
	public int piece; // either BLUE(1) or RED(2) 
	public int count = -1;
	public Move[] tempArr = new Move[5];
	
	
	// represent the state of this board
	// this should be updated on every newly-made move
	public int boardState = Piece.EMPTY; 

	@Override
	public int init(int n, int p) {
		Move temp = new Move();
		temp.Row = 8;
		temp.Col = 8;
		temp.P = p;
		tempArr[0] = temp;

		temp = new Move();
		temp.Row = 8;
		temp.Col = 9;
		temp.P = p;
		tempArr[1] = temp;

		temp = new Move();
		temp.Row = 9;
		temp.Col = 8;
		temp.P = p;
		tempArr[2] = temp;

		temp = new Move();
		temp.Row = 9;
		temp.Col = 10;
		temp.P = p;
		tempArr[3] = temp;

		temp = new Move();
		temp.Row = 10;
		temp.Col = 9;
		temp.P = p;
		tempArr[4] = temp;



		try{
			gameBoard = new Board(n);
			this.piece = p;
		}catch (Exception e){
			return -1;
		}
		return 0;

	}

	@Override
	public Move makeMove() {
		// create Move object
		Move move = new Move();
		move.P = piece;
		boolean moveMade = false;
		
		if(count < 4){
			count++;
			//moveMade = true;
			gameBoard.setBoard(tempArr[count]);
			return tempArr[count];

		}



		// Make an available move by randomly choosing two values for row and column
		while(!moveMade){
			Random rand = new Random();
			int row = rand.nextInt(gameBoard.size);
			int col = rand.nextInt(gameBoard.size);
			
			if(gameBoard.board[row][col].getCharValue() == '+'){
				move.Row = row;
				move.Col = col;
				gameBoard.setBoard(move);
				moveMade = true;
			}
		}
		
		// return the Move object so that the opponent can update their board config
		return move;
	}

	@Override
	public int opponentMove(Move m) {
		// if the opponent's move is illegal, return INVALID
		if (gameBoard.board[m.Row][m.Col].getCharValue() != '+'){
			boardState = Piece.INVALID;
			return boardState;
		}

		return gameBoard.setBoard(m);		
	}

	
	@Override
	public int getWinner() {
		
		// if the opponent makes illegal move
		if (boardState == Piece.INVALID)
			return boardState;
		
		// if the game has not finished
		if(gameBoard.getPossibleMoves() > 0)
			return Piece.EMPTY;

		// if red wins
		if(gameBoard.redHex > gameBoard.blueHex)
			return Piece.RED;
		// if blue wins
		else if(gameBoard.redHex < gameBoard.blueHex)
			return Piece.BLUE;
		// if draw
		else
			return Piece.DEAD;
		
	}

	
	@Override
	public void printBoard(PrintStream output) {
		// call printBoard function of Board object
		gameBoard.printBoard(output);
		
	}

}
