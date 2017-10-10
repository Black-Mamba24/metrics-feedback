package com.zhaiyi.metrics_feedback.test.xml.action;

import com.zhaiyi.metrics_feedback.action.Action;
import com.zhaiyi.metrics_feedback.configuration.SafeRange;

/**
 * Created by zhaiyi on 2017/10/10.
 */
public class MeterAction implements Action {
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
