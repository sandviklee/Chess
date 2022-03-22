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

    public ImageView ChessboardView() throws FileNotFoundException {
        InputStream board = new FileInputStream("src/main/java/Chess/images/Chessboard.png");
        Image boardimg = new Image(board);
        ImageView boardView = new ImageView(boardimg);
        return boardView;
    }

    public ArrayList<ImageView> MatrixToFXML() throws FileNotFoundException {
        InputStream bb = new FileInputStream("src/main/java/Chess/images/cpb/b_bishop_png_shadow_128px.png");
        InputStream hb = new FileInputStream("src/main/java/Chess/images/cpb/b_knight_png_shadow_128px.png");
        InputStream rb = new FileInputStream("src/main/java/Chess/images/cpb/b_rook_png_shadow_128px.png");
        InputStream kb = new FileInputStream("src/main/java/Chess/images/cpb/b_king_png_shadow_128px.png");
        InputStream qb = new FileInputStream("src/main/java/Chess/images/cpb/b_queen_png_shadow_128px.png");
        InputStream pb = new FileInputStream("src/main/java/Chess/images/cpb/b_pawn_png_shadow_128px.png");

        InputStream bw = new FileInputStream("src/main/java/Chess/images/cpw/w_bishop_png_shadow_128px.png");
        InputStream hw = new FileInputStream("src/main/java/Chess/images/cpw/w_knight_png_shadow_128px.png");
        InputStream rw = new FileInputStream("src/main/java/Chess/images/cpw/w_rook_png_shadow_128px.png");
        InputStream kw = new FileInputStream("src/main/java/Chess/images/cpw/w_king_png_shadow_128px.png");
        InputStream qw = new FileInputStream("src/main/java/Chess/images/cpw/w_queen_png_shadow_128px.png");
        InputStream pw = new FileInputStream("src/main/java/Chess/images/cpw/w_pawn_png_shadow_128px.png");

        Image bbImage = new Image(bb);
        Image hbImage = new Image(hb);
        Image rbImage = new Image(rb);
        Image kbImage = new Image(kb);
        Image qbImage = new Image(qb);
        Image pbImage = new Image(pb);

        Image bwImage = new Image(bw);
        Image hwImage = new Image(hw);
        Image rwImage = new Image(rw);
        Image kwImage = new Image(kw);
        Image qwImage = new Image(qw);
        Image pwImage = new Image(pw);

        ArrayList<ImageView> ImageArray = new ArrayList<>();

        ImageView ImageView = new ImageView();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BasePiece piece = MainBoard.get(i).get(j);
                if (!(piece == null)) {
                    if (piece.getPieceName().equals("Chess.Pieces.Bishop")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(bbImage);
                        } else {
                            ImageView = new ImageView(bwImage);
                        }
                        
                        ImageView.setX(piece.getPiecePos().get(0)*64 + 1);
                        ImageView.setY(piece.getPiecePos().get(1)*64 + 1);
                        ImageArray.add(ImageView);
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.Knight")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(hbImage);
                        } else {
                            ImageView = new ImageView(hwImage);
                        }
                        
                        ImageView.setX(piece.getPiecePos().get(0)*64 + 1);
                        ImageView.setY(piece.getPiecePos().get(1)*64 + 1);
                        ImageArray.add(ImageView);
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.Rook")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(rbImage);
                        } else {
                            ImageView = new ImageView(rwImage);
                        }

                        ImageView.setX(piece.getPiecePos().get(0)*64 + 1);
                        ImageView.setY(piece.getPiecePos().get(1)*64 + 1);
                        ImageArray.add(ImageView);
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.King")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(kbImage);
                        } else {
                            ImageView = new ImageView(kwImage);
                        }

                        ImageView.setX(piece.getPiecePos().get(0)*64 + 1);
                        ImageView.setY(piece.getPiecePos().get(1)*64 + 1);
                        ImageArray.add(ImageView);
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.Queen")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(qbImage);
                        } else {
                            ImageView = new ImageView(qwImage);
                        }

                        ImageView.setX(piece.getPiecePos().get(0)*64 + 1);
                        ImageView.setY(piece.getPiecePos().get(1)*64 + 1);
                        ImageArray.add(ImageView);
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.Pawn")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(pbImage);
                        } else {
                            ImageView = new ImageView(pwImage);
                        }
                        
                        ImageView.setX(piece.getPiecePos().get(0)*64 + 1);
                        ImageView.setY(piece.getPiecePos().get(1)*64 + 1);
                        ImageArray.add(ImageView);
                    }
                    else {
                        ImageArray.add(null);
                    }
                }
            }
        }
        return ImageArray;

    }

    public ArrayList<ArrayList<BasePiece>> getChessboardState() {
        return MainBoard;
    }

    public ArrayList<ArrayList<BasePiece>> setChessboardState() {
        return MainBoard;
    }

    public void Move(int x_1, int y_1, int x_2, int y_2) {
        BasePiece piece = MainBoard.get(y_1).get(x_1);
        MainBoard.get(y_2).remove(x_2);
        MainBoard.get(y_2).add(x_2, piece);
        MainBoard.get(y_1).remove(piece);

        MainBoard.get(y_1).add(x_1, null);
        piece = MainBoard.get(y_2).get(x_2);
        
        piece.setPiecePos(x_2, y_2);
        

    }
    public static void main(String[] args) throws FileNotFoundException {
        Chessboard c1 = new Chessboard();
        // System.out.println(c1.MatrixToFXML().size(
        System.out.println(c1.getChessboardState());
        c1.Move(0, 1, 0, 2);
        System.out.println("" + "\n" + c1.getChessboardState());
    }
}
