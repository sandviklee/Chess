package Chess;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import Chess.Chessboard.Chessboard;
import Chess.Pieces.BasePiece;
import Chess.Pieces.King;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class CheckGameState {
    private Chessboard chessboard;
    private Move ChessMove;
    private ArrayList<ArrayList<BasePiece>> chessboardState = new ArrayList<>();
    private boolean kingWCheck;
    private boolean kingBCheck;
    private boolean checkMate = false;
    private BasePiece pieceWCheck;
    private BasePiece pieceBCheck;
    private ArrayList<ArrayList<Integer>> piecePosWCheck = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> piecePosBCheck = new ArrayList<>();
    private String playerWName;
    private String playerBName;
    private ArrayList<Integer> kingPosB = new ArrayList<>();
    private ArrayList<Integer> kingPosW = new ArrayList<>();
    private boolean draw = false;

    public CheckGameState(Chessboard chessboard, Move ChessMove) {
        this.chessboard = chessboard;
        this.ChessMove = ChessMove;
        if (chessboard == null || ChessMove == null) {
            throw new NullPointerException();
        }
    }

    public ArrayList<Integer> getKingWPos() {
        return kingPosW;
    }

    public ArrayList<Integer> getKingBPos() {
        return kingPosB;
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

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public boolean getDraw() {
        return draw;
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

    public void AlertGameState(String header, String text, AlertType alerttype) {
        Alert a = new Alert(alerttype);
        a.setHeaderText(header);
        a.setContentText(text);
        a.show();
    }

    private ArrayList<Integer> kingPosFinder(ArrayList<ArrayList<BasePiece>> chessboardState, char k) {
        for (ArrayList<BasePiece> row : chessboardState) {
            for (BasePiece piece : row) {
                if (piece != null) {
                    if (piece instanceof King && piece.getPieceColor() == k) {
                        return piece.getPiecePos();
                    }
                }
            }
        }
        return null;
    }
    protected int checkState; //This is protected because im just using it here, and in the Tests.
    public void inCheck() throws FileNotFoundException {
        chessboardState = chessboard.getChessboardState();
        kingPosB = kingPosFinder(chessboardState, 'b');
        kingPosW = kingPosFinder(chessboardState, 'w');

        checkState = -1;
        for (ArrayList<BasePiece> row : chessboardState) {
            for (BasePiece piece : row) {
                if (piece != null) {
                    if (piece.getPieceColor() == 'w') {
                        int posX = piece.getPiecePos().get(0);
                        int posY = piece.getPiecePos().get(1);
                        if (ChessMove.getValidatedPattern(posX, posY).contains(kingPosB)) {
                            checkState += 1;
                            kingBCheck = true;
                            piecePosBCheck.add(piece.getPiecePos());
                            pieceBCheck = piece;      
                        }
                        
                    } else if (piece.getPieceColor() == 'b') {
                        int posX = piece.getPiecePos().get(0);
                        int posY = piece.getPiecePos().get(1);
                        if (ChessMove.getValidatedPattern(posX, posY).contains(kingPosW)) {
                            checkState += 1;
                            kingWCheck = true;
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
        chessboardState = chessboard.getChessboardState();
        kingPosB = kingPosFinder(chessboardState, 'b');
        kingPosW = kingPosFinder(chessboardState, 'w');

        if (getKingWCheck()) {
            int posX = kingPosW.get(0);
            int posY = kingPosW.get(1);
            ArrayList<ArrayList<Integer>> allWhitePiecesPos = new ArrayList<>();
            if (ChessMove.getValidatedPattern(posX, posY).isEmpty()) {
                for (ArrayList<BasePiece> row : chessboardState) {
                    for (BasePiece piece : row) {
                        if (piece != null && piece.getPieceColor() == 'w') {
                            allWhitePiecesPos.add(piece.getPiecePos());
                        }
                    }
                }

                ArrayList<ArrayList<Integer>> allBlockedMoves = new ArrayList<>(chessboardState.get(posY).get(posX).layPattern(posX, posY));
                for (ArrayList<Integer> arrayList : allBlockedMoves) {
                    if (allWhitePiecesPos.contains(arrayList)) {
                        allWhitePiecesPos.remove(arrayList);
                    }
                }
                allBlockedMoves.remove(kingPosW);

                if (allWhitePiecesPos.stream().anyMatch(a -> ChessMove.getValidatedPattern(a.get(0), a.get(1)).stream().anyMatch(c -> piecePosWCheck.contains(c)))) { 
                    //Oneliner that checks if there are any pieces that can knock out the checking piece. 
                    if (piecePosWCheck.size() > 1 && !(piecePosWCheck.stream().distinct().count() <= 1)) {
                        System.out.println("State 2");
                        checkMate = true;
                    }
                } else {
                    if ((allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null)).anyMatch(a -> chessboardState.get(a.get(1)).get(a.get(0)).getPieceColor() == 'w')) {
                        //Oneliner that checks for all the white pieces in the blocked move area.
                        if (allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null).anyMatch(a -> ChessMove.getValidatedPattern(a.get(0), a.get(1)).stream().anyMatch(c -> piecePosWCheck.contains(c)))) {
                            //Oneliner that checks if there are any pieces that blocks the king from moving that can knock out the piece.
                            if (piecePosWCheck.size() > 1 && !(piecePosWCheck.stream().distinct().count() <= 1)) {
                                System.out.println("State 4");
                                checkMate = true;
                            }
                        }
                        else {
                            //System.out.println(ChessMove.getValidatedPattern(piecePosWCheck.get(0).get(0), piecePosWCheck.get(0).get(1)).stream().filter(a -> (a.get(0) < piecePosWCheck.get(0).get(0) && a.get(0) > kingPosW.get(0))).filter(b -> (b.get(1) > piecePosWCheck.get(0).get(1) && b.get(1) < kingPosW.get(1))).toList());
                            if (allWhitePiecesPos.stream().anyMatch(a -> ChessMove.getValidatedPattern(a.get(0), a.get(1)).stream().anyMatch(c -> (ChessMove.getValidatedPattern(piecePosWCheck.get(0).get(0), piecePosWCheck.get(0).get(1)).stream().filter(x -> (x.get(0) < piecePosWCheck.get(0).get(0) && x.get(0) > kingPosW.get(0))).filter(y -> (y.get(1) > piecePosWCheck.get(0).get(1) && y.get(1) < kingPosW.get(1)))).toList().contains(c)))) {
                               /*Oneliner that is not finished, but checks if there are pieces that can block the path to the king, such that you cannot put it in checkmate.
                                if you want to check this out, load in the file "PieceCanBlockCheckmatePattern" from testFiles in saveFiles.
                                This oneliner is also not enough to check every state, read the documentation at point 3.5 for more information.*/
                            } else {
                                System.out.println("State 3");
                                checkMate = true;
                            }
                            
                        }
                    } else {
                        System.out.println("State 1");
                        checkMate = true;
                    } 
                }
            } 
        }

        if (getKingBCheck()) {
            int posX = kingPosB.get(0);
            int posY = kingPosB.get(1);
            ArrayList<ArrayList<Integer>> allBlackPiecesPos = new ArrayList<>();
            if (ChessMove.getValidatedPattern(posX, posY).isEmpty()) {
                for (ArrayList<BasePiece> row : chessboardState) {
                    for (BasePiece piece : row) {
                        if (piece != null && piece.getPieceColor() == 'b') {
                            allBlackPiecesPos.add(piece.getPiecePos());
                        }
                    }
                }

                ArrayList<ArrayList<Integer>> allBlockedMoves = new ArrayList<>(chessboardState.get(posY).get(posX).layPattern(posX, posY));
                for (ArrayList<Integer> arrayList : allBlockedMoves) {
                    if (allBlackPiecesPos.contains(arrayList)) {
                        allBlackPiecesPos.remove(arrayList);
                    }
                }
                allBlockedMoves.remove(kingPosB);

                if (allBlackPiecesPos.stream().anyMatch(a -> ChessMove.getValidatedPattern(a.get(0), a.get(1)).stream().anyMatch(c -> piecePosBCheck.contains(c)))) {
                    if (piecePosBCheck.size() > 1 && !(piecePosBCheck.stream().distinct().count() <= 1)) {
                        System.out.println("State 2");
                        checkMate = true;
                    }
                } else {
                    if ((allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null)).anyMatch(a -> chessboardState.get(a.get(1)).get(a.get(0)).getPieceColor() == 'b')) {
                        if (allBlockedMoves.stream().filter(b -> chessboardState.get(b.get(1)).get(b.get(0)) != null).anyMatch(a -> ChessMove.getValidatedPattern(a.get(0), a.get(1)).stream().anyMatch(c -> piecePosBCheck.contains(c)))) {
                            if (piecePosBCheck.size() > 1 && !(piecePosBCheck.stream().distinct().count() <= 1)) {
                                System.out.println("State 4");
                                checkMate = true;
                            }
                        }
                        else {
                            System.out.println("State 3");
                            checkMate = true;
                        }
                    } else {
                        System.out.println("State 1");
                        checkMate = true;
                    } 
                }
            } 
        }

        ChessMove.setGameOver(checkMate);
    }

    public void inDraw() {
        if (draw) {
            ChessMove.setGameOver(true);
        }
    }

    public void setgameStateAlert() throws FileNotFoundException {
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

        if (draw) {
            AlertGameState("Draw!", "This game ended in a draw!", AlertType.INFORMATION);
        }
    }

}
