package koszolko.crawler.sitemap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sitemap {
    private final String id;
    private final WebPageDocument webPageDocument;
}
