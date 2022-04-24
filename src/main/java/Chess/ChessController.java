package Chess;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Chess.Chessboard.IO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ChessController implements Initializable{
    @FXML private GridPane gridPane;
    @FXML private Pane grd;
    @FXML private Label player1;
    @FXML private Label player2;
    private boolean drawOffer = false;

    private IO IOsave = new IO();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){  
        MainmenuController.ChessInititalize.ChessPlayInit(gridPane);
        MainmenuController.ChessInititalize.setPane(grd);

        String nameW = "PLAYER1";
        String nameB = "PLAYER1";
        
        if (MainmenuController.player1Name != null) {
            nameW = MainmenuController.player1Name;
            player1.setText(nameW);
            
        } else {
            if (IO.player2Name != null) {
                nameW = IO.player1Name;
                player1.setText(nameW);
                
            } else {
                nameW = "PLAYER1";
                player1.setText(nameW);
                
            }
        }

        if (MainmenuController.player2Name != null) {
            nameB = MainmenuController.player2Name;
            player2.setText(nameB);
            
        } else {
            if (IO.player2Name != null) {
                nameB = IO.player2Name;
                player2.setText(nameB);
                
            } else {
                nameB = "PLAYER2";
                player2.setText(nameB);
                
            }
            
        }
        System.out.println(nameW);
        MainmenuController.ChessInititalize.checkGameState.setPlayerWName(nameW);
        MainmenuController.ChessInititalize.checkGameState.setPlayerBName(nameB);
        
    }
    
    @FXML
    private void offerdrawClick() {
        if (!MainmenuController.ChessInititalize.ChessMove.getGameOver()) {
            Alert a = new Alert(AlertType.WARNING);
            if (MainmenuController.ChessInititalize.ChessMove.getWhiteTurn()) {
                a.setHeaderText("White offers Draw!");
                a.setContentText("White is in a pickle! He wants a draw.");
            } else {
                a.setHeaderText("Black offers Draw!");
                a.setContentText("Black is in a pickle! He wants a draw.");
            }
            drawOffer = true;
            a.show(); 
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Not available.");
            a.setContentText("You cannot offer a draw in an ended game.");
            a.show(); 
        }
    }

    @FXML
    private void claimdrawClick() {
        if (drawOffer) {
            MainmenuController.ChessInititalize.checkGameState.setDraw(drawOffer);
            MainmenuController.ChessInititalize.ChessMove.setGameOver(drawOffer);
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Draw!");
            a.setContentText("This game ended in draw.");
            a.show(); 
        }
    }


    @FXML
    private void saveClick() {
        if (!MainmenuController.ChessInititalize.ChessMove.getGameOver()) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text files", "*.txt"));
                fileChooser.setInitialDirectory(new File("src/main/resources/Chess/SaveFiles"));
                File file = fileChooser.showSaveDialog(MainmenuController.primaryStage);
                IOsave.save(file, MainmenuController.ChessInititalize);
                //System.out.println("Du har lagret.");
            } catch (Exception e) {
                
            }
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Not a savable game.");
            a.setContentText("You cannot save a game which has ended.");
            a.show(); 
        }
    }

    @FXML
    private void quitClick() throws IOException {
        MainmenuController.primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Mainmenu.fxml"))));
        MainmenuController.primaryStage.show();
    }

}