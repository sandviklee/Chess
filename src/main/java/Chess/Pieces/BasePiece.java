package Chess.Pieces;

import java.util.ArrayList;

/*This is where my BasePiece abstract class is written, where all the underclasses (Pieces) are made out of this class */

public abstract class BasePiece {
    public int pieceColor;
    ArrayList<Integer> availColor = new ArrayList<>();
    ArrayList<Integer> pos = new ArrayList<>();
    private static boolean moved = false;

    public BasePiece(int pieceColor, int x, int y) {
        availColor.add(-1);
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

    public abstract ArrayList<ArrayList<Integer>>  layPattern(int x, int y);

    public String getPieceName() {
        return "" + this.getClass().getName() + "";
    }

    public String getPieceColor() {
        if (pieceColor == -1) {
            return "b";
        } return "w";
    }

    public void setPiecePos(int x, int y) {
        if (!(x < 8 && x >= 0) || !(y < 8 && y >= 0)) {
            System.out.println(x + y);
            throw new IllegalArgumentException("The piece is not in an available range.");
        }
        if (this.getClass() == Pawn.class) {
            moved = true;
        }
        
        pos.clear();
        pos.add(x);
        pos.add(y);
    }

    public ArrayList<Integer> getPiecePos() {
        return new ArrayList<>(pos);
    }
    
    public static void setMoved(boolean moved) {
        BasePiece.moved = moved;
    }

    public boolean getMoved() {
        return moved;
    }

    @Override
    public String toString() {
        String[] pieceNameList = getPieceName().split("\\.");
        String pieceName = pieceNameList[2];
        return pieceName;
    }

}
