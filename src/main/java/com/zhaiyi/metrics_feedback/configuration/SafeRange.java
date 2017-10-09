package com.zhaiyi.metrics_feedback.configuration;

/**
 * Created by zhaiyi on 2017/9/25.
 */
public class SafeRange {
    private double min;
    private double max;

    public SafeRange(double max) {
        this(0, max);
    }

    public SafeRange(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "[" + min + ", " + max + "]";
    }
}
