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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/* This is where the "Main" stuff is gonna go on, where the most important functions like updating the game
while moving, or initializing when the main chessboard is done. */

public class ChessInit {

    // FIELDS
    private Pane[][] paneArray;
    public List<Integer> mouseposlist = new ArrayList<>();
    private ImageView draggable;
    private int msecupdate = 10;
    public Chessboard chessboard;

    // IMPORTANT FIELDS
    public Move ChessMove;
    public CheckGameState checkGameState;
    

    // CONSTRUCTOR
    public ChessInit(Chessboard chessboard) {
      this.chessboard = chessboard;
      this.ChessMove = new Move(chessboard);
      this.checkGameState = new CheckGameState(chessboard, ChessMove);
    }
    

    // GROUP FOR ALL IMAGES ON THE CHESSBOARD
    private Group root = new Group();

    public Group getRoot() {
      return root;
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
                  if (ChessMove.Moving) {
                    Platform.runLater(updater);
                    ChessMove.Moving = false;
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
                    
                    BasePiece boardpiece = chessboard.getChessboardState().get(xaxis).get(yaxis);
                    availPattern(boardpiece, mouseposlist.get(0), mouseposlist.get(1));
    
                    ImageView piece = (ImageView) root.getChildren().get(((xaxis)*8 + yaxis) + 1);
                    root.getChildren().remove(piece);
                    root.getChildren().add(root.getChildren().size()-1, piece);
                    this.draggable = (ImageView) root.getChildren().get(root.getChildren().size()-2);
                    root.getChildren().remove(piece);
                    root.getChildren().add(root.getChildren().size()-1, piece);

                    Thread thread = new Thread(new Runnable() {
                      @Override
                      public void run() {
                        Runnable updater = new Runnable() {
                        @Override
                          public void run() {
                              if (draggable != null) {
                                gridPane.setOnMouseMoved(event -> {
                                  availPattern(boardpiece, mouseposlist.get(0), mouseposlist.get(1));
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
                    checkGameState.inCheck();   
                    }                
                });  
            }
        }
    }

    private void availPattern(BasePiece piece, int x, int y) {
      if (piece != null) {
        if ((piece.getPieceColor().equals("w") && ChessMove.whiteTurn) || (piece.getPieceColor().equals("b") && ChessMove.blackTurn))  {
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
