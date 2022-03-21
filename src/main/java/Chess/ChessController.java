package Chess;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class ChessController implements Initializable{
    @FXML private GridPane gridpane;
    @FXML private Button x0y0;
    private Pane[][] paneArray;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        paneArray = new Pane[8][8];

        for (int i = 0; i < 8; i++) {
            int IntegerI = i; //Making the y coordinate static
            for (int j = 0; j < 8; j++) {
                Pane pane = new Pane();
                int IntegerJ = j; //Making the x coordinate static
                pane.setOnMouseClicked(e -> { System.out.println("y: " + IntegerI + " x: " + IntegerJ);});
                paneArray[i][j] = pane;
                gridpane.add(pane, i, j);
            }
        }
    }    

    @FXML
    public void BlackClick() {
        System.out.println("DU sug");
        changeColorToBlack(paneArray[1][1]);
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