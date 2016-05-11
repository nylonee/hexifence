/**
 *
 * @author Nihal Mirpuri (nmirpuri)
 * @author Tessa Song (songt)
 * @version 1.0
 * 
 *  Attribution : https://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html
 */


import java.io.PrintStream;
import java.util.*;
import java.util.Random;

import aiproj.hexifence.*;

public class SongtNmirpuriClever implements Player, Piece {
	public static final int LIMIT_DEPTH = 1;
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
		List<Move> posbMoves = gameBoard.generatePosbMoves();
		//System.out.println("number of posb moves : "+ posbMoves.size());
		 
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
			//System.out.println(score);
			return new int[] {score, bestRow, bestCol};
		}
		
		// if reached the limit depth
		else if(depth == 0){
			if (piece == BLUE)
				score = 10*gameBoard.blueHex - 3*gameBoard.redHex;
			else
				score = 10*gameBoard.redHex - 3*gameBoard.blueHex;
			
			//System.out.println(score);
			return new int[] {score, bestRow, bestCol};
		}
		
		// neither limit depth reached nor game finished
		else {
			for (Move move : posbMoves) {
				//System.out.println("hello");
	            // try this move for the current "player"

				if(turn == MYTURN){
					move.P = piece;
				}else{
					if(piece == Piece.BLUE){
						move.P = Piece.RED;
					}else{
						move.P = Piece.BLUE;
					}
				}


	            gameBoard.setBoard(move);
	            //gameBoard.printBoard(System.out);
	            //System.out.println(move.Row + " " + move.Col);
	            if (turn == MYTURN) {  // needs to maximize value
	            	//System.out.println(alpha + " " + beta);
	               score = minimax(depth - 1, OPPTURN, alpha, beta)[0];
	               //System.out.println(score);
	               if (score > alpha) {
	               	
	                  alpha = score;
	                  bestRow = move.Row;
	                  bestCol = move.Col;

	               }
	            } else {  // needs to minimize value
	            	//System.out.println(alpha + " " + beta);
	               score = minimax(depth - 1, MYTURN, alpha, beta)[0];
	               if (score < beta) {
	               	//System.out.println("helloooo");
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
		//System.out.println(MININT+" "+MAXINT);
		//System.out.println(result[0]+" "+result[1]+" "+result[2]);

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
