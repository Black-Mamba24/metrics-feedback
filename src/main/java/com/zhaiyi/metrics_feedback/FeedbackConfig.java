package com.zhaiyi.metrics_feedback;

import com.zhaiyi.metrics_feedback.configuration.FeedbackConfiguration;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public interface FeedbackConfig {
    FeedbackConfiguration config(FeedbackConfiguration.Builder builder);
}
