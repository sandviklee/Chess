package Chess;

import java.util.ArrayList;

import Chess.Chessboard.Chessboard;
import Chess.Pieces.BasePiece;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CheckGameState {

    private static ArrayList<ArrayList<Integer>> CheckPiecePatterns = new ArrayList<>();
    private Chessboard chessboard;
    private Move ChessMove;

    public CheckGameState(Chessboard chessboard, Move ChessMove) {
        this.chessboard = chessboard;
        this.ChessMove = ChessMove;
    }
    
    public void inCheck() {
        ArrayList<ArrayList<BasePiece>> chessboardState = chessboard.getChessboardState();
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
                    if (piece.getPieceColor().equals("w")) {
                        ArrayList<ArrayList<Integer>> piecePatterns = ChessMove.validatePattern(piece.getPiecePos().get(0), piece.getPiecePos().get(1));
                        if (piecePatterns.contains(kingPos)) {
                            System.out.println("Black king in check! by " + piece);
                            Alert a = new Alert(AlertType.NONE);
                            a.setAlertType(AlertType.WARNING);
                            a.setHeaderText("Black King in check!");
                            a.setContentText("Black king in check! by " + piece);
                            
                            a.show();
                        }
                        
                    }
                }

            }
        }

        

    }
    
}
