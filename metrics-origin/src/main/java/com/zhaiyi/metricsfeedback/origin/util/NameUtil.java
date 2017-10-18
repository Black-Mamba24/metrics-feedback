package com.zhaiyi.metricsfeedback.origin.util;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by zhaiyi on 2017/9/25.
 */
public class NameUtil {
    public static String getMetricName(Class clazz, String... names) {
        return name(clazz, names);
    }

    public static String getMetricName(String name, String... names) {
        return name(name, names);
    }

    public static String getLogbackMetricName(String level) {
        return name(ch.qos.logback.core.Appender.class, level.toLowerCase());
    }

    public static String getLog4jMetricName(String level) {
        return name(org.apache.log4j.Appender.class, level.toLowerCase());
    }

    public static String getLog4j2MetricName(String level) {
        return name(org.apache.logging.log4j.core.Appender.class, level.toLowerCase());
    }

    public static String getJvmMetricName(String prefix, String... names) {
        return name(prefix, names);
    }

}
