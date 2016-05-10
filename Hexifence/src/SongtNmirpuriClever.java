import java.io.PrintStream;
import java.util.Random;

import aiproj.hexifence.*;

public class SongtNmirpuriClever implements Player, Piece {
	
	public Board gameBoard; // the board to put pieces on
	public int piece; // either BLUE(1) or RED(2) 
	
	
	// represent the state of this board
	// this should be updated on every newly-made move
	public int boardState = Piece.EMPTY; 

	@Override
	public int init(int n, int p) {
		try{
			gameBoard = new Board(n);
			this.piece = p;
		}catch (Exception e){
			return -1;
		}
		return 0;
	}

	
	/**
	 * return the next move to make using minimax Search tree
	 * @return Move the next move
	 */
	public Move miniMaxDecision(){
		int possibleMoves = gameBoard.getPossibleMoves();
		int[] valueArray = new int[possibleMoves];
		Move[] moveArray = new Move[possibleMoves];
		int maxIndex = 0;
		int idx = 0;
		
		// built a Move array which contains all possible moves for the current state		
		// and find out the minimax value of it
		outerloop:
		for(int i = 0; i<gameBoard.size; i++){
			for(int j = 0; j<gameBoard.size; j++){
				if (gameBoard.board[i][j].getCharValue() == '+'){
					Move move = new Move();
					move.Row = i;
					move.Col = j;
					move.P = piece;
					moveArray[idx] = move;
					idx++;
					
					// copy the current board and make the possible move
					Board board = new Board(gameBoard);
					board.setBoard(move, piece);
					
					// find out the minimax value of this move
					valueArray[idx] = miniMaxValue(board);
				}
				
				// if we find out all possible move positions, break the loop
				if (idx == possibleMoves)
					break outerloop;
				
			}
		}
		
		// return the move with highest evaluation value
		for(int i = 0; i<possibleMoves; i++){
			if (valueArray[maxIndex] < valueArray[i])
				maxIndex = i;
		}
		
		return moveArray[maxIndex];
		
	}
	
	
	/**
	 * decide the minimax Value of the given state
	 * @return evaluation value 
	 */
	public int miniMaxValue(Board board){
		int value = 0;
		
		return value;
		
	}
	

	@Override
	public Move makeMove() {
		// declare Move object
		Move move;
		
		// using miniMax search tree find out the next move
		move = miniMaxDecision();
		move.P = piece;
		
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
		// update the board by this move
		if(this.piece == Piece.BLUE)
			return gameBoard.setBoard(m, Piece.RED);
		else
			return gameBoard.setBoard(m, Piece.BLUE);		
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
