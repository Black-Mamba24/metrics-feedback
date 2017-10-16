package com.zhaiyi.metricsfeedback.origin.handler.impl;

import com.codahale.metrics.Meter;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.configuration.SafeRange;
import com.zhaiyi.metricsfeedback.origin.constants.MetircConstants;
import static com.zhaiyi.metricsfeedback.origin.constants.MetircConstants.*;
import java.util.Map;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public class MeterHandler extends BaseHandler {
    public MeterHandler(FeedbackConfiguration configuration, Meter meter) {
        super(configuration, meter);
    }

    @Override
    public void handle() {
        if (metric != null) {
            Meter meter = (Meter) metric;
            for (Map.Entry<String, SafeRange> entry : configuration.getThreshold().entrySet()) {
                String key = entry.getKey();
                SafeRange safeRange = entry.getValue();
                double current;
                switch (key) {
                    case count:
                        current = meter.getCount();
                        break;
                    case meanRate:
                        current = meter.getMeanRate();
                        break;
                    case oneMinuteRate:
                        current = meter.getOneMinuteRate();
                        break;
                    case fiveMinuteRate:
                        current = meter.getFiveMinuteRate();
                        break;
                    case fifteenMinuteRate:
                        current = meter.getFifteenMinuteRate();
                        break;
                    default:
                        throw new IllegalArgumentException("meter metric item " + key + " is illegal");
                }

                judge(key, safeRange, current);
            }
        }
    }
}
