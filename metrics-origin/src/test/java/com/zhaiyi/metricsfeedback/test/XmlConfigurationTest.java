package com.zhaiyi.metricsfeedback.test;

import com.codahale.metrics.Counter;
import com.zhaiyi.metricsfeedback.util.NameUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhaiyi on 2017/10/10.
 */
public class XmlConfigurationTest extends BaseTest {

    @Before
    public void setUp() {
        prepare();
        xmlConfig();
    }

    @Test
    public void counterTest() throws InterruptedException {
        Counter counter = registry.counter(NameUtil.getMetricName(XmlConfigurationTest.class, "counter"));
        feedbackManager.start();
        for (int i = 0; i < 40; i++) {
            if (i <= 20) {
                counter.inc();
            } else {
                counter.dec();
            }
            Thread.sleep(1000);
        }
    }
}
