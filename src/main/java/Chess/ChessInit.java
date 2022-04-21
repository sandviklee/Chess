package Chess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Chess.Chessboard.Chessboard;
import Chess.Pieces.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/* This is where the "Main" stuff is gonna go on, where the most important functions like updating the game
while moving. This is the Initialization of the chess game. */

public class ChessInit {

    // FIELDS
    private Pane[][] paneArray;
    private List<Integer> mouseposlist = new ArrayList<>();
    private ImageView draggable;
    private int msecupdate = 10;
    private Chessboard chessboard;
    private Move ChessMove;
    private CheckGameState checkGameState;
    private Pane pane;
    private ArrayList<BasePiece> piecesList = new ArrayList<>();
    private int pieceOutY;
    private int pieceOutX;
    
    // CONSTRUCTOR
    public ChessInit(Chessboard chessboard, boolean pawnDoubleMove) {
      this.chessboard = chessboard;
      this.ChessMove = new Move(chessboard);
      this.checkGameState = new CheckGameState(chessboard, ChessMove);
      BasePiece.setMoved(pawnDoubleMove); // Pawn double move available ?
      
    }
    
    // GROUP FOR ALL IMAGES ON THE CHESSBOARD
    private Group root = new Group();

    public Group getRoot() {
      return root;
    }

    // GROUP FOR IMAGES: ALL PIECES OUT
    private Group piecesOutGroup = new Group();

    public void setPane(Pane pane) {
      this.pane = pane;
    }

    public void updatePiecesOut(Pane pane) throws FileNotFoundException {

      BasePiece piece = null;

      Image bbImage = chessboard.addChessImage("cpb/b_bishop");
      Image hbImage = chessboard.addChessImage("cpb/b_knight");
      Image rbImage = chessboard.addChessImage("cpb/b_rook");
      Image kbImage = chessboard.addChessImage("cpb/b_king");
      Image qbImage = chessboard.addChessImage("cpb/b_queen");
      Image pbImage = chessboard.addChessImage("cpb/b_pawn");

      Image bwImage = chessboard.addChessImage("cpw/w_bishop");
      Image hwImage = chessboard.addChessImage("cpw/w_knight");
      Image rwImage = chessboard.addChessImage("cpw/w_rook");
      Image kwImage = chessboard.addChessImage("cpw/w_king");
      Image qwImage = chessboard.addChessImage("cpw/w_queen");
      Image pwImage = chessboard.addChessImage("cpw/w_pawn");

      ImageView ImageView = new ImageView();
      if (!ChessMove.piecesOut.isEmpty()) {
        piece = ChessMove.piecesOut.get(0);
      }
      
      if (piece != null) {
        piecesList.add(piece);
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

        ImageView.setScaleX(0.30);
        ImageView.setScaleY(0.30);
        ImageView.setX(pieceOutX*25 - 10);
        ImageView.setY(pieceOutY*25 - 10);

        if (piecesList.size() >= 2) {
          if (!piecesList.get(piecesList.size() - 2).equals(piece)) {
            piecesOutGroup.getChildren().addAll(ImageView);

            if ((piecesOutGroup.getChildren().size())%8 == 0) {
              pieceOutY++;
              pieceOutX = 0;
            } else {
              pieceOutX++;
            }

          } else {
            piecesList.remove(piecesList.size() - 1);
          }
        } else {
          piecesOutGroup.getChildren().addAll(ImageView);
          pieceOutX++;
        }
        pane.getChildren().addAll(piecesOutGroup);

        System.out.println(piecesList);
      }
    }
    
    public void ChessPlay() throws IOException {
        root = new Group();
        root.getChildren().add(chessboard.ChessboardView());
        root.getChildren().addAll(chessboard.MatrixToFXML());
        root.getChildren().add(FXMLLoader.load(ChessApp.class.getResource("Chessboard.fxml")));
        Thread thread = new Thread(new Runnable() {
          @Override
          public void run() {
            Runnable updater = new Runnable() {
              @Override
              public void run() {
                try {
                  ChessPlayUpdate(root);
                  updatePiecesOut(pane);
                  
                } catch (FileNotFoundException e) {
                  e.printStackTrace();
                }
              }
            };
              while (true) {
                try {
                  Thread.sleep(msecupdate);
                } catch (InterruptedException e) {
                  }
                  if (ChessMove.getMoving()) {
                    Platform.runLater(updater);
                    ChessMove.setMoving(false);
                  } 
              }
            }
          });
        thread.setDaemon(true);
        thread.start();
    }

