package com.zhaiyi.metricsfeedback.origin.util;

import com.codahale.metrics.MetricRegistry;

/**
 * Created by zhaiyi on 2017/10/16.
 */
public class MetricRegistryUtil {
    private static MetricRegistry metricRegistry;

    private MetricRegistryUtil() {
    }

    static {
        if (metricRegistry == null) {
            metricRegistry = new MetricRegistry();
        }
    }

    public static MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

}
