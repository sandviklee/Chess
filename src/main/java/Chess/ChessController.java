package Chess;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class ChessController implements Initializable{
    @FXML private GridPane gridPane;
    @FXML private Button x0y0;
    private Pane[][] paneArray;
    public static List<Integer> mouseposlist = new ArrayList<>();

    private ImageView draggable;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
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
 
                        ImageView piece = (ImageView) ChessApp.root.getChildren().get(((IntegerJ)*8 + IntegerI) + 1);
                        ChessApp.root.getChildren().remove(piece);
                        ChessApp.root.getChildren().add(ChessApp.root.getChildren().size()-1, piece);
                        this.draggable = (ImageView) ChessApp.root.getChildren().get(ChessApp.root.getChildren().size()-2);
                        ChessApp.root.getChildren().remove(piece);
                        ChessApp.root.getChildren().add(ChessApp.root.getChildren().size()-1, piece);
                        
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
                                    Thread.sleep(1);
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

    @FXML
    public void paneClick() {
    }

    // @FXML
    // private void mouseEntered() {
    //     // Node source = (Node)e.getSource();
    //     // Integer colIndex = GridPane.getColumnIndex(source);
    //     // Integer rowIndex = GridPane.getRowIndex(source);
    //     // System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
    //     System.out.println("Hei!");
    // }

    

    


}