package Chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertFalse(checkGameState.getKingBCheck());
        assertTrue(checkGameState.getKingWCheck());
        assertFalse(checkGameState.getCheckMate());
        

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
    }

    @Test
    public void testDraw() throws FileNotFoundException, NotEnoughPiecesException {
        checkGameState.inDraw();
        assertFalse(checkGameState.getDraw());
        checkGameState.setDraw(true);
        checkGameState.inDraw();
        assertTrue(checkGameState.getDraw());
    }
}
