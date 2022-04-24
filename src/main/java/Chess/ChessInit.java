package Chess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Chess.Chessboard.Chessboard;
import Chess.Chessboard.PiecePlacer;
import Chess.Pieces.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
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
    public Chessboard chessboard;
    public Move ChessMove;
    public CheckGameState checkGameState;
    private Pane pane;
    private ArrayList<BasePiece> piecesList = new ArrayList<>();
    private int pieceOutY;
    private int pieceOutX;
    private List<String> piecesOutList = new ArrayList<>();
    private boolean pieceKnockedOut = false;
    
    // CONSTRUCTOR
    public ChessInit(Chessboard chessboard, boolean pawnDoubleMove) {
      this.chessboard = chessboard;
      this.ChessMove = new Move(chessboard);
      this.checkGameState = new CheckGameState(chessboard, ChessMove);
      this.piecesOutList = PiecePlacer.IOload.getPiecesOut();
      //BasePiece.setMoved(pawnDoubleMove); // Pawn double move available ?
      
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

    public void setKnockedOut(boolean statement) {
      this.pieceKnockedOut = statement;
    }

    public ArrayList<BasePiece> getpiecesList() {
      return piecesList;
    }

    public void loadAllPiecesOut(Pane pane, boolean statement) throws FileNotFoundException {
      for (String pieceStr : piecesOutList) {
        ImageView ImageView = new ImageView();
        switch (pieceStr) {
          case "B":
            ImageView = new ImageView(chessboard.bbImage);
            break;
          case "b":
            ImageView = new ImageView(chessboard.bwImage);
            break;
          case "H":
            ImageView = new ImageView(chessboard.hbImage);
            break;
          case "h":
            ImageView = new ImageView(chessboard.hwImage);
            break;
          case "R":
            ImageView = new ImageView(chessboard.rbImage);
            break;
          case "r":
            ImageView = new ImageView(chessboard.rwImage);
            break;
          case "K":
            ImageView = new ImageView(chessboard.kbImage);
            break;
          case "k":
            ImageView = new ImageView(chessboard.kwImage);
            break;
          case "Q":
            ImageView = new ImageView(chessboard.qbImage);
            break;
          case "q":
            ImageView = new ImageView(chessboard.qwImage);
            break;
          case "P":
            ImageView = new ImageView(chessboard.pbImage);
            break;
          case "p":
            ImageView = new ImageView(chessboard.pwImage);
            break;   
        }
        ImageView.setScaleX(0.30);
        ImageView.setScaleY(0.30);
        ImageView.setX(pieceOutX*25 - 10);
        ImageView.setY(pieceOutY*25 - 10);

        if (statement) {
          piecesOutGroup.getChildren().addAll(ImageView);
        }
        
        if ((piecesOutGroup.getChildren().size())%8 == 0) {
          pieceOutY++;
          pieceOutX = 0;
        } else {
          pieceOutX++;
        }
        
      }
      if (!statement) {
        pane.getChildren().clear();
      }
      pane.getChildren().addAll(piecesOutGroup);
    }

    public void updatePiecesOut(Pane pane) throws FileNotFoundException {
      BasePiece piece = null;
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
                      ImageView = new ImageView(chessboard.bbImage);
                      break;
                  case 'w':
                      ImageView = new ImageView(chessboard.bwImage);
                      break;                            
              }
              break;
          case "Knight":
              switch (piece.getPieceColor()) {
                  case 'b':
                      ImageView = new ImageView(chessboard.hbImage);
                      break;
                  case 'w':
                      ImageView = new ImageView(chessboard.hwImage);
                      break;                            
              }
              break;
          case "Rook":
              switch (piece.getPieceColor()) {
                  case 'b':
                      ImageView = new ImageView(chessboard.rbImage);
                      break;
                  case 'w':
                      ImageView = new ImageView(chessboard.rwImage);
                      break;                            
              }
              break;
          case "King":
              switch (piece.getPieceColor()) {
                  case 'b':
                      ImageView = new ImageView(chessboard.kbImage);
                      break;
                  case 'w':
                      ImageView = new ImageView(chessboard.kwImage);
                      break;                            
              }
              break;
          case "Queen":
              switch (piece.getPieceColor()) {
                  case 'b':
                      ImageView = new ImageView(chessboard.qbImage);
                      break;
                  case 'w':
                      ImageView = new ImageView(chessboard.qwImage);
                      break;                            
              }
              break;
          case "Pawn":
              switch (piece.getPieceColor()) {
                  case 'b':
                      ImageView = new ImageView(chessboard.pbImage);
                      break;
                  case 'w':
                      ImageView = new ImageView(chessboard.pwImage);
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
    
    int gameStart = -1;
    public void ChessPlay() throws IOException {
        if (gameStart != 0) {
          try {
            checkGameState.inCheck();
            checkGameState.inCheckMate();
            gameStart++;   
          } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            }
        }
        root = new Group();
        root.getChildren().add(chessboard.ChessboardView());
        root.getChildren().addAll(chessboard.MatrixToFXML());
        root.getChildren().add(FXMLLoader.load(ChessApp.class.getResource("Chessboard.fxml")));
        
        try {
          if (piecesOutList != null) {
            loadAllPiecesOut(pane, true);
          }
            
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }
        
        Thread thread = new Thread(new Runnable() {
          @Override
          public void run() {
            Runnable updater = new Runnable() {
              @Override
              public void run() {
                try {
                  System.out.println(ChessMove.knockedOut);
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
      if (!ChessMove.getGameOver()) {
        if ((piece.getPieceColor() == 'w' && ChessMove.getWhiteTurn()) || (piece.getPieceColor() == 'b' && ChessMove.getBlackTurn()))  {
          for (ArrayList<Integer> pos : ChessMove.validatePattern(x, y)) {
            GreenClick(pos.get(0), pos.get(1));
          }
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
