package com.zhaiyi.metricsfeedback;

import com.zhaiyi.metricsfeedback.annotation.CounterConfig;
import com.zhaiyi.metricsfeedback.annotation.HistogramConfig;
import com.zhaiyi.metricsfeedback.annotation.MeterConfig;
import com.zhaiyi.metricsfeedback.annotation.TimerConfig;
import com.zhaiyi.metricsfeedback.constants.CounterConstants;
import com.zhaiyi.metricsfeedback.constants.HistogramConstants;
import com.zhaiyi.metricsfeedback.constants.MeterConstants;
import com.zhaiyi.metricsfeedback.constants.TimerConstants;

import java.util.Random;

/**
 * Created by zhaiyi on 2017/10/17.
 */

public class MyService {
    private Random random = new Random();

    @CounterConfig(period = 1, thresholds = {CounterConstants.count + ", 0, 10"}, action = MyAction.class)
    public void counter() {
        System.out.println("counter service...");
    }

    @MeterConfig(period = 1, thresholds = {MeterConstants.oneMinuteRate + ", 0, 1.85", MeterConstants.fiveMinuteRate + ", 0, 1.80"}, action = MyAction.class)
    public void meter() {
        System.out.println("meter service...");
    }

    @HistogramConfig(period = 1, thresholds = {HistogramConstants.percent75 + ", 70, 80", HistogramConstants.percent95 + ", 90, 98"}, action = MyAction.class)
    public void histogram() throws InterruptedException {
        System.out.println("histogram service...");
        Thread.sleep(random.nextInt(100));
    }

    @TimerConfig(period = 1, thresholds = {TimerConstants.min + ", 0, 10", TimerConstants.max + ", 90, 110"}, action = MyAction.class)
    public void timer() throws InterruptedException {
        System.out.println("timer service...");
        Thread.sleep(random.nextInt(100));
    }
}
