package com.zhaiyi.metricsfeedback.origin.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zhaiyi on 2017/9/24.
 */
public abstract class LifeCycleSupport implements LifeCycle {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCycleSupport.class);

    protected AtomicBoolean inited = new AtomicBoolean(false);

    protected AtomicBoolean started = new AtomicBoolean(false);

    protected AtomicBoolean stopped = new AtomicBoolean(false);

    @Override
    public void init() {
        if (inited.compareAndSet(false, true)) {
            LOG.info("class {} Life Cycle start init", this.getClass());
            try {
                doInit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            LOG.info("class {} Life Cycle finish init", this.getClass());
        }
    }

    @Override
    public void start() {
        stopped.set(false);
        if (!inited.get()) {
            init();
        }
        if (started.compareAndSet(false, true)) {
            LOG.info("class {} Life Cycle start init", this.getClass());
            try {
                doStart();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            LOG.info("class {} Life Cycle finish init", this.getClass());
        }
    }

    @Override
    public void stop() {
        if (started.compareAndSet(false, true)) {
            LOG.info("class {} Life Cycle start init", this.getClass());
            try {
                doStop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            LOG.info("class {} Life Cycle finish init", this.getClass());
        }
        started.set(false);
    }

    public boolean isStarted() {
        return started.get();
    }

    public abstract void doInit() throws Exception;

    public abstract void doStart() throws Exception;

    public abstract void doStop() throws Exception;
}
