package com.zhaiyi.metricsfeedback;

import com.zhaiyi.metricsfeedback.configuration.FeedbackConfiguration;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public interface FeedbackConfig {
    FeedbackConfiguration config(FeedbackConfiguration.Builder builder);
}
