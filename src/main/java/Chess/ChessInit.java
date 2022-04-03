package Chess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Chess.Chessboard.Chessboard;
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

    // INITIALIZING A STATIC "GLOBAL" CHESS INITIALIZER
    public static ChessInit ChessInitialize = new ChessInit();

    // FIELDS
    private Pane[][] paneArray;
    public static List<Integer> mouseposlist = new ArrayList<>();
    private ImageView draggable;
    
    // INITIALIZING A STATIC "GLOBAL" CHESSBOARD AVAILABLE TO EVERY CLASS
    public static Chessboard chessboard = new Chessboard();

    // GROUPING THE IMPORTANT STUFF
    static Group root = new Group();
    
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
                  Thread.sleep(10);
                } catch (InterruptedException e) {
                  }
                  if (Move.Moving) {
                    Platform.runLater(updater);
                    Move.Moving = false;
                  } 
              }
            }
          });
        thread.setDaemon(true);
        thread.start();
    }

    public void ChessPlayUpdate(Group g) throws FileNotFoundException {
        g.getChildren().clear();
        g.getChildren().add(ChessInit.chessboard.ChessboardView());
    
        g.getChildren().addAll(ChessInit.chessboard.MatrixToFXML());
        try {
          g.getChildren().add(FXMLLoader.load(ChessApp.class.getResource("Chessboard.fxml")));
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    public void ChessPlayInit(GridPane gridPane) {
        paneArray = new Pane[8][8];
        for (int i = 0; i < 8; i++) {
            int IntegerI = i; //Making the y coordinate static
            for (int j = 0; j < 8; j++) {
                int IntegerJ = j; //Making the x coordinate static
                Pane pane = new Pane();
                paneArray[i][j] = pane;
                pane.setCursor(Cursor.HAND);
                gridPane.add(pane, IntegerI, IntegerJ);
                pane.setOnMouseEntered(e -> {
                    GrayHover(IntegerI, IntegerJ);
                });
                pane.setOnMouseExited(e -> {
                    RemoveGrayHover(IntegerI, IntegerJ);
                });
                
                pane.setOnMouseClicked(e -> { //Lambda Eventhandler MouseEvent
                    System.out.println("FIRST y: " + IntegerJ + " x: " + IntegerI);

                    if (mouseposlist.size() < 2) {
                        mouseposlist.add(IntegerI);
                        mouseposlist.add(IntegerJ);
                        BlackClick(mouseposlist.get(0), mouseposlist.get(1));
 
                        ImageView piece = (ImageView) root.getChildren().get(((IntegerJ)*8 + IntegerI) + 1);
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
                                            draggable.setX(event.getSceneX() - 32);
                                            draggable.setY(event.getSceneY() - 32);
                                        });
                                    }
                                }
                            };
                                while (true) {
                                  try {
                                    Thread.sleep(100);
                                  } catch (InterruptedException e) {
                                    }
                                Platform.runLater(updater);
                                }
                              }
                            });
                          thread.setDaemon(true);
                          thread.start();

                    } else if (mouseposlist.size() == 2) {
                        mouseposlist.add(IntegerI);
                        mouseposlist.add(IntegerJ);
                        BlackClick(mouseposlist.get(2), mouseposlist.get(3));
                        Move.MovePiece();
                        mouseposlist.clear();
                        }                
                });  
            }
        }
    }

    public void BlackClick(int x, int y) {
        changeColorToBlack(paneArray[x][y]);
    }

    public void GrayHover(int x, int y) {
        changeColorToGray(paneArray[x][y]);
    }

    public void RemoveGrayHover(int x, int y) {
        changeColorToNone(paneArray[x][y]);
    }

    public void changeColorToNone(Pane pane) {
        if (pane == null) return;
        pane.setStyle(null);
    }
    
    private void changeColorToGray(Pane pane) {
        if (pane == null) return;
        pane.setStyle("-fx-background-color: rgba(211, 211, 211, .2);");
    }

    private void changeColorToBlack(Pane pane) {
        if (pane == null) return;
        pane.setStyle("-fx-background-color: rgba(0, 255, 0, .2);");
    }



}
