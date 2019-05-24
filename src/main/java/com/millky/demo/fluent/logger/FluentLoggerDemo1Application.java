package com.millky.demo.fluent.logger;

import com.google.common.flogger.FluentLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.flogger.LazyArgs.lazy;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class FluentLoggerDemo1Application {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        SpringApplication.run(FluentLoggerDemo1Application.class, args);
    }

    @Scheduled(fixedDelay = 1000)
    public void test() {

        int cnt = atomicInteger.getAndIncrement();

        log.info("Slf4j = {}" + cnt);

        logger.atInfo().every(2).log("Fluent every(2) = %s", cnt);

        logger.atWarning()
                .atMostEvery(3, SECONDS)
                .log("Fluent atMostEvery(3, SECONDS) = %s", lazy(() -> cnt % 10));
    }
}
