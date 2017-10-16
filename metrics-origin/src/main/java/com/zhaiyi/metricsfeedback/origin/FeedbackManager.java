package com.zhaiyi.metricsfeedback.origin;

import ch.qos.logback.classic.LoggerContext;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.codahale.metrics.logback.InstrumentedAppender;
import com.google.common.base.Preconditions;
import com.zhaiyi.metricsfeedback.origin.configuration.FeedbackConfiguration;
import com.zhaiyi.metricsfeedback.origin.constants.JvmConstants;
import com.zhaiyi.metricsfeedback.origin.lifecycle.LifeCycleSupport;
import com.zhaiyi.metricsfeedback.origin.util.LogUtil;
import org.apache.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 框架入口,调用start()启动框架
 * Created by zhaiyi on 2017/9/24.
 */
public class FeedbackManager extends LifeCycleSupport {
    private static Logger LOG;
    private MetricRegistry registry;
    private int poolSize = 10;
    //编码配置包路径
    private String configPackage;
    //配置文件配置路径
    private String configFile;
    private String loggerName = "root";
    private Reflections reflections;

    private Set<FeedbackConfig> configs = new HashSet<FeedbackConfig>();
    private Set<FeedbackConfiguration> configurations = new HashSet<FeedbackConfiguration>();
    private FeedbackScheduledExecutorService feedbackExecutorService = new FeedbackScheduledExecutorService();
    public FeedbackManager(MetricRegistry registry, int poolSize) {
        Preconditions.checkNotNull(registry);
        Preconditions.checkArgument(poolSize >= 0);
        this.registry = registry;
        this.poolSize = poolSize;
    }

    @Override
    public void doInit() throws Exception {
        logConfig();
        if (configPackage != null) {
            reflections = new Reflections(configPackage, new SubTypesScanner(), new ResourcesScanner());
            instantiate();
        }

        if (configFile != null) {
            FeedbackXmlParser parser = new FeedbackXmlParser();
            List<FeedbackConfiguration> xmlConfigurations = parser.parse(configFile);
            configurations.addAll(xmlConfigurations.stream().collect(Collectors.toList()));
        }
    }

    private void instantiate() throws IllegalAccessException, InstantiationException {
        //获取所有子类class,实例化,并获取Feedback配置
        Set<Class<? extends FeedbackConfig>> configClasses = reflections.getSubTypesOf(FeedbackConfig.class);

        for (Class<? extends FeedbackConfig> configClass : configClasses) {
            configs.add(configClass.newInstance());
        }

        for (FeedbackConfig config : configs) {
            FeedbackConfiguration.Builder builder = new FeedbackConfiguration.Builder();
            configurations.add(config.config(builder));
        }
    }

    private void logConfig() throws Exception {
        Logger logger;
        logger = LoggerFactory.getLogger(loggerName);
        if (logger == null) {
            throw new IllegalArgumentException("can't find logger: " + loggerName);
        }
        LogUtil.setLogger(logger);
        LOG = LogUtil.getLogger();
    }

    @Override
    public void doStart() {
        Preconditions.checkNotNull(this.registry);
        feedbackExecutorService.setMetricRegistry(this.registry)
                .setConfigurations(this.configurations)
                .setPoolSize(this.poolSize);
        feedbackExecutorService.start();
    }

    @Override
    public void doStop() {
        feedbackExecutorService.stop();
    }

    public void jvmMetric() {
//        Preconditions.checkArgument(isStarted());
        gcMetric();
        memoryMetric();
        threadStatesMetric();
    }

    public void gcMetric() {
//        Preconditions.checkArgument(isStarted());
        Preconditions.checkNotNull(this.registry);
        registry.register(JvmConstants.jvmGC, new GarbageCollectorMetricSet());
    }

    public void memoryMetric() {
//        Preconditions.checkArgument(isStarted());
        Preconditions.checkNotNull(this.registry);
        registry.register(JvmConstants.jvmMemory, new MemoryUsageGaugeSet());
    }

    public void threadStatesMetric() {
//        Preconditions.checkArgument(isStarted());
        Preconditions.checkNotNull(this.registry);
        registry.register(JvmConstants.jvmThreadStates, new ThreadStatesGaugeSet());
    }

    public void logbackMetric(String loggerName) {
//        Preconditions.checkArgument(isStarted());
        Preconditions.checkNotNull(this.registry);
        Preconditions.checkNotNull(loggerName);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        ch.qos.logback.classic.Logger logger = context.getLogger(loggerName);
        if (logger != null) {
            InstrumentedAppender appender = new InstrumentedAppender(this.registry);
            appender.setContext(logger.getLoggerContext());
            appender.start();
            logger.addAppender(appender);
        } else {
            LOG.warn("can't get logger: " + loggerName + " can't to register logback metric");
        }
    }

    public void log4jMetric(String loggerName) {
//        Preconditions.checkArgument(isStarted());
        Preconditions.checkNotNull(this.registry);
        Preconditions.checkNotNull(loggerName);

        com.codahale.metrics.log4j.InstrumentedAppender appender = new com.codahale.metrics.log4j.InstrumentedAppender(registry);
        appender.activateOptions();
        org.apache.log4j.Logger logger = LogManager.getLogger(loggerName);
        if (logger != null) {
            logger.addAppender(appender);
        } else {
            LOG.warn("can't get logger: " + loggerName + " can't to register log4j metric");
        }
    }

    public void log4j2Metric(String loggerName) {
//        Preconditions.checkArgument(isStarted());
        Preconditions.checkNotNull(this.registry);
        Preconditions.checkNotNull(loggerName);

        com.codahale.metrics.log4j2.InstrumentedAppender appender = new com.codahale.metrics.log4j2.InstrumentedAppender(registry);
        appender.start();
        org.apache.logging.log4j.core.LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
        Configuration configuration = context.getConfiguration();
        LoggerConfig config = configuration.getLoggerConfig(loggerName);
        if (config != null) {
            config.addAppender(appender, null, null);
            context.updateLoggers(configuration);
        } else {
            LOG.warn("can't get logger: " + loggerName + " config, can't to register log4j2 metric");
        }
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public void setConfigPackage(String configPackage) {
        this.configPackage = configPackage;
    }
}
