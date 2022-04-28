package Chess.Chessboard;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import Chess.Exceptions.NotEnoughPiecesException;
import Chess.Pieces.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chessboard {
    private ArrayList<ArrayList<BasePiece>> chessboard;
    private PiecePlacer piecePlacer;

    public PiecePlacer getPiecePlacer() {
        return piecePlacer;
    }

    public Chessboard() throws FileNotFoundException, NotEnoughPiecesException {
        File newGameFile = new File("src/main/resources/Chess/SaveFiles/newGameState.txt");
        piecePlacer = new PiecePlacer(newGameFile);
        piecePlacer.addPieces();
        this.chessboard = piecePlacer.getOuter();
    }

    public Chessboard(File filename) throws FileNotFoundException, NotEnoughPiecesException {
        PiecePlacer chessboard = new PiecePlacer(filename);
        chessboard.addPieces();
        this.chessboard = chessboard.getOuter();
    }

    public ImageView ChessboardView() throws FileNotFoundException {
        InputStream board = new FileInputStream("src/main/resources/Chess/images/Chessboard.png");
        Image boardimg = new Image(board);
        ImageView boardView = new ImageView(boardimg);
        return boardView;
    }

    public Image addChessImage(String s) throws FileNotFoundException {
        InputStream file = new FileInputStream("src/main/resources/Chess/images/" + (String) s + "_png_shadow_128px.png");
        Image image = new Image(file);
        return (Image) image;
    }

    //Adding all images.
    public Image bbImage = addChessImage("cpb/b_bishop");
    public Image hbImage = addChessImage("cpb/b_knight");
    public Image rbImage = addChessImage("cpb/b_rook");
    public Image kbImage = addChessImage("cpb/b_king");
    public Image qbImage = addChessImage("cpb/b_queen");
    public Image pbImage = addChessImage("cpb/b_pawn");
    public Image bwImage = addChessImage("cpw/w_bishop");
    public Image hwImage = addChessImage("cpw/w_knight");
    public Image rwImage = addChessImage("cpw/w_rook");
    public Image kwImage = addChessImage("cpw/w_king");
    public Image qwImage = addChessImage("cpw/w_queen");
    public Image pwImage = addChessImage("cpw/w_pawn");

    public ArrayList<ImageView> MatrixToFXML() throws FileNotFoundException {
        ArrayList<ImageView> ImageArray = new ArrayList<>();
        ImageView ImageView = new ImageView();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BasePiece piece = chessboard.get(i).get(j);
                if (!(piece == null)) {
                    switch (piece.toString()) {
                        case "Bishop":
                            switch (piece.getPieceColor()) {
                                case 'b':
                                    ImageView = new ImageView(bbImage);
                                    break;
                                case 'w':
                                    ImageView = new ImageView(bwImage);
                                    break;                            
                            }
                            break;
                        case "Knight":
                            switch (piece.getPieceColor()) {
                                case 'b':
                                    ImageView = new ImageView(hbImage);
                                    break;
                                case 'w':
                                    ImageView = new ImageView(hwImage);
                                    break;                            
                            }
                            break;
                        case "Rook":
                            switch (piece.getPieceColor()) {
                                case 'b':
                                    ImageView = new ImageView(rbImage);
                                    break;
                                case 'w':
                                    ImageView = new ImageView(rwImage);
                                    break;                            
                            }
                            break;
                        case "King":
                            switch (piece.getPieceColor()) {
                                case 'b':
                                    ImageView = new ImageView(kbImage);
                                    break;
                                case 'w':
                                    ImageView = new ImageView(kwImage);
                                    break;                            
                            }
                            break;
                        case "Queen":
                            switch (piece.getPieceColor()) {
                                case 'b':
                                    ImageView = new ImageView(qbImage);
                                    break;
                                case 'w':
                                    ImageView = new ImageView(qwImage);
                                    break;                            
                            }
                            break;
                        case "Pawn":
                            switch (piece.getPieceColor()) {
                                case 'b':
                                    ImageView = new ImageView(pbImage);
                                    break;
                                case 'w':
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
        return new ArrayList<>(chessboard);
    }

    public void setChessboardState(int x_1, int y_1, int x_2, int y_2) {
        BasePiece piece = chessboard.get(y_1).get(x_1);
        BasePiece piece_2 = chessboard.get(y_2).get(x_2);

        if (y_1 != y_2) {
            chessboard.get(y_2).remove(x_2);
            chessboard.get(y_2).add(x_2, piece);
            chessboard.get(y_1).remove(piece);
            chessboard.get(y_1).add(x_1, null);
            piece = chessboard.get(y_2).get(x_2);
            piece.setPiecePos(x_2, y_2);
            
        } else if (y_1 == y_2) {
            if (piece_2 == null) {
                Collections.swap(chessboard.get(y_1), x_1, x_2);
                piece.setPiecePos(x_2, y_2);

            } else if (piece.getPieceColor() != (piece_2.getPieceColor())) {
                Collections.swap(chessboard.get(y_1), x_1, x_2);
                chessboard.get(y_2).remove(x_1);
                chessboard.get(y_2).add(x_1, null);
                piece.setPiecePos(x_2, y_2);
            }
        }

    }

    public void PawnToQueen(int pieceColor, int x_1, int y_1) {
        BasePiece piece = new Queen(pieceColor, x_1, y_1);
        chessboard.get(y_1).remove(x_1);
        chessboard.get(y_1).add(x_1, piece);
    }
}
