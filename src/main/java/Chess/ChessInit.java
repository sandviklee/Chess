package Chess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Chess.Chessboard.Chessboard;
import Chess.Chessboard.IO;
import Chess.Chessboard.PiecePlacer;
import Chess.Exceptions.GameEndedException;
import Chess.Pieces.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/* This is where the "Main" stuff is gonna go on, where the most important functions like updating the game
while moving or initializing save methods, drawStates etc. This is the Initialization of the chess game, for both
gameplay wise and interface. Therefore, this object is made when you make a new chessgame.*/

public class ChessInit {
  // FIELDS
  private Pane[][] paneArray;
  private List<Integer> mouseposlist = new ArrayList<>();
  private ImageView draggable;
  private int pieceOutY = 0;
  private int pieceOutX = 0;
  private int msecupdate = 10;
  private Pane piecesOutPane;
  private ArrayList<BasePiece> piecesList = new ArrayList<>();
  private List<String> piecesOutList = new ArrayList<>();
  private long fps = 60_000_000; //60 fps.
  private String nameW = "PLAYER1";
  private String nameB = "PLAYER2";
  private Chessboard chessboard; //Public because its used directly.
  private Move ChessMove;
  private CheckGameState checkGameState;
  
  // CONSTRUCTOR
  public ChessInit(Chessboard chessboard) {
    this.chessboard = chessboard;
    this.ChessMove = new Move(chessboard);
    this.checkGameState = new CheckGameState(chessboard, ChessMove);
    this.piecesOutList = PiecePlacer.IOload.getPiecesOut();
    if (chessboard == null) {
      throw new NullPointerException();
    } 
  }

  // GROUP FOR ALL IMAGES ON THE CHESSBOARD
  private Group root = new Group();

  public Group getRoot() {
    return root;
  }

  // GROUP FOR IMAGES: ALL PIECES OUT
  private Group piecesOutGroup = new Group();

  // Method for setting piecesOut pane.
  public void setPane(Pane pane) {
    this.piecesOutPane = pane;
  }

  public ArrayList<BasePiece> getpiecesList() {
    return piecesList;
  }

  public String getPlayer1Name() {
    return nameW;
  }

  public String getPlayer2Name() {
    return nameB;
  }

  public Chessboard getChessboard() {
    return chessboard;
  }

  public Move getMove() {
    return ChessMove;
  }

  public CheckGameState getCheckGameState() {
    return checkGameState;
  }

  /*Code for adding knocked out pieces to a pane*/
  protected int done = -1;
  boolean statement = true;
  private void updatePiecesOut(Pane pane) throws FileNotFoundException {
    if (piecesOutList != null && !(piecesOutList.size() - 1 == 0) && !ChessMove.getKnockedOut()) {
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
        if (done == -1) {
          ImageView.setScaleX(0.30);
          ImageView.setScaleY(0.30);
          ImageView.setX(pieceOutX*25 - 10);
          ImageView.setY(pieceOutY*25 - 10);
          
          piecesOutGroup.getChildren().addAll(ImageView);

          if ((piecesOutGroup.getChildren().size())%8 == 0) {
            pieceOutY++;
            pieceOutX = 0;
          } else {
            pieceOutX++;
          }
        }
      }
      pane.getChildren().addAll(piecesOutGroup);
      done++;
    }

    BasePiece piece = null;
    ImageView ImageView = new ImageView();
    
    if (!ChessMove.getPiecesOut().isEmpty()) {
      piece = ChessMove.getPiecesOut().get(0);
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

      statement = piecesList.size() >= 2;
      if (piecesOutList != null) {
        statement = piecesOutList.size() > 0;
      }

      if (statement) {
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
            if ((pieceOutX + 1)%8 == 0) {
              pieceOutY++;
              pieceOutX = 0;
            } else {
              pieceOutX++;
            }
            
          }
        } else {
          piecesOutGroup.getChildren().addAll(ImageView);
          pieceOutX++;
        }
      
