package ru.adanil.shorter.model;

public class ErrorDetails {

    private int code;
    private String message;


    public ErrorDetails(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDetails() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
