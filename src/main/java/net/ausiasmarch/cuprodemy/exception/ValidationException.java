package net.ausiasmarch.cuprodemy.exception;



public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super("ERROR: Field data not valid: " + msg);
    }

}
