package yut.controller;

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

    private  boolean endAllow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public int endGame(int playerId) {
        endAllow = false;
        int winnerPlayer = playerId;
        return winnerPlayer;
    }

}

