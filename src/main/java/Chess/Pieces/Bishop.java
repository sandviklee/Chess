package Chess.Pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends BasePiece {

    public Bishop(int pieceColor, int x, int y) {
        super(pieceColor, x, y);
    }

    @Override
    public boolean legalMove(int x, int y) {
        return Math.abs(this.pos.get(0)-x) == Math.abs(this.pos.get(1)-y);
    }

    @Override
    public ArrayList<ArrayList<Integer>> layPattern(int x, int y) {
        ArrayList<ArrayList<Integer>> pattern = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (legalMove(j, i)) {
                    pattern.add(new ArrayList<>(Arrays.asList(j, i)));
                }
            }
        }
    
        return pattern;
    }
    
}
