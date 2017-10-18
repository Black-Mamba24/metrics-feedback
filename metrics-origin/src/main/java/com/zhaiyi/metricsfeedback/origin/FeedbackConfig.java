package com.zhaiyi.metricsfeedback.origin;

import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public interface FeedbackConfig {
    FeedbackConfiguration config(FeedbackConfiguration.Builder builder);
}
