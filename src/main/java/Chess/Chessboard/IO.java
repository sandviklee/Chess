package Chess.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Chess.ChessInit;
import Chess.Exceptions.NotEnoughPiecesException;
import Chess.Pieces.BasePiece;
import Chess.Pieces.Pawn;

public class IO implements IIO {
    private List<String> chessPiecesOutList;
    private static String player1Name;
    private static String player2Name;
    public static boolean whiteTurn = true;
    public static boolean blackTurn = false;
    private static ArrayList<Boolean> pawnDoubleList = new ArrayList<>();

    public static ArrayList<Boolean> getPawnDoubleList() {
        return pawnDoubleList;
    }
    
    public List<String> getPiecesOut() {
        return chessPiecesOutList;
    }

    public static String getPlayer1NameIO() {
        return player1Name;
    }

    public static String getPlayer2NameIO() {
        return player2Name;
    }

    @Override
    public void save(File filename, ChessInit ChessInitialize) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (ArrayList<BasePiece> row : ChessInitialize.chessboard.getChessboardState()) {
                for (BasePiece piece : row) {
                    if (piece != null) {
                        allCases(piece, writer);
                    } else {
                        writer.print("0,");
                    }
                }
            }
            writer.println();
            for (BasePiece piece : ChessInitialize.getpiecesList()) {
                allCases(piece, writer);
            }
            writer.println();
            writer.print(ChessInitialize.getPlayer1Name() + ",");
            writer.print(ChessInitialize.getPlayer2Name() + ",");

            writer.println();
            if (ChessInitialize.ChessMove.getWhiteTurn()) {
                writer.print("w");
            }
            else {
                writer.print("b");
            }
            writer.println();
            for (ArrayList<BasePiece> row : ChessInitialize.chessboard.getChessboardState()) {
                for (BasePiece basePiece : row) {
                    if (basePiece instanceof Pawn) {
                        if (basePiece.pawnDoubleMove) {
                            writer.print("t,");
                        } else {
                            writer.print("f,");
                        }
                    }
                }
            }

            writer.flush();
            writer.close();
        }
    }
    

    @Override
    public List<String> load(File filename) throws FileNotFoundException, NotEnoughPiecesException {
        try (Scanner scanner = new Scanner(filename)) {
            String[] lineInfo = {};
            String line = scanner.nextLine();
            lineInfo = line.split(",");

            List<String> pieceList = Arrays.asList(lineInfo);
            if (pieceList.size() != 64) {
                throw new NotEnoughPiecesException("Ikke nokk brikker!");
            }
            if (scanner.hasNext()) {
                String secondline = scanner.nextLine();
                chessPiecesOutList = Arrays.asList(secondline.split(","));
            }

            if (scanner.hasNext()) {
                String thirdline = scanner.nextLine();
                String[] liste = thirdline.split(",");
                player1Name = liste[0];
                player2Name = liste[1];
            }

            if (scanner.hasNext()) {
                String thirdline = scanner.nextLine();
                if (thirdline.equals("b")) {
                    whiteTurn = false;
                    blackTurn = true;
                }
            }

            if (scanner.hasNext()) {
                pawnDoubleList = new ArrayList<>();
                String fourthline = scanner.nextLine();
                String[] truefalseliste = fourthline.split(",");
                for (String string : truefalseliste) {
                    if (string.equals("t")) {
                        pawnDoubleList.add(true);
                    } else {
                        pawnDoubleList.add(false);
                    }
                }
                
            }
            return pieceList;
        }
    }


    private void allCases(BasePiece piece, PrintWriter writer) {
        switch (piece.toString()) {
            case "Rook":
                switch (piece.getPieceColor()) {
                    case 'w':
                        writer.print("r,");
                        break;
                    case 'b':
                        writer.print("R,");
                        break;
                }
                break;
            case "Knight":
                switch (piece.getPieceColor()) {
                    case 'w':
                        writer.print("h,");
                        break;
                    case 'b':
                        writer.print("H,");
                        break;
                }
                break;
            case "Bishop":
                switch (piece.getPieceColor()) {
                    case 'w':
                        writer.print("b,");
                        break;
                    case 'b':
                        writer.print("B,");
                        break;
                }
                break;
            case "Queen":
                switch (piece.getPieceColor()) {
                    case 'w':
                        writer.print("q,");
                        break;
                    case 'b':
                        writer.print("Q,");
                        break;
                }
                break;
            case "King":
                switch (piece.getPieceColor()) {
                    case 'w':
                        writer.print("k,");
                        break;
                    case 'b':
                        writer.print("K,");
                        break;
                }    
                break;
            case "Pawn":
                switch (piece.getPieceColor()) {
                    case 'w':
                        writer.print("p,");
                        break;
                    case 'b':
                        writer.print("P,");
                        break;
                }
                break;
        }
    }

}
