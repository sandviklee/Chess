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
        assertNotNull(chessboard.getChessboardState(), "Checks if there is a chessboard available.");
        assertThrows(NotEnoughPiecesException.class, () -> {
            chessboard = new Chessboard(corruptFile);
        }, "Throws if there is not enough Pieces.");
    } 

    @Test
    public void testChessImages() throws FileNotFoundException {
        assertTrue(chessboard.addChessImage("cpb/b_bishop") instanceof Image, "Checks if the file that has been loaded is an Image.");
        assertThrows(FileNotFoundException.class, () -> {
            chessboard.addChessImage("");
        }, "Throws if there is no file.");
        assertTrue(chessboard.ChessboardView() instanceof ImageView, "Checks if the chessboardView is an ImageView.");
        
    }

    @Test
    public void testMatrixToFXML() throws FileNotFoundException {
        assertTrue(chessboard.MatrixToFXML() instanceof ArrayList<ImageView>, "Checks if the chessboard is the correct instance");
    }

    @Test
    public void testsetChessboardState() {
        ArrayList<ArrayList<BasePiece>> oldChessboardState = new ArrayList<>();
        ArrayList<ArrayList<BasePiece>> oldChessboardState2 = new ArrayList<>();
        ArrayList<ArrayList<BasePiece>> oldChessboardState3 = new ArrayList<>();
        
        //General movement
        int x_1 = 2; //First x-pos
        int y_1 = 1; //First y-pos
        int x_2 = 2; //Second x-pos
        int y_2 = 5; //Second y-pos
        
        BasePiece piece = chessboard.getChessboardState().get(y_1).get(x_1); //Initiating a piece on the chessboard.
        
        for (ArrayList<BasePiece> arrayList : chessboard.getChessboardState()) {
            oldChessboardState.add(new ArrayList<>(arrayList)); //Saving the chessbordState
        }
        
        assertEquals(Arrays.asList(x_1, y_1), piece.getPiecePos(), "Checks if Piece pos is correct.");

        chessboard.setChessboardState(x_1, y_1, x_2, y_2); 

        assertNotEquals(oldChessboardState.get(y_2), chessboard.getChessboardState().get(y_2), "Checks that the chesspiece has moved");
        assertNotEquals(Arrays.asList(x_1, y_1), piece.getPiecePos(), "Checks if the Piece pos has been updated.");
        assertNotEquals(oldChessboardState, chessboard.getChessboardState(), "Checks that the old chessboardstate is not equal to the new.");
        assertEquals(oldChessboardState.get(y_1).size(), chessboard.getChessboardState().get(y_1).size(), "Checks that there still are 8 pieces on the initiating row.");
        assertEquals(oldChessboardState.get(y_2).size(), chessboard.getChessboardState().get(y_2).size(), "Checks that there still are 8 pieces on the final row.");
        assertEquals(Arrays.asList(x_2, y_2), piece.getPiecePos(), "Checks if the Piece pos has been updated to the correct pos.");
        assertEquals(piece, chessboard.getChessboardState().get(y_2).get(x_2), "Checks if the piece in the final pos still is the same object.");    
        assertNull(chessboard.getChessboardState().get(y_1).get(x_1), "Checks that the initiating position now is Null (Without any piece).");

        //SideWays onto nullpiece
        int x_3 = 3;
        int y_3 = 5;

        for (ArrayList<BasePiece> arrayList : chessboard.getChessboardState()) {
            oldChessboardState2.add(new ArrayList<>(arrayList)); //Saving the chessbordState
        }

        chessboard.setChessboardState(x_2, y_2, x_3, y_3);

        assertNotEquals(oldChessboardState2.get(y_2), chessboard.getChessboardState().get(y_2), "Checks that the old chessboardstate is not equal to the new.");
        assertEquals(Arrays.asList(x_3, y_3), piece.getPiecePos(), "Checks if Piece pos is correct.");
        
        //An extra move.
        int x_4 = 3;
        int y_4 = 6;
        chessboard.setChessboardState(x_3, y_3, x_4, y_4);

        //Attacking 
        int x_5 = 4;
        int y_5 = 6;

        for (ArrayList<BasePiece> arrayList : chessboard.getChessboardState()) {
            oldChessboardState3.add(new ArrayList<>(arrayList)); //Saving the chessbordState
        }

        chessboard.setChessboardState(x_4, y_4, x_5, y_5);

        assertNotEquals(oldChessboardState3.get(y_4), chessboard.getChessboardState().get(y_4), "Checks that the old chessboardstate is not equal to the new.");
        assertEquals(piece, chessboard.getChessboardState().get(y_5).get(x_5), "Checks if the piece in the final pos still is the same object.");    
        assertEquals(oldChessboardState3.get(y_4).size(), chessboard.getChessboardState().get(y_4).size(),  "Checks that there still are 8 pieces on the final row.");
        assertEquals(Arrays.asList(x_5, y_5), piece.getPiecePos(), "Checks if the Piece pos has been updated to the correct pos.");
    }

    @Test
    public void testPawnToQueen() {
        int pieceColor = -1;
        int x_1 = 0;
        int y_1 = 1;

        ArrayList<ArrayList<BasePiece>> oldChessboardState = new ArrayList<>();
        for (ArrayList<BasePiece> arrayList : chessboard.getChessboardState()) {
            oldChessboardState.add(new ArrayList<>(arrayList)); //Saving the chessbordState
        }
        BasePiece piece = chessboard.getChessboardState().get(y_1).get(x_1);
        assertTrue(piece instanceof Pawn, "Checks if the piece is a Pawn.");
        chessboard.PawnToQueen(pieceColor, x_1, y_1);
        assertEquals(oldChessboardState.get(y_1).size(), chessboard.getChessboardState().get(y_1).size(), "Checks that there still are 8 pieces on the final row.");
        piece = chessboard.getChessboardState().get(y_1).get(x_1);
        assertTrue(piece instanceof Queen, "Checks if the piece is now a Queen.");
    }

}
