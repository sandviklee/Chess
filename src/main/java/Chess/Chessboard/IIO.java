
package Chess.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;

public interface IIO {
    
    public void save(File filename, Chessboard chessboard) throws FileNotFoundException;

    public String[] load(File filename) throws FileNotFoundException;
        
}
