package Chess;


/* This class is for implementing moving on the main board, but also updating the Chessgame class */

public class Move {

    public static boolean Moving = false;

    public static void MovePiece() {
        //TODO: ADD MORE FUNCTIONALITY
        //NEED BASIC PIECE LOGIC AND MOVING FROM THE SAME SPACE TO THE OTHER IS NOT AN AVAILABLE MOVE.
        Moving = true;
        if (!((Chess.chessboard.getChessboardState()).get(ChessController.mouseposlist.get(1)).get(ChessController.mouseposlist.get(0)) == null)) {
            Chess.chessboard.Move(ChessController.mouseposlist.get(0), ChessController.mouseposlist.get(1), ChessController.mouseposlist.get(2), ChessController.mouseposlist.get(3));
            System.out.println("Moved!");
            // System.out.println(Chess.chessboard.getChessboardState());
        } else {
            System.out.println("Not a legal move!");
            // throw new IllegalArgumentException("You can't do that move!" + " y1: " + ChessController.mouseposlist.get(1) + " x1: " + ChessController.mouseposlist.get(0));
        }
        
    }

}
