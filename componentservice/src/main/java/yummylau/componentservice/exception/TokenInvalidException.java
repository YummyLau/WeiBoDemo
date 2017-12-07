package yummylau.componentservice.exception;

/**
 * Created by g8931 on 2017/12/7.
 */

public class TokenInvalidException extends IllegalArgumentException {

    public TokenInvalidException() {
        super("token invalid");
    }
}
