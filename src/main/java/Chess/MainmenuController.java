package Chess;

import Chess.Chessboard.Chessboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainmenuController {

    private Stage primaryStage;
    private Scene scene;
    private Parent root;
    public static ChessInit ChessInititalize;

    @FXML
    public void newgameClick(ActionEvent event) throws Exception {
        Chessboard chessboard = new Chessboard();
        ChessInititalize = new ChessInit(chessboard);
        ChessInititalize.ChessPlay();
        root = ChessInititalize.getRoot();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
