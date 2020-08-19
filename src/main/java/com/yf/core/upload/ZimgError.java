package com.yf.core.upload;

public class ZimgError {
    int code;
    String message;

    public ZimgError() {
    }

    public ZimgError(int code, String message) {
        this.code = code;
        this.message = message;
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
