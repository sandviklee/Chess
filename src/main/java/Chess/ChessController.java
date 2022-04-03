package Chess;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ChessController implements Initializable{
    @FXML private GridPane gridPane;
    @FXML private Button x0y0;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ChessInit.ChessInitialize.ChessPlayInit(gridPane);
    }    

    @FXML
    public void paneClick() {
    }

    // @FXML
    // private void mouseEntered() {
    //     // Node source = (Node)e.getSource();
    //     // Integer colIndex = GridPane.getColumnIndex(source);
    //     // Integer rowIndex = GridPane.getRowIndex(source);
    //     // System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
    //     System.out.println("Hei!");
    // }

    

    


}