package Chess;

import java.io.FileInputStream;
import java.io.InputStream;

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

    // InputStream bb = new FileInputStream("src/main/java/Chess/images/cpb/b_bishop_png_shadow_128px.png");
    // InputStream hb = new FileInputStream("src/main/java/Chess/images/cpb/b_knight_png_shadow_128px.png");
    // InputStream rb = new FileInputStream("src/main/java/Chess/images/cpb/b_rook_png_shadow_128px.png");
    // InputStream kb = new FileInputStream("src/main/java/Chess/images/cpb/b_king_png_shadow_128px.png");
    // InputStream qb = new FileInputStream("src/main/java/Chess/images/cpb/b_queen_png_shadow_128px.png");
    // InputStream pb = new FileInputStream("src/main/java/Chess/images/cpb/b_rook_png_shadow_128px.png");

    
    // Image image = new Image(bb);
    // ImageView imageView1 = new ImageView(image);
    // imageView1.setX(64);
    // imageView1.setY(0);

    Chessboard c1 = new Chessboard();
  
    primaryStage.setTitle("Chess");
    Group root = new Group(FXMLLoader.load(Game.class.getResource("Chessboard.fxml")), c1.MatrixToFXML());
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);

    // PiecePlacer placer = new PiecePlacer();
    // placer.addPieces();

    // container.getChildren().add(mainImageView);

    primaryStage.show();
    
  }

  public static void main(String[] args) {
    Game.launch(args);
  }
}