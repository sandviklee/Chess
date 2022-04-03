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
        InputStream board = new FileInputStream("src/main/resources/Chess/images/Chessboard.png");
        Image boardimg = new Image(board);
        ImageView boardView = new ImageView(boardimg);
        return boardView;
    }

    private Image addChessImage(String s) throws FileNotFoundException {
        InputStream file = new FileInputStream(s);
        Image image = new Image(file);
        return image;
    }

    public ArrayList<ImageView> MatrixToFXML() throws FileNotFoundException {
        Image bbImage = addChessImage("src/main/resources/Chess/images/cpb/b_bishop_png_shadow_128px.png");
        Image hbImage = addChessImage("src/main/resources/Chess/images/cpb/b_knight_png_shadow_128px.png");
        Image rbImage = addChessImage("src/main/resources/Chess/images/cpb/b_rook_png_shadow_128px.png");
        Image kbImage = addChessImage("src/main/resources/Chess/images/cpb/b_king_png_shadow_128px.png");
        Image qbImage = addChessImage("src/main/resources/Chess/images/cpb/b_queen_png_shadow_128px.png");
        Image pbImage = addChessImage("src/main/resources/Chess/images/cpb/b_pawn_png_shadow_128px.png");

        Image bwImage = addChessImage("src/main/resources/Chess/images/cpw/w_bishop_png_shadow_128px.png");
        Image hwImage = addChessImage("src/main/resources/Chess/images/cpw/w_knight_png_shadow_128px.png");
        Image rwImage = addChessImage("src/main/resources/Chess/images/cpw/w_rook_png_shadow_128px.png");
        Image kwImage = addChessImage("src/main/resources/Chess/images/cpw/w_king_png_shadow_128px.png");
        Image qwImage = addChessImage("src/main/resources/Chess/images/cpw/w_queen_png_shadow_128px.png");
        Image pwImage = addChessImage("src/main/resources/Chess/images/cpw/w_pawn_png_shadow_128px.png");

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
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.Knight")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(hbImage);
                        } else {
                            ImageView = new ImageView(hwImage);
                        }
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.Rook")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(rbImage);
                        } else {
                            ImageView = new ImageView(rwImage);
                        }
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.King")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(kbImage);
                        } else {
                            ImageView = new ImageView(kwImage);
                        }
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.Queen")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(qbImage);
                        } else {
                            ImageView = new ImageView(qwImage);
                        }
                    }
                    else if (piece.getPieceName().equals("Chess.Pieces.Pawn")) {
                        if (piece.getPieceColor().equals("b")) {
                            ImageView = new ImageView(pbImage);
                        } else {
                            ImageView = new ImageView(pwImage);
                        }
                    }
                    ImageView.setX(piece.getPiecePos().get(0)*64 + 1);
                    ImageView.setY(piece.getPiecePos().get(1)*64 + 1);
                    ImageArray.add(ImageView);
                
                    } else {
                        ImageView = new ImageView();
                        ImageArray.add(ImageView);
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
        try {
            MainBoard.get(y_2).remove(x_2);
            MainBoard.get(y_2).add(x_2, piece);
            MainBoard.get(y_1).remove(piece);

            MainBoard.get(y_1).add(x_1, null);
            piece = MainBoard.get(y_2).get(x_2);
            
            piece.setPiecePos(x_2, y_2);
        } catch (Exception e) {
            //TODO: FIKS EXCEPTION
        }

        

    }
    public static void main(String[] args) throws FileNotFoundException {
        Chessboard c1 = new Chessboard();
        // System.out.println(c1.MatrixToFXML().size(
        System.out.println(c1.getChessboardState());
        c1.Move(0, 1, 0, 2);
        System.out.println("" + "\n" + c1.getChessboardState());
    }
}
