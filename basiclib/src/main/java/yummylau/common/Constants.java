package yummylau.common;

import java.nio.charset.Charset;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class Constants {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public final static int MAX_AGE = 10;
    public final static int MAX_STALE = 60;

    public final static int READ_TIME_OUT = 30;
    public final static int WRITE_TIME_OUT = 30;
    public final static int CONNECT_TIME_OUT = 15;
}
