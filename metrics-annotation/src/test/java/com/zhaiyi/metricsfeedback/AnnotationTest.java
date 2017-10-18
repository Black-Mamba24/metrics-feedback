package com.zhaiyi.metricsfeedback;

import com.codahale.metrics.ConsoleReporter;
import com.zhaiyi.metricsfeedback.util.MetricRegistryUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/10/17.
 */
public class AnnotationTest {

    ClassPathXmlApplicationContext context;
    MyService service;
    Random random = new Random();

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("bean.xml");
        context.start();
        FeedbackManager manager = (FeedbackManager) context.getBean("feedbackManager");
        manager.start();
        service = (MyService) context.getBean("myService");
        ConsoleReporter.forRegistry(MetricRegistryUtil.getMetricRegistry()).build().start(1, TimeUnit.SECONDS);
    }

    @Test
    public void counterTest() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            service.counter();
            Thread.sleep(1000);
        }
    }

    @Test
    public void meterTest() throws InterruptedException {
        for (int i = 0; i < 200; i++) {
            service.meter();
            Thread.sleep(600 - 2 * i);
        }
    }

    @Test
    public void histogramTest() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            service.histogram();
        }
    }

    @Test
    public void timerTest() throws InterruptedException {
        Thread.sleep(1000 * 3);
        for (int i = 0; i < 200; i++) {
            service.timer();
        }
    }
}
