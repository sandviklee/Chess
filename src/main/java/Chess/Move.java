package Chess;

import java.util.ArrayList;
import java.util.Arrays;

import Chess.Chessboard.Chessboard;
import Chess.Chessboard.IO;
import Chess.Pieces.*;

/* This class is for implementing moving on the main board, but also validating piece patterns. */

public class Move {
    private Chessboard chessboard;
    private boolean Moving = false;
    private boolean whiteTurn = IO.whiteTurn;
    private boolean blackTurn = IO.blackTurn;
    private boolean statement = false;
    private boolean gameOver = false;
    private boolean knockedOut = false;
    private ArrayList<BasePiece> piecesOut = new ArrayList<>();

    public Move(Chessboard chessboard) {
        this.chessboard = chessboard;
        if (chessboard == null) {
            throw new NullPointerException();
        }
    }

    public ArrayList<BasePiece> getPiecesOut() {
        return new ArrayList<>(piecesOut);
    }

    public boolean getMoving() {
        return Moving;
    }

    public void setMoving(boolean Moving) {
        this.Moving = Moving;
    }

    public boolean getWhiteTurn() {
        return whiteTurn;
    }

    public boolean getBlackTurn() {
        return blackTurn;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public boolean getKnockedOut() {
        return knockedOut;
    }

    public ArrayList<ArrayList<Integer>> getValidatedPattern(int x, int y) {
        return new ArrayList<>(validatedPattern(x, y));
    }

    //Move method that is updated to how the chessboard is.
    public void MovePiece(int x_1, int y_1, int x_2, int y_2) {
        System.out.println(gameOver);
        Moving = true;
        BasePiece piece = chessboard.getChessboardState().get(y_1).get(x_1);
        BasePiece piece_2 = chessboard.getChessboardState().get(y_2).get(x_2);
    
        if (!(piece == null) && !(piece == piece_2)) {
            if (whiteTurn) {
                statement = (piece.getPieceColor() == 'w');
            } else if (blackTurn) {
                statement = (piece.getPieceColor() == 'b');
            }
            if (statement && getValidatedPattern(x_1, y_1).contains(new ArrayList<>(Arrays.asList(x_2, y_2))) && !gameOver) {
                if (piece_2 == null) {
                    try {
                        chessboard.setChessboardState(x_1, y_1, x_2, y_2);
                        SpecialMove(piece, x_2, y_2);
                        piece.pawnDoubleMove = false;                        
                        if (whiteTurn) {
                            whiteTurn = false;
                            blackTurn = true;
                        } else {
                            whiteTurn = true;
                            blackTurn = false;
                        }
                    } catch (Exception e) {
                        System.out.println("Bug:" + e);
                    }

                } else {                    
                    if (whiteTurn) {
                        whiteTurn = false;
                        blackTurn = true;
                    } else {
                        whiteTurn = true;
                        blackTurn = false;
                    }
                    knockedOut = true;
                    piecesOut.clear();
                    piecesOut.add(piece_2);
                    chessboard.setChessboardState(x_1, y_1, x_2, y_2);
                    SpecialMove(piece, x_2, y_2);
                    
                }
            } 
        }
    }

    private void SpecialMove(BasePiece piece, int x, int y) { // Movement which is special in game
        if (piece instanceof Pawn) {
            switch (piece.getPieceColor()) {
                case 'w':
                    if (piece.getPiecePos().equals(Arrays.asList(piece.getPiecePos().get(0), 0))) {
                        chessboard.PawnToQueen(piece.pieceColor, x, y);
                    }
                    break;
                case 'b':
                    if (piece.getPiecePos().equals(Arrays.asList(piece.getPiecePos().get(0), 7))) {
                        chessboard.PawnToQueen(piece.pieceColor, x, y);
                    }
                    break;
            }
        }
    }

    private ArrayList<ArrayList<Integer>> validatedPattern(int x, int y) {
        ArrayList<ArrayList<BasePiece>> chessboardState = chessboard.getChessboardState();
        BasePiece piece = chessboardState.get(y).get(x);
        ArrayList<ArrayList<Integer>> pattern = new ArrayList<>();

        if (piece != null) {
            pattern = piece.layPattern(x, y);
        }
        
        if (piece instanceof Pawn) {
            ArrayList<ArrayList<Integer>> allInvalidPos = new ArrayList<>();

            for (ArrayList<Integer> pos : pattern) {
                
                if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                    allInvalidPos.add(new ArrayList<>(pos));
                    if (piece.getPiecePos().equals(Arrays.asList(pos.get(0), pos.get(1)+1*piece.pieceColor))) {
                        allInvalidPos.addAll(new ArrayList<>(pattern));
                    } 
                } 
            }

            for (ArrayList<Integer> pos : allInvalidPos) {
                pattern.remove(pos);
            }

            if ((y < 7 && x < 7) && chessboardState.get(y+1).get(x+1) != null && !(chessboardState.get(y+1).get(x+1).getPieceColor() == piece.getPieceColor())) {
                pattern.add(new ArrayList<>(Arrays.asList(x+1, y+1)));
            }

            if ((y < 7 && x > 0) && chessboardState.get(y+1).get(x-1) != null && !(chessboardState.get(y+1).get(x-1).getPieceColor() == piece.getPieceColor())) {
                pattern.add(new ArrayList<>(Arrays.asList(x-1, y+1)));
            }

            if ((y > 0 && x < 7) && chessboardState.get(y-1).get(x+1) != null && !(chessboardState.get(y-1).get(x+1).getPieceColor() == piece.getPieceColor())) {
                pattern.add(new ArrayList<>(Arrays.asList(x+1, y-1)));
            }

            if ((y > 0 && x > 0) && chessboardState.get(y-1).get(x-1) != null && !(chessboardState.get(y-1).get(x-1).getPieceColor() == piece.getPieceColor())) {
                pattern.add(new ArrayList<>(Arrays.asList(x-1, y-1)));
            }     

        }
        else if (piece instanceof Knight) {
            ArrayList<ArrayList<Integer>> allInvalidPos = new ArrayList<>();

            for (ArrayList<Integer> pos : pattern) {
                if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                    if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceColor() == piece.getPieceColor()) {
                        allInvalidPos.add(new ArrayList<>(pos));
                    } 
                }
            }

