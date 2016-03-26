public class TestBoard {
	public static void main(String[] args){
		Board board = new Board();
		System.out.println(board.possibleMoves());
		System.out.println(board.maxByOneMove());
		System.out.println(board.availableCaptures());
	}

}
