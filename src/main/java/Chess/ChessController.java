package Chess;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class ChessController implements Initializable{
    @FXML private GridPane gridPane;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ChessInit.ChessInitialize.ChessPlayInit(gridPane);
    }    

    @FXML
    public void paneClick() {
    }

}