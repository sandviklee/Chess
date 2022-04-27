package Chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Chess.Chessboard.Chessboard;
import Chess.Exceptions.NotEnoughPiecesException;
import Chess.Pieces.BasePiece;
import Chess.Pieces.Knight;
import Chess.Pieces.Pawn;

public class MoveTest {
    Move ChessMove;
    Chessboard chessboard;

    @BeforeEach
    public void setup() throws FileNotFoundException, NotEnoughPiecesException {
        chessboard = new Chessboard();
        ChessMove = new Move(chessboard);
    }

    @Test
    public void testMove() {
        assertNotNull(ChessMove);
    }

    @Test
    public void testMovePiece() {
        int x_1 = 2;
        int y_1 = 6;
        int x_2 = 2;
        int y_2 = 4;
        
        //Beveger hvit i sin pattern.
        ArrayList<ArrayList<BasePiece>> chessboardState = chessboard.getChessboardState();
        BasePiece piece = chessboardState.get(y_1).get(x_1);
        assertEquals(true, ChessMove.getWhiteTurn());
        assertTrue(piece.pawnDoubleMove);
        ChessMove.MovePiece(x_1, y_1, x_2, y_2);
        assertEquals(piece, chessboardState.get(y_2).get(x_2));
        assertEquals(false, ChessMove.getWhiteTurn());
        assertFalse(piece.pawnDoubleMove);
        assertTrue(ChessMove.getMoving());

        //Prøver å bevege hvit igjen.
        int x_3 = 2;
        int y_3 = 3;
        ChessMove.MovePiece(x_2, y_2, x_3, y_3);
        assertNotEquals(piece, chessboardState.get(y_3).get(x_3)); //Because it is not whites turn! You cannot move the piece again.
        

        //Beveger svart i sin pattern.
        x_1 = 3;
        y_1 = 1;
        x_2 = 3;
        y_2 = 3;
        BasePiece piece2 = chessboardState.get(y_1).get(x_1);
        assertTrue(piece2.pawnDoubleMove);
        assertTrue(ChessMove.getBlackTurn());
        ChessMove.MovePiece(x_1, y_1, x_2, y_2);
        assertFalse(ChessMove.getBlackTurn());
        assertFalse(piece2.pawnDoubleMove);
        assertEquals(piece2, chessboardState.get(y_2).get(x_2));

        //Prøver å bevege hvit utenfor sin lovlige pattern.
        assertFalse(ChessMove.getBlackTurn());
        assertFalse(piece.pawnDoubleMove);
        x_1 = 2;
        y_1 = 4;
        x_2 = 2;
        y_2 = 2;
        ChessMove.MovePiece(x_1, y_1, x_2, y_2);
        assertNotEquals(piece, chessboardState.get(y_2).get(x_2)); 

        //Hvit attacker svart.
        x_2 = 3;
        y_2 = 3;
        assertTrue(ChessMove.getWhiteTurn());
        assertFalse(ChessMove.getBlackTurn());
        ChessMove.MovePiece(x_1, y_1, x_2, y_2);
        assertTrue(ChessMove.getBlackTurn());
        assertFalse(ChessMove.getWhiteTurn());
        assertEquals(piece, chessboardState.get(y_2).get(x_2)); 
        assertTrue(ChessMove.knockedOut);
        assertEquals(Arrays.asList(piece2), ChessMove.piecesOut);

        //Svart slår ut den hvite brikken som angrep.
        x_1 = 3;
        y_1 = 0;
        x_2 = 3;
        y_2 = 3;

        BasePiece piece3 = chessboardState.get(0).get(3);

        assertTrue(ChessMove.getBlackTurn());
        assertFalse(ChessMove.getWhiteTurn());
        ChessMove.MovePiece(x_1, y_1, x_2, y_2);
        assertTrue(ChessMove.getWhiteTurn());
        assertFalse(ChessMove.getBlackTurn());
        assertEquals(piece3, chessboardState.get(y_2).get(x_2)); 
        assertTrue(ChessMove.knockedOut);
        assertEquals(Arrays.asList(piece), ChessMove.piecesOut);
    }

    @Test
    public void testValidatedPattern() {
        for (ArrayList<BasePiece> row : chessboard.getChessboardState()) {
            for (BasePiece basePiece : row) {
                if (basePiece instanceof Pawn) {
                    assertEquals(Arrays.asList(Arrays.asList(basePiece.getPiecePos().get(0), basePiece.getPiecePos().get(1) - 2*basePiece.pieceColor), (Arrays.asList(basePiece.getPiecePos().get(0), 
                    basePiece.getPiecePos().get(1) - 1*basePiece.pieceColor))) ,
                    ChessMove.getValidatedPattern(basePiece.getPiecePos().get(0), basePiece.getPiecePos().get(1)));
                } else if (basePiece instanceof Knight) {
                    assertEquals(Arrays.asList(Arrays.asList(basePiece.getPiecePos().get(0) - 1, basePiece.getPiecePos().get(1) - 2*basePiece.pieceColor), Arrays.asList(basePiece.getPiecePos().get(0) + 1, basePiece.getPiecePos().get(1) - 2*basePiece.pieceColor)) ,
                    ChessMove.getValidatedPattern(basePiece.getPiecePos().get(0), basePiece.getPiecePos().get(1)));
                } else if (basePiece != null) {
                    assertEquals(0, ChessMove.getValidatedPattern(basePiece.getPiecePos().get(0), basePiece.getPiecePos().get(1)).size());
                }
            }
        }
    }
}
