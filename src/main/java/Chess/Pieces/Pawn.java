package Chess.Pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends BasePiece {
    public Pawn(int pieceColor, int x, int y) {
        super(pieceColor, x, y);
    }

    @Override
    public boolean legalMove(int x, int y) {
        if (this.pos.get(0) - x != 0) {
            return false;
        } 
        if (pawnDoubleMove) {
            return ((this.pos.get(1)-y) * this.pieceColor) >= 1 && ((this.pos.get(1)-y) * this.pieceColor) <= 2;
        }
        return ((this.pos.get(1)-y) * this.pieceColor) == 1;
        
    }

    @Override
    public ArrayList<ArrayList<Integer>> layPattern(int x, int y) {
        ArrayList<ArrayList<Integer>> pattern = new ArrayList<>(); 
        if (legalMove(x, y - 2*this.pieceColor)) {
            pattern.add(new ArrayList<>(Arrays.asList(x , y - 2*this.pieceColor)));
        }
        if (legalMove(x, y - 1*this.pieceColor)) {
            pattern.add(new ArrayList<>(Arrays.asList(x , y - 1*this.pieceColor)));
        }
        return new ArrayList<>(pattern);
    }
    
}
