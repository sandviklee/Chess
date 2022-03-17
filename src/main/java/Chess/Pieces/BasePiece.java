package Chess.Pieces;

import java.util.ArrayList;

public abstract class BasePiece {
    int pieceColor;
    ArrayList<Integer> availColor = new ArrayList<>();
    ArrayList<Integer> pos = new ArrayList<>();

    public BasePiece(int pieceColor, int x, int y) {
        availColor.add(0);
        availColor.add(1);

        if (!(availColor.contains(pieceColor))) {
            throw new IllegalArgumentException("Not an available color");
        } this.pieceColor = pieceColor;
        
        if (!(x < 8 && x >= 0) || !(y < 8 && y >= 0)) {
            System.out.println(x + y);
            throw new IllegalArgumentException("The piece is not in an available range.");
        } 


        pos.add(x);
        pos.add(y);
    }

    public abstract boolean legalMove(int x, int y);

    public abstract ArrayList<Integer> layPattern(int x, int y);

    public String getPieceName() {
        return "" + this.getClass().getName() + "_" + this.getPieceColor() + "";
    }

    public String getPieceColor() {
        if (pieceColor == 1) {
            return "Black";
        } return "White";
    }

    public void setPiecePos(int x, int y) {
        if (!(x < 8 && x >= 0) || !(y < 8 && y >= 0)) {
            System.out.println(x + y);
            throw new IllegalArgumentException("The piece is not in an available range.");
        } 
        pos.clear();
        pos.add(x);
        pos.add(y);
    }

    public ArrayList<Integer> getPiecePos() {
        return new ArrayList<>(pos);
    }

    @Override
    public String toString() {
        return "" + this.getPieceName() + this.getPiecePos() + "";
    }

}
