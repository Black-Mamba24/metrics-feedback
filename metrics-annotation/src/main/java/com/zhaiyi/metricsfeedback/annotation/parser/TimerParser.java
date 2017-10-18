package com.zhaiyi.metricsfeedback.annotation.parser;

import com.codahale.metrics.Timer;
import com.zhaiyi.metricsfeedback.origin.action.Action;
import com.zhaiyi.metricsfeedback.annotation.annotation.TimerConfig;
import com.zhaiyi.metricsfeedback.origin.util.MetricRegistryUtil;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.constants.MetricType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/10/18.
 */

@Aspect
public class TimerParser extends BaseParser {

    @Around("all() && @annotation(tc)")
    public Object parse(ProceedingJoinPoint joinPoint, TimerConfig tc) throws Throwable {
        Timer timer;
        String metricName = getMetricName(joinPoint);
        if (!annotationMetrics.containsKey(metricName)) {
            timer = MetricRegistryUtil.getMetricRegistry().timer(metricName);
            annotationMetrics.put(metricName, timer);
            if (tc != null && tc.action() != null) {
                FeedbackConfiguration.Builder builder = new FeedbackConfiguration.Builder();
                builder.type(MetricType.TIMER).metricName(metricName)
                        .period(tc.period()).action((Action) tc.action().newInstance());
                if (tc.thresholds() != null) {
                    setThresholds(builder, tc.thresholds());
                }
                FeedbackConfiguration fc = builder.build();
                feedbackManager.addConfiguration(fc);
            }
        }
        timer = (Timer) annotationMetrics.get(metricName);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        timer.update(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
        return result;
    }
}
