package com.zhaiyi.metricsfeedback.origin.test;

import com.codahale.metrics.Gauge;
import com.zhaiyi.metricsfeedback.origin.util.NameUtil;
import com.zhaiyi.metricsfeedback.origin.constants.JvmConstants;
import org.junit.Before;
import org.junit.Test;

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
