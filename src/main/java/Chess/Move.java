package Chess;

import java.util.ArrayList;
import java.util.Arrays;

import Chess.Chessboard.Chessboard;
import Chess.Pieces.BasePiece;

/* This class is for implementing moving on the main board, but also updating the Chessgame class */

public class Move {
    private boolean Moving = false;
    private boolean whiteTurn = true;
    private boolean blackTurn = true;
    private boolean statement = true;
    private boolean KingNotCheck = true;
    private boolean gameOver = false;
    private Chessboard chessboard;


    public Move(Chessboard chessboard) {
        this.chessboard = chessboard;
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

    public void setKingNotCheck(boolean KingNotCheck) {
        this.KingNotCheck = KingNotCheck;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void MovePiece(int x_1, int y_1, int x_2, int y_2) {
        //TODO: ADD MORE FUNCTIONALITY
        //NEED BASIC PIECE LOGIC AND MOVING FROM THE SAME SPACE TO THE OTHER IS NOT AN AVAILABLE MOVE.
        Moving = true;

        BasePiece piece = chessboard.getChessboardState().get(y_1).get(x_1);
        BasePiece piece_2 = chessboard.getChessboardState().get(y_2).get(x_2);

        if (!(piece == null) && !(piece == piece_2)) {
            /*
            if (whiteTurn) {
                statement = piece.getPieceColor().equals("w");

            } else if (blackTurn) {
                statement = piece.getPieceColor().equals("b");

            }
            */
            // if (!KingNotCheck) {
            //     KingNotCheck = piece.toString().equals("King");
            // }

            if (statement && validatePattern(x_1, y_1).contains(new ArrayList<>(Arrays.asList(x_2, y_2)))
            /*&& KingNotCheck*/ && !gameOver) {
                if (piece_2 == null) {
                    try {
                        chessboard.setChessboardState(x_1, y_1, x_2, y_2);
                        SpecialMove(piece, x_2, y_2);
                        System.out.println("Moved!");
                        KingNotCheck = true;
                        
                        /*
                        if (whiteTurn) {
                            whiteTurn = false;
                            blackTurn = true;
                        } else {
                            whiteTurn = true;
                            blackTurn = false;
                        }
                        */
                    } catch (Exception e) {
                        System.out.println("Bug:" + e);
                    }

                } else {
                    if ((piece.getPieceColor() == piece_2.getPieceColor())) {
                        System.out.println("Not a legal move!");
                    } else {
                        chessboard.setChessboardState(x_1, y_1, x_2, y_2);
                        SpecialMove(piece, x_2, y_2);
                        System.out.println("Moved!");
                    }
                }
            //System.out.println(chessboard.getChessboardState());
            } 
        }
    }

    private void SpecialMove(BasePiece piece, int x, int y) { // Movement which is special in game
        if (piece.toString().equals("Pawn")) {
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

    public ArrayList<ArrayList<Integer>> validatePattern(int x, int y) {
        ArrayList<ArrayList<BasePiece>> chessboardState = chessboard.getChessboardState();
        BasePiece piece = chessboardState.get(y).get(x);
        ArrayList<ArrayList<Integer>> pattern = new ArrayList<>();

        if (piece != null) {
            pattern = piece.layPattern(x, y);
        }
        
        if (piece.toString().equals("Pawn")) {
            try {
                if (chessboardState.get(y-1*piece.pieceColor).get(x) != null) {
                    pattern.remove(new ArrayList<>(Arrays.asList(x, y-1*piece.pieceColor)));
                }
            } catch (Exception e) {
                //TODO: handle exception 
            }
            try {
                if (chessboardState.get(y+1).get(x+1) != null && !(chessboardState.get(y+1).get(x+1).getPieceColor() == piece.getPieceColor())) {
                    pattern.add(new ArrayList<>(Arrays.asList(x+1, y+1)));
                }
            } catch (Exception e) {
                //TODO: handle exception
            }

            try {
                if (chessboardState.get(y+1).get(x-1) != null && !(chessboardState.get(y+1).get(x-1).getPieceColor() == piece.getPieceColor())) {
                    pattern.add(new ArrayList<>(Arrays.asList(x-1, y+1)));
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
            try {

                if (chessboardState.get(y-1).get(x+1) != null && !(chessboardState.get(y-1).get(x+1).getPieceColor() == piece.getPieceColor())) {
                    pattern.add(new ArrayList<>(Arrays.asList(x+1, y-1)));
                }
                
            } catch (Exception e) {
                //TODO: handle exception
            }

            try {
                if (chessboardState.get(y-1).get(x-1) != null && !(chessboardState.get(y-1).get(x-1).getPieceColor() == piece.getPieceColor())) {
                    pattern.add(new ArrayList<>(Arrays.asList(x-1, y-1)));
                }        
                
            } catch (Exception e) {
                //TODO: handle exception
            }


        }
        else if (piece.toString().equals("Knight")) {
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
        else if (piece.toString().equals("Queen") || piece.toString().equals("Bishop") 
        || piece.toString().equals("Rook")) {
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

            if (piece.toString().equals("Queen") || piece.toString().equals("Rook")) {
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

            if (piece.toString().equals("Queen") || piece.toString().equals("Bishop")) {
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
        else if (piece.toString().equals("King")) {
            pattern.remove(new ArrayList<>(Arrays.asList(x , y)));
            ArrayList<ArrayList<Integer>> allInvalidPos = new ArrayList<>();

            for (ArrayList<Integer> pos : pattern) {
                if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                    if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceColor() == piece.getPieceColor()) {
                        allInvalidPos.add(new ArrayList<>(pos));
                    }
                    else if (pos.equals(Arrays.asList(x - 1, y)) || pos.equals(Arrays.asList(x + 1, y))) {
                        if (chessboardState.get(pos.get(1)).get(pos.get(0)).toString().equals("Pawn")) {
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

                            if (!pieces.toString().equals("King")) {

                                if (pieces.toString().equals("Bishop") || pieces.toString().equals("Rook") || pieces.toString().equals("Queen")) {
                                    
                                    if (validatePattern(posX, posY).contains(Arrays.asList(x, y)) && !piece.layPattern(x, y).contains(Arrays.asList(posX, posY))) {
                                        for (ArrayList<Integer> piecepos : pieces.layPattern(posX, posY)) {
                                            allInvalidPos.add(new ArrayList<>(piecepos));
                                        }
                                    } else {
                                        for (ArrayList<Integer> piecepos : validatePattern(posX, posY)) {
                                            allInvalidPos.add(new ArrayList<>(piecepos));
                                        }
                                    }

                                    if (pieces.toString().equals("Bishop") || pieces.toString().equals("Queen")) {
                                        
                                        for (ArrayList<Integer> patternpos : pattern) {
                                            int patternX = patternpos.get(0);
                                            int patternY = patternpos.get(1);
                                            if (pieces.layPattern(posX, posY).contains(patternpos)) {
                                                if (chessboardState.get(patternY).get(patternX) != null) {
                                                    if (!chessboardState.get(patternY).get(patternX).equals(pieces)) {
                                                        if (validatePattern(posX, posY).contains(Arrays.asList(patternX - 1, patternY + 1)) ||
                                                        validatePattern(posX, posY).contains(Arrays.asList(patternX + 1, patternY + 1)) ||
                                                        validatePattern(posX, posY).contains(Arrays.asList(patternX - 1, patternY - 1)) ||
                                                        validatePattern(posX, posY).contains(Arrays.asList(patternX + 1, patternY - 1)) ||
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

                                    if (pieces.toString().equals("Rook") || pieces.toString().equals("Queen")) {

                                        for (ArrayList<Integer> patternpos : pattern) {
                                            int patternX = patternpos.get(0);
                                            int patternY = patternpos.get(1);
                                            if (pieces.layPattern(posX, posY).contains(patternpos)) {
                                                if (chessboardState.get(patternY).get(patternX) != null) {
                                                    if (!chessboardState.get(patternY).get(patternX).equals(pieces)) {
                                                        if (validatePattern(posX, posY).contains(Arrays.asList(patternX, patternY + 1)) ||
                                                        validatePattern(posX, posY).contains(Arrays.asList(patternX, patternY - 1)) ||
                                                        validatePattern(posX, posY).contains(Arrays.asList(patternX - 1, patternY)) ||
                                                        validatePattern(posX, posY).contains(Arrays.asList(patternX + 1, patternY)) ||
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

                                else if (pieces.toString().equals("Pawn")) {
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

                                else if (pieces.toString().equals("Knight")) {
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
            //System.out.println(allInvalidPos + ": InvalidPos");
            for (ArrayList<Integer> pos : allInvalidPos) {
                pattern.remove(pos);
            }
        } 
    return pattern;
    }
}


