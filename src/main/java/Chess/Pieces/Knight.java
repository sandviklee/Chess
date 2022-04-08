package Chess.Pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Knight extends BasePiece {

    public Knight(int pieceColor, int x, int y) {
        super(pieceColor, x, y);

    }

    @Override
    public boolean legalMove(int x, int y) {
        ArrayList<Integer> movementList = new ArrayList<>(Arrays.asList(Math.abs(this.pos.get(0)-x), Math.abs(this.pos.get(1)-y)));
        ArrayList<Integer> rightMoveList = new ArrayList<>(Arrays.asList(1, 2));
        if (IntStream.rangeClosed(0, 7).anyMatch(a -> a == x) && IntStream.rangeClosed(0, 7).anyMatch(b -> b == y)) {
            return (movementList.stream().sorted().toList()).equals(rightMoveList);
        }
        return false;
    }

    @Override
    public ArrayList<ArrayList<Integer>> layPattern(int x, int y) {
        ArrayList<ArrayList<Integer>> pattern = new ArrayList<>(); 
        
        if (legalMove(x + 1, y - 2*this.pieceColor)) {
            pattern.add(new ArrayList<>(Arrays.asList(x + 1, y -2*this.pieceColor)));
        }
        if (legalMove(x - 1, y - 2*this.pieceColor)) {
            pattern.add(new ArrayList<>(Arrays.asList(x - 1, y -2*this.pieceColor)));
        }
        if (legalMove(x + 1, y + 2*this.pieceColor)) {
            pattern.add(new ArrayList<>(Arrays.asList(x + 1, y + 2*this.pieceColor)));
        }
        if (legalMove(x - 1, y + 2*this.pieceColor)) {
            pattern.add(new ArrayList<>(Arrays.asList(x - 1, y + 2*this.pieceColor)));
        }
        if (legalMove(x - 2, y + 1)) {
            pattern.add(new ArrayList<>(Arrays.asList(x - 2, y + 1)));
        }
        if (legalMove(x - 2, y - 1)) {
            pattern.add(new ArrayList<>(Arrays.asList(x - 2, y - 1)));
        }
        if (legalMove(x + 2, y + 1)) {
            pattern.add(new ArrayList<>(Arrays.asList(x + 2, y + 1)));
        }
        if (legalMove(x + 2, y - 1)) {
            pattern.add(new ArrayList<>(Arrays.asList(x + 2, y - 1)));
        }
        pattern.add(new ArrayList<>(Arrays.asList(x, y)));
        return pattern;
    }
    
}
