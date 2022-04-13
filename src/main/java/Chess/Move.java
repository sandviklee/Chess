package Chess;

import java.util.ArrayList;
import java.util.Arrays;

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
            if (validatePattern(x_1, y_1).contains(new ArrayList<>(Arrays.asList(x_2, y_2)))) {
                if (piece_2 == null) {
                    try {
                        ChessInit.chessboard.Move(x_1, y_1, x_2, y_2);
                        System.out.println("Moved!");
                    } catch (Exception e) {
                        System.out.println("BUG:" + " " + e);
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

    public static ArrayList<ArrayList<Integer>> validatePattern(int x, int y) {
        ArrayList<ArrayList<BasePiece>> chessboardState =  ChessInit.chessboard.getChessboardState();
        BasePiece piece = chessboardState.get(y).get(x);
        ArrayList<ArrayList<Integer>> pattern = piece.layPattern(x, y);

        switch (piece.getPieceName()) {
            case "Chess.Pieces.Pawn":
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
                break;

            case "Chess.Pieces.Bishop":
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

                for (ArrayList<Integer> pos : allInvalidPos) {
                    pattern.remove(pos);
                }
                break;
            case "Chess.Pieces.Rook":
                pattern.remove(new ArrayList<>(Arrays.asList(x , y)));
                allPiecesInFrontPos = new ArrayList<>();
                allInvalidPos = new ArrayList<>();

                for (ArrayList<Integer> pos : pattern) {
                    if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                        allPiecesInFrontPos.add(new ArrayList<Integer>(Arrays.asList(pos.get(0), pos.get(1))));
                        if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceColor().equals(piece.getPieceColor())) {
                            allInvalidPos.add(new ArrayList<>(pos));
                        }
                    }
                }

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
                
                for (ArrayList<Integer> pos : allInvalidPos) {
                    pattern.remove(pos);
                }
                break;
            case "Chess.Pieces.Knight":
                allInvalidPos = new ArrayList<>();
                for (ArrayList<Integer> pos : pattern) {
                    if (chessboardState.get(pos.get(1)).get(pos.get(0)) != null) {
                        if (chessboardState.get(pos.get(1)).get(pos.get(0)).getPieceColor().equals(piece.getPieceColor())) {
                            allInvalidPos.add(pos);
                        }
                    }
                }

                for (ArrayList<Integer> pos : allInvalidPos) {
                    pattern.remove(pos);
                }
                break;
            } 

        return pattern;

    }

}
