package com.zhaiyi.metricsfeedback.configuration;

import com.zhaiyi.metricsfeedback.action.impl.EmptyAction;
import com.zhaiyi.metricsfeedback.action.Action;
import com.zhaiyi.metricsfeedback.constants.MetricType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public class FeedbackConfiguration {
    private MetricType type;
    private String metricName;
    private Map<String, SafeRange> threshold;
    private long initialDelay;
    private long period;
    private TimeUnit timeUnit;
    private Action action;

    private FeedbackConfiguration(MetricType type, String metricName,
                                  Map<String, SafeRange> threshold, long initialDelay, long period, TimeUnit timeUnit, Action action) {
        this.type = type;
        this.metricName = metricName;
        this.threshold = threshold;
        this.initialDelay = initialDelay;
        this.period = period;
        this.timeUnit = timeUnit;
        this.action = action;
    }

    public static class Builder {
        private MetricType type;
        private String metricName;
        private Map<String, SafeRange> threshold = new HashMap<String, SafeRange>();
        private long initialDelay = 0;
        private long period = 60;
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        private Action action = new EmptyAction();

        public Builder type(MetricType type) {
            this.type = type;
            return this;
        }

        public Builder metricName(String metricName) {
            this.metricName = metricName;
            return this;
        }

        public Builder setThreshold(String k, double max) {
            threshold.put(k, new SafeRange(max));
            return this;
        }

        public Builder setThreshold(String k, double min, double max) {
            threshold.put(k, new SafeRange(min, max));
            return this;
        }

        public Builder initialDelay(long initialDelay) {
            this.initialDelay = initialDelay;
            return this;
        }

        public Builder period(long period) {
            this.period = period;
            return this;
        }

        public Builder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder action(Action action) {
            this.action = action;
            return this;
        }

        public FeedbackConfiguration build() {
            return new FeedbackConfiguration(type, metricName, threshold, initialDelay, period, timeUnit, action);
        }
    }

    public MetricType getType() {
        return type;
    }

    public String getMetricName() {
        return metricName;
    }

    public Map<String, SafeRange> getThreshold() {
        return threshold;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public long getPeriod() {
        return period;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public Action getAction() {
        return action;
    }

    public void setType(MetricType type) {
        this.type = type;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public void setThreshold(String k, double max) {
        threshold.put(k, new SafeRange(max));
    }

    public void setThreshold(String k, double min, double max) {
        threshold.put(k, new SafeRange(min, max));
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public String toString() {
        return "FeedbackConfiguration{" +
                "onUnsafe=" + action +
                ", type=" + type +
                ", metricName='" + metricName + '\'' +
                ", threshold=" + threshold +
                ", initialDelay=" + initialDelay +
                ", period=" + period +
                ", timeUnit=" + timeUnit +
                '}';
    }
}
