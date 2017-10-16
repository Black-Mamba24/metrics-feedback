package com.zhaiyi.metricsfeedback.origin.handler.impl;

import com.codahale.metrics.Counter;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.configuration.SafeRange;
import com.zhaiyi.metricsfeedback.origin.constants.MetircConstants;

import java.util.Map;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public class CounterHandler extends BaseHandler {

    public CounterHandler(FeedbackConfiguration configuration, Counter counter) {
        super(configuration, counter);
    }

    @Override
    public void handle() {
        if(metric != null) {
            Counter counter = (Counter) metric;
            for (Map.Entry<String, SafeRange> entry : configuration.getThreshold().entrySet()) {
                String key = entry.getKey();
                SafeRange safeRange = entry.getValue();
                if (!MetircConstants.count.equals(key)) {
                    throw new IllegalArgumentException("counter metric item " + key + " is illegal");
                }
                double current = counter.getCount();

                judge(key, safeRange, current);
            }
        }
    }
}
