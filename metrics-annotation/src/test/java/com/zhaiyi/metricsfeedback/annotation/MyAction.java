package com.zhaiyi.metricsfeedback.annotation;

import com.zhaiyi.metricsfeedback.origin.action.Action;
import com.zhaiyi.metricsfeedback.origin.configuration.SafeRange;

/**
 * Created by zhaiyi on 2017/10/18.
 */
public class MyAction implements Action {
    @Override
    public void onSafe(String metricName, SafeRange safeRange, double current) {
        System.out.println("onSafe");
    }

    @Override
    public void onUnsafe(String metricName, SafeRange safeRange, double current) {
        System.out.println("onUnsafe");
    }

    @Override
    public void onResume(String metricName, SafeRange safeRange, double current) {
        System.out.println("onResume");
    }
}
