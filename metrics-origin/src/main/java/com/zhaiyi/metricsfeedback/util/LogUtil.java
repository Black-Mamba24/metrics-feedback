package com.zhaiyi.metricsfeedback.util;

import org.slf4j.Logger;

/**
 * Created by zhaiyi on 2017/9/28.
 */
public class LogUtil {
    private static Logger logger;

    public static synchronized void setLogger(Logger logger) {
        if (logger != null) {
            LogUtil.logger = logger;
        }
    }

    public static Logger getLogger() {
        Logger log = LogUtil.logger;
        if (log == null)
            throw new NullPointerException("logger is null");
        return log;
    }
}
