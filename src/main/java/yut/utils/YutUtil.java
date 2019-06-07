package yut.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * Created with IntelliJ IDEA
 * User: wangyong
 * Date: 2019-06-07
 * Time: 15:23
 * Description:
 */
public class YutUtil {

    /**
     * throw Yut then return a score for user forward or back
     *
     * @return score
     */
    public static int throwYut() {
        int score = RandomUtil.randomInt(1, 7);
        return score == 6 ? -1 : score;
    }
}
