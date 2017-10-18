package com.zhaiyi.metricsfeedback.origin.util;

import com.codahale.metrics.MetricRegistry;

/**
 * Created by zhaiyi on 2017/10/16.
 */
public class MetricRegistryUtil {
    private static MetricRegistry metricRegistry;

    private MetricRegistryUtil() {
    }

    public synchronized static MetricRegistry getMetricRegistry() {
        if(metricRegistry == null) {
            metricRegistry = new MetricRegistry();
        }
        return metricRegistry;
    }

    public synchronized static void setMetricRegistry(MetricRegistry mr) {
        if(metricRegistry == null) {
            metricRegistry = mr;
        }
    }

}
