
package Chess.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;

import Chess.ChessInit;

public interface IIO {
    
    public void save(File filename, ChessInit chessInitialize) throws FileNotFoundException;

    public String[] load(File filename) throws FileNotFoundException;
        
}
