package Chess.Chessboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Chess.Exceptions.NotEnoughPiecesException;
import Chess.Pieces.*;


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
        assertTrue(pieceplacer.getPieceList().size() == 64, "Checks if there are 64 pieces to place.");
        assertThrows(NotEnoughPiecesException.class, () -> {
            pieceplacer = new PiecePlacer(corruptFile);
        }, "Throws if there are not enough pieces in the file.");
    }

    @Test
    public void testAddPieces() {
        assertTrue(pieceplacer.getOuter().size() == 0, "Checks that pieceplacer doesnt have any pieces.");
        pieceplacer.addPieces(); //Adds pieces.
        int pawnCount = 0;
        int bishopCount = 0;
        int knightCount = 0;
        int kingCount = 0;
        int queenCount = 0;
        int rookCount = 0;
        for (ArrayList<BasePiece> row : pieceplacer.getOuter()) {
            for (BasePiece basePiece : row) {
                if (basePiece instanceof Pawn) {
                    pawnCount++;
                } else if (basePiece instanceof Bishop) {
                    bishopCount++;
                } else if (basePiece instanceof Knight) {
                    knightCount++;
                } else if (basePiece instanceof King) {
                    kingCount++;
                } else if (basePiece instanceof Queen) {
                    queenCount++;
                } else if (basePiece instanceof Rook) {
                    rookCount++;
                }
            }
        }
        assertFalse(pieceplacer.getOuter().size() == 0, "Checks to see that the pieceplacer has gotten pieces.");
        assertEquals(16, pawnCount, "Checks if the amount of pawns are 16.");
        assertEquals(4, bishopCount, "Checks if the amount of bishops are 4");
        assertEquals(4, knightCount, "Checks if the amount of knights are 4");
        assertEquals(2, kingCount, "Checks if the amount of kings are 2");
        assertEquals(2, queenCount, "Checks if the amount of queens are 2");
        assertEquals(4, rookCount, "Checks if the amount of rooks are 4");
        
    }

}
