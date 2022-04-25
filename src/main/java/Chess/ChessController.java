package Chess;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Chess.Chessboard.IO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ChessController implements Initializable{
    @FXML private GridPane chessGridPane;
    @FXML private Pane piecesOutPane;
    @FXML private Label player1;
    @FXML private Label player2;
    private ChessInit ChessInitialize = MainmenuController.ChessInitialize;

    private IO IOsave = new IO();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){  
        ChessInitialize.ChessPlayInitialize(chessGridPane, piecesOutPane, player1, player2);
    }
    
    @FXML
    private void offerdrawClick() {
        ChessInitialize.drawState();
    }

    @FXML
    private void claimdrawClick() {
        ChessInitialize.drawnState();
    }

    @FXML
    private void saveClick() {
        ChessInitialize.saveGameState(IOsave);
    }

    @FXML
    private void quitClick() throws IOException {
        MainmenuController.primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Mainmenu.fxml"))));
        MainmenuController.primaryStage.show();
    }

}