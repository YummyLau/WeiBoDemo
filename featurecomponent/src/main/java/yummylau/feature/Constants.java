package yummylau.feature;

/**
 * 常量
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class Constants {

    public static final String ROUTER_MAIN = "/feature/mainActivity/";

    //links
    public static final String A_TAG = "<show>([\\s\\S]+?)</show>";
    public static final String SHOW_ASSERT_TAG = "(?<=(<show>))([\\s\\S]+?)(?=(</show>))";
}