    public void ChessPlayUpdate(Group g) throws FileNotFoundException {
        g.getChildren().clear();
        g.getChildren().add(chessboard.ChessboardView());
    
        g.getChildren().addAll(chessboard.MatrixToFXML());
        try {
          g.getChildren().add(FXMLLoader.load(ChessApp.class.getResource("Chessboard.fxml")));
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    public void ChessPlayInit(GridPane gridPane) {
        paneArray = new Pane[8][8];
        for (int i = 0; i < 8; i++) {
            int yaxis = i; //Making the y coordinate static
            for (int j = 0; j < 8; j++) {
                int xaxis = j; //Making the x coordinate static
                Pane pane = new Pane(); //Making a pane for every gridpane space
                paneArray[i][j] = pane;

                pane.setCursor(Cursor.HAND); //Changes cursor on board to hand

                gridPane.add(pane, yaxis, xaxis);

                pane.setOnMouseEntered(e -> {
                    GrayHover(yaxis, xaxis);
                });
                pane.setOnMouseExited(e -> {
                    RemoveGrayHover(yaxis, xaxis);
                });
                
                pane.setOnMouseClicked(e -> { //Lambda Eventhandler MouseEvent
                  System.out.println("y: " + xaxis + " x: " + yaxis);

                  if (mouseposlist.size() < 2) {
                    mouseposlist.add(yaxis);
                    mouseposlist.add(xaxis);
                    
                    ImageView piece = (ImageView) root.getChildren().get(((xaxis)*8 + yaxis) + 1);
                    root.getChildren().remove(piece);
                    root.getChildren().add(root.getChildren().size()-1, piece);
                    this.draggable = (ImageView) root.getChildren().get(root.getChildren().size()-2);
                    root.getChildren().remove(piece);
                    root.getChildren().add(root.getChildren().size()-1, piece);

                    BasePiece boardpiece = chessboard.getChessboardState().get(xaxis).get(yaxis);

                    Thread thread = new Thread(new Runnable() {
                      @Override
                      public void run() {
                        Runnable updater = new Runnable() {
                        @Override
                          public void run() {
                              if (draggable != null) {
                                gridPane.setOnMouseMoved(event -> {
                                  if (piece != null && !checkGameState.getCheckMate()) {
                                    try {
                                      availPattern(boardpiece, yaxis, xaxis);
                                    } catch (Exception e) {
                                      //TODO: handle exception
                                    }
                                    
                                  }
                                  
                                  draggable.setX(event.getSceneX() - 32);
                                  draggable.setY(event.getSceneY() - 32);
                                });
                              }
                            }
                        };
                        while (true) {
                          try {
                            Thread.sleep(msecupdate);
                          } catch (InterruptedException e) {}
                        Platform.runLater(updater);
                        }
                      }
                    });
                    thread.setDaemon(true);
                    thread.start();

                } else if (mouseposlist.size() == 2) {
                    mouseposlist.add(yaxis);
                    mouseposlist.add(xaxis);
                    ChessMove.MovePiece(mouseposlist.get(0), mouseposlist.get(1), mouseposlist.get(2), mouseposlist.get(3));
                    mouseposlist.clear();
                    try {
                      checkGameState.inCheck();
                      checkGameState.inCheckMate();   
                    } catch (FileNotFoundException e1) {
                      e1.printStackTrace();
                      }
                    
                    }                
                });  
            }
        }
    }

    private void availPattern(BasePiece piece, int x, int y) {
      if ((piece.getPieceColor() == 'w' && ChessMove.getWhiteTurn()) || (piece.getPieceColor() == 'b' && ChessMove.getBlackTurn()))  {
        for (ArrayList<Integer> pos : ChessMove.validatePattern(x, y)) {
          GreenClick(pos.get(0), pos.get(1));
        }
      }
    }



    private void GreenClick(int x, int y) {
        changeColorToGreen(paneArray[x][y]);
    }

    private void GrayHover(int x, int y) {
        changeColorToGray(paneArray[x][y]);
    }

    private void RemoveGrayHover(int x, int y) {
        changeColorToNone(paneArray[x][y]);
    }

    private void changeColorToNone(Pane pane) {
        if (pane == null) return;
        pane.setStyle(null);
    }
    
    private void changeColorToGray(Pane pane) {
        if (pane == null) return;
        pane.setStyle("-fx-background-color: rgba(211, 211, 211, .2);");
    }

    private void changeColorToGreen(Pane pane) {
        if (pane == null) return;
        pane.setStyle("-fx-background-color: rgba(0, 255, 0, .2);");
    }



}
