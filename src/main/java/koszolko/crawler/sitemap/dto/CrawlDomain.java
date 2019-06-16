package koszolko.crawler.sitemap.dto;

import koszolko.crawler.page.model.Url;
import lombok.Value;

@Value
public class CrawlDomain {
    private final Url url;
    private final int limit;
    //todo powinna byc klasa Timeout(int timeout, TimeUnit unit)
    private final int fetchPageTimeoutInSeconds = 10;
}
