package Chess.Pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KingTest {
    BasePiece piece;
    private int pieceColor = -1;
    private int x = 4;
    private int y = 0;
    private int x_2 = 4;
    private int y_2 = 1;
    private int x_3 = -1;

    
    @BeforeEach
    public void setup() {
        piece = new King(pieceColor, x, y);
    }

    @Test
    @DisplayName("Tests the King constructor.")
    public void testKing() {
        assertEquals('b', piece.getPieceColor());
        assertEquals(Arrays.asList(x , y), piece.getPiecePos());

        assertThrows(IllegalArgumentException.class, () -> {
            piece = new King(y, 0, 0);
        }, y + " is not an available color!");
        assertThrows(IllegalArgumentException.class, () -> {
            piece = new King(pieceColor, pieceColor, pieceColor);
        }, pieceColor + " is not available in the interval 0 < (x && y) < 8");
    }

    @Test
    @DisplayName("Tests the King names.")
    public void testKingNames() {
        assertEquals("Chess.Pieces.King", piece.getPieceName());
        assertEquals("King", piece.toString());
    }

    @Test
    @DisplayName("Tests the Kings moving methods.")
    public void testKingMovement() {
        assertTrue(piece.legalMove(x_2, y_2));
        assertTrue(piece.layPattern(x, y).contains(Arrays.asList(x_2, y_2)));
        piece.setPiecePos(x_2, y_2);
        assertEquals(Arrays.asList(x_2, y_2), piece.getPiecePos());
        assertFalse(piece.legalMove(x_3, y_2));
        assertFalse(piece.layPattern(x_2, y_2).contains(Arrays.asList(x_3, y_2)));
        assertThrows(IllegalArgumentException.class, () -> {
            piece.setPiecePos(x_3, y_2);
        }, x_3 + " is not available in the interval 0 < (x && y) < 8");
    }
}
