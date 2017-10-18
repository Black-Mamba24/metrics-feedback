package com.zhaiyi.metricsfeedback.test.xml.action;

import com.zhaiyi.metricsfeedback.action.Action;
import com.zhaiyi.metricsfeedback.configuration.SafeRange;

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
