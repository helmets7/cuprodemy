package net.ausiasmarch.cuprodemy.exception;

public class JWTException extends RuntimeException {

    public JWTException(String msg) {
        super("ERROR: JWTException: " + msg);
    }

}