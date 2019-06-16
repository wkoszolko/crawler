package koszolko.crawler.shared.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Data
@Validated
@ConfigurationProperties("app.fetch-page-client.threads")
public class HttpClientThreadPoolConfiguration {
    @Positive
    private int poolSize = 1;
}
