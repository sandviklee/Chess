package Chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessApp extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception { 
    primaryStage.setTitle("Chess");
    ChessInit.ChessInitialize.ChessPlay();
    Scene scene = new Scene(ChessInit.root);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  public static void main(String[] args) {
    ChessApp.launch(args);
  }
}