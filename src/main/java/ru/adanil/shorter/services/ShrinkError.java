package ru.adanil.shorter.services;

public class ShrinkError extends Exception {

    //to display user
    private String userDisplayerror;

    public ShrinkError(String message) {
        super(message);
    }

    public ShrinkError(String message, String userError) {
        super(message);
        this.userDisplayerror = userError;
    }

    public String getUserDisplayerror() {
        return userDisplayerror==null?getMessage():userDisplayerror;
    }

    public void setUserDisplayerror(String userDisplayerror) {
        this.userDisplayerror = userDisplayerror;
    }
}
