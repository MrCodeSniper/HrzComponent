package com.hrz.hrzcomponent;

public class ResponseEntity {

    private int state;
    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "state=" + state +
                ", message='" + message + '\'' +
                '}';
    }
}
