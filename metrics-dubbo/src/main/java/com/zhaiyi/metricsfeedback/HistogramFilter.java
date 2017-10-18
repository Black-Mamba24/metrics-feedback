package com.zhaiyi.metricsfeedback;

import com.alibaba.dubbo.rpc.*;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.zhaiyi.metricsfeedback.util.MetricRegistryUtil;
import com.zhaiyi.metricsfeedback.util.NameUtil;

/**
 * Created by zhaiyi on 2017/10/16.
 */
public class HistogramFilter implements Filter {
    private Histogram histogram;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (histogram == null) {
            MetricRegistry registry = MetricRegistryUtil.getMetricRegistry();
            String metricName = NameUtil.getMetricName(invoker.getInterface(), invocation.getMethodName(), "histogram");
            histogram = registry.histogram(metricName);
        }

        long start = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        if (!result.hasException()) {
            histogram.update(System.currentTimeMillis() - start);
        }

        return result;
    }
}
