package com.zhaiyi.metrics_feedback.handler.impl;

import com.codahale.metrics.Metric;
import com.zhaiyi.metrics_feedback.configuration.FeedbackConfiguration;
import com.zhaiyi.metrics_feedback.configuration.SafeRange;
import com.zhaiyi.metrics_feedback.handler.Handler;
import com.zhaiyi.metrics_feedback.util.LogUtil;
import org.slf4j.Logger;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public abstract class BaseHandler implements Handler {
    protected Logger LOG = LogUtil.getLogger();
    protected FeedbackConfiguration configuration;
    protected Metric metric;
    protected boolean last = true;
    protected boolean fresh = true;

    public BaseHandler(FeedbackConfiguration configuration, Metric metric) {
        this.configuration = configuration;
        this.metric = metric;
    }

    private boolean isSafe(SafeRange safeRange, double current) {
        return safeRange.getMin() <= current && current <= safeRange.getMax();
    }

    protected void judge(SafeRange safeRange, double current) {
        last = fresh;
        fresh = isSafe(safeRange, current);
        if (!fresh) {
            LOG.info("{} is unsafe", configuration.getMetricName());
            configuration.getAction().onUnsafe(configuration.getMetricName(), safeRange, current);
        } else if (last) {
            LOG.info("{} is safe", configuration.getMetricName());
            configuration.getAction().onSafe(configuration.getMetricName(), safeRange, current);
        } else {
            LOG.info("{} is resumed", configuration.getMetricName());
            configuration.getAction().onResume(configuration.getMetricName(), safeRange, current);
        }
    }
}
