package com.zhaiyi.metricsfeedback.origin.config.gauge;

import com.zhaiyi.metricsfeedback.origin.FeedbackConfig;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.configuration.SafeRange;
import com.zhaiyi.metricsfeedback.origin.MetricTest;
import com.zhaiyi.metricsfeedback.origin.util.NameUtil;
import com.zhaiyi.metricsfeedback.origin.constants.GaugeConstants;
import com.zhaiyi.metricsfeedback.origin.constants.MetricType;
import com.zhaiyi.metricsfeedback.origin.action.Action;
import junit.framework.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/10/4.
 */
public class GaugeDoubleConfig implements FeedbackConfig {
    @Override
    public FeedbackConfiguration config(FeedbackConfiguration.Builder builder) {
        return builder.type(MetricType.GAUGE).metricName(NameUtil.getMetricName(MetricTest.class, "gaugeDouble"))
                .setThreshold(GaugeConstants.value, -10d, 10d).initialDelay(0).period(1).timeUnit(TimeUnit.SECONDS)
                .action(new Action() {
                    @Override
                    public void onSafe(String metricName, SafeRange safeRange, double current) {
                        Assert.assertTrue(current <= safeRange.getMax() && current >= safeRange.getMin());
                    }

                    @Override
                    public void onUnsafe(String metricName, SafeRange safeRange, double current) {
                        Assert.assertFalse(current <= safeRange.getMax() && current >= safeRange.getMin());
                    }

                    @Override
                    public void onResume(String metricName, SafeRange safeRange, double current) {
                        Assert.assertTrue(current <= safeRange.getMax() && current >= safeRange.getMin());
                    }
                }).build();
    }
}
