package Chess;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import Chess.Chessboard.Chessboard;
import Chess.Pieces.BasePiece;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class CheckGameState {

    private Chessboard chessboard;
    private Move ChessMove;
    private boolean kingWCheck;
    private boolean kingBCheck;
    private boolean checkMate = false;

    public CheckGameState(Chessboard chessboard, Move ChessMove) {
        this.chessboard = chessboard;
        this.ChessMove = ChessMove;
    }

    public boolean getKingWCheck() {
        return kingWCheck;
    }

    public boolean getKingBCheck() {
        return kingBCheck;
    }

    public boolean getCheckMate() {
        return checkMate;
    }
    
    public void inCheck() throws FileNotFoundException {
        ArrayList<ArrayList<BasePiece>> chessboardState = chessboard.getChessboardState();
        ArrayList<Integer> kingPosB = new ArrayList<>();
        ArrayList<Integer> kingPosW = new ArrayList<>();

        for (ArrayList<BasePiece> row : chessboardState) {
            for (BasePiece piece : row) {
                if (piece != null) {
                    switch (piece.toString()) {
                        case "King":
                            switch (piece.getPieceColor()) {
                                case "w":
                                    kingPosW = new ArrayList<>(piece.getPiecePos());
                                    break;
                                case "b":
                                    kingPosB = new ArrayList<>(piece.getPiecePos());
                                    break;
                            }
                            break;
                    }
                }
            }
        }

        int checkState = -1;
        for (ArrayList<BasePiece> row : chessboardState) {
            for (BasePiece piece : row) {
                if (piece != null) {
                    if (piece.getPieceColor().equals("w")) {
                        int posX = piece.getPiecePos().get(0);
                        int posY = piece.getPiecePos().get(1);
                        ArrayList<ArrayList<Integer>> piecePatterns = ChessMove.validatePattern(posX, posY);
                        if (piecePatterns.contains(kingPosB)) {
                            checkState += 1;
                            kingBCheck = true;
                            ChessMove.KingNotCheck = false;
                            
                            Alert a = new Alert(AlertType.NONE);
                            a.setAlertType(AlertType.WARNING);
                            a.setHeaderText("Black King in check!");
                            a.setGraphic(new ImageView(chessboard.addChessImage("cpb/b_king")));
                            a.setContentText("Black king in check! by White " + piece);
                            a.show(); 

                        }
                        
                    } else if (piece.getPieceColor().equals("b")) {
                        int posX = piece.getPiecePos().get(0);
                        int posY = piece.getPiecePos().get(1);
                        if (ChessMove.validatePattern(posX, posY).contains(kingPosW)) {
                            checkState += 1;
                            kingWCheck = true;
                            ChessMove.KingNotCheck = false;

                            Alert a = new Alert(AlertType.NONE);
                            a.setAlertType(AlertType.WARNING);
                            a.setGraphic(new ImageView(chessboard.addChessImage("cpw/w_king")));
                            a.setHeaderText("White King in check!");
                            a.setContentText("White king in check! by Black " + piece);
                            a.show(); 

                        } 
                    }
                }
            }
        }
        if (checkState == -1) {
            kingWCheck = false;
            kingBCheck = false;
        }
    }
    
    public void inCheckMate() {
        ArrayList<ArrayList<BasePiece>> chessboardState = chessboard.getChessboardState();
        ArrayList<Integer> kingPosB = new ArrayList<>();
        ArrayList<Integer> kingPosW = new ArrayList<>();

        for (ArrayList<BasePiece> row : chessboardState) {
            for (BasePiece piece : row) {
                if (piece != null) {
                    switch (piece.toString()) {
                        case "King":
                            switch (piece.getPieceColor()) {
                                case "w":
                                    kingPosW = new ArrayList<>(piece.getPiecePos());
                                    
                                    break;
                                case "b":
                                    kingPosB = new ArrayList<>(piece.getPiecePos());
                                    
                                    break;
                            }
                            break;
                    }
                }
            }
        }
        System.out.println("White king checked: " + getKingWCheck());
        if (getKingWCheck()) {
            int posX = kingPosW.get(0);
            int posY = kingPosW.get(1);
            if (ChessMove.validatePattern(posX, posY).isEmpty()) {
                ArrayList<ArrayList<Integer>> allBlockedMoves = new ArrayList<>(chessboardState.get(posY).get(posX).layPattern(posX, posY));
                if ((allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null)).allMatch(a -> chessboardState.get(a.get(1)).get(a.get(0)).getPieceColor().equals("w"))) {
                    System.out.println("No available pattern.");
                    if (allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null).allMatch(a -> ChessMove.validatePattern(a.get(0), a.get(1)).stream().filter(c -> chessboardState.get(c.get(1))))) {

                    }
                } else {
                    checkMate = true;
                    System.out.println("DU E CHECKED!!");
                } 
            } 
        }
        
    }

}
