package yut.model;

import lombok.Data;

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
    private int id;

    //index for this marker in the game board index
    private int index;
}
