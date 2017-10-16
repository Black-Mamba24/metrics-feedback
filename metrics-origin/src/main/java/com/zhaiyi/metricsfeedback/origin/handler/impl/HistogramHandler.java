package com.zhaiyi.metricsfeedback.origin.handler.impl;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Snapshot;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.configuration.SafeRange;
import static com.zhaiyi.metricsfeedback.origin.constants.MetircConstants.*;
import java.util.Map;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public class HistogramHandler extends BaseHandler {
    public HistogramHandler(FeedbackConfiguration configuration, Histogram histogram) {
        super(configuration, histogram);
    }

    @Override
    public void handle() {
        if (metric != null) {
            Histogram histogram = (Histogram) metric;
            for (Map.Entry<String, SafeRange> entry : configuration.getThreshold().entrySet()) {
                String key = entry.getKey();
                SafeRange safeRange = entry.getValue();
                double current;
                Snapshot snapshot = histogram.getSnapshot();
                switch (key) {
                    case count:
                        current = histogram.getCount();
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
                        throw new IllegalArgumentException("histogram metric item " + key + " is illegal");
                }

                judge(key, safeRange, current);
            }
        }
    }
}
