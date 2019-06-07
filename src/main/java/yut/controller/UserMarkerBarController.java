package yut.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import lombok.Setter;
import yut.model.Marker;
import yut.model.Player;
import yut.utils.SettingUtil;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
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

        //get player animal icon
        String playerAnimal = SettingUtil.getProperty("player" + player.getId() + "Animal");

        //render marker
        this.getPlayer().getMarkerList().forEach(marker -> {
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
                System.out.println(((Marker) eventBtn.getUserData()).getId());
            });
            markerHBox.getChildren().add(button);
        });
    }
}
