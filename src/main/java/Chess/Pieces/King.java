package Chess.Pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class King extends BasePiece {

    public King(int pieceColor, int x, int y) {
        super(pieceColor, x, y);

    }

    @Override
    public boolean legalMove(int x, int y) {
        if ((x < 8 && x >= 0) && (y < 8 && y >= 0)) {
            return (IntStream.rangeClosed(0, 1).anyMatch(a -> a == Math.abs(x-this.pos.get(0))) && (IntStream.rangeClosed(0, 1).anyMatch(b -> b == Math.abs(y-this.pos.get(1)))));
        } 
        return false;
        
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
