package com.zhaiyi.metricsfeedback.annotation.parser;

import com.codahale.metrics.Meter;
import com.zhaiyi.metricsfeedback.origin.action.Action;
import com.zhaiyi.metricsfeedback.annotation.annotation.MeterConfig;
import com.zhaiyi.metricsfeedback.origin.util.MetricRegistryUtil;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.constants.MetricType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by zhaiyi on 2017/10/18.
 */

@Aspect
public class MeterParser extends BaseParser {

    @Around("all() && @annotation(mc)")
    public Object parse(ProceedingJoinPoint joinPoint, MeterConfig mc) throws Throwable {
        Meter meter;
        String metricName = getMetricName(joinPoint);
        if (!annotationMetrics.containsKey(metricName)) {
            meter = MetricRegistryUtil.getMetricRegistry().meter(metricName);
            annotationMetrics.put(metricName, meter);

            if (mc != null && mc.action() != null) {
                FeedbackConfiguration.Builder builder = new FeedbackConfiguration.Builder();
                builder.type(MetricType.METER).metricName(metricName)
                        .period(mc.period()).action((Action) mc.action().newInstance());
                if(mc.thresholds() != null) {
                    setThresholds(builder, mc.thresholds());
                }
                FeedbackConfiguration fc = builder.build();
                feedbackManager.addConfiguration(fc);
            }
        }
        meter = (Meter) annotationMetrics.get(metricName);
        Object result = joinPoint.proceed();
        meter.mark();
        return result;
    }
}
