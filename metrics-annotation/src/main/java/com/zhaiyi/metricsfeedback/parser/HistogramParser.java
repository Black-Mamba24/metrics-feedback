package com.zhaiyi.metricsfeedback.parser;

import com.codahale.metrics.Histogram;
import com.zhaiyi.metricsfeedback.action.Action;
import com.zhaiyi.metricsfeedback.annotation.HistogramConfig;
import com.zhaiyi.metricsfeedback.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.constants.MetricType;
import com.zhaiyi.metricsfeedback.util.MetricRegistryUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by zhaiyi on 2017/10/18.
 */

@Aspect
public class HistogramParser extends BaseParser {

    @Around("all() && @annotation(hc)")
    public Object parse(ProceedingJoinPoint joinPoint, HistogramConfig hc) throws Throwable {
        Histogram histogram;
        String metricName = getMetricName(joinPoint);
        if (!annotationMetrics.containsKey(metricName)) {
            histogram = MetricRegistryUtil.getMetricRegistry().histogram(metricName);
            annotationMetrics.put(metricName, histogram);

            if (hc != null && hc.action() != null) {
                FeedbackConfiguration.Builder builder = new FeedbackConfiguration.Builder();
                builder.type(MetricType.HISTOGRAM).metricName(metricName)
                        .period(hc.period()).action((Action) hc.action().newInstance());
                if (hc.thresholds() != null) {
                    setThresholds(builder, hc.thresholds());
                }
                FeedbackConfiguration fc = builder.build();
                feedbackManager.addConfiguration(fc);
            }
        }
        histogram = (Histogram) annotationMetrics.get(metricName);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        histogram.update(System.currentTimeMillis() - start);
        return result;
    }
}
