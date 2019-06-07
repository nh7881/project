package yut.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yut.YutGameApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements BaseController {

    @FXML
    private UserBoardController userBoardController;

    @FXML
    private GameBoardController gameBoardController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameBoardController.renderGameGrid();
    }

    /**
     * start a new game
     *
     * @param event
     */
    public void startNewGame(ActionEvent event) {
        //invoke StartDialog
        if (invokeStartDialog()) {
            //init game data

        }
    }

    private boolean invokeStartDialog() {
        FXMLLoader loader = null;
        AnchorPane page = null;
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            loader = new FXMLLoader();
            loader.setLocation(YutGameApp.class.getResource("/view/StartDialog.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.initOwner(YutGameApp.getPrimaryStage());
        dialogStage.setTitle("New Game");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);

        dialogStage.setScene(scene);
        StartDialogController controller = loader.getController();

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isStartAllow();
    }
//    private List<Yut> makeyut() {
//
//    	List<Yut> yut_list = new ArrayList<Yut>();
//    	for(int i = 0; i < Integer.parseInt(SettingUtil.getProperty("yutCount")); i++) {
//    		if(i == 0) yut_list.add(new Yut(true));
//    		else yut_list.add(new Yut(false));
//    	}
//    	return yut_list;
//    }
}
