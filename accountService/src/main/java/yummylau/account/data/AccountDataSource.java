package yummylau.account.data;

/**
 * account 模块数据接口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/28.
 */

public interface AccountDataSource {

    void login();

    void logout();

    void refreshToken();
}
