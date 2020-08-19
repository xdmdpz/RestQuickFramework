package com.yf.core.exception;

import org.springframework.http.HttpStatus;


public class Exceptions {

    public static class ApiException extends RuntimeException {

        private int statsCode;

        public ApiException() {
            super("服务器内部错误");
            this.statsCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        public ApiException(int statusCode, String message) {
            super(message);
            this.statsCode = statusCode;
        }

        public ApiException(HttpStatus status, String message) {
            super(message);
            this.statsCode = status.value();
        }

        public int getStatsCode() {
            return statsCode;
        }

    }

    public static class DataValidationFailedException extends ApiException {
        public DataValidationFailedException(String message) {
            super(400, message);
        }
    }

    //请求语法有误
    public static class UnauthorizedException extends ApiException {
        public UnauthorizedException(String message) {
            super(401, message);
        }
    }

    //请求禁止，权限不足等原因
    public static class ForbiddenException extends ApiException {
        public ForbiddenException(String message) {
            super(403, message);
        }
    }

    //验证相关数据不存在
    public static class DataNotFoundException extends ApiException {
        public DataNotFoundException(String message) {
            super(404, message);
        }
    }

    //数据冲突
    public static class DataConflictedException extends ApiException {
        public DataConflictedException(String message) {
            super(409, message);
        }
    }


}
