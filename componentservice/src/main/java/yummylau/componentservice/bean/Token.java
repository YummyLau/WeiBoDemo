package yummylau.componentservice.bean;

/**
 * 全局共享token
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class Token {

    public int uid;
    public String accessToken;
    public String refreshToken;
    public long expiresTime;        //accessToken生命周期
    public String phoneNum;

}
