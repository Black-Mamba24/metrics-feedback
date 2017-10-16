package com.zhaiyi.metricsfeedback.origin.handler.impl;

import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.configuration.SafeRange;

import java.util.Map;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public class TimerHandler extends BaseHandler {

    public TimerHandler(FeedbackConfiguration configuration, Timer timer) {
        super(configuration, timer);
    }

    @Override
    public void handle() {
        if (metric != null) {
            Timer timer = (Timer) metric;
            Snapshot snapshot = timer.getSnapshot();
            for (Map.Entry<String, SafeRange> entry : configuration.getThreshold().entrySet()) {
                String key = entry.getKey();
                SafeRange safeRange = entry.getValue();
                double current;
                switch (key) {
                    case count:
                        current = timer.getCount();
                        break;
                    case meanRate:
                        current = timer.getMeanRate();
                        break;
                    case oneMinuteRate:
                        current = timer.getOneMinuteRate();
                        break;
                    case fiveMinuteRate:
                        current = timer.getFiveMinuteRate();
                        break;
                    case fifteenMinuteRate:
                        current = timer.getFifteenMinuteRate();
                        break;
                    case min:
                        current = snapshot.getMin();
                        break;
                    case max:
                        current = snapshot.getMax();
                        break;
                    case mean:
                        current = snapshot.getMean();
                        break;
                    case stddev:
                        current = snapshot.getStdDev();
                        break;
                    case median:
                        current = snapshot.getMedian();
                        break;
                    case percent75:
                        current = snapshot.get75thPercentile();
                        break;
                    case percent95:
                        current = snapshot.get95thPercentile();
                        break;
                    case percent98:
                        current = snapshot.get98thPercentile();
                        break;
                    case percent99:
                        current = snapshot.get99thPercentile();
                        break;
                    case percent999:
                        current = snapshot.get999thPercentile();
                        break;
                    default:
                        throw new IllegalArgumentException("timer metric item " + key + " is illegal");
                }

                judge(key, safeRange, current);
            }
        }
    }
}
