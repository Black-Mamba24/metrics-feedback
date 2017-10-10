# metrics-feedback
##关于metrics：
metrics提供了对次数、频率、数据分布的统计，能够帮助开发运维人员统计系统中各种参数

##metrics-feedback功能：
metrics-feedback基于metrics的统计值，实现当被监控指标处于正常范围、超出异常范围、恢复到正常时系统可以执行自定义的反馈操作
帮助开发者用编码的方式处理系统异常情况

metrics-feedback实现了对Gauge、Counter、Meter、Histogram、Timer的统计反馈，同时支持了常用的jvm相关的统计项例如memory、gc、thread-state，同时支持logback、log4j、log4j2的日志统计

##使用方法：
metrics-feedback提供了编码配置：
实现FeedbackConfig接口，通过builder配置参数
```java
public interface FeedbackConfig {
    FeedbackConfiguration config(FeedbackConfiguration.Builder builder);
}
```
例如：
```java
public class HeapUsageConfig implements FeedbackConfig {
    @Override
    public FeedbackConfiguration config(FeedbackConfiguration.Builder builder) {
        return builder.type(MetricType.GAUGE).metricName(NameUtil.getJvmMetricName(JvmConstants.jvmMemory, JvmConstants.heapUsage))
                .setThreshold(MetircConstants.value, 0, 0.03).initialDelay(0).period(1).timeUnit(TimeUnit.SECONDS)
                .action(new Action() {
                    @Override
                    public void onSafe(String metricName, SafeRange safeRange, double current) {
                        //safe action
                    }

                    @Override
                    public void onUnsafe(String metricName, SafeRange safeRange, double current) {
                        //unsafe action
                    }

                    @Override
                    public void onResume(String metricName, SafeRange safeRange, double current) {
                        //resume action
                    }
                })
                .build();
    }
}
```

同时，需要用户构造FeedbackManager对象:

需要3个参数：
* MetricRegistry	metrics注册中心
* poolSize		线程池大小
* configPackage		配置类的包路径名称

通过feedbackManager.start()启动, feedbackManager.stop()停止
