package com.yf.common.base;


import org.springframework.http.HttpStatus;

/**
 * 数据传输基类
 */
public class RestResponse {

    private int statusCode;
    private String message;
    private Object data;


    public RestResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public RestResponse() {

    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    /**
     * 请求成功
     *
     * @param data
     * @return
     */
    public RestResponse success(Object data) {
        return builder(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public RestResponse success() {
        return success(null);
    }


    /**
     * 系统错误
     *
     * @return
     */
    public RestResponse failed(int statusCode, String message) {
        return failed(statusCode, message, null);
    }

    public RestResponse failed(int statusCode, String message, Object data) {
        return builder(statusCode, message, data);
    }


    private RestResponse builder(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        return this;
    }

    private RestResponse builder(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
        return this;
    }


}
