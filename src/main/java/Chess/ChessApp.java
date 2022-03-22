package Chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ChessApp extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception { 

    primaryStage.setTitle("Chess");
    Group root = new Group();
    root.getChildren().add(Chess.chessboard.ChessboardView());
    root.getChildren().addAll(Chess.chessboard.MatrixToFXML());
    root.getChildren().add(FXMLLoader.load(ChessApp.class.getResource("Chessboard.fxml")));
    
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  public static void main(String[] args) {
    ChessApp.launch(args);
  }
}