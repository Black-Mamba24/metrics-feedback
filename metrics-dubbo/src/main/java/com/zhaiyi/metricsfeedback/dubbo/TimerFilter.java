package com.zhaiyi.metricsfeedback.dubbo;

import com.alibaba.dubbo.rpc.*;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.zhaiyi.metricsfeedback.origin.util.MetricRegistryUtil;
import com.zhaiyi.metricsfeedback.origin.util.NameUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/10/16.
 */
public class TimerFilter implements Filter {
    private Timer timer;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (timer == null) {
            MetricRegistry registry = MetricRegistryUtil.getMetricRegistry();
            String metricName = NameUtil.getMetricName(invoker.getInterface(), invocation.getMethodName(), "timer");
            timer = registry.timer(metricName);
        }

        long start = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        timer.update(System.currentTimeMillis() - start, TimeUnit.MICROSECONDS);

        return result;
    }
}
