package yummylau.common.net;

/**
 * code状态码
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class HttpCode {

    //错误代码
    public int code;

    //错误信息
    public String msg;

    //详细描述
    public String detail;

    public HttpCode() {
    }

    public HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HttpCode(int code, String msg, String detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }
}
