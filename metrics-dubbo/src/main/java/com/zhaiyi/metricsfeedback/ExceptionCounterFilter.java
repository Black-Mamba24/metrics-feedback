package com.zhaiyi.metricsfeedback;

import com.alibaba.dubbo.rpc.*;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.zhaiyi.metricsfeedback.util.MetricRegistryUtil;
import com.zhaiyi.metricsfeedback.util.NameUtil;

/**
 * Created by zhaiyi on 2017/10/16.
 */
public class ExceptionCounterFilter implements Filter {
    private Counter exceptionCounter;
    private Counter successCounter;
    private Gauge successRate;
    private MetricRegistry registry = MetricRegistryUtil.getMetricRegistry();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (exceptionCounter == null) {
            String metricName = NameUtil.getMetricName(invoker.getInterface(), invocation.getMethodName(), "exception.counter");
            exceptionCounter = registry.counter(metricName);
        }

        if (successCounter == null) {
            String metricName = NameUtil.getMetricName(invoker.getInterface(), invocation.getMethodName(), "success.counter");
            successCounter = registry.counter(metricName);
        }

        if (successRate == null) {
            successRate = () -> successCounter.getCount() * 1.0 / (successCounter.getCount() + exceptionCounter.getCount());
            registry.register(NameUtil.getMetricName(invoker.getInterface(), invocation.getMethodName(), "success.rate"), successRate);
        }

        Result result = invoker.invoke(invocation);
        if (result.hasException()) {
            exceptionCounter.inc();
        } else {
            successCounter.inc();
        }

        return result;
    }
}
