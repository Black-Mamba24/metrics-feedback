package com.zhaiyi.metricsfeedback.handler.impl;

import com.codahale.metrics.Gauge;
import com.zhaiyi.metricsfeedback.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.configuration.SafeRange;
import com.zhaiyi.metricsfeedback.constants.GaugeConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public class GaugeHandler extends BaseHandler {

    public GaugeHandler(FeedbackConfiguration configuration, Gauge gauge) {
        super(configuration, gauge);
    }

    @Override
    public void handle() {
        if (metric != null) {
            Gauge gauge = (Gauge) metric;
            for (Map.Entry<String, SafeRange> entry : configuration.getThreshold().entrySet()) {
                String key = entry.getKey();
                SafeRange safeRange = entry.getValue();
                if (!GaugeConstants.value.equals(key)) {
                    throw new IllegalArgumentException("gauge metric item " + key + " is illegal");
                }
                Object value = gauge.getValue();
                if (!(value instanceof Number)) {
                    LOG.warn("{} gauge value is not number.", configuration.getMetricName());
                    return;
                }
                double current = 0.0;
                switch (value.getClass().getSimpleName()) {
                    case "Byte":
                        current = (byte) value;
                        break;
                    case "Short":
                        current = (short) value;
                        break;
                    case "Integer":
                        current = (int) value;
                        break;
                    case "Long":
                        current = (long) value;
                        break;
                    case "Float":
                        current = (float) value;
                        break;
                    case "Double":
                        current = (double) value;
                        break;
                    case "AtomicInteger":
                        current = ((AtomicInteger) value).get();
                        break;
                    case "AtomicLong":
                        current = ((AtomicLong) value).get();
                        break;
                    case "BigDecimal":
                        current = ((BigDecimal) value).doubleValue();
                        break;
                    case "BigInteger":
                        current = ((BigInteger) value).doubleValue();
                        break;
                    default:
                        throw new IllegalArgumentException("gauge current value is not number");
                }

                judge(key, safeRange, current);

            }
        }
    }
}
