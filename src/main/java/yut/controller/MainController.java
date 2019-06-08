package yut.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import yut.YutGameApp;
import yut.model.Marker;
import yut.utils.ContextUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainController implements BaseController {

    @Getter
    @FXML
    private UserBoardController userBoardController;

    @FXML
    private GameBoardController gameBoardController;

    ExecutorService executorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userBoardController.init(this);
        gameBoardController.init(this);
    }

    private Marker currentMarker;

    /**
     * start a new game
     *
     * @param event
     */
    public void startNewGame(ActionEvent event) {
        //invoke StartDialog
        ContextUtil.setData(ContextUtil.ContextKey.IS_START, false);
        StartDialogController controller = invokeStartDialog();

        //shutdown old thread pool
        if (this.executorService != null) {
            this.executorService.shutdownNow();
        }

        if (controller.isStartAllow()) {
            ContextUtil.setData(ContextUtil.ContextKey.IS_START, true);

            //init game data
            int playerCountNum = controller.getPlayerCountNum();
            int markerCountNum = controller.getMarkerCountNum();
            userBoardController.initPlayer(playerCountNum, markerCountNum);

            //set current player is play1
            ContextUtil.setCurrentPlayer(userBoardController.getPlayerList().get(0));

            Runnable gameRule = () -> {
                while (true) {
                    String processStateText = String.format("Play %d's Turn!!", ContextUtil.getCurrentPlayer().getId() + 1);

                    Platform.runLater(() -> userBoardController.setProcessStateText(processStateText));

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            Runnable gameGridTip = () -> {

            };

            this.executorService = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
            this.executorService.submit(gameRule);
//            this.executorService.submit(gameGridTip);
        }
    }

    private StartDialogController invokeStartDialog() {
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

        return controller;
    }


    //algorithm implement
    public void playGame() {
        /*currentMarker = ContextUtil.getCurrentMarker();
        //(userBoardController.getAllScore().get(0) == -1)
        System.out.println("run  ");
        for(int  i = 0;  i < ContextUtil.getCurrentPlayer().getMarkerList().size(); i++)
        {
            if(ContextUtil.getCurrentPlayer().getMarkerList().get(i).getIndex() < 0
                    && userBoardController.getAllScore().get(0) == -1) {
                userBoardController.removeScoreImageView(-1);
                //turn terminate
            }
        }*/
        //userBoardController.mainController.gameBoardController.

//        userBoardController.getPlayerList();
//        userBoardController.getAllScore();
//        userBoardController.setProcessStateText("asdasd");
//        userBoardController.removeScoreImageView(3);
//        userBoardController.removeMarkerBtn(currentMarker);
//        userBoardController.addMarkerBtn(eatenMarker);
    }
}
