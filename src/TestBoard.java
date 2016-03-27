/** TestBoard class which makes use of Board class
 *  by creating an instance of it and calling proper functions
 *
 */
public class TestBoard {
	public static void main(String[] args){
		Board board = new Board();
		System.out.println(board.getPossibleMoves());
		System.out.println(board.getMaxByOneMove());
		System.out.println(board.getAvailableCaptures());
	}

}
