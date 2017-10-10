package com.zhaiyi.metrics_feedback.test.config.gauge;

import com.zhaiyi.metrics_feedback.FeedbackConfig;
import com.zhaiyi.metrics_feedback.action.Action;
import com.zhaiyi.metrics_feedback.configuration.FeedbackConfiguration;
import com.zhaiyi.metrics_feedback.test.MetricTest;
import com.zhaiyi.metrics_feedback.configuration.SafeRange;
import com.zhaiyi.metrics_feedback.constants.MetricType;
import com.zhaiyi.metrics_feedback.util.NameUtil;
import com.zhaiyi.metrics_feedback.constants.MetircConstants;
import junit.framework.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/10/3.
 */
public class GaugeIntegerConfig implements FeedbackConfig {
    @Override
    public FeedbackConfiguration config(FeedbackConfiguration.Builder builder) {
        return builder.type(MetricType.GAUGE).metricName(NameUtil.getMetricName(MetricTest.class, "gaugeInteger"))
                .setThreshold(MetircConstants.value, -5, 5).initialDelay(0).period(1).timeUnit(TimeUnit.SECONDS)
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