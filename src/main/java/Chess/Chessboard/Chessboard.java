package Chess.Chessboard;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import Chess.Pieces.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chessboard {
    private ArrayList<ArrayList<BasePiece>> MainBoard;

    public Chessboard() {
        PiecePlacer MainBoard = new PiecePlacer();
        MainBoard.addPieces();
        this.MainBoard = MainBoard.getOuter();
    }

    public ArrayList<ImageView> MatrixToFXML() throws FileNotFoundException {
        InputStream bb = new FileInputStream("src/main/java/Chess/images/cpb/b_bishop_png_shadow_128px.png");
        InputStream hb = new FileInputStream("src/main/java/Chess/images/cpb/b_knight_png_shadow_128px.png");
        InputStream rb = new FileInputStream("src/main/java/Chess/images/cpb/b_rook_png_shadow_128px.png");
        InputStream kb = new FileInputStream("src/main/java/Chess/images/cpb/b_king_png_shadow_128px.png");
        InputStream qb = new FileInputStream("src/main/java/Chess/images/cpb/b_queen_png_shadow_128px.png");
        InputStream pb = new FileInputStream("src/main/java/Chess/images/cpb/b_pawn_png_shadow_128px.png");
        ArrayList<ImageView> ImageArray = new ArrayList<>();

        Image bbImage = new Image(bb);
        Image pbImage = new Image(pb);
        ImageView ImageView = new ImageView();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!((MainBoard.get(i)).get(j) == null)) {
                    if ((MainBoard.get(i)).get(j).getPieceName().equals("Chess.Pieces.Rook_Black")) {
                        ImageView = new ImageView(bbImage);
                        ImageView.setX((MainBoard.get(i)).get(j).getPiecePos().get(0)*64 + 1);
                        ImageView.setY((MainBoard.get(i)).get(j).getPiecePos().get(1)*64 + 1);
                        ImageArray.add(ImageView);
                    }
                    else if ((MainBoard.get(i)).get(j).getPieceName().equals("Chess.Pieces.Pawn_Black")) {
                        ImageView = new ImageView(pbImage);
                        ImageView.setX((MainBoard.get(i)).get(j).getPiecePos().get(0)*64 + 1);
                        ImageView.setY((MainBoard.get(i)).get(j).getPiecePos().get(1)*64 + 1);
                        ImageArray.add(ImageView);
                    }
                    
                    
                }
            }
        }
        return ImageArray;

    }

    public ArrayList<ArrayList<BasePiece>> getChessboardState() {
        return MainBoard;
    }

    public void Move(int x_1, int y_1, int x_2, int y_2) {
        BasePiece piece = MainBoard.get(y_1).get(x_1);
        MainBoard.get(y_2).add(x_2, piece);
        piece = MainBoard.get(y_2).get(x_2);
        piece.setPiecePos(x_2, y_2);
        MainBoard.get(y_1).add(x_1, null);

    }


    public static void main(String[] args) throws FileNotFoundException {
        Chessboard c1 = new Chessboard();
        // System.out.println(c1.MatrixToFXML().size(
        System.out.println(c1.getChessboardState());
        c1.Move(0, 0, 1, 2);
        System.out.println("" + "\n" + c1.getChessboardState());
    }
}
