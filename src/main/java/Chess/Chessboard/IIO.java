
package Chess.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import Chess.ChessInit;
import Chess.Exceptions.NotEnoughPiecesException;


public interface IIO {
    
    public void save(File filename, ChessInit chessInitialize) throws FileNotFoundException;

    public List<String> load(File filename) throws FileNotFoundException, NotEnoughPiecesException;
    
}
