# metrics-feedback
## 关于metrics：
metrics提供了对次数、频率、数据分布的统计，能够帮助开发运维人员统计系统中各种参数

## metrics-feedback功能：
metrics-feedback基于metrics的统计值，实现当被监控指标处于正常范围、超出异常范围、恢复到正常时系统可以执行自定义的反馈操作
帮助开发者用编码的方式处理系统异常情况

metrics-feedback实现了对Gauge、Counter、Meter、Histogram、Timer的统计反馈，同时支持了常用的jvm相关的统计项例如memory、gc、thread-state，同时支持logback、log4j、log4j2的日志统计

## 使用方法：

### 编码配置
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
`编码配置需要设置config类package路径，以便框架发现`

```java
feedbackManager.setConfigPackage()
```

### 配置文件配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configurations xmlns="http://www.w3schools.com"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="Config.xsd">
    <configuration>
        <type>COUNTER</type>
        <metricName>com.zhaiyi.metrics_feedback.test.XmlConfigurationTest.counter</metricName>
        <initialDelay>0</initialDelay>
        <period>1</period>
        <timeUnit>SECONDS</timeUnit>
        <action>com.zhaiyi.metrics_feedback.test.xml.action.CounterAction</action>
        <thresholds>
            <threshold>
                <name>count</name>
                <min>0</min>
                <max>10</max>
            </threshold>
        </thresholds>
    </configuration>
</configurations>
```

`配置文件配置需要设置配置文件路径`

```java
feedbackManager.setConfigFile()
```

### 启动框架
同时，需要用户构造FeedbackManager对象:

需要2个参数：
* MetricRegistry	metrics注册中心
* poolSize		线程池大小

你可以自定义日志打印策略：
```java
feedbackManager.setLoggerName()
```

通过一下语句启动、停止框架：
```java
feedbackManager.start()
feedbackManager.stop()
```

## metrics-dubbo
为了降低代码侵入，并更好的支持dubbo服务，提供了dubbo filter，能够度量dubbo调用次数、时长。

