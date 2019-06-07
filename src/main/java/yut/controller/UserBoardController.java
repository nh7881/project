package yut.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import yut.YutGameApp;
import yut.model.Marker;
import yut.model.Player;
import yut.utils.ContextUtil;
import yut.utils.YutUtil;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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

    MainController mainController;

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

    public void init(MainController mainController) {
        this.mainController = mainController;
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
                    marker.setIndex(0);
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
     * @param marker
     */
    public void addMarkerBtn(Marker marker) {
        Player player = marker.getOwnPlayer();

        //get player own userMarkerBar
        userMarkerBarControllerMap.get(player).addMarkerBtn(marker);
    }

    /**
     * @param marker
     */
    public void removeMarkerBtn(Marker marker) {
        Player player = marker.getOwnPlayer();
        //get player own userMarkerBar
        userMarkerBarControllerMap.get(player).removeMarkerBtn(marker);
    }

    /**
     * get all score for this
     */
    public List<Integer> getAllScore() {
        return scoreHBox.getChildren().stream()
                .map(image -> (Integer) image.getUserData())
                .collect(Collectors.toList());
    }

    /**
     * remove a score imageView form scoreHBox
     *
     * @param score
     */
    public void removeScoreImageView(int score) {
        for (Node node : scoreHBox.getChildren()) {
            if (node.getUserData().equals(score)) {
                scoreHBox.getChildren().remove(node);
                return;
            }
        }
    }

    /**
     * set process state lable text
     *
     * @param stateText
     */
    public void setProcessStateText(String stateText) {
        processState.setText(stateText);
    }

    public Player getNextPlayer(Player player) {
        int index = this.getPlayerList().indexOf(player);
        if (index + 1 > this.getPlayerList().size() - 1) {
            return this.getPlayerList().get(0);
        } else {
            return this.getPlayerList().get(index + 1);
        }
    }

    /**
     * throw button click event
     *
     * @param event
     */
    public void handleThrow(ActionEvent event) {
        if (!(Boolean) ContextUtil.getData(ContextUtil.ContextKey.IS_START)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(YutGameApp.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Please start game first!");
            alert.showAndWait();
            return;
        }

        int score = YutUtil.throwYut();
        ImageView imageView = new ImageView();
        imageView.setFitWidth(50.0);
        imageView.setFitHeight(50.0);
        imageView.setImage(new Image(YutGameApp.class.getResourceAsStream("/res/score/circle" + score + ".png")));
        imageView.setUserData(score);
        scoreHBox.getChildren().add(imageView);
    }

    private void renderUserMarkerBar() {
        //clear first
        userMarkerBarControllerMap.clear();
        scoreHBox.getChildren().clear();
        userMarkerBox.getChildren().clear();

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
