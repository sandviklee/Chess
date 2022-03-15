package Chess.Chessboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Chess.Pieces.*;

public class PiecePlacer {
    private ArrayList<ArrayList<BasePiece>> outer = new ArrayList<>();
    private ArrayList<BasePiece> inner = new ArrayList<>();

    private String[] string = 
    {"R1", "H1", "B1", "Q1", "K1", "B1", "H1", "R1",
     "P1", "P1", "P1", "P1", "P1", "P1", "P1", "P1",
      "0",  "0",  "0",  "0",  "0",  "0",  "0",  "0",
      "0",  "0",  "0",  "0",  "0",  "0",  "0",  "0",
      "0",  "0",  "0",  "0",  "0",  "0",  "0",  "0",
      "0",  "0",  "0",  "0",  "0",  "0",  "0",  "0",
      "P0", "P0", "P0", "P0", "P0", "P0", "P0", "P0",
      "R0", "H0", "B0", "Q0", "K0", "B0", "H0", "R0"};
    public List<String> PieceList = Arrays.asList(string);

    public void addPieces() {
        int counterx = 0;
        int countery = 0;

        for (String strPiece : PieceList) {
            if (inner.size() < 7) {
                if (!(strPiece.equals("0"))) {
                    if ((strPiece.substring(0, 1)).equals("P")) {
                        inner.add(new Pawn(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("R")) {
                        inner.add(new Rook(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("H")) {
                        inner.add(new Knight(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("B")) {
                        inner.add(new Bishop(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("Q")) {
                        inner.add(new Queen(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("K")) {
                        inner.add(new King(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    
                } else if (strPiece.equals("0")) {
                    inner.add(null);
                }
                counterx++;
            } else {
                
                if (!(strPiece.equals("0"))) {
                    if ((strPiece.substring(0, 1)).equals("P")) {
                        inner.add(new Pawn(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("R")) {
                        inner.add(new Rook(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("H")) {
                        inner.add(new Knight(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("B")) {
                        inner.add(new Bishop(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("Q")) {
                        inner.add(new Queen(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    else if ((strPiece.substring(0, 1)).equals("K")) {
                        inner.add(new King(Integer.parseInt(strPiece.substring(1, 2)), counterx , countery));
                    }
                    
                } else if (strPiece.equals("0")) {
                    inner.add(null);
                }
                // System.out.println(inner);
                counterx = 0;
                this.outer.add(inner);
                inner = new ArrayList<>();
                countery++;
                
            }
        }
    }

    public ArrayList<ArrayList<BasePiece>> getOuter() {
        return this.outer;
    }

    public ArrayList<BasePiece> getInner() {
        return this.inner;
    }

    public static void main(String[] args) {
        PiecePlacer placer = new PiecePlacer();
        System.out.println(placer.PieceList);
        placer.addPieces();
        System.out.println(placer.getOuter());
    
    }


}
