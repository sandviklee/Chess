package Chess;

import java.util.ArrayList;

import Chess.Pieces.BasePiece;

public class CheckGameState {
    public static ArrayList<ArrayList<Integer>> CheckPiecePatterns = new ArrayList<>();

    public static void inCheck() {
        ArrayList<ArrayList<BasePiece>> chessboardState = ChessInit.chessboard.getChessboardState();
        ArrayList<Integer> kingPos = new ArrayList<>();

        for (ArrayList<BasePiece> row : chessboardState) {
            for (BasePiece piece : row) {
                if (piece != null) {
                    if (piece.getPieceName().equals("Chess.Pieces.King") && piece.getPieceColor().equals("b")) {
                        kingPos = new ArrayList<>(piece.getPiecePos());
                    }
                }
            }
        }

        for (ArrayList<BasePiece> row : chessboardState) {
            for (BasePiece piece : row) {
                if (piece != null) {
                    if (piece.getPieceColor().equals("w") && !(piece.getPieceName().equals("Chess.Pieces.Pawn"))) {
                        ArrayList<ArrayList<Integer>> piecePatterns = Move.validatePattern(piece.getPiecePos().get(0), piece.getPiecePos().get(1));
                        if (piecePatterns.contains(kingPos)) {
                            System.out.println("Black king in check! by " + piece);
                
                        }
                        
                    }
                }

            }
        }

        

    }
    
}
