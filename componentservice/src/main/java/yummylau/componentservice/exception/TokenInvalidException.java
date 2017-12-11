package yummylau.componentservice.exception;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class TokenInvalidException extends IllegalArgumentException {

    public TokenInvalidException() {
        super("token invalid");
    }
}
