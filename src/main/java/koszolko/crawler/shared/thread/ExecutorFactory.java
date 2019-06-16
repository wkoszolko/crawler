package koszolko.crawler.shared.thread;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties({HttpClientThreadPoolConfiguration.class})
class ExecutorFactory {
    @Bean(destroyMethod = "shutdownNow")
    ExecutorService httpFetchCacheThreadPool(HttpClientThreadPoolConfiguration config) {
        return Executors.newFixedThreadPool(
                config.getPoolSize(),
                new CustomizableThreadFactory("httpFetchCacheThreadPool-")
        );
    }
}
