package yut.utils;

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

    public static void saveData(ContextKey key, Object value) {
        datas.put(key, value);
    }

    public enum ContextKey {
        IS_START;
    }
}
