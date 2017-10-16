package com.zhaiyi.metricsfeedback.origin;

import com.codahale.metrics.*;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.handler.Handler;
import com.zhaiyi.metricsfeedback.origin.handler.impl.*;
import com.zhaiyi.metricsfeedback.origin.lifecycle.LifeCycleSupport;
import com.zhaiyi.metricsfeedback.origin.util.LogUtil;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 调度中心
 * Created by zhaiyi on 2017/9/24.
 */
public class FeedbackScheduledExecutorService extends LifeCycleSupport {
    private Logger LOG;
    private Set<FeedbackConfiguration> configurations;
    private MetricRegistry metricRegistry;
    private int poolSize;
    private ScheduledExecutorService scheduledExecutorService;
    private Map<String, Metric> metricMap = new HashMap<>();
    private Map<String, Handler> handlerMap = new HashMap<>();

    @Override
    public void doInit() throws Exception {
        LOG = LogUtil.getLogger();
        scheduledExecutorService = Executors.newScheduledThreadPool(poolSize);
        validateAndPrepare();
    }

    private void validateAndPrepare() {
        for (FeedbackConfiguration configuration : configurations) {
            SortedMap mMap;
            Handler handler;
            Metric metric;
            String metricName = configuration.getMetricName();
            MetricFilter filter = (name, metric1) -> name.equals(metricName);
            switch (configuration.getType()) {
                case GAUGE:
                    mMap = metricRegistry.getGauges(filter);
                    metric = (Metric) mMap.get(metricName);
                    handler = new GaugeHandler(configuration, (Gauge) metric);
                    break;
                case COUNTER:
                    mMap = metricRegistry.getCounters(filter);
                    metric = (Metric) mMap.get(metricName);
                    handler = new CounterHandler(configuration, (Counter) metric);
                    break;
                case METER:
                    mMap = metricRegistry.getMeters(filter);
                    metric = (Metric) mMap.get(metricName);
                    handler = new MeterHandler(configuration, (Meter) metric);
                    break;
                case HISTOGRAM:
                    mMap = metricRegistry.getHistograms(filter);
                    metric = (Metric) mMap.get(metricName);
                    handler = new HistogramHandler(configuration, (Histogram) metric);
                    break;
                case TIMER:
                    mMap = metricRegistry.getTimers(filter);
                    metric = (Metric) mMap.get(metricName);
                    handler = new TimerHandler(configuration, (Timer) metric);
                    break;
                default:
                    throw new IllegalArgumentException(configuration.getType() + " is illegal");
            }
            if (metric != null && !mMap.isEmpty()) {
                this.metricMap.putAll(mMap);
                this.handlerMap.put(metricName, handler);
            } else {
                LOG.error(metricName + " is wrong, can't get this metric");
            }
        }
    }

    @Override
    public void doStart() {
        for (FeedbackConfiguration configuration : configurations) {
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                handlerMap.get(configuration.getMetricName()).handle();
            }, configuration.getInitialDelay(), configuration.getPeriod(), configuration.getTimeUnit());
        }
    }

    @Override
    public void doStop() throws Exception {
        scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

    public FeedbackScheduledExecutorService setMetricRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
        return this;
    }

    public FeedbackScheduledExecutorService setConfigurations(Set<FeedbackConfiguration> configurations) {
        this.configurations = configurations;
        return this;
    }

    public FeedbackScheduledExecutorService setPoolSize(int poolSize) {
        this.poolSize = poolSize;
        return this;
    }
}
