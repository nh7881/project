package yut.model;

import lombok.Data;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/6
 * Time: 15:02
 * Description: TODO
 */
@Data
public class Marker {
    // id for each marker
    private Integer id;

    //index for this marker in the game board index
    //init index is -1
    private Integer index;

    private Player ownPlayer;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Marker marker = (Marker) o;
        return Objects.equals(id, marker.id) && Objects.equals(ownPlayer, marker.ownPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownPlayer);
    }
}
