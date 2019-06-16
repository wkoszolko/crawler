package koszolko.crawler.sitemap.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Data
@Validated
@ConfigurationProperties("app.fetch-page-client")
public class FetchTimeoutConfiguration {
    @Positive
    private int fetchTimeoutInSeconds = 10;
}
