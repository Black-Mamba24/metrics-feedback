package com.zhaiyi.metricsfeedback;

import com.alibaba.dubbo.rpc.*;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.zhaiyi.metricsfeedback.util.MetricRegistryUtil;
import com.zhaiyi.metricsfeedback.util.NameUtil;

/**
 * Created by zhaiyi on 2017/10/16.
 */
public class MeterFilter implements Filter {
    private Meter meter;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (meter == null) {
            MetricRegistry registry = MetricRegistryUtil.getMetricRegistry();
            String metricName = NameUtil.getMetricName(invoker.getInterface(), invocation.getMethodName(), "meter");
            meter = registry.meter(metricName);
        }

        Result result = invoker.invoke(invocation);
        if (!result.hasException()) {
            meter.mark();
        }
        
        return result;
    }
}