      pane.getChildren().addAll(piecesOutGroup);
      
      }
    }
  
  private int gameStart = -1; //private because it is only used by this class.
  public void ChessPlay() throws IOException {
    if (gameStart != 0) {
      try {
        checkGameState.inCheck();
        checkGameState.inCheckMate();
        gameStart++;

        if (!(IO.getPawnDoubleList().size() == 0)) {
          int i = 0;
          for (ArrayList<BasePiece> row : chessboard.getChessboardState()) {
            for (BasePiece basePiece : row) {
              if (basePiece instanceof Pawn) {
                basePiece.pawnDoubleMove = IO.getPawnDoubleList().get(i);
                if (i < IO.getPawnDoubleList().size() -1) {
                  i++;
                }
              }
            }
          }
        } 
      } catch (FileNotFoundException e1) {
        System.out.println("Initializing failed.");
        e1.printStackTrace();
        }
      
    }
    System.out.println("Initializing complete!");

    root = new Group();
    root.getChildren().add(chessboard.ChessboardView());
    root.getChildren().addAll(chessboard.MatrixToFXML());
    root.getChildren().add(FXMLLoader.load(ChessApp.class.getResource("Chessboard.fxml")));
    updatePiecesOut(piecesOutPane); //To show all piecesOut

    /*Here i create a Thread to wait for an update to the chessboard, if the chessboard has been
    updated, it will run ChessPlayUpdate() and updatePiecesOut()*/

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        Runnable updater = new Runnable() {
          @Override
          public void run() {
            try {
              ChessPlayUpdate(root);
              updatePiecesOut(piecesOutPane);
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

  //This is a method to update the GUI on the chessboard, if a piece has been moved. 
  private void ChessPlayUpdate(Group g) throws FileNotFoundException {
    g.getChildren().clear();
    g.getChildren().add(chessboard.ChessboardView());

    g.getChildren().addAll(chessboard.MatrixToFXML());
    try {
      g.getChildren().add(FXMLLoader.load(ChessApp.class.getResource("Chessboard.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //This is initializing how the chessboard will be. This is initialized by the controller, because it has the gridpane.
  public void ChessPlaySetup(GridPane gridPane) {
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
            //System.out.println("x: " + yaxis + " y: " + xaxis);
            GrayHover(yaxis, xaxis);
          });
          pane.setOnMouseExited(e -> {
            RemoveGrayHover(yaxis, xaxis);
          });
          
          pane.setOnMouseClicked(e -> { //Lambda Eventhandler MouseEvent
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
              /* Here i initialize an AnimationTimer, so that when you click on the piece, you can
              see that the piece image is updated to your mousepointers coordinates */
              new AnimationTimer() {
                private long tick = 0;
                @Override
                public void handle(long now) {
                  if (now - tick >= fps) { 
                    if (draggable != null) {
                      gridPane.setOnMouseMoved(event -> {
                        if (piece != null && !checkGameState.getCheckMate()) {
                          try {
                            availPattern(boardpiece, yaxis, xaxis);
                          } catch (Exception e) {
                          }
                        }
                        draggable.setX(event.getSceneX() - 32);
                        draggable.setY(event.getSceneY() - 32);
                      });
                    }
                  }
                  tick = now;
                }
              }.start();

            } else if (mouseposlist.size() == 2) {
                mouseposlist.add(yaxis);
                mouseposlist.add(xaxis);
                ChessMove.MovePiece(mouseposlist.get(0), mouseposlist.get(1), mouseposlist.get(2), mouseposlist.get(3));
                mouseposlist.clear();
            
                try {
                  checkGameState.inCheck();
                  checkGameState.inCheckMate();
                  checkGameState.inDraw();
                  checkGameState.setgameStateAlert(); 
                } catch (FileNotFoundException e1) {
                  e1.printStackTrace();
                }
            }                
        });  
      }
    }
  }

  //This is initializing how the chessboard will be. This is initialized by the controller, because it has the gridpane.
  public void ChessPlayInitialize(GridPane grid, Pane pane, Label player1, Label player2) {
    ChessPlaySetup(grid);
    setPane(pane);

    nameW = "PLAYER1";
    nameB = "PLAYER1";
    
    if (MainmenuController.player1Name != null) {
        nameW = MainmenuController.player1Name;
        player1.setText(nameW);
        
    } else {
        if (IO.getPlayer1NameIO() != null) {
            nameW = IO.getPlayer1NameIO();
            player1.setText(nameW);
            
        } else {
            nameW = "PLAYER1";
            player1.setText(nameW);
        }
    }

    if (MainmenuController.player2Name != null) {
        nameB = MainmenuController.player2Name;
        player2.setText(nameB);
        
    } else {
        if (IO.getPlayer2NameIO() != null) {
            nameB = IO.getPlayer1NameIO();
            player2.setText(nameB);
   
        } else {
            nameB = "PLAYER2";
            player2.setText(nameB);
        }
    }
    checkGameState.setPlayerWName(nameW);
    checkGameState.setPlayerBName(nameB);
  }

  private boolean drawOffer = false; //protected because this is the only class that uses it.
  public void drawState() throws GameEndedException {
    if (!checkGameState.getDraw()) {
      if (!ChessMove.getGameOver()) {
          if (ChessMove.getWhiteTurn()) {
            checkGameState.AlertGameState("White offers DRAW!", "White is in a pickle! Black, do you want to draw? (Claim Draw)", AlertType.WARNING);
          } else if (ChessMove.getBlackTurn()) {
            checkGameState.AlertGameState("Black offers DRAW!", "Black is in a pickle! White, do you want to draw? (Claim Draw)", AlertType.WARNING);
          }
          drawOffer = true;
      } else {
        throw new GameEndedException("You cannot offer draw when the game has ended.");
      }
    }
  }

  public void drawnState() {
    if (drawOffer) {
      checkGameState.setDraw(true);
      checkGameState.AlertGameState("Draw!", "This game ended in draw!", AlertType.INFORMATION);
      ChessMove.setGameOver(true);
    }
  }
  
  public void saveGameState(IO IOsave) throws GameEndedException {
    if (!ChessMove.getGameOver()) {
      try {
          //Here i use a FileChooser to open the OS explorer, so that you can save a file. FileChooser uses my own IO save method from the Class IO. 
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Save");
          fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text files", "*.txt"));
          fileChooser.setInitialDirectory(new File("src/main/resources/Chess/SaveFiles"));
          File file = fileChooser.showSaveDialog(MainmenuController.primaryStage);
          IOsave.save(file, this);
          
      } catch (FileNotFoundException e) {
          System.out.println("Du kan ikke lagre grunnet: " + e);
      }
  } else {
      throw new GameEndedException("You cannot save when the game has ended.");
    }
  }

  //Shows the validated patterns made from the Move class as green squares.
  private void availPattern(BasePiece piece, int x, int y) {
    if (!ChessMove.getGameOver()) {
      if ((piece.getPieceColor() == 'w' && ChessMove.getWhiteTurn()) || (piece.getPieceColor() == 'b' && ChessMove.getBlackTurn()))  {
        for (ArrayList<Integer> pos : ChessMove.getValidatedPattern(x, y)) {
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
