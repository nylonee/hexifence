import java.util.*;


public class TestBoard {
	public static void main(String[] args){
		Board board = new Board();
		System.out.println(board.possibleMoves());
		System.out.println(board.countMaxByOneMove());
		System.out.println(board.countAvailableCaptures());
	}

}
