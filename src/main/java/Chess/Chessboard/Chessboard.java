package Chess.Chessboard;

import java.util.ArrayList;
import java.util.Collections;
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
        InputStream file = new FileInputStream("src/main/resources/Chess/images/" + (String) s + "_png_shadow_128px.png");
        Image image = new Image(file);
        return (Image) image;
    }

    public ArrayList<ImageView> MatrixToFXML() throws FileNotFoundException {
        //Adding all images.
        Image bbImage = addChessImage("cpb/b_bishop");
        Image hbImage = addChessImage("cpb/b_knight");
        Image rbImage = addChessImage("cpb/b_rook");
        Image kbImage = addChessImage("cpb/b_king");
        Image qbImage = addChessImage("cpb/b_queen");
        Image pbImage = addChessImage("cpb/b_pawn");

        Image bwImage = addChessImage("cpw/w_bishop");
        Image hwImage = addChessImage("cpw/w_knight");
        Image rwImage = addChessImage("cpw/w_rook");
        Image kwImage = addChessImage("cpw/w_king");
        Image qwImage = addChessImage("cpw/w_queen");
        Image pwImage = addChessImage("cpw/w_pawn");

        ArrayList<ImageView> ImageArray = new ArrayList<>();

        ImageView ImageView = new ImageView();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BasePiece piece = MainBoard.get(i).get(j);
                if (!(piece == null)) {
                    switch (piece.getPieceName()) {
                        case "Chess.Pieces.Bishop":
                            switch (piece.getPieceColor()) {
                                case "b":
                                    ImageView = new ImageView(bbImage);
                                    break;
                                case "w":
                                    ImageView = new ImageView(bwImage);
                                    break;                            
                            }
                            break;
                        case "Chess.Pieces.Knight":
                            switch (piece.getPieceColor()) {
                                case "b":
                                    ImageView = new ImageView(hbImage);
                                    break;
                                case "w":
                                    ImageView = new ImageView(hwImage);
                                    break;                            
                            }
                            break;
                        case "Chess.Pieces.Rook":
                            switch (piece.getPieceColor()) {
                                case "b":
                                    ImageView = new ImageView(rbImage);
                                    break;
                                case "w":
                                    ImageView = new ImageView(rwImage);
                                    break;                            
                            }
                            break;
                        case "Chess.Pieces.King":
                            switch (piece.getPieceColor()) {
                                case "b":
                                    ImageView = new ImageView(kbImage);
                                    break;
                                case "w":
                                    ImageView = new ImageView(kwImage);
                                    break;                            
                            }
                            break;
                        case "Chess.Pieces.Queen":
                            switch (piece.getPieceColor()) {
                                case "b":
                                    ImageView = new ImageView(qbImage);
                                    break;
                                case "w":
                                    ImageView = new ImageView(qwImage);
                                    break;                            
                            }
                            break;
                        case "Chess.Pieces.Pawn":
                            switch (piece.getPieceColor()) {
                                case "b":
                                    ImageView = new ImageView(pbImage);
                                    break;
                                case "w":
                                    ImageView = new ImageView(pwImage);
                                    break;                            
                            }
                            break; 
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
        BasePiece piece_2 = MainBoard.get(y_2).get(x_2);
        System.out.println(x_1 + " " + y_1);
        System.out.println(x_2 + " " + y_2);
        
            if (y_1 != y_2) {
                MainBoard.get(y_2).remove(x_2);
                MainBoard.get(y_2).add(x_2, piece);
                MainBoard.get(y_1).remove(piece);

                MainBoard.get(y_1).add(x_1, null);
                piece = MainBoard.get(y_2).get(x_2);
                
                piece.setPiecePos(x_2, y_2);
            } else if (y_1 == y_2) {
                if (piece_2 == null) {
                    Collections.swap(MainBoard.get(y_1), x_1, x_2);
                    piece.setPiecePos(x_2, y_2);

                } else if (!(piece.getPieceColor().equals(piece_2.getPieceColor()))) {
                    System.out.println("DU fette sug");
                    Collections.swap(MainBoard.get(y_1), x_1, x_2);
                    MainBoard.get(y_2).remove(x_1);
                    MainBoard.get(y_2).add(x_1, null);
                    piece.setPiecePos(x_2, y_2);
                }
            }
        System.out.println(piece);

    }
    public static void main(String[] args) throws FileNotFoundException {
        Chessboard c1 = new Chessboard();
        c1.Move(0, 1, 0, 2);
        System.out.println("" + "\n" + c1.getChessboardState());
        c1.Move(0, 1, 0, 2);
        System.out.println("" + "\n" + c1.getChessboardState());
    }
}
