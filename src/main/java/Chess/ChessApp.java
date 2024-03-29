package Chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessApp extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception { 
    primaryStage.setTitle("Chess Game");
    primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Mainmenu.fxml"))));
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  public static void main(String[] args) {
    ChessApp.launch(args);
  }
}