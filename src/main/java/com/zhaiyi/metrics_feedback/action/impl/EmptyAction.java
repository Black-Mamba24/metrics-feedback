package com.zhaiyi.metrics_feedback.action.impl;

import com.zhaiyi.metrics_feedback.action.Action;
import com.zhaiyi.metrics_feedback.configuration.SafeRange;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public class EmptyAction implements Action {

    @Override
    public void onSafe(String metricName, SafeRange safeRange, double current) {

    }

    @Override
    public void onUnsafe(String metricName, SafeRange safeRange, double current) {

    }

    @Override
    public void onResume(String metricName, SafeRange safeRange, double current) {

    }
}
