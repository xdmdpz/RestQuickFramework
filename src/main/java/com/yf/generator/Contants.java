package com.yf.generator;

/**
 * Created by if on 2017/12/1.
 */
public class Contants {
    public static final String GENERATE_SUFFIX_ENTITY = ".java";
    public static final String GENERATE_SUFFIX_SERVICE = "Service.java";
    public static final String GENERATE_SUFFIX_JPA = "Repository.java";
    public static final String GENERATE_SUFFIX_CONTROLLER = "Controller.java";

    /**
     * 生成文件的上级目录
     *
     * 如 .../modules/UserInfo/domain/UserInfo.java
     *
     */
    public static final String GENERATE_ENTITY_PATH = "/domain";
    public static final String GENERATE_SERVICE_PATH = "/service";
    public static final String GENERATE_JPA_PATH = "/repository";
    public static final String GENERATE_CONTROLLER_PATH = "/controller";

    public static final String GENERATE_FILE_ENCODING = "utf-8";


    //数据库名称
    public static final String GENERATE_DATABASENAME = "example_Insurance";

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_ENCODING = "utf-8";
    public static final String JDBC_URL = "jdbc:mysql://101.201.78.224:3306/" + GENERATE_DATABASENAME + "?useUnicode=true&characterEncoding=" + DB_ENCODING;
    public static final String JDBC_USERNAME = "bdvtest";
    public static final String JDBC_PASSWORD = "bdvtest@";

    //副本目录
    public static final String GENERATE_CARBON = "/carbon";

    //项目别名
    public static final String GENERATE_PROJECTNAME = "com.yf";

    //生成路径
    public static final String GENERATE_PACKAGENAME = GENERATE_PROJECTNAME + ".modules";

}