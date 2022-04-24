package Chess.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Chess.ChessInit;
import Chess.MainmenuController;
import Chess.Pieces.BasePiece;

public class IO implements IIO {

    private List<String> chessPiecesOutList;

    public List<String> getPiecesOut() {
        return chessPiecesOutList;
    }

    public static String player1Name = "PLAYER1";
    public static String player2Name = "PLAYER2";
    public static boolean whiteTurn = true;
    public static boolean blackTurn = false;

    @Override
    public void save(File filename, ChessInit ChessInitialize) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (ArrayList<BasePiece> row : ChessInitialize.chessboard.getChessboardState()) {
                for (BasePiece piece : row) {
                    if (piece != null) {
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
                    } else {
                        writer.print("0,");
                    }
                }
            }
            writer.println("");
            for (BasePiece piece : ChessInitialize.getpiecesList()) {
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
            writer.println("");
            writer.print(MainmenuController.player1Name + ",");
            writer.print(MainmenuController.player2Name + ",");

            writer.println("");
            if (ChessInitialize.ChessMove.getWhiteTurn()) {
                writer.print("w");
            }
            else {
                writer.print("b");
            }

            writer.flush();
            writer.close();
        }
    }
    

    @Override
    public String[] load(File filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(filename)) {
            String[] lineInfo = {};

            String line = scanner.nextLine();
            lineInfo = line.split(",");
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
                    System.out.println("heisann!");
                    whiteTurn = false;
                    blackTurn = true;
                }
            }
            return lineInfo;
        }
    }
}
