package yut.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import yut.YutGameApp;
import yut.model.Marker;
import yut.utils.ContextUtil;
import yut.utils.SettingUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.Cursor.HAND;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/6
 * Time: 0:54
 * Description: TODO
 */
public class GameBoardController implements BaseController {
    /**
     * have 29 game grids in all
     * <p>
     * 10    9    8    7    6    5
     * <p>
     * 11   71             51    4
     * <p>
     * 12       72     52        3
     * <p>
     * 13          73            2
     * <p>
     * 14     53         74      1
     * <p>
     * 54                 75
     * <p>
     * 15   16   17   18    19   0
     */
    List<ImageView> gameGridList = new ArrayList<>(29);

    MainController mainController;

    int winner;

    @FXML
    private AnchorPane gameGridPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        float width = 55;

        for (int i = 0; i < 29; i++) {
            ImageView gameGrid = new ImageView();
            gameGrid.setImage(new Image(YutGameApp.class.getResourceAsStream("/res/blue_marker.png")));
            gameGrid.setCursor(HAND);
            gameGrid.setId(String.valueOf(i));
            gameGrid.setFitWidth(width);
            gameGrid.setFitHeight(width);

            //mouse release event
            gameGrid.setOnMouseReleased(event -> {
                ImageView imageView = (ImageView) event.getSource();
                Marker marker = (Marker) imageView.getUserData();

                if (marker != null) {
                    System.out.println("marker data is " + marker.getId());

                    if (checkForward(ContextUtil.getCurrentMarker(), Integer.parseInt(imageView.getId()))) {
                        //eat other player's marker, make it go back user marker bar
                        this.mainController.getUserBoardController().addMarkerBtn(marker);
                    } else {
                        //set this marker is current marker, forward form here
                        ContextUtil.setCurrentMarker(marker);
                        return;
                    }
                }
                //forward in here
                if (checkForward(ContextUtil.getCurrentMarker(), Integer.parseInt(imageView.getId()))) {
                    forWard(ContextUtil.getCurrentMarker(), Integer.parseInt(imageView.getId()));
                }

                if (this.mainController.getUserBoardController().getAllScore().size() == 0) {
                    ContextUtil.setCurrentPlayer(this.mainController.getUserBoardController().getNextPlayer(ContextUtil.getCurrentMarker().getOwnPlayer()));
                }
            });
            gameGridList.add(gameGrid);
        }
    }

    public void init(MainController mainController) {
        this.mainController = mainController;
        this.renderGameGrid();
    }

    /**
     * render border line and game grid
     */
    public void renderGameGrid() {
        ImageView borderLine = new ImageView();
        borderLine.setImage(new Image(YutGameApp.class.getResourceAsStream("/res/board_lines.png")));
        borderLine.setCursor(HAND);
        borderLine.setFitWidth(gameGridPane.getPrefWidth() - 80);
        borderLine.setFitHeight(gameGridPane.getPrefWidth() - 80);
        gameGridPane.getChildren().add(borderLine);
        AnchorPane.setLeftAnchor(borderLine, 30.0);
        AnchorPane.setTopAnchor(borderLine, 40.0);

        gameGridList.forEach(gameGrid -> {
            gameGridPane.getChildren().add(gameGrid);
            int gridId = Integer.parseInt(gameGrid.getId());
            if (gridId < 6) {
                AnchorPane.setBottomAnchor(gameGrid, gridId * 82.0 + 10.0);
                AnchorPane.setRightAnchor(gameGrid, 6.0);
            } else if (gridId >= 6 && gridId < 11) {
                AnchorPane.setTopAnchor(gameGrid, 15.0);
                AnchorPane.setRightAnchor(gameGrid, (gridId - 5) * 82.0 + 6.0);
            } else if (gridId >= 11 && gridId < 16) {
                AnchorPane.setLeftAnchor(gameGrid, 7.0);
                AnchorPane.setTopAnchor(gameGrid, (gridId - 10) * 83.0 + 10.0);
            } else if (gridId >= 16 && gridId < 20) {
                AnchorPane.setBottomAnchor(gameGrid, 10.0);
                AnchorPane.setLeftAnchor(gameGrid, (gridId - 15) * 83.0 + 10.0);
            } else if (gridId == 20) {
                gameGrid.setId("71");
                AnchorPane.setTopAnchor(gameGrid, 100.0);
                AnchorPane.setLeftAnchor(gameGrid, 90.0);
            } else if (gridId == 21) {
                gameGrid.setId("72");
                AnchorPane.setTopAnchor(gameGrid, 160.0);
                AnchorPane.setLeftAnchor(gameGrid, 150.0);
            } else if (gridId == 22) {
                gameGrid.setId("73");
                AnchorPane.setTopAnchor(gameGrid, 220.0);
                AnchorPane.setRightAnchor(gameGrid, 210.0);
            } else if (gridId == 23) {
                gameGrid.setId("74");
                AnchorPane.setTopAnchor(gameGrid, 280.0);
                AnchorPane.setRightAnchor(gameGrid, 150.0);
            } else if (gridId == 24) {
                gameGrid.setId("75");
                AnchorPane.setTopAnchor(gameGrid, 340.0);
                AnchorPane.setRightAnchor(gameGrid, 90.0);
            } else if (gridId == 25) {
                gameGrid.setId("51");
                AnchorPane.setTopAnchor(gameGrid, 100.0);
                AnchorPane.setRightAnchor(gameGrid, 90.0);
            } else if (gridId == 26) {
                gameGrid.setId("52");
                AnchorPane.setTopAnchor(gameGrid, 160.0);
                AnchorPane.setRightAnchor(gameGrid, 150.0);
            } else if (gridId == 27) {
                gameGrid.setId("54");
                AnchorPane.setTopAnchor(gameGrid, 280.0);
                AnchorPane.setLeftAnchor(gameGrid, 150.0);
            } else if (gridId == 28) {
                gameGrid.setId("55");
                AnchorPane.setTopAnchor(gameGrid, 340.0);
                AnchorPane.setLeftAnchor(gameGrid, 90.0);
            }

            //set road sign image
            this.setRoadSignImage(gameGrid);
        });
    }

    // before mark move, position where mark will go change.
    public int changeMarker(int index) {
        if (index == 5) {
            return -45; //first diagonal
        } else if (index == 10) {
            return -60; //second diagonal
        }
        return 0;
    }

    // after mark move, position where mark gone change.
    public int changeTarget(int markerIndex, int targetIndex) {
        if (markerIndex > 50 && markerIndex < 56 && targetIndex > 14 && targetIndex < 20) {
            return 41; // third diagonal
        } else if ((markerIndex < 53 && markerIndex > 50 && targetIndex == 73) ||
                (markerIndex == 5 && targetIndex == 73)) {
            return -20; //center
        } else if ((markerIndex == 51 && targetIndex == 5) ||
                (markerIndex == 5 && targetIndex == 4)) {
            return 45;
        } else if ((markerIndex == 71 && targetIndex == 10) ||
                (markerIndex == 10 && targetIndex == 9)) {
            return 60;
        } else if (markerIndex == 0 && targetIndex == 75) {
            return -76;
        } else if (markerIndex == 54 && targetIndex == 73) {
            return -20;
        } else if (markerIndex < 20 && markerIndex >= 15 && targetIndex == 0) {
            return 20;
        } else if (markerIndex > 70 && targetIndex == 0) {
            return 76;
        }
        return 0;
    }

    // check marker finish
    public int checkMarkerEnd(int markerid, int targetid, int stepCount) {
        int i = 0;

        if (targetid == 0 && stepCount >= 0 && (markerid >= 15 || markerid == 0)) {
            for (i = 0; i < this.mainController.getUserBoardController().getAllScore().size(); i++) {
                if (stepCount < this.mainController.getUserBoardController().getAllScore().get(i)) {
                    return this.mainController.getUserBoardController().getAllScore().get(i);
                }
            }
        }
        return stepCount;
    }

    // change grid id and index
    public int changeDialog(int index) {
        int temp = index;
        if (index > 70 && index <= 75) {
            temp = temp - 51;
        } else if (index == 51 || index == 52) {
            temp = temp - 26;
        } else if (index == 54 || index == 55) {
            temp = temp - 27;
        }
        return temp;
    }

    /**
     * forward or back
     *
     * @param marker
     * @param targetIndex forward where, index of grid
     */
    public void forWard(Marker marker, int targetIndex) {
        int stepCount = targetIndex - marker.getIndex();

        // before move marker, change position
        stepCount = stepCount + changeMarker(marker.getIndex());
        // after move marker, change position
        stepCount = stepCount + changeTarget(marker.getIndex(), targetIndex);
        //can forward target
        ImageView start = gameGridList.get(changeDialog(marker.getIndex()));
        ImageView target = gameGridList.get(changeDialog(targetIndex));
        //reset grid image
        start.setUserData(null);
        start.setImage(new Image(YutGameApp.class.getResourceAsStream("/res/blue_marker.png")));
        this.setRoadSignImage(start);

        //check marker end
        if (checkMarkerEnd(marker.getIndex(), targetIndex, stepCount) != stepCount) { // when marker finish, discard first score.
            stepCount = checkMarkerEnd(marker.getIndex(), targetIndex, stepCount);
            target.setUserData(null);
            marker.setHasEnded(true);
        } else {
            //forward
            String playerAnimal = SettingUtil.getProperty("player" + marker.getOwnPlayer().getId() + "Animal");
            target.setImage(new Image(YutGameApp.class.getResourceAsStream("/res/marker/" + playerAnimal + ".png")));
            target.setRotate(0);

            //save all data
            target.setUserData(marker);
            marker.setIndex(targetIndex);
        }

        //delete button in user marker bar
        this.mainController.getUserBoardController().removeMarkerBtn(ContextUtil.getCurrentMarker());
        this.mainController.getUserBoardController().removeScoreImageView(stepCount);
        if (ContextUtil.getCurrentPlayer().isWin() == true) {
            winner = ContextUtil.getCurrentPlayer().getId();
            System.out.println("go final stage");
        }
    }

    public boolean checkForward(Marker marker, int targetIndex) {
        if (marker == null) {
            return false;
        }

        List<Integer> allScores = this.mainController.getUserBoardController().getAllScore();
        int stepCount = targetIndex - marker.getIndex();
        stepCount = stepCount + changeMarker(marker.getIndex());
        // after move marker, change position
        stepCount = stepCount + changeTarget(marker.getIndex(), targetIndex);
        //check marker finish

        if (checkMarkerEnd(marker.getIndex(), targetIndex, stepCount) != stepCount) {
            return true;
        }
        //can forward target
        return allScores.contains(stepCount);
    }

    private void setRoadSignImage(ImageView gameGrid) {
        int gridId = Integer.parseInt(gameGrid.getId());

        if (gridId == 0 || gridId == 5 || gridId == 10 || gridId == 15 || gridId == 73) {
            gameGrid.setImage(new Image(YutGameApp.class.getResourceAsStream("/res/arrow.png")));
            switch (gridId) {
                case 0:
                    gameGrid.setRotate(-90);
                    break;
                case 5:
                    gameGrid.setRotate(135);
                    break;
                case 10:
                    gameGrid.setRotate(45);
                    break;
                case 73:
                    gameGrid.setRotate(45);
                    break;
            }
        }
    }
}
