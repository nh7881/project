package yut.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/6
 * Time: 0:54
 * Description: TODO
 */
public class UserBoardController implements BaseController {

    @FXML
    private Button throwBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set throw button style when was clicked
        throwBtn.addEventHandler(MouseDragEvent.MOUSE_PRESSED,event -> {
            Button button = (Button) event.getSource();
            button.setStyle("-fx-background-color: #56AFC1; -fx-border-color: yellow; -fx-border-width: 5; -fx-background-radius: 8; -fx-border-radius: 8;");
        });
        throwBtn.addEventHandler(MouseDragEvent.MOUSE_RELEASED,event -> {
            Button button = (Button) event.getSource();
            button.setStyle("-fx-background-color: #56AFC1; -fx-border-color: #246E88; -fx-border-width: 5; -fx-background-radius: 8; -fx-border-radius: 8;");
        });
    }
}
