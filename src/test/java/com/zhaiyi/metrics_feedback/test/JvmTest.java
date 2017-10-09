package com.zhaiyi.metrics_feedback.test;

import com.codahale.metrics.Gauge;
import com.zhaiyi.metrics_feedback.util.NameUtil;
import org.junit.Before;
import org.junit.Test;
import com.zhaiyi.metrics_feedback.constants.JvmConstants;

/**
 * Created by zhaiyi on 2017/10/9.
 */
public class JvmTest extends BaseTest {
    private final int MB = 1024 * 1024;

    @Before
    public void setUp() {
        prepare();
    }

    @Test
    public void heapUsage() throws InterruptedException {
        feedbackManager.memoryMetric();
        feedbackManager.start();
        final String metricName = NameUtil.getJvmMetricName(JvmConstants.jvmMemory, JvmConstants.heapUsage);
        Gauge gauge = registry.getGauges(
                (name, metric) -> metricName.equals(name))
                .get(metricName);
        int[][] arrays = new int[1024][];
        for (int i = 0; i < 1024; i++) {
            arrays[i] = new int[MB];
            System.out.println(gauge.getValue());
            Thread.sleep(1000);
        }
    }
}
