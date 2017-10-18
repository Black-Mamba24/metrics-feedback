package com.zhaiyi.metricsfeedback.annotation;

import java.lang.annotation.*;

/**
 * Created by zhaiyi on 2017/10/17.
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimerConfig {
    long period() default 60;

    String[] thresholds() default {};

    Class action();
}
