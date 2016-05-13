/** The final relational game playing agent for playing hexagon fence game.
 * The general strategy used is minimax + alpha-beta pruning algorithm.
 * The description of how the evaluation function was driven is written in
 * comment.txt file
 *
 * @author Nihal Mirpuri (nmirpuri)
 * @author Tessa Song (songt)
 * @version 1.0
 * 
 * Attribution: 
 *  The basic frame of minimax function in this script was referred from
 *  https://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html
 *  
 */


import java.io.PrintStream;
import java.util.*;
import aiproj.hexifence.*;

public class SongtNmirpuriClever implements Player, Piece {
	public static final int LIMIT_DEPTH = 3;
	public static final int MAXINT =  Integer.MAX_VALUE / 2;
	public static final int MININT =  Integer.MIN_VALUE / 2;
	public static final int MYTURN =  1;
	public static final int OPPTURN =  2;
	public Board gameBoard; // the board to put pieces on
	public int piece; // either BLUE(1) or RED(2) 
	// represent the state of this board
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
	 * + alpha beta pruning
	 * @return Move : the next move
	 */
	// check if the end of game has reached when generating children
	// = if getwinner() == BLUE or RED ... RETURN 1 OR -1 OR 0
	public int[] minimax(int depth, int turn, int alpha, int beta){
		List<Move> posbMoves;

		// generate all possible moves
		if (turn == MYTURN) {
			posbMoves = gameBoard.generatePosbMoves(piece);
		} else {
			if(piece == Piece.BLUE){
				posbMoves = gameBoard.generatePosbMoves(Piece.RED);
			}else{
				posbMoves = gameBoard.generatePosbMoves(Piece.BLUE);
			}
		}
		 
	    // mySeed is maximizing; while oppSeed is minimizing
	    int score;
	    int bestRow = -1;
	    int bestCol = -1;
	     
	    
		// if game finished, win score 1, lose score -1, draw score 0
		if(getWinner() != 0){
			if(getWinner() == Piece.DEAD)
				score = 0;
			else if((getWinner() == Piece.BLUE && piece == Piece.BLUE) ||
					(getWinner() == Piece.RED && piece == Piece.RED) )
				score = 1;
			else
				score = -1;
			return new int[] {score, bestRow, bestCol};
		}
		
		// if reached the limit depth
		else if(depth == 0){
			if (piece == BLUE)
				score = 10*gameBoard.blueHex - 3*gameBoard.redHex;
			else
				score = 10*gameBoard.redHex - 3*gameBoard.blueHex;

			return new int[] {score, bestRow, bestCol};
		}
		
		// neither limit depth reached nor game finished
		else {
			for (Move move : posbMoves) {

				// try this move for the current "player"
				int tempB = gameBoard.blueHex;
				int tempR = gameBoard.redHex;
	            gameBoard.setBoard(move);
	            
	            if (turn == MYTURN) {  // needs to maximize value
	            	// if this move gives additional move
	            	if (move.P == Piece.BLUE && tempB < gameBoard.blueHex){
	            		score = minimax(depth - 1, MYTURN, alpha, beta)[0];	
	            		//System.out.println(score);
	            	}else if (move.P == Piece.RED && tempR < gameBoard.redHex){
	            		score = minimax(depth - 1, MYTURN, alpha, beta)[0];	
	            		//System.out.println(score);
	            	}
	            	// otherwise
	            	else
	            		score = minimax(depth - 1, OPPTURN, alpha, beta)[0];	
	       
	            	
	               if (score > alpha) {
	                  alpha = score;
	                  bestRow = move.Row;
	                  bestCol = move.Col;

	               }
	            } else {  // needs to minimize value
	            	// if this move gives additional move
	            	if (move.P == Piece.BLUE && tempB < gameBoard.blueHex)
	            		score = minimax(depth - 1, OPPTURN, alpha, beta)[0];	
	            	else if (move.P == Piece.RED && tempR < gameBoard.redHex)
	            		score = minimax(depth - 1, OPPTURN, alpha, beta)[0];
	            	//otherwise
	            	else
	            		score = minimax(depth - 1, MYTURN, alpha, beta)[0];
	            	
	               if (score < beta) {
	                  beta = score;
	                  bestRow = move.Row;
	                  bestCol = move.Col;
	               }
	            }
	            
	            // undo move
	            gameBoard.undoMove(move);
	            
	            // cut-off
	            if (alpha >= beta) break;
	         }

	        return new int[] {(turn == MYTURN) ? alpha : beta, bestRow, bestCol};	
		}
		
	}


	@Override
	public Move makeMove() {
		// get all possible moves as an array
		Move move = new Move();
		
		int[] result = minimax(LIMIT_DEPTH, MYTURN, MININT, MAXINT);

		move.Row = result[1];
		move.Col = result[2];
		move.P = piece;

		gameBoard.setBoard(move);
		
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
