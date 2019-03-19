package com.yf.modules.upload;

public class ZimgResponse {
    boolean ret ;
    ZimgInfo info;
    ZimgError error;

    public ZimgResponse() {
    }

    public ZimgResponse(boolean ret, ZimgInfo info) {
        this.ret = ret;
        this.info = info;
        this.error = null;
    }

    public ZimgResponse(boolean ret, ZimgError error) {
        this.ret = ret;
        this.error = error;
        this.info = null;
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public ZimgInfo getInfo() {
        return info;
    }

    public void setInfo(ZimgInfo info) {
        this.info = info;
    }

    public ZimgError getError() {
        return error;
    }

    public void setError(ZimgError error) {
        this.error = error;
    }
}
class ZimgInfo{
    String md5;
    int size;

    public ZimgInfo() {
    }

    public ZimgInfo(String md5, int size) {
        this.md5 = md5;
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
class  ZimgError{
    int code ;
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
