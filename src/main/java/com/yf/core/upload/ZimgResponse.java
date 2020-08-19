package com.yf.core.upload;

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
