package Chess;

import java.util.ArrayList;
import java.util.Arrays;

import Chess.Chessboard.Chessboard;
import Chess.Pieces.BasePiece;


/* This class is for implementing moving on the main board, but also updating the Chessgame class */

public class Move {
    public boolean Moving = false;
    public boolean whiteTurn = true;
    public boolean blackTurn = true;
    private boolean statement = true;
    private Chessboard chessboard;


    public Move(Chessboard chessboard) {
        this.chessboard = chessboard;
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

            if (statement && validatePattern(x_1, y_1).contains(new ArrayList<>(Arrays.asList(x_2, y_2)))) {
                if (piece_2 == null) {
                    try {
                        chessboard.setChessboardState(x_1, y_1, x_2, y_2);
                        System.out.println("Moved!");
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
                        System.out.println("BUG:" + " " + e);
                    }

                } else {
                    if (((piece.getPieceColor()).equals(piece_2.getPieceColor()))) {
                        System.out.println("Not a legal move!");
                    } else {
                        chessboard.setChessboardState(x_1, y_1, x_2, y_2);
                        System.out.println("Moved!");
                    }
                }
            //System.out.println(chessboard.getChessboardState());
            } 
        }
    }

    public ArrayList<ArrayList<Integer>> validatePattern(int x, int y) {
        ArrayList<ArrayList<BasePiece>> chessboardState = chessboard.getChessboardState();
        BasePiece piece = chessboardState.get(y).get(x);
        ArrayList<ArrayList<Integer>> pattern = piece.layPattern(x, y);

        if (piece.getPieceName().equals("Chess.Pieces.Pawn")) {
            try {
                if (chessboardState.get(y-1*piece.pieceColor).get(x) != null) {
                    pattern.remove(new ArrayList<>(Arrays.asList(x, y-1*piece.pieceColor)));
                }
            } catch (Exception e) {
                //TODO: handle exception 
            }
            try {
                if (chessboardState.get(y+1).get(x+1) != null && !chessboardState.get(y+1).get(x+1).getPieceColor().equals(piece.getPieceColor())) {
                    pattern.add(new ArrayList<>(Arrays.asList(x+1, y+1)));
                }
            } catch (Exception e) {
                //TODO: handle exception
            }

            try {
                if (chessboardState.get(y+1).get(x-1) != null && !chessboardState.get(y+1).get(x-1).getPieceColor().equals(piece.getPieceColor())) {
                    pattern.add(new ArrayList<>(Arrays.asList(x-1, y+1)));
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
            try {

                if (chessboardState.get(y-1).get(x+1) != null && !chessboardState.get(y-1).get(x+1).getPieceColor().equals(piece.getPieceColor())) {
                    pattern.add(new ArrayList<>(Arrays.asList(x+1, y-1)));
                }
                
            } catch (Exception e) {
                //TODO: handle exception
            }

            try {
                if (chessboardState.get(y-1).get(x-1) != null && !chessboardState.get(y-1).get(x-1).getPieceColor().equals(piece.getPieceColor())) {
                    pattern.add(new ArrayList<>(Arrays.asList(x-1, y-1)));
                }        
                
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        else if (piece.getPieceName().equals("Chess.Pieces.Queen") || piece.getPieceName().equals("Chess.Pieces.Bishop") 
        || piece.getPieceName().equals("Chess.Pieces.Rook")) {
            pattern.remove(new ArrayList<>(Arrays.asList(x , y)));
            ArrayList<ArrayList<Integer>> allPiecesInFrontPos = new ArrayList<>();
            ArrayList<ArrayList<Integer>> allInvalidPos = new ArrayList<>();

            for (ArrayList<Integer> pos : pattern) {
                if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                    allPiecesInFrontPos.add(new ArrayList<Integer>(Arrays.asList(pos.get(0), pos.get(1))));
                    if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceColor().equals(piece.getPieceColor())) {
                        allInvalidPos.add(new ArrayList<>(pos));
                    }
                }
            }

            if (piece.getPieceName().equals("Chess.Pieces.Queen") || piece.getPieceName().equals("Chess.Pieces.Rook")) {
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

            if (piece.getPieceName().equals("Chess.Pieces.Queen") || piece.getPieceName().equals("Chess.Pieces.Bishop")) {
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
        else if (piece.getPieceName().equals("Chess.Pieces.King")) {
            pattern.remove(new ArrayList<>(Arrays.asList(x , y)));
            ArrayList<ArrayList<Integer>> allInvalidPos = new ArrayList<>();

            for (ArrayList<Integer> pos : pattern) {
                if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                    if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceColor().equals(piece.getPieceColor())) {
                        allInvalidPos.add(new ArrayList<>(pos));
                    }
                    else if (pos.equals(Arrays.asList(x - 1, y)) || pos.equals(Arrays.asList(x + 1, y))) {
                        if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceName().equals("Chess.Pieces.Pawn")) {
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

                        if (!pieces.getPieceColor().equals(piece.getPieceColor())) {

                            if (!pieces.getPieceName().equals("Chess.Pieces.King")) {

                                if (pieces.getPieceName().equals("Chess.Pieces.Bishop") || pieces.getPieceName().equals("Chess.Pieces.Rook") || pieces.getPieceName().equals("Chess.Pieces.Queen")) {

                                    if (validatePattern(posX, posY).contains(Arrays.asList(x, y))) {
                                        for (ArrayList<Integer> piecepos : pieces.layPattern(posX, posY)) {
                                            allInvalidPos.add(new ArrayList<>(piecepos));
                                        }
                                    } else {
                                        for (ArrayList<Integer> piecepos : validatePattern(posX, posY)) {
                                            allInvalidPos.add(new ArrayList<>(piecepos));
                                        }
                                    }

                                    if (pieces.getPieceName().equals("Chess.Pieces.Bishop") || pieces.getPieceName().equals("Chess.Pieces.Queen")) {

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

                                    if (pieces.getPieceName().equals("Chess.Pieces.Rook") || pieces.getPieceName().equals("Chess.Pieces.Queen")) {

                                        for (ArrayList<Integer> patternpos : pattern) {
                                            int patternX = patternpos.get(0);
                                            int patternY = patternpos.get(1);
                                            if (pieces.layPattern(posX, posY).contains(patternpos)) {
                                                if (chessboardState.get(patternY).get(patternX) != null) {
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

                                else if (pieces.getPieceName().equals("Chess.Pieces.Pawn")) {
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

                                else if (pieces.getPieceName().equals("Chess.Pieces.Knight")) {
                                    for (ArrayList<Integer> piecepos : pieces.layPattern(x, y)) {
                                        allInvalidPos.add(new ArrayList<>(piecepos));
                                    }
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
