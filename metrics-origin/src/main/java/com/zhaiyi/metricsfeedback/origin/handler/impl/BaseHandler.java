package com.zhaiyi.metricsfeedback.origin.handler.impl;

import com.codahale.metrics.Metric;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.configuration.SafeRange;
import com.zhaiyi.metricsfeedback.origin.handler.Handler;
import com.zhaiyi.metricsfeedback.origin.util.LogUtil;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public abstract class BaseHandler implements Handler {
    protected Logger LOG = LogUtil.getLogger();
    protected FeedbackConfiguration configuration;
    protected Metric metric;
    private Map<String, Pair> stateMap = new HashMap<>();

    private class Pair {
        private boolean last;
        private boolean fresh;

        public Pair() {
            this.last = true;
            this.fresh = true;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public boolean getFresh() {
            return fresh;
        }

        public boolean getLast() {
            return last;
        }
    }

    public BaseHandler(FeedbackConfiguration configuration, Metric metric) {
        this.configuration = configuration;
        this.metric = metric;
    }

    private boolean isSafe(SafeRange safeRange, double current) {
        return safeRange.getMin() <= current && current <= safeRange.getMax();
    }

    protected void judge(String key, SafeRange safeRange, double current) {
        if (!stateMap.containsKey(key)) {
            stateMap.put(key, new Pair());
        }
        Pair pair = stateMap.get(key);
        pair.setLast(pair.getFresh());
        pair.setFresh(isSafe(safeRange, current));
        if (!pair.getFresh()) {
            LOG.info("{} is unsafe", configuration.getMetricName() + "." + key);
            configuration.getAction().onUnsafe(configuration.getMetricName(), safeRange, current);
        } else if (pair.getLast()) {
            LOG.info("{} is safe", configuration.getMetricName() + "." + key);
            configuration.getAction().onSafe(configuration.getMetricName(), safeRange, current);
        } else {
            LOG.info("{} is resumed", configuration.getMetricName() + "." + key);
            configuration.getAction().onResume(configuration.getMetricName(), safeRange, current);
        }
    }
}
