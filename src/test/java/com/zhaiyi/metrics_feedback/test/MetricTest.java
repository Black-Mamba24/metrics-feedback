package com.zhaiyi.metrics_feedback.test;

import com.codahale.metrics.*;
import com.zhaiyi.metrics_feedback.util.NameUtil;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhaiyi on 2017/9/30.
 */
public class MetricTest extends BaseTest {

    @Before
    public void setUp() {
        prepare();
        codeConfig();
    }

    /* counter */
    @Test
    public void counterTest() throws InterruptedException {
        Counter counter = registry.counter(NameUtil.getMetricName(MetricTest.class, "counter"));
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i <= 12) {
                counter.inc();
            } else {
                counter.dec();
            }
            Thread.sleep(1000);
        }
    }

    /* gauge */
    @Test
    public void gaugeByteTest() throws InterruptedException {
        class Bytes {
            byte b = 0;

            public byte getB() {
                return b;
            }
        }

        Bytes bytes = new Bytes();
        Gauge gaugeByte = bytes::getB;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeByte"), gaugeByte);
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                bytes.b++;
            } else {
                bytes.b--;
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeShortTest() throws InterruptedException {
        class Shorts {
            byte s = 0;

            public byte getS() {
                return s;
            }
        }

        Shorts shorts = new Shorts();
        Gauge gaugeShort = shorts::getS;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeShort"), gaugeShort);
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                shorts.s++;
            } else {
                shorts.s--;
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeIntegerTest() throws InterruptedException {
        LinkedList list = new LinkedList();
        Gauge gaugeInteger = list::size;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeInteger"), gaugeInteger);
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                list.offer(new Object());
            } else {
                list.poll();
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeLongTest() throws InterruptedException {
        class Longs {
            long l = Long.MAX_VALUE - 3;

            public long getL() {
                return l;
            }
        }
        Longs ls = new Longs();
        Gauge gaugeLong = ls::getL;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeLong"), gaugeLong);
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                ls.l++;
            } else {
                ls.l--;
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeFloatTest() throws InterruptedException {
        class Wallet {
            float money = 0f;

            public float getMoney() {
                return money;
            }

            public void add(float money) {
                this.money += money;
            }

            public void sub(float money) {
                this.money -= money;
            }
        }
        Wallet wallet = new Wallet();
        Gauge gaugeFloat = wallet::getMoney;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeFloat"), gaugeFloat);
        feedbackManager.start();
        for (int i = 0; i < 60; i++) {
            if (i < 30) {
                wallet.add(1f);
            } else {
                wallet.sub(2f);
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeDoubleTest() throws InterruptedException {
        class Score {
            double score = 0d;

            public double getScore() {
                return score;
            }
        }
        Score s = new Score();
        Gauge gaugeDouble = s::getScore;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeDouble"), gaugeDouble);
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                s.score++;
            } else {
                s.score--;
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeAtomicIntegerTest() throws InterruptedException {
        AtomicInteger ai = new AtomicInteger();
        Gauge gaugeAtomicInteger = ai::get;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeAtomicInteger"), gaugeAtomicInteger);
        feedbackManager.start();
        for (int i = 0; i < 60; i++) {
            if (i < 40) {
                ai.incrementAndGet();
            } else {
                ai.decrementAndGet();
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeAtomicLongTest() throws InterruptedException {
        AtomicLong al = new AtomicLong();
        Gauge gaugeAtomicLong = al::get;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeAtomicLong"), gaugeAtomicLong);
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                al.incrementAndGet();
            } else {
                al.decrementAndGet();
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeBigDecimalTest() throws InterruptedException {
        class Balance {
            BigDecimal balance = new BigDecimal("0");

            public BigDecimal getBalance() {
                return balance;
            }

            public void add(BigDecimal arg) {
                balance = balance.add(arg);
            }

            public void sub(BigDecimal arg) {
                balance = balance.subtract(arg);
            }
        }
        Balance balance = new Balance();
        Gauge gaugeBigDecimal = balance::getBalance;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeBigDecimal"), gaugeBigDecimal);
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                balance.add(new BigDecimal("1.5"));
            } else {
                balance.sub(new BigDecimal("1.5"));
            }
            System.out.println(balance.getBalance());
            Thread.sleep(1000);
        }
    }

    @Test
    public void gaugeBigIntegerTest() throws InterruptedException {
        class Bank {
            BigInteger cash = new BigInteger("0");

            public BigInteger getCash() {
                return cash;
            }

            public void add(BigInteger arg) {
                cash = cash.add(arg);
            }

            public void sub(BigInteger arg) {
                cash = cash.subtract(arg);
            }
        }
        Bank bank = new Bank();
        Gauge gaugeBigInteger = bank::getCash;
        registry.register(NameUtil.getMetricName(MetricTest.class, "gaugeBigInteger"), gaugeBigInteger);
        feedbackManager.start();
        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                bank.add(new BigInteger("2"));
            } else {
                bank.sub(new BigInteger("3"));
            }
            System.out.println(bank.getCash());
            Thread.sleep(1000);
        }
    }

    /*meter*/
    @Test
    public void meterCountTest() throws InterruptedException {
        Meter meter = registry.meter(NameUtil.getMetricName(MetricTest.class, "meterCount"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            meter.mark();
            Thread.sleep(random.nextInt(1000));
        }
    }


    @Test
    public void meterMeanRateTest() throws InterruptedException {
        Meter meter = registry.meter(NameUtil.getMetricName(MetricTest.class, "meterMeanRate"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            meter.mark();
            Thread.sleep(random.nextInt(1000 - i));
        }
    }

    @Test
    public void meterOneMinuteRateTest() throws InterruptedException {
        Meter meter = registry.meter(NameUtil.getMetricName(MetricTest.class, "meterOneMinuteRate"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            meter.mark();
            Thread.sleep(random.nextInt(1000 - i));
        }
    }

    @Test
    public void meterFiveMinuteRateTest() throws InterruptedException {
        Meter meter = registry.meter(NameUtil.getMetricName(MetricTest.class, "meterFiveMinuteRate"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            meter.mark();
            Thread.sleep(random.nextInt(1000 - i));
        }
    }

    @Test
    public void meterFifteenMinuteRateTest() throws InterruptedException {
        Meter meter = registry.meter(NameUtil.getMetricName(MetricTest.class, "meterFifteenMinuteRate"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            meter.mark();
            Thread.sleep(random.nextInt(1000 - i));
        }
    }

    /* histogram */
    @Test
    public void histogram75Test() throws InterruptedException {
        Histogram histogram = registry.histogram(NameUtil.getMetricName(MetricTest.class, "histogram75"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            histogram.update(random.nextInt(100));
            Thread.sleep(random.nextInt(1000));
        }
    }

    @Test
    public void histogram95Test() throws InterruptedException {
        Histogram histogram = registry.histogram(NameUtil.getMetricName(MetricTest.class, "histogram95"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            histogram.update(random.nextInt(100));
            Thread.sleep(random.nextInt(1000));
        }
    }

    @Test
    public void histogram98Test() throws InterruptedException {
        Histogram histogram = registry.histogram(NameUtil.getMetricName(MetricTest.class, "histogram98"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            histogram.update(random.nextInt(100));
            Thread.sleep(random.nextInt(1000));
        }
    }

    @Test
    public void histogram99Test() throws InterruptedException {
        Histogram histogram = registry.histogram(NameUtil.getMetricName(MetricTest.class, "histogram99"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            histogram.update(random.nextInt(100));
            Thread.sleep(random.nextInt(1000));
        }
    }

    @Test
    public void histogram999Test() throws InterruptedException {
        Histogram histogram = registry.histogram(NameUtil.getMetricName(MetricTest.class, "histogram999"));
        ConsoleReporter.forRegistry(registry).build().start(1, TimeUnit.SECONDS);
        feedbackManager.start();
        for(int i = 0; i < 15 * 60; i++) {
            histogram.update(random.nextInt(100));
            Thread.sleep(random.nextInt(1000));
        }
    }

    @Test
    public void switchTest() {
        String s = null;
        switch (s) {
            case "dd":
                break;
            default:
                System.out.print("null");
        }
    }

}


