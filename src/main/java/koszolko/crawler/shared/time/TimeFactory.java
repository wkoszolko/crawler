package koszolko.crawler.shared.time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

@Configuration
class TimeFactory {
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean(name = "dateTimeFormatter")
    DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    }
}
