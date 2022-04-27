package Chess.Pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BishopTest {
    BasePiece piece;
    private int pieceColor = -1;
    private int x = 2;
    private int y = 0;
    private int x_2 = 4;
    private int y_2 = 2;
    private int x_3 = -1;

    
    @BeforeEach
    public void setup() {
        piece = new Bishop(pieceColor, x, y);
    }

    @Test
    @DisplayName("Tests the Bishop constructor.")
    public void testBishop() {
        assertEquals('b', piece.getPieceColor());
        assertEquals(Arrays.asList(x , y), piece.getPiecePos());

        assertThrows(IllegalArgumentException.class, () -> {
            piece = new Bishop(y, 0, 0);
        }, y + " is not an available color!");
        assertThrows(IllegalArgumentException.class, () -> {
            piece = new Bishop(pieceColor, pieceColor, pieceColor);
        }, pieceColor + " is not available in the interval 0 < (x && y) < 8");
    }

    @Test
    @DisplayName("Tests the Bishop names.")
    public void testBishopNames() {
        assertEquals("Chess.Pieces.Bishop", piece.getPieceName());
        assertEquals("Bishop", piece.toString());
    }

    @Test
    @DisplayName("Tests the Bishops moving methods.")
    public void testBishopMovement() {
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
