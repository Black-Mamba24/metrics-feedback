package com.zhaiyi.metrics_feedback.action;

import com.zhaiyi.metrics_feedback.configuration.SafeRange;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public interface Action {
    void onSafe(String metricName, SafeRange safeRange, double current);

    void onUnsafe(String metricName, SafeRange safeRange, double current);

    void onResume(String metricName, SafeRange safeRange, double current);
}
