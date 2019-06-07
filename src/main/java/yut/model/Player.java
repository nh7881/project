package yut.model;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/6
 * Time: 14:59
 * Description: TODO
 */
public class Player {
	public Player(Object marker_shape ,int marker_num) {
		List<Marker> marker_list = new ArrayList<Marker>();
    	for(int i = 0; i < marker_num; i++) {
    		marker_list.add(new Marker(marker_shape));
    	}
	}
}
