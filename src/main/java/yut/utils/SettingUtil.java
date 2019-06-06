package yut.utils;

import yut.YutGameApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA
 *
 * @author xiaohun
 * Date: 2019/6/6
 * Time: 15:49
 * Description: TODO
 */
public class SettingUtil {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(YutGameApp.class.getResourceAsStream("/setting.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        Optional<String> value = Optional.ofNullable(properties.getProperty(key));
        value.orElseThrow(() -> new IllegalArgumentException("Have no any value for given key!"));
        return value.get();
    }
}
