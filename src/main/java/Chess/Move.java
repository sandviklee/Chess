package Chess;


/* This class is for implementing moving on the main board, but also updating the Chessgame class */

public class Move {

    public static boolean Moving = false;

    public static void MovePiece() {
        //TODO: ADD MORE FUNCTIONALITY
        //NEED BASIC PIECE LOGIC AND MOVING FROM THE SAME SPACE TO THE OTHER IS NOT AN AVAILABLE MOVE.
        Moving = true;
        int x_1 = ChessController.mouseposlist.get(0);
        int y_1 = ChessController.mouseposlist.get(1);
        int x_2 = ChessController.mouseposlist.get(2);
        int y_2 = ChessController.mouseposlist.get(3);

        if (!((Chess.chessboard.getChessboardState()).get(y_1).get(x_1) == null) && 
        !((Chess.chessboard.getChessboardState()).get(y_1).get(x_1) == (Chess.chessboard.getChessboardState()).get(y_2).get(x_2))) {
            if (Chess.chessboard.getChessboardState().get(y_2).get(x_2) == null) {
                Chess.chessboard.Move(x_1, y_1, x_2, y_2);
                System.out.println("Moved!");
            } else {
                if ((((Chess.chessboard.getChessboardState()).get(y_1).get(x_1).getPieceColor()).equals(Chess.chessboard.getChessboardState().get(y_2).get(x_2).getPieceColor()))) {
                    System.out.println("Not a legal move!");
                } else {
                    Chess.chessboard.Move(x_1, y_1, x_2, y_2);
                    System.out.println("Moved!");
                }
            }
            
            
        System.out.println(Chess.chessboard.getChessboardState());
        } else {
            
            // throw new IllegalArgumentException("You can't do that move!" + " y1: " + ChessController.mouseposlist.get(1) + " x1: " + ChessController.mouseposlist.get(0));
        }
        
    }

}
