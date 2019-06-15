package koszolko.crawler.sitemap.service;

import koszolko.crawler.page.dto.Link;
import koszolko.crawler.page.dto.Page;
import koszolko.crawler.sitemap.dto.Sitemap;
import koszolko.crawler.sitemap.dto.WebPageDocument;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.util.IdGenerator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SitemapBuilder {
    private final IdGenerator idGenerator;

    public Sitemap build(List<Page> pages) {
        String url = pages.get(0).getUrl();
        List<String> links = pages.get(0).getLinks().stream().map(Link::getUrl).collect(Collectors.toList());
        WebPageDocument webPageDocument = new WebPageDocument(url, links);
        String id = idGenerator.generateId().toString();
        return new Sitemap(id, webPageDocument);
    }
}
