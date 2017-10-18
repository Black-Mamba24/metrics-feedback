package com.zhaiyi.metricsfeedback.annotation.parser;

import com.codahale.metrics.Metric;
import com.zhaiyi.metricsfeedback.origin.FeedbackManager;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhaiyi on 2017/10/17.
 */
public abstract class BaseParser {
    private Logger LOG = LoggerFactory.getLogger(BaseParser.class);
    protected ConcurrentHashMap<String, Metric> annotationMetrics = new ConcurrentHashMap<>();
    protected FeedbackManager feedbackManager;

    public void setFeedbackManager(FeedbackManager feedbackManager) {
        this.feedbackManager = feedbackManager;
    }

    @Pointcut("execution(* *.*(..))")
    protected void all() {
    }

    protected String getMetricName(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Signature s = joinPoint.getSignature();
        if (!(s instanceof MethodSignature)) {
            throw new IllegalArgumentException("metric config annotation only on method");
        }
        MethodSignature ms = (MethodSignature) s;
        Object target = joinPoint.getTarget();
        String clazzName = target.getClass().getName();
        String methodName = target.getClass().getMethod(ms.getName(), ms.getParameterTypes()).getName();
        return clazzName + "." + methodName;
    }

    protected void setThresholds(FeedbackConfiguration.Builder builder, String[] thresholds) {
        for (String th : thresholds) {
            if (StringUtils.isEmpty(th)) {
                continue;
            }
            String[] ths = th.split(",");
            if (ths.length == 2) {
                builder.setThreshold(ths[0].trim(), Double.parseDouble(ths[1].trim()));
            } else if (ths.length == 3) {
                builder.setThreshold(ths[0].trim(), Double.parseDouble(ths[1].trim()), Double.parseDouble(ths[2].trim()));
            } else {
                LOG.error("CounterConfig thresholds are wrong");
            }
        }
    }
}
