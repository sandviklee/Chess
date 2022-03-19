package Chess;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;

import Chess.Chessboard.Chessboard;
import Chess.Chessboard.PiecePlacer;
import Chess.Pieces.BasePiece;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception { 

    primaryStage.setTitle("Chess");
    Chessboard c1 = new Chessboard();
    c1.Move(0, 1, 0, 2);
    c1.Move(0, 2, 0, 3);
    System.out.println(c1.getChessboardState());
    Group root = new Group();
    root.getChildren().add(FXMLLoader.load(Game.class.getResource("Chessboard.fxml")));
    root.getChildren().add(c1.ChessboardView());
    root.getChildren().addAll(c1.MatrixToFXML());

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();


  }

  public static void main(String[] args) {
    Game.launch(args);
  }
}