package com.zhaiyi.metricsfeedback.annotation.annotation;

import com.zhaiyi.metricsfeedback.origin.action.impl.EmptyAction;

import java.lang.annotation.*;

/**
 * Created by zhaiyi on 2017/10/17.
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CounterConfig {
    long period() default 60;

    String[] thresholds() default {};

    Class action() default EmptyAction.class;
}