            for (ArrayList<Integer> pos : allInvalidPos) {
                pattern.remove(pos);
            }

        }
        else if (piece instanceof Queen || piece instanceof Rook || piece instanceof Bishop) {
            pattern.remove(new ArrayList<>(Arrays.asList(x , y)));
            ArrayList<ArrayList<Integer>> allPiecesInFrontPos = new ArrayList<>();
            ArrayList<ArrayList<Integer>> allInvalidPos = new ArrayList<>();

            for (ArrayList<Integer> pos : pattern) {
                if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                    allPiecesInFrontPos.add(new ArrayList<Integer>(Arrays.asList(pos.get(0), pos.get(1))));
                    if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceColor() == piece.getPieceColor()) {
                        allInvalidPos.add(new ArrayList<>(pos));
                    }
                }
            }
            
            if (piece instanceof Queen || piece instanceof Rook) {
                //Rook logic
                for (int i = 0; i < allPiecesInFrontPos.size(); i++) {
                    if (allPiecesInFrontPos.get(i).get(1) > piece.getPiecePos().get(1)) {
                        for (int j = 1; j <= 7 - allPiecesInFrontPos.get(i).get(1); j++) {
                            int posX = allPiecesInFrontPos.get(i).get(0);
                            int posY = allPiecesInFrontPos.get(i).get(1) + j;
                            allInvalidPos.add(new ArrayList<>(Arrays.asList(posX, posY)));
                            
                        }
                    } else if (allPiecesInFrontPos.get(i).get(1) < piece.getPiecePos().get(1)) {
                        for (int j = 1; j <= allPiecesInFrontPos.get(i).get(1); j++) {
                            int posX = allPiecesInFrontPos.get(i).get(0);
                            int posY = allPiecesInFrontPos.get(i).get(1) - j;
                            allInvalidPos.add(new ArrayList<>(Arrays.asList(posX, posY)));
                            
                        }
                    } else if (allPiecesInFrontPos.get(i).get(0) > piece.getPiecePos().get(0)) {
                        for (int j = 1; j <= 7 - allPiecesInFrontPos.get(i).get(0); j++) {
                            int posX = allPiecesInFrontPos.get(i).get(0) + j;
                            int posY = allPiecesInFrontPos.get(i).get(1);
                            allInvalidPos.add(new ArrayList<>(Arrays.asList(posX, posY)));
                            
                        }
                    } else if (allPiecesInFrontPos.get(i).get(0) < piece.getPiecePos().get(0)) {
                        for (int j = 1; j <= allPiecesInFrontPos.get(i).get(0); j++) {
                            int posX = allPiecesInFrontPos.get(i).get(0) - j;
                            int posY = allPiecesInFrontPos.get(i).get(1);
                            allInvalidPos.add(new ArrayList<>(Arrays.asList(posX, posY)));
                            
                        }
                    }
                }
            }

            if (piece instanceof Queen || piece instanceof Bishop) {
                //Bishop logic
                for (int i = 0; i < allPiecesInFrontPos.size(); i++) {

                    if (allPiecesInFrontPos.get(i).get(1) > piece.getPiecePos().get(1)) {
                        if (allPiecesInFrontPos.get(i).get(0) < piece.getPiecePos().get(0)) {
                            for (int j = 1; j <= allPiecesInFrontPos.get(i).get(0); j++) {
                                int posX = allPiecesInFrontPos.get(i).get(0) - j;
                                int posY = allPiecesInFrontPos.get(i).get(1) + j;
                                allInvalidPos.add(new ArrayList<>(Arrays.asList(posX, posY)));
                            }
                        } else if (allPiecesInFrontPos.get(i).get(0) > piece.getPiecePos().get(0)) 
                            for (int j = 1; j <= 7 - allPiecesInFrontPos.get(i).get(0); j++) {
                                int posX = allPiecesInFrontPos.get(i).get(0) + j;
                                int posY = allPiecesInFrontPos.get(i).get(1) + j;
                                allInvalidPos.add(new ArrayList<>(Arrays.asList(posX, posY)));
                            }
                    } else if (allPiecesInFrontPos.get(i).get(1) < piece.getPiecePos().get(1)) {

                        if (allPiecesInFrontPos.get(i).get(0) < piece.getPiecePos().get(0)) {
                            for (int j = 1; j <= allPiecesInFrontPos.get(i).get(0); j++) {
                                int posX = allPiecesInFrontPos.get(i).get(0) - j;
                                int posY = allPiecesInFrontPos.get(i).get(1) - j;
                                allInvalidPos.add(new ArrayList<>(Arrays.asList(posX, posY)));
                            }
                        } else if (allPiecesInFrontPos.get(i).get(0) > piece.getPiecePos().get(0)) 
                            for (int j = 1; j <= 7 - allPiecesInFrontPos.get(i).get(0); j++) {
                                int posX = allPiecesInFrontPos.get(i).get(0) + j;
                                int posY = allPiecesInFrontPos.get(i).get(1) - j;
                                allInvalidPos.add(new ArrayList<>(Arrays.asList(posX, posY)));
                        }
                    }
                }
            }
            for (ArrayList<Integer> pos : allInvalidPos) {
                pattern.remove(pos);
            }
        }
        else if (piece instanceof King) {
            pattern.remove(new ArrayList<>(Arrays.asList(x , y)));
            ArrayList<ArrayList<Integer>> allInvalidPos = new ArrayList<>();

            for (ArrayList<Integer> pos : pattern) {
                if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                    if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceColor() == piece.getPieceColor()) {
                        allInvalidPos.add(new ArrayList<>(pos));
                    }
                    else if (pos.equals(Arrays.asList(x - 1, y)) || pos.equals(Arrays.asList(x + 1, y))) {
                        if (chessboardState.get(pos.get(1)).get(pos.get(0)) instanceof Pawn) {
                            allInvalidPos.add(new ArrayList<>(Arrays.asList(x, y + 1)));
                            allInvalidPos.add(new ArrayList<>(Arrays.asList(x, y - 1)));
                        }
                    } 
                }
            }
            
            for (ArrayList<BasePiece> row : chessboardState) {
                for (BasePiece pieces : row) {
                    if (pieces != null) {
                        int posX = pieces.getPiecePos().get(0);
                        int posY = pieces.getPiecePos().get(1);

                        if (!(pieces.getPieceColor() == piece.getPieceColor())) { //Not the same color.

                            if (!(pieces instanceof King)) {

                                if (pieces instanceof Bishop || pieces instanceof Rook || pieces instanceof Queen) {
                                    
                                    if (getValidatedPattern(posX, posY).contains(Arrays.asList(x, y)) && !piece.layPattern(x, y).contains(Arrays.asList(posX, posY))) {
                                        for (ArrayList<Integer> piecepos : pieces.layPattern(posX, posY)) {
                                            allInvalidPos.add(new ArrayList<>(piecepos));
                                        }
                                    } else {
                                        for (ArrayList<Integer> piecepos : getValidatedPattern(posX, posY)) {
                                            allInvalidPos.add(new ArrayList<>(piecepos));
                                        }
                                    }

                                    if (pieces instanceof Bishop || pieces instanceof Queen) {
                                        
                                        for (ArrayList<Integer> patternpos : pattern) {
                                            int patternX = patternpos.get(0);
                                            int patternY = patternpos.get(1);
                                            if (pieces.layPattern(posX, posY).contains(patternpos)) {
                                                if (chessboardState.get(patternY).get(patternX) != null) {
                                                    if (!chessboardState.get(patternY).get(patternX).equals(pieces)) {
                                                        if (getValidatedPattern(posX, posY).contains(Arrays.asList(patternX - 1, patternY + 1)) ||
                                                        getValidatedPattern(posX, posY).contains(Arrays.asList(patternX + 1, patternY + 1)) ||
                                                        getValidatedPattern(posX, posY).contains(Arrays.asList(patternX - 1, patternY - 1)) ||
                                                        getValidatedPattern(posX, posY).contains(Arrays.asList(patternX + 1, patternY - 1)) ||
                                                        pieces.getPiecePos().equals(Arrays.asList(patternX - 1, patternY + 1)) ||
                                                        pieces.getPiecePos().equals(Arrays.asList(patternX + 1, patternY + 1)) ||
                                                        pieces.getPiecePos().equals(Arrays.asList(patternX - 1, patternY - 1)) ||
                                                        pieces.getPiecePos().equals(Arrays.asList(patternX + 1, patternY - 1))) {
                                                            allInvalidPos.add(new ArrayList<>(patternpos));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (pieces instanceof Rook || pieces instanceof Queen) {

                                        for (ArrayList<Integer> patternpos : pattern) {
                                            int patternX = patternpos.get(0);
                                            int patternY = patternpos.get(1);
                                            if (pieces.layPattern(posX, posY).contains(patternpos)) {
                                                if (chessboardState.get(patternY).get(patternX) != null) {
                                                    if (!chessboardState.get(patternY).get(patternX).equals(pieces)) {
                                                        if (getValidatedPattern(posX, posY).contains(Arrays.asList(patternX, patternY + 1)) ||
                                                        getValidatedPattern(posX, posY).contains(Arrays.asList(patternX, patternY - 1)) ||
                                                        getValidatedPattern(posX, posY).contains(Arrays.asList(patternX - 1, patternY)) ||
                                                        getValidatedPattern(posX, posY).contains(Arrays.asList(patternX + 1, patternY)) ||
                                                        pieces.getPiecePos().equals(Arrays.asList(patternX, patternY + 1)) ||
                                                        pieces.getPiecePos().equals(Arrays.asList(patternX, patternY - 1)) ||
                                                        pieces.getPiecePos().equals(Arrays.asList(patternX - 1, patternY)) ||
                                                        pieces.getPiecePos().equals(Arrays.asList(patternX + 1, patternY))) {
                                                            allInvalidPos.add(new ArrayList<>(patternpos));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                else if (pieces instanceof Pawn) {
                                    allInvalidPos.add(new ArrayList<>(Arrays.asList(posX + 1, posY + 1)));
                                    allInvalidPos.add(new ArrayList<>(Arrays.asList(posX - 1, posY + 1)));
                                    allInvalidPos.add(new ArrayList<>(Arrays.asList(posX - 1, posY - 1)));
                                    allInvalidPos.add(new ArrayList<>(Arrays.asList(posX + 1, posY - 1)));

                                    for (ArrayList<Integer> patternpos : pattern) {
                                        int patternX = patternpos.get(0);
                                        int patternY = patternpos.get(1);
        
                                        if (chessboardState.get(patternY).get(patternX) != null) {
                                            if (pieces.getPiecePos().equals(Arrays.asList(patternX - 1, patternY + 1)) ||
                                                pieces.getPiecePos().equals(Arrays.asList(patternX + 1, patternY + 1)) ||
                                                pieces.getPiecePos().equals(Arrays.asList(patternX - 1, patternY - 1)) ||
                                                pieces.getPiecePos().equals(Arrays.asList(patternX + 1, patternY - 1))) {
                                                    allInvalidPos.add(new ArrayList<>(patternpos));
                                                }
                                        }
                                    }
                                }

                                else if (pieces instanceof Knight) {
                                    for (ArrayList<Integer> piecepos : pieces.layPattern(x, y)) {
                                        allInvalidPos.add(new ArrayList<>(piecepos));
                                    }
                                }
                            }
                            else {
                                for (ArrayList<Integer> piecepos : pieces.layPattern(posX, posY)) {
                                    allInvalidPos.add(new ArrayList<>(piecepos));
                                }
                            }
                        }
                    }
                }
            }

            for (ArrayList<Integer> pos : allInvalidPos) {
                pattern.remove(pos);
            }
        } 
    return new ArrayList<>(pattern);
    }
}


