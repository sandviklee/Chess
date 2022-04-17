package Chess.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Chess.Pieces.*;

public class PiecePlacer {
    private ArrayList<ArrayList<BasePiece>> outer = new ArrayList<>();
    private ArrayList<BasePiece> inner = new ArrayList<>();
    public List<String> PieceList;

    public PiecePlacer(File filename) throws FileNotFoundException  {
        IO IOload = new IO();
        this.PieceList = Arrays.asList(IOload.load(filename));
    }
    
    public void addPieces() {
        int counterx = 0;
        int countery = 0;
        int colorb = -1;
        int colorw = 1;

        for (String strPiece : PieceList) {
            if (inner.size() < 7) {
                if (!(strPiece.equals("0"))) {
                    switch (strPiece.substring(0, 1)) {
                        case "P":
                            inner.add(new Pawn(colorb, counterx , countery));
                            break;
                        case "R":
                            inner.add(new Rook(colorb, counterx , countery));
                            break;
                        case "H":
                            inner.add(new Knight(colorb, counterx , countery));
                            break;
                        case "B":
                            inner.add(new Bishop(colorb, counterx , countery));
                            break;
                        case "Q":
                            inner.add(new Queen(colorb, counterx , countery));
                            break;
                        case "K":
                            inner.add(new King(colorb, counterx , countery));
                            break;
                        case "p":
                            inner.add(new Pawn(colorw, counterx , countery));
                            break;
                        case "r":
                            inner.add(new Rook(colorw, counterx , countery));
                            break;
                        case "h":
                            inner.add(new Knight(colorw, counterx , countery));
                            break;
                        case "b":
                            inner.add(new Bishop(colorw, counterx , countery));
                            break;
                        case "q":
                            inner.add(new Queen(colorw, counterx , countery));
                            break;
                        case "k":
                            inner.add(new King(1, counterx , countery));
                            break;
                    }
                    
                } else if (strPiece.equals("0")) {
                    inner.add(null);
                }
                counterx++;
            } else {

                if (!(strPiece.equals("0"))) {
                    switch (strPiece.substring(0, 1)) {
                        case "P":
                            inner.add(new Pawn(colorb, counterx , countery));
                            break;
                        case "R":
                            inner.add(new Rook(colorb, counterx , countery));
                            break;
                        case "H":
                            inner.add(new Knight(colorb, counterx , countery));
                            break;
                        case "B":
                            inner.add(new Bishop(colorb, counterx , countery));
                            break;
                        case "Q":
                            inner.add(new Queen(colorb, counterx , countery));
                            break;
                        case "K":
                            inner.add(new King(colorb, counterx , countery));
                            break;
                        case "p":
                            inner.add(new Pawn(colorw, counterx , countery));
                            break;
                        case "r":
                            inner.add(new Rook(colorw, counterx , countery));
                            break;
                        case "h":
                            inner.add(new Knight(colorw, counterx , countery));
                            break;
                        case "b":
                            inner.add(new Bishop(colorw, counterx , countery));
                            break;
                        case "q":
                            inner.add(new Queen(colorw, counterx , countery));
                            break;
                        case "k":
                            inner.add(new King(1, counterx , countery));
                            break;
                    }

                } else if (strPiece.equals("0")) {
                    inner.add(null);
                }
                
                System.out.println(inner);
                counterx = 0;
                this.outer.add(inner);
                inner = new ArrayList<>();
                countery++;
                
            }
        }
    }

    public ArrayList<ArrayList<BasePiece>> getOuter() {
        return new ArrayList<ArrayList<BasePiece>>(this.outer);
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("newGameState.txt");
        PiecePlacer placer = new PiecePlacer(file);
        System.out.println(placer.PieceList);
        //placer.addPieces();
        // System.out.println(placer.getOuter());
    
    }


}
