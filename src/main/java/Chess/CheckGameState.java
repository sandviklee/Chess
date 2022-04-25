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
    private BasePiece pieceWCheck;
    private BasePiece pieceBCheck;
    private ArrayList<ArrayList<Integer>> piecePosWCheck = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> piecePosBCheck = new ArrayList<>();
    private String playerWName;
    private String playerBName;
    public boolean draw = false;

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

    public void setDraw(boolean b) {
        this.draw = b;
    }

    public void setPlayerWName(String name) {
        this.playerWName = name;
    }

    public void setPlayerBName(String name) {
        this.playerBName = name;
    }

    private void AlertGameState(String header, String Text, String ChessImg, BasePiece piece) throws FileNotFoundException {
        Alert a = new Alert(AlertType.WARNING);
        a.setHeaderText(header);
        a.setGraphic(new ImageView(chessboard.addChessImage(ChessImg)));
        a.setContentText(Text + piece);
        a.show(); 
    }

    public void AlertGameState(String header, String text) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setHeaderText(header);
        a.setContentText(text);
        a.show();
    }

    public void AlertGameState(String header, String text, AlertType alerttype) {
        Alert a = new Alert(alerttype);
        a.setHeaderText(header);
        a.setContentText(text);
        a.show();
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
                                case 'w':
                                    kingPosW = new ArrayList<>(piece.getPiecePos());
                                    break;
                                case 'b':
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
                    if (piece.getPieceColor() == 'w') {
                        int posX = piece.getPiecePos().get(0);
                        int posY = piece.getPiecePos().get(1);
                        if (ChessMove.validatePattern(posX, posY).contains(kingPosB)) {
                            checkState += 1;
                            kingBCheck = true;
                            // ChessMove.setKingNotCheck(false);
                            piecePosBCheck.add(piece.getPiecePos());
                            pieceBCheck = piece;      
                        }
                        
                    } else if (piece.getPieceColor() == 'b') {
                        int posX = piece.getPiecePos().get(0);
                        int posY = piece.getPiecePos().get(1);
                        if (ChessMove.validatePattern(posX, posY).contains(kingPosW)) {
                            checkState += 1;
                            kingWCheck = true;
                            // ChessMove.setKingNotCheck(false);
                            piecePosWCheck.add(piece.getPiecePos());
                            pieceWCheck = piece;               
                        } 
                    }
                }
            }
        }
        if (checkState == -1) {
            kingWCheck = false;
            piecePosBCheck.clear();
            kingBCheck = false;
            piecePosWCheck.clear();
        }
    }
    
    public void inCheckMate() throws FileNotFoundException {
        ArrayList<ArrayList<BasePiece>> chessboardState = chessboard.getChessboardState();
        ArrayList<Integer> kingPosB = new ArrayList<>();
        ArrayList<Integer> kingPosW = new ArrayList<>();

        for (ArrayList<BasePiece> row : chessboardState) {
            for (BasePiece piece : row) {
                if (piece != null) {
                    switch (piece.toString()) {
                        case "King":
                            switch (piece.getPieceColor()) {
                                case 'w':
                                    kingPosW = new ArrayList<>(piece.getPiecePos());                     
                                    break;
                                case 'b':
                                    kingPosB = new ArrayList<>(piece.getPiecePos());
                                    break;
                            }
                            break;
                    }
                }
            }
        }

        if (getKingWCheck()) {
            int posX = kingPosW.get(0);
            int posY = kingPosW.get(1);
            ArrayList<ArrayList<Integer>> allWhitePiecesPos = new ArrayList<>();
            if (ChessMove.validatePattern(posX, posY).isEmpty()) {
                for (ArrayList<BasePiece> row : chessboardState) {
                    for (BasePiece piece : row) {
                        if (piece != null && piece.getPieceColor() == 'w') {
                            allWhitePiecesPos.add(piece.getPiecePos());
                        }
                    }
                }
                ArrayList<ArrayList<Integer>> allBlockedMoves = new ArrayList<>(chessboardState.get(posY).get(posX).layPattern(posX, posY));
                
                if (allWhitePiecesPos.stream().anyMatch(a -> ChessMove.validatePattern(a.get(0), a.get(1)).stream().anyMatch(c -> c.equals(piecePosWCheck.get(0))))) {
                    System.out.println("Du kan fortsatt bevege deg.");
                } else {
                    if ((allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null)).allMatch(a -> chessboardState.get(a.get(1)).get(a.get(0)).getPieceColor() == 'w')) {
                        if (allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null).anyMatch(a -> ChessMove.validatePattern(a.get(0), a.get(1)).stream().anyMatch(c -> c.equals(piecePosWCheck.get(0))))) {
                            System.out.println("Du kan fortsatt bevege deg.");
                        }
                        else {
                            checkMate = true;
                        }
                    } else {
                        checkMate = true;
                    } 
                }

                if (piecePosWCheck.size() > 1 && !(piecePosWCheck.stream().distinct().count() <= 1)) {
                    checkMate = true;
                }
            } 
        }

        if (getKingBCheck()) {
            int posX = kingPosB.get(0);
            int posY = kingPosB.get(1);
            ArrayList<ArrayList<Integer>> allBlackPiecesPos = new ArrayList<>();
            if (ChessMove.validatePattern(posX, posY).isEmpty()) {
                for (ArrayList<BasePiece> row : chessboardState) {
                    for (BasePiece piece : row) {
                        if (piece != null && piece.getPieceColor() == 'b') {
                            allBlackPiecesPos.add(piece.getPiecePos());
                        }
                    }
                }

                ArrayList<ArrayList<Integer>> allBlockedMoves = new ArrayList<>(chessboardState.get(posY).get(posX).layPattern(posX, posY));
                if (allBlackPiecesPos.stream().anyMatch(a -> ChessMove.validatePattern(a.get(0), a.get(1)).stream().anyMatch(c -> c.equals(piecePosBCheck.get(0))))) {
                    System.out.println("Du kan fortsatt bevege deg.");
                } else {
                    if ((allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null)).allMatch(a -> chessboardState.get(a.get(1)).get(a.get(0)).getPieceColor() == 'b')) {
                        if (allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null).anyMatch(a -> ChessMove.validatePattern(a.get(0), a.get(1)).stream().anyMatch(c -> c.equals(piecePosWCheck.get(0))))) {
                            System.out.println("Du kan fortsatt bevege deg.");   
                        }
                        else {
                            checkMate = true;
                        }
                    } else {
                        checkMate = true;
    
                    } 
                }
                if (piecePosBCheck.size() > 1 && !(piecePosBCheck.stream().distinct().count() <= 1)) {
                    checkMate = true;
                }
            } 
        }

        ChessMove.setGameOver(checkMate);
        if (getKingWCheck()) {
            if (checkMate) {
                AlertGameState("Black Won!", "" + playerWName + " Won! Checked by ", "cpb/b_" + pieceWCheck + "".toLowerCase(), pieceWCheck);
            } else {
                AlertGameState("White King in check!", "Checked by Black ", "cpw/w_king", pieceWCheck);
            }          
        } else if (getKingBCheck()) {
            if (checkMate) {
                AlertGameState("White Won!", playerBName + " Won! Checked by ", "cpw/w_" + pieceBCheck + "".toLowerCase(), pieceBCheck);
            } else {
                AlertGameState("Black King in check!", "Checked by White ", "cpb/b_king", pieceBCheck);
            }          
        }
        
    }

    public void inDraw() {
        if (draw) {
            ChessMove.setGameOver(draw);
            AlertGameState("Draw!", "This game ended in a draw!");
        }
    }

}
