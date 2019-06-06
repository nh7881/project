package yut.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import yut.utils.SettingUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/6
 * Time: 14:54
 * Description: TODO
 */
public class StartDialogController implements BaseController {

    @Setter
    private Stage dialogStage;

    @Getter
    private boolean startAllow;

    @Getter
    private int playerCountNum;

    @Getter
    private int markerCountNum;

    @FXML
    private TextField playerCount;

    @FXML
    private TextField markerCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * startBtn click event
     *
     * @param event
     */
    public void startGame(ActionEvent event) {
        startAllow = false;
        int playerCountNum = Integer.parseInt(playerCount.getText());
        int markerCountNum = Integer.parseInt(markerCount.getText());

        int playerMaxCountNum = Integer.parseInt(SettingUtil.getProperty("playerMaxCount"));
        int markerMaxCountNum = Integer.parseInt(SettingUtil.getProperty("markerMaxCount"));

        if (playerCountNum > playerMaxCountNum || markerCountNum > markerMaxCountNum) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Exceed the limit");
            alert.setContentText("The number of players or markers exceeds the limit");
            alert.showAndWait();
        } else {
            startAllow = true;
            this.playerCountNum = playerCountNum;
            this.markerCountNum = markerCountNum;
            this.dialogStage.close();
        }
    }
}
