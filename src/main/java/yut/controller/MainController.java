package yut.controller;

import javafx.event.ActionEvent;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        dialogStage.setTitle("New Game");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller.
        StartDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isStartAllow();
    }
}
