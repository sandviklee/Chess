package Chess.Chessboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Chess.Exceptions.NotEnoughPiecesException;
import Chess.Pieces.BasePiece;
import Chess.Pieces.Pawn;
import Chess.Pieces.Queen;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessboardTest {
    Chessboard chessboard;
    private File corruptFile = new File("src/main/resources/Chess/SaveFiles/TestFiles/newGameStateTest.txt");

    @BeforeEach
    public void setup() throws FileNotFoundException, NotEnoughPiecesException {
        chessboard = new Chessboard();
    }

    @Test
    public void testChessboard() {
        assertNotNull(chessboard.getChessboardState());
        assertThrows(NotEnoughPiecesException.class, () -> {
            chessboard = new Chessboard(corruptFile);
        });
    } 

    @Test
    public void testChessImages() throws FileNotFoundException {
        assertTrue(chessboard.addChessImage("cpb/b_bishop") instanceof Image);
        assertThrows(FileNotFoundException.class, () -> {
            chessboard.addChessImage("");
        });

        assertTrue(chessboard.ChessboardView() instanceof ImageView);
        
    }

    @Test
    public void testMatrixToFXML() throws FileNotFoundException {
        assertNotNull(chessboard.getChessboardState());
        assertTrue(chessboard.MatrixToFXML() instanceof ArrayList<ImageView>);
    }

    @Test
    public void testsetChessboardState() {
        ArrayList<ArrayList<BasePiece>> oldChessboardState = new ArrayList<>();
        ArrayList<ArrayList<BasePiece>> oldChessboardState2 = new ArrayList<>();
        ArrayList<ArrayList<BasePiece>> oldChessboardState3 = new ArrayList<>();
        
        //General movement
        int x_1 = 2;
        int y_1 = 1;
        int x_2 = 2;
        int y_2 = 5;
        
        BasePiece piece = chessboard.getChessboardState().get(y_1).get(x_1);
        
        for (ArrayList<BasePiece> arrayList : chessboard.getChessboardState()) {
            oldChessboardState.add(new ArrayList<>(arrayList));
        }
        
        assertEquals(Arrays.asList(x_1, y_1), piece.getPiecePos());
        chessboard.setChessboardState(x_1, y_1, x_2, y_2); //Utføring.
        assertNotEquals(oldChessboardState.get(y_2), chessboard.getChessboardState().get(y_2));
        assertNotEquals(Arrays.asList(x_1, y_1), piece.getPiecePos());
        assertNotEquals(oldChessboardState, chessboard.getChessboardState());
        assertEquals(oldChessboardState.get(y_1).size(), chessboard.getChessboardState().get(y_1).size());
        assertEquals(oldChessboardState.get(y_2).size(), chessboard.getChessboardState().get(y_2).size());
        assertEquals(Arrays.asList(x_2, y_2), piece.getPiecePos());
        assertEquals(piece, chessboard.getChessboardState().get(y_2).get(x_2));    
        assertNull(chessboard.getChessboardState().get(y_1).get(x_1));

        //SideWays onto nullpiece
        int x_3 = 3;
        int y_3 = 5;

        for (ArrayList<BasePiece> arrayList : chessboard.getChessboardState()) {
            oldChessboardState2.add(new ArrayList<>(arrayList));
        }

        chessboard.setChessboardState(x_2, y_2, x_3, y_3);
        assertNotEquals(oldChessboardState2.get(y_2), chessboard.getChessboardState().get(y_2));
        assertEquals(Arrays.asList(x_3, y_3), piece.getPiecePos());
        
        //An extra move.
        int x_4 = 3;
        int y_4 = 6;
        chessboard.setChessboardState(x_3, y_3, x_4, y_4);

        //Attacking 
        int x_5 = 4;
        int y_5 = 6;

        for (ArrayList<BasePiece> arrayList : chessboard.getChessboardState()) {
            oldChessboardState3.add(new ArrayList<>(arrayList));
        }

        chessboard.setChessboardState(x_4, y_4, x_5, y_5);
        assertNotEquals(oldChessboardState3.get(y_4), chessboard.getChessboardState().get(y_4));
        assertEquals(piece, chessboard.getChessboardState().get(y_5).get(x_5));    
        assertEquals(oldChessboardState3.get(y_4).size(), chessboard.getChessboardState().get(y_4).size());
        assertEquals(Arrays.asList(x_5, y_5), piece.getPiecePos());
    }

    @Test
    public void testPawnToQueen() {
        int pieceColor = -1;
        int x_1 = 0;
        int y_1 = 1;

        ArrayList<ArrayList<BasePiece>> oldChessboardState = new ArrayList<>();
        for (ArrayList<BasePiece> arrayList : chessboard.getChessboardState()) {
            oldChessboardState.add(new ArrayList<>(arrayList));
        }
        BasePiece piece = chessboard.getChessboardState().get(y_1).get(x_1);
        assertTrue(piece instanceof Pawn);
        chessboard.PawnToQueen(pieceColor, x_1, y_1);
        assertEquals(oldChessboardState.get(y_1).size(), chessboard.getChessboardState().get(y_1).size());
        piece = chessboard.getChessboardState().get(y_1).get(x_1);
        assertTrue(piece instanceof Queen);
    }

}
