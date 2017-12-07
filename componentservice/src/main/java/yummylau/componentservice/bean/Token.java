package yummylau.componentservice.bean;

/**
 * 全局共享token
 * Created by g8931 on 2017/11/29.
 */

public class Token {

    public int uid;
    public String accessToken;
    public String refreshToken;
    public long expiresTime;        //accessToken生命周期
    public String phoneNum;

}
