package com.yf.core.upload;

public class ZimgInfo{
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