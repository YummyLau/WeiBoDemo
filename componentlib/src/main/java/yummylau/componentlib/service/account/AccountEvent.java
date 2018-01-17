package yummylau.componentlib.service.account;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 账户体系流程
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2018/01/17.
 */

public class AccountEvent {

    public static final int ACCOUNT_LOGIN = 0x01;
    public static final int ACCOUNT_LOGOUT = 0x02;

    private int type;

    public AccountEvent(@EventType int type) {
        this.type = type;
    }

    @IntDef({ACCOUNT_LOGIN, ACCOUNT_LOGOUT})
    @Target({ElementType.PARAMETER, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }
}
