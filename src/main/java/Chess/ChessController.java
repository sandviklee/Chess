package Chess;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class ChessController implements Initializable{
    @FXML private GridPane gridPane;
    @FXML private Button x0y0;
    private Pane[][] paneArray;
    public static List<Integer> mouseposlist = new ArrayList<>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        paneArray = new Pane[8][8];

        for (int i = 0; i < 8; i++) {
            int IntegerI = i; //Making the y coordinate static
            for (int j = 0; j < 8; j++) {
                int IntegerJ = j; //Making the x coordinate static
                Pane pane = new Pane();
                paneArray[i][j] = pane;
                gridPane.add(pane, IntegerI, IntegerJ);
                pane.setOnMouseClicked(e -> { System.out.println("FIRST y: " + IntegerJ + " x: " + IntegerI);
                if (mouseposlist.size() < 2) {
                    mouseposlist.add(IntegerI);
                    mouseposlist.add(IntegerJ);
                    BlackClick(mouseposlist.get(0), mouseposlist.get(1));
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

    @FXML
    public void BlackClick(int x, int y) {
        changeColorToBlack(paneArray[x][y]);
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