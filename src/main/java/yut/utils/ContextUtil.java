package yut.utils;

import javafx.scene.control.Alert;
import yut.YutGameApp;
import yut.model.Marker;
import yut.model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/7
 * Time: 20:09
 * Description:
 * this class store context datas whole application
 */
public class ContextUtil {
    public static Map<ContextKey, Object> datas = new LinkedHashMap<>();

    public static Object getData(ContextKey key) {
        return datas.get(key);
    }

    public static void setData(ContextKey key, Object value) {
        datas.put(key, value);
    }

    public static Marker getCurrentMarker() {
        return (Marker) getData(ContextUtil.ContextKey.CURRENT_MARKER);
    }

    public static void setCurrentMarker(Object value) {
        Marker marker = (Marker) value;
        if(!marker.getOwnPlayer().equals(getCurrentPlayer())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(YutGameApp.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Please choose your own marker!");
            alert.showAndWait();
            return;
        }
        datas.put(ContextUtil.ContextKey.CURRENT_MARKER, value);
    }

    public static Player getCurrentPlayer() {
        return (Player) getData(ContextUtil.ContextKey.CURRENT_PLAYER);
    }

    public static void setCurrentPlayer(Object value) {
        datas.put(ContextUtil.ContextKey.CURRENT_PLAYER, value);
    }

    public enum ContextKey {
        //is started
        IS_START,
        //current player
        CURRENT_PLAYER,
        //current marker
        CURRENT_MARKER;
    }
}
