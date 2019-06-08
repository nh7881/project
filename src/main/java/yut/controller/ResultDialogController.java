package yut.controller;

import javafx.fxml.FXML;
import yut.model.Player;
import yut.utils.ContextUtil;

import java.awt.*;
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
public class ResultDialogController implements BaseController {

    @FXML
    private Label playerId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void endGame(Player player) {
        //end game first
        ContextUtil.setData(ContextUtil.ContextKey.IS_START, false);

        playerId.setText(String.valueOf(player.getId()));
    }
}

