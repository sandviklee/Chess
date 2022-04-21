package Chess;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Chess.Chessboard.Chessboard;
import Chess.Chessboard.IO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ChessController implements Initializable{
    @FXML private GridPane gridPane;
    @FXML private Pane grd;

    private IO IOsave = new IO();

    private Chessboard chessboard = MainmenuController.chessboard;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){  
        MainmenuController.ChessInititalize.ChessPlayInit(gridPane);
        MainmenuController.ChessInititalize.setPane(grd);
    }    

    @FXML
    public void saveClick() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text files", "*.txt"));
            fileChooser.setInitialDirectory(new File("src/main/resources/Chess/SaveFiles"));
            File file = fileChooser.showSaveDialog(MainmenuController.primaryStage);
            IOsave.save(file, chessboard);
            System.out.println("Du har lagret.");
        } catch (Exception e) {
            
        }
    }

    @FXML
    public void quitClick() throws IOException {
        MainmenuController.primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Mainmenu.fxml"))));
        MainmenuController.primaryStage.show();
    }

}