package Chess;

import Chess.Pieces.BasePiece;

/* This class is for implementing moving on the main board, but also updating the Chessgame class */

public class Move {

    public static boolean Moving = false;

    public static void MovePiece() {
        //TODO: ADD MORE FUNCTIONALITY
        //NEED BASIC PIECE LOGIC AND MOVING FROM THE SAME SPACE TO THE OTHER IS NOT AN AVAILABLE MOVE.
        Moving = true;
        int x_1 = ChessInit.mouseposlist.get(0);
        int y_1 = ChessInit.mouseposlist.get(1);
        int x_2 = ChessInit.mouseposlist.get(2);
        int y_2 = ChessInit.mouseposlist.get(3);

        BasePiece piece = ChessInit.chessboard.getChessboardState().get(y_1).get(x_1);
        BasePiece piece_2 = ChessInit.chessboard.getChessboardState().get(y_2).get(x_2);

        if (!(piece == null) && !(piece == piece_2)) {
            if (piece.legalMove(x_2, y_2)) {
                if (piece_2 == null) {
                    try {
                        ChessInit.chessboard.Move(x_1, y_1, x_2, y_2);
                        System.out.println("Moved!");
                    } catch (Exception e) {
                        System.out.println("BUGGGG");
                    }

                } else {
                    if (((piece.getPieceColor()).equals(piece_2.getPieceColor()))) {
                        System.out.println("Not a legal move!");
                    } else {
                        ChessInit.chessboard.Move(x_1, y_1, x_2, y_2);
                        System.out.println("Moved!");
                    }
                }
            System.out.println(ChessInit.chessboard.getChessboardState());
            } 
        }
    }

    public void validatePattern() {

    }

}
