package yut.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import yut.YutGameApp;
import yut.model.Marker;
import yut.model.Player;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/6
 * Time: 0:54
 * Description: TODO
 */
public class UserBoardController implements BaseController {

    @Getter
    private List<Player> playerList = new ArrayList<>();

    /**
     * each player have own userMarkerBarController
     */
    private Map<Player, UserMarkerBarController> userMarkerBarControllerMap = new LinkedHashMap<>();

    @FXML
    private VBox userMarkerBox;

    @FXML
    private Label processState;

    @FXML
    private HBox scoreHBox;

    @FXML
    private Button throwBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set throw button style when was clicked
        throwBtn.addEventHandler(MouseDragEvent.MOUSE_PRESSED, event -> {
            Button button = (Button) event.getSource();
            button.setStyle("-fx-background-color: #56AFC1; -fx-border-color: yellow; -fx-border-width: 5; -fx-background-radius: 8; -fx-border-radius: 8;");
        });
        throwBtn.addEventHandler(MouseDragEvent.MOUSE_RELEASED, event -> {
            Button button = (Button) event.getSource();
            button.setStyle("-fx-background-color: #56AFC1; -fx-border-color: #246E88; -fx-border-width: 5; -fx-background-radius: 8; -fx-border-radius: 8;");
        });
    }


    /**
     * init all
     *
     * @param playerCount
     * @param markerCount
     * @return
     */
    public List<Player> initPlayer(int playerCount, int markerCount) {
        // remove all elements of playerList
        this.getPlayerList().clear();

        //add player
        for (int i = 0; i < playerCount; i++) {
            Player player = new Player();
            player.setId(i);
            this.getPlayerList().add(player);
        }

        //add markers for each player
        for (int i = 0; i < playerCount; i++) {
            this.getPlayerList().forEach(player -> {
                List<Marker> markerList = new ArrayList<>();
                for (int j = 0; j < markerCount; j++) {
                    Marker marker = new Marker();
                    marker.setId(j);
                    marker.setIndex(-1);
                    marker.setOwnPlayer(player);
                    markerList.add(marker);
                }
                player.setMarkerList(markerList);
            });
        }

        //render user marker bar
        renderUserMarkerBar();
        return this.getPlayerList();
    }

    /**
     * set process state lable text
     *
     * @param stateText
     */
    public void setProcessStateText(String stateText) {
        processState.setText(stateText);
    }


    private void renderUserMarkerBar() {
        this.getPlayerList().forEach(player -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(YutGameApp.class.getResource("/view/UserMarkerBar.fxml"));
                AnchorPane page = loader.load();
                userMarkerBox.getChildren().add(page);

                //save controller for each player
                UserMarkerBarController controller = loader.getController();
                controller.setPlayer(player);
                controller.render();

                userMarkerBarControllerMap.put(player, controller);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
