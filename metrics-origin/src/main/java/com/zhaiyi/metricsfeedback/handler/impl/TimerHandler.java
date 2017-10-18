package com.zhaiyi.metricsfeedback.handler.impl;

import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.zhaiyi.metricsfeedback.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.configuration.SafeRange;
import com.zhaiyi.metricsfeedback.constants.TimerConstants;

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
                    case TimerConstants.count:
                        current = timer.getCount();
                        break;
                    case TimerConstants.meanRate:
                        current = timer.getMeanRate();
                        break;
                    case TimerConstants.oneMinuteRate:
                        current = timer.getOneMinuteRate();
                        break;
                    case TimerConstants.fiveMinuteRate:
                        current = timer.getFiveMinuteRate();
                        break;
                    case TimerConstants.fifteenMinuteRate:
                        current = timer.getFifteenMinuteRate();
                        break;
                    case TimerConstants.min:
                        current = snapshot.getMin();
                        break;
                    case TimerConstants.max:
                        current = snapshot.getMax();
                        break;
                    case TimerConstants.mean:
                        current = snapshot.getMean();
                        break;
                    case TimerConstants.stddev:
                        current = snapshot.getStdDev();
                        break;
                    case TimerConstants.median:
                        current = snapshot.getMedian();
                        break;
                    case TimerConstants.percent75:
                        current = snapshot.get75thPercentile();
                        break;
                    case TimerConstants.percent95:
                        current = snapshot.get95thPercentile();
                        break;
                    case TimerConstants.percent98:
                        current = snapshot.get98thPercentile();
                        break;
                    case TimerConstants.percent99:
                        current = snapshot.get99thPercentile();
                        break;
                    case TimerConstants.percent999:
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
