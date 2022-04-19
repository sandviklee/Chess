package Chess.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import Chess.Pieces.BasePiece;

public class IO implements IIO {

    @Override
    public void save(File filename, Chessboard chessboard) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (ArrayList<BasePiece> row : chessboard.getChessboardState()) {
                for (BasePiece piece : row) {
                    if (piece != null) {
                        switch (piece.toString()) {
                            case "Rook":
                                switch (piece.getPieceColor()) {
                                    case "w":
                                        writer.print("r,");
                                        break;
                                    case "b":
                                        writer.print("R,");
                                        break;
                                }
                                break;
                            case "Knight":
                                switch (piece.getPieceColor()) {
                                    case "w":
                                        writer.print("h,");
                                        break;
                                    case "b":
                                        writer.print("H,");
                                        break;
                                }
                                break;
                            case "Bishop":
                                switch (piece.getPieceColor()) {
                                    case "w":
                                        writer.print("b,");
                                        break;
                                    case "b":
                                        writer.print("B,");
                                        break;
                                }
                                break;
                            case "Queen":
                                switch (piece.getPieceColor()) {
                                    case "w":
                                        writer.print("q,");
                                        break;
                                    case "b":
                                        writer.print("Q,");
                                        break;
                                }
                                break;
                            case "King":
                                switch (piece.getPieceColor()) {
                                    case "w":
                                        writer.print("k,");
                                        break;
                                    case "b":
                                        writer.print("K,");
                                        break;
                                }    
                                break;
                            case "Pawn":
                                switch (piece.getPieceColor()) {
                                    case "w":
                                        writer.print("p,");
                                        break;
                                    case "b":
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
            writer.flush();
            writer.close();
        }
    }
    

    @Override
    public String[] load(File filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(filename)) {
            String[] lineInfo = {};

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineInfo = line.split(",");
            }
            return lineInfo;
        }
        
    }

    public static void main(String[] args) throws FileNotFoundException {
        IO io = new IO();
        //System.out.println(Arrays.asList(io.load("newGameState.txt")));
        

    }

}
