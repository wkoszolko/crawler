package koszolko.crawler.shared.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
class ExecutorFactory {
    @Bean(destroyMethod = "shutdownNow")
    ExecutorService httpFetchCacheThreadPool() {
        return Executors.newFixedThreadPool(
                36,
                new CustomizableThreadFactory("httpFetchCacheThreadPool-")
        );
    }
}
