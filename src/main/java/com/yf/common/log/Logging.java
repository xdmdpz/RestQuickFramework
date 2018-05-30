package com.yf.common.log;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * log 处理类
 * <p>
 * Created by li on 2016/9/12.
 */
public class Logging {

    private static final Logger logger = Logger.getLogger(Logging.class);

    public static void setLevel(Level level) {
        logger.setLevel(level);
    }

    public static void debug(Object s) {
        logger.debug(s.toString());
    }

    public static void info(Object s) {
        logger.info(s.toString());
    }

    public static void warning(Object s) {
        logger.warn(s.toString());
    }

    public static void error(Object s) {
        logger.error(s.toString());
    }

}
