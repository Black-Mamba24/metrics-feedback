package com.zhaiyi.metricsfeedback.test.config.meter;

import com.zhaiyi.metricsfeedback.FeedbackConfig;
import com.zhaiyi.metricsfeedback.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.configuration.SafeRange;
import com.zhaiyi.metricsfeedback.constants.MeterConstants;
import com.zhaiyi.metricsfeedback.constants.MetricType;
import com.zhaiyi.metricsfeedback.action.Action;
import com.zhaiyi.metricsfeedback.test.MetricTest;
import com.zhaiyi.metricsfeedback.util.NameUtil;
import junit.framework.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/10/9.
 */
public class MeterFiveMinuteRateConfig implements FeedbackConfig {
    @Override
    public FeedbackConfiguration config(FeedbackConfiguration.Builder builder) {
        return builder.type(MetricType.METER).metricName(NameUtil.getMetricName(MetricTest.class, "meterFiveMinuteRate"))
                .setThreshold(MeterConstants.fiveMinuteRate, 0, 2.38).initialDelay(0).period(1).timeUnit(TimeUnit.SECONDS)
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
                })
                .build();
    }
}
