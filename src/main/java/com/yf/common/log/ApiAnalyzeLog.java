package com.yf.common.log;


import org.apache.log4j.Logger;

public class ApiAnalyzeLog {

    private static final Logger logger = Logger.getLogger(ApiAnalyzeLog.class);
    final private String SPLIT_SYMBOL = "|";
    private String path;
    private String module;
    private String method;
    private String queryParam;
    private String postParam;
    private String requestTime;
    private long inTime;
    private long outTime;
    private long runTime;
    private int statusCode;
    private String message;
    private String equipment;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public String getPostParam() {
        return postParam;
    }

    public void setPostParam(String postParam) {
        this.postParam = postParam;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public long getInTime() {
        return inTime;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }


    public void outputDetailLog() {
        outTime = System.currentTimeMillis();
        runTime = outTime - inTime;
        logger.info(toString());

    }

    /**
     * 通过RunTimeLog对象的json字符串设置日志打印格式
     *
     * @return 自定义打印出的日志打印格式的字符串
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(method).append(SPLIT_SYMBOL).append(path).append(SPLIT_SYMBOL)
                .append(module).append(SPLIT_SYMBOL).append(queryParam).append(SPLIT_SYMBOL)
                .append(postParam).append(SPLIT_SYMBOL).append(statusCode).append(SPLIT_SYMBOL)
                .append(message).append(SPLIT_SYMBOL).append(equipment).append(SPLIT_SYMBOL)
                .append(requestTime).append(SPLIT_SYMBOL).append(inTime).append(SPLIT_SYMBOL)
                .append(outTime).append(SPLIT_SYMBOL).append(runTime).append(SPLIT_SYMBOL);
        return builder.toString();
    }

}
