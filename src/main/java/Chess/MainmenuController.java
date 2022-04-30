package Chess;

import java.io.File;

import Chess.Chessboard.Chessboard;
import Chess.Chessboard.IO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class MainmenuController {
    /* Here i use alot of static fields because i thought it would be easier to 
    just make them global, because they should be able to reach without 
    initiating a new object of this class*/
    public static Stage primaryStage; //Public and static because its taken directly.
    private Scene scene;
    private Parent root;
    public static ChessInit ChessInitialize; //Public and static because its taken directly.
    public static Chessboard chessboard; //Public and static because its taken directly.
    public static String player1Name = null; //Public and static because its taken directly.
    public static String player2Name = null; //Public and static because its taken directly.

    @FXML public TextField player1; //Public because its used directly.
    @FXML public TextField player2; //Public because its used directly.

    @FXML
    private void newgameClick(ActionEvent event) throws Exception {
        player1Name = "PLAYER1";
        player2Name = "PLAYER2";
        IO.whiteTurn = true;
        IO.blackTurn = false;
        
        if (!player1.getText().isBlank()) {
            player1Name = player1.getText();
        }
        if (!player2.getText().isBlank()) {
            player2Name = player2.getText();
        }
        
        chessboard = new Chessboard();
        ChessInitialize = new ChessInit(chessboard);
        ChessInitialize.ChessPlay();
        root = ChessInitialize.getRoot();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    private void loadClick(ActionEvent event) throws Exception {
        player1Name = null;
        player2Name = null;

        //Here i use a FileChooser to open the OS explorer, so that you can load in a file you choose. FileChooser uses my own IO load method from the Class IO. 
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text files", "*.txt")
        );
        fileChooser.setInitialDirectory(new File("src/main/resources/Chess/SaveFiles"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        
        chessboard = new Chessboard(selectedFile); 
        ChessInitialize = new ChessInit(chessboard);
        ChessInitialize.ChessPlay();
        root = ChessInitialize.getRoot();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
