package Chess;

import java.io.File;

import Chess.Chessboard.Chessboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class MainmenuController {

    public static Stage primaryStage;
    private Scene scene;
    private Parent root;
    public static ChessInit ChessInititalize;
    public static Chessboard chessboard;

    @FXML
    private void newgameClick(ActionEvent event) throws Exception {
        chessboard = new Chessboard();
        ChessInititalize = new ChessInit(chessboard, false);
        ChessInititalize.ChessPlay();
        root = ChessInititalize.getRoot();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    private void loadClick(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text files", "*.txt")
        );
        fileChooser.setInitialDirectory(new File("src/main/resources/Chess/SaveFiles"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        chessboard = new Chessboard(selectedFile); 
        ChessInititalize = new ChessInit(chessboard, true);
        ChessInititalize.ChessPlay();
        root = ChessInititalize.getRoot();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
