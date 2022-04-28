package Chess.Chessboard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Chess.Exceptions.NotEnoughPiecesException;

public class PiecePlacerTest {
    PiecePlacer pieceplacer;
    private File file = new File("src/main/resources/Chess/SaveFiles/newGameState.txt");
    private File corruptFile = new File("src/main/resources/Chess/SaveFiles/TestFiles/newGameStateTest.txt");

    @BeforeEach
    public void setup() throws FileNotFoundException, NotEnoughPiecesException {
        pieceplacer = new PiecePlacer(file);
    }

    @Test
    public void testPiecePlacer() {
        assertTrue(pieceplacer.getPieceList().size() == 64);
        assertThrows(NotEnoughPiecesException.class, () -> {
            pieceplacer = new PiecePlacer(corruptFile);
        });
    }

    @Test
    public void testAddPieces() {
        assertTrue(pieceplacer.getOuter().size() == 0);
        pieceplacer.addPieces();
        assertFalse(pieceplacer.getOuter().size() == 0);
    }

}
