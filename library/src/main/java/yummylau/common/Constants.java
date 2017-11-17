package yummylau.common;

import java.nio.charset.Charset;

/**
 * Created by g8931 on 2017/11/14.
 */

public class Constants {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public final static int MAX_AGE = 10;
    public final static int MAX_STALE = 60;

    public final static int READ_TIME_OUT = 30;
    public final static int WRITE_TIME_OUT = 30;
    public final static int CONNECT_TIME_OUT = 15;
}
