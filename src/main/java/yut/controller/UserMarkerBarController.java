package yut.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import lombok.Setter;
import yut.model.Marker;
import yut.model.Player;
import yut.utils.ContextUtil;
import yut.utils.SettingUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/6
 * Time: 14:52
 * Description: TODO
 */
public class UserMarkerBarController implements BaseController {

    @Getter
    @Setter
    private Player player;

    @FXML
    private Label userNoLab;

    @FXML
    private HBox markerHBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //render player and marker
    public void render() {
        //render player
        userNoLab.setText(String.valueOf(this.getPlayer().getId() + 1));

        //render marker
        this.getPlayer().getMarkerList().forEach(this::addMarkerBtn);
    }

    /**
     * @param marker
     */
    public void addMarkerBtn(Marker marker) {
        //get player animal icon
        String playerAnimal = SettingUtil.getProperty("player" + player.getId() + "Animal");

        Button button = new Button();
        button.setPrefHeight(33.0);
        button.setPrefWidth(33.0);
        button.setCursor(Cursor.HAND);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setStyle("-fx-background-image: url(/res/marker/" + playerAnimal + ".png); -fx-background-position: center center; -fx-background-size: stretch stretch;");

        //set marker object for each marker button
        button.setUserData(marker);

        //handle marker btn clicked event
        button.setOnAction(event -> {
            Button eventBtn = (Button) event.getSource();
            Marker eventMarker = (Marker) eventBtn.getUserData();

            //save current marker
            ContextUtil.setCurrentMarker(eventMarker);
            System.out.println("current player: " + eventMarker.getOwnPlayer().getId() +", marker:" + marker.getId());
        });
        markerHBox.getChildren().add(button);
    }

    /**
     * @param marker
     */
    public void removeMarkerBtn(Marker marker) {
        for (Node node : markerHBox.getChildren()) {
            if (node.getUserData().equals(marker)) {
                markerHBox.getChildren().remove(node);
                return;
            }
        }
    }
}
