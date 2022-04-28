package Chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Chess.Chessboard.Chessboard;
import Chess.Exceptions.NotEnoughPiecesException;

public class CheckGameStateTest {
    Chessboard chessboard;
    Move ChessMove;
    CheckGameState checkGameState;

    @BeforeEach
    public void setup() throws FileNotFoundException, NotEnoughPiecesException {
        chessboard = new Chessboard();
        ChessMove = new Move(chessboard);
        checkGameState = new CheckGameState(chessboard, ChessMove);
    }

    @Test
    public void testCheckGameStateTest() {
        assertNotNull(chessboard);
        assertThrows(NullPointerException.class, () -> {
            checkGameState = new CheckGameState(null, null);
        });
        assertThrows(NullPointerException.class, () -> {
            checkGameState = new CheckGameState(chessboard, null);
        });
        assertThrows(NullPointerException.class, () -> {
            checkGameState = new CheckGameState(null, ChessMove);
        });
    }

    @Test
    public void testInCheck() throws FileNotFoundException, NotEnoughPiecesException {
        //Normal game start state;
        checkGameState.inCheck();
        assertEquals(Arrays.asList(4, 0), checkGameState.getKingBPos());
        assertEquals(Arrays.asList(4, 7), checkGameState.getKingWPos());
        assertFalse(checkGameState.getKingBCheck());
        assertFalse(checkGameState.getKingWCheck());
        assertFalse(checkGameState.getCheckMate());

        //White in check state;
        chessboard = new Chessboard(new File("src/main/resources/Chess/SaveFiles/TestFiles/WhiteInCheck.txt"));
        checkGameState = new CheckGameState(chessboard, ChessMove);

        checkGameState.inCheck();
        assertEquals(Arrays.asList(4, 0), checkGameState.getKingBPos());
        assertEquals(Arrays.asList(7, 3), checkGameState.getKingWPos());
        assertNotEquals(-1, checkGameState.checkState);
        assertFalse(checkGameState.getKingBCheck());
        assertTrue(checkGameState.getKingWCheck());
        assertFalse(checkGameState.getCheckMate());

        //From checked, to not checked.
        int x_1 = checkGameState.getKingWPos().get(0);
        int y_1 = checkGameState.getKingWPos().get(1);
        chessboard.setChessboardState(x_1, y_1, x_1, y_1 + 1);
        checkGameState.inCheck();
        assertEquals(-1, checkGameState.checkState);
        assertFalse(checkGameState.getKingWCheck());
        
    
        //Black in check state;
        chessboard = new Chessboard(new File("src/main/resources/Chess/SaveFiles/TestFiles/BlackInCheck.txt"));
        checkGameState = new CheckGameState(chessboard, ChessMove);

        checkGameState.inCheck();
        assertEquals(Arrays.asList(7, 4), checkGameState.getKingBPos());
        assertEquals(Arrays.asList(4, 7), checkGameState.getKingWPos());
        assertTrue(checkGameState.getKingBCheck());
        assertFalse(checkGameState.getKingWCheck());
        assertFalse(checkGameState.getCheckMate());
    }

    @Test
    public void testInCheckMate() throws FileNotFoundException, NotEnoughPiecesException {
        //Not in checkmate (Normal start state)
        checkGameState.inCheck();
        checkGameState.inCheckMate();
        assertEquals(Arrays.asList(4, 0), checkGameState.getKingBPos());
        assertEquals(Arrays.asList(4, 7), checkGameState.getKingWPos());
        assertFalse(checkGameState.getKingBCheck());
        assertFalse(checkGameState.getKingWCheck());
        assertFalse(checkGameState.getCheckMate());
        assertFalse(ChessMove.getGameOver());
        
        //White in checkmate (State 1);
        chessboard = new Chessboard(new File("src/main/resources/Chess/SaveFiles/TestFiles/BlackWonState1.txt"));
        ChessMove = new Move(chessboard);
        checkGameState = new CheckGameState(chessboard, ChessMove);

        checkGameState.inCheck();
        checkGameState.inCheckMate();
        assertEquals(Arrays.asList(4, 0), checkGameState.getKingBPos());
        assertEquals(Arrays.asList(7, 3), checkGameState.getKingWPos());
        assertFalse(checkGameState.getKingBCheck());
        assertTrue(checkGameState.getKingWCheck());
        assertTrue(checkGameState.getCheckMate());
        assertTrue(ChessMove.getGameOver());

        //White in checkmate (State 2);
        chessboard = new Chessboard(new File("src/main/resources/Chess/SaveFiles/TestFiles/BlackWonState2.txt"));
        ChessMove = new Move(chessboard);
        checkGameState = new CheckGameState(chessboard, ChessMove);

        checkGameState.inCheck();
        checkGameState.inCheckMate();
        assertEquals(Arrays.asList(4, 0), checkGameState.getKingBPos());
        assertEquals(Arrays.asList(7, 3), checkGameState.getKingWPos());
        assertFalse(checkGameState.getKingBCheck());
        assertTrue(checkGameState.getKingWCheck());
        assertTrue(checkGameState.getCheckMate());
        assertTrue(ChessMove.getGameOver());


        //White in checkmate (State 3); 
        chessboard = new Chessboard(new File("src/main/resources/Chess/SaveFiles/TestFiles/BlackWonState3.txt"));
        ChessMove = new Move(chessboard);
        checkGameState = new CheckGameState(chessboard, ChessMove);

        checkGameState.inCheck();
        checkGameState.inCheckMate();
        assertEquals(Arrays.asList(4, 0), checkGameState.getKingBPos());
        assertEquals(Arrays.asList(7, 3), checkGameState.getKingWPos());
        assertFalse(checkGameState.getKingBCheck());
        assertTrue(checkGameState.getKingWCheck());
        assertTrue(checkGameState.getCheckMate());
        assertTrue(ChessMove.getGameOver());

        //White in checkmate (State 4); 
        chessboard = new Chessboard(new File("src/main/resources/Chess/SaveFiles/TestFiles/BlackWonState4.txt"));
        ChessMove = new Move(chessboard);
        checkGameState = new CheckGameState(chessboard, ChessMove);

        checkGameState.inCheck();
        checkGameState.inCheckMate();
        assertEquals(Arrays.asList(4, 0), checkGameState.getKingBPos());
        assertEquals(Arrays.asList(7, 3), checkGameState.getKingWPos());
        assertFalse(checkGameState.getKingBCheck());
        assertTrue(checkGameState.getKingWCheck());
        assertTrue(checkGameState.getCheckMate());
        assertTrue(ChessMove.getGameOver());
    }

    @Test
    public void testDraw() throws FileNotFoundException, NotEnoughPiecesException {
        checkGameState.inDraw();
        assertFalse(checkGameState.getDraw());
        checkGameState.setDraw(true);
        checkGameState.inDraw();
        assertTrue(checkGameState.getDraw());
        assertTrue(ChessMove.getGameOver());
    }
}
