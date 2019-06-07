package yut.controller;

import javafx.fxml.FXML;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import yut.YutGameApp;

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
     * 11   20             21    4
     * <p>
     * 12       22     23        3
     * <p>
     * 13          24            2
     * <p>
     * 14     25         26      1
     * <p>
     * 27                28
     * <p>
     * 15   16   17   18    19   0
     */
    List<ImageView> gameGridList = new ArrayList<>(29);
    
    List<Player> player_list = new ArrayList<Player>();
	List<Yut> yut_list = new ArrayList<Yut>();

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
            gameGrid.setOnMouseReleased(event -> System.out.println(((ImageView) event.getSource()).getId()));
            gameGridList.add(gameGrid);
        }
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
                AnchorPane.setTopAnchor(gameGrid, 100.0);
                AnchorPane.setLeftAnchor(gameGrid, 90.0);
            } else if (gridId == 21) {
                AnchorPane.setTopAnchor(gameGrid, 100.0);
                AnchorPane.setRightAnchor(gameGrid, 90.0);
            } else if (gridId == 22) {
                AnchorPane.setTopAnchor(gameGrid, 160.0);
                AnchorPane.setLeftAnchor(gameGrid, 150.0);
            } else if (gridId == 23) {
                AnchorPane.setTopAnchor(gameGrid, 160.0);
                AnchorPane.setRightAnchor(gameGrid, 150.0);
            } else if (gridId == 24) {
                AnchorPane.setTopAnchor(gameGrid, 220.0);
                AnchorPane.setRightAnchor(gameGrid, 210.0);
            } else if (gridId == 25) {
                AnchorPane.setTopAnchor(gameGrid, 280.0);
                AnchorPane.setLeftAnchor(gameGrid, 150.0);
            } else if (gridId == 26) {
                AnchorPane.setTopAnchor(gameGrid, 280.0);
                AnchorPane.setRightAnchor(gameGrid, 150.0);
            } else if (gridId == 27) {
                AnchorPane.setTopAnchor(gameGrid, 340.0);
                AnchorPane.setLeftAnchor(gameGrid, 90.0);
            } else if (gridId == 28) {
                AnchorPane.setTopAnchor(gameGrid, 340.0);
                AnchorPane.setRightAnchor(gameGrid, 90.0);
            }

            //set road sign image
            this.setRoadSignImage(gameGrid);
        });
    }

    public void setRoadSignImage(ImageView gameGrid) {
        int gridId = Integer.parseInt(gameGrid.getId());

        if (gridId == 0 || gridId == 5 || gridId == 10 || gridId == 15 || gridId == 24) {
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
                case 24:
                    gameGrid.setRotate(45);
                    break;
            }
        }
    }
    public int throw_yut(List<Yut> yut_list) {
    	int yut_go = 0;
    	Random rand = new Random();
    	boolean back_do = false;
    	for(int i = 0; i<Integer.parseInt(SettingUtil.getProperty("yutCount")); i++) {
    		Yut temp_yut = yut_list.get(i); 
    		temp_yut.isback = rand.nextBoolean();
    		if (temp_yut.isback == true) {
    			if (temp_yut.back_do == true) {
    				back_do = true;
    			}
    			yut_go = yut_go + 1;
    		}    		
    	}
    	if ( (back_do == true) && (yut_go == 1)) return -1;
    	else if (yut_go == 0) return 5;
    	else return yut_go;
    }
}