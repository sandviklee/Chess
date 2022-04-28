package Chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Chess.Chessboard.Chessboard;
import Chess.Chessboard.IO;
import Chess.Exceptions.GameEndedException;
import Chess.Exceptions.NotEnoughPiecesException;

public class IOTest {
    ChessInit ChessInitialize;
    Chessboard chessboard;
    List<String> savedGameState;
    IO iO;

    @BeforeEach
    public void setup() throws FileNotFoundException, NotEnoughPiecesException {
        chessboard = new Chessboard();
        ChessInitialize = new ChessInit(chessboard);
        iO = new IO();
    }

    @Test
    public void testChessInit() {
        assertNotNull(chessboard);
        assertNotNull(ChessInitialize);
        assertThrows(NullPointerException.class, () -> {
            chessboard = null;
            ChessInitialize = new ChessInit(chessboard);
        });
    }

    @Test
    public void testLoadFile() {
        //General Loading
        List<String> savedGameState;
        try {
            savedGameState = iO.load(new File("src/main/resources/Chess/SaveFiles/newGameState.txt"));
        } catch (Exception e) {
            fail("Could not load saved file.");
            return;
        }
        assertEquals(chessboard.getPiecePlacer().PieceList, savedGameState);
        assertFalse(ChessInitialize.ChessMove.getGameOver());
    }


    @Test 
    public void testLoadFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> {
            savedGameState = iO.load(new File("something"));
        });
    }

    @Test
    public void testLoadBadFile() {
        assertThrows(NotEnoughPiecesException.class, () -> {
            savedGameState = iO.load(new File("src/main/resources/Chess/SaveFiles/TestFiles/newGameStateTest.txt"));
        });
    }

    @Test
    public void testSaveFile() throws FileNotFoundException, NotEnoughPiecesException {
        //First with the IO class direct saving method.
        String fileName = "Save_test.txt";
        try {
            iO.save(new File(fileName), ChessInitialize);
        } catch (Exception e) {
            fail("Could not save file");
        }

        byte[] testFile = null, newTestFile = null; 
         
        
        try {
			testFile = Files.readAllBytes(Path.of("src/main/resources/Chess/SaveFiles/newGameState.txt"));
		} catch (IOException e) {
			fail("Could not load file");
		}

		try {
			newTestFile = Files.readAllBytes(Path.of(fileName));
		} catch (IOException e) {
			fail("Could not load file");
		}

        assertNotNull(newTestFile);
        assertNotNull(testFile);
        assertTrue(Arrays.equals(testFile, newTestFile));

        //Secondly with the save method in ChessInitialize
        //Need a chessboard where it's gameover. Because of FileChooser, you can't run it as a test.
        assertFalse(ChessInitialize.checkGameState.getCheckMate());
        chessboard = new Chessboard(new File("src/main/resources/Chess/SaveFiles/TestFiles/BlackWonState2.txt"));
        ChessInitialize = new ChessInit(chessboard);
        
        ChessInitialize.checkGameState.inCheck();
        ChessInitialize.checkGameState.inCheckMate();
        assertTrue(ChessInitialize.checkGameState.getCheckMate());
        assertThrows(GameEndedException.class, () -> {
            ChessInitialize.saveGameState(iO);
        });
        assertThrows(GameEndedException.class, () -> {
            ChessInitialize.drawState();
        });
    }

    @AfterAll
    static void removeFiles() {
        String fileName = "Save_test.txt";
        File newFile = new File(fileName);
        newFile.delete();
    }

}
