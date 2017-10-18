package com.zhaiyi.metricsfeedback.test.config.gauge;

import com.zhaiyi.metricsfeedback.FeedbackConfig;
import com.zhaiyi.metricsfeedback.action.Action;
import com.zhaiyi.metricsfeedback.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.constants.GaugeConstants;
import com.zhaiyi.metricsfeedback.test.MetricTest;
import com.zhaiyi.metricsfeedback.configuration.SafeRange;
import com.zhaiyi.metricsfeedback.constants.MetricType;
import com.zhaiyi.metricsfeedback.util.NameUtil;
import junit.framework.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/10/5.
 */
public class GaugeBigIntegerConfig implements FeedbackConfig {
    @Override
    public FeedbackConfiguration config(FeedbackConfiguration.Builder builder) {
        return builder.type(MetricType.GAUGE).metricName(NameUtil.getMetricName(MetricTest.class, "gaugeBigInteger"))
                .setThreshold(GaugeConstants.value, -10, 10).initialDelay(0).period(1).timeUnit(TimeUnit.SECONDS)
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
