package com.yf.core.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 数据传输基类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<T> {

    private int code;
    private String message;
    private T data;



    public static RestResponse<String> success() {
        return success("");
    }
    public static<T> RestResponse<T> success(T data) {
        return build(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),data);
    }

    public static RestResponse<String> failed(int code, String message) {
        return failed(code, message, "");
    }

    public static<T> RestResponse<T> failed(int code, String message, T data) {
        return build(code, message, data);
    }

    private static<T> RestResponse<T> build(int code,String message ,T data) {
        return new RestResponse<T>(code,message,data);
    }
}
