package koszolko.crawler.sitemap.dto;

import koszolko.crawler.page.dto.Url;
import lombok.Value;

@Value
public class CrawlDomain {
    private final Url url;
    private final int limit;
}
