package koszolko.crawler.sitemap.service;

import koszolko.crawler.page.dto.Page;
import koszolko.crawler.sitemap.dto.Sitemap;
import koszolko.crawler.sitemap.dto.WebPageDocument;
import lombok.AllArgsConstructor;
import org.springframework.util.IdGenerator;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class SitemapBuilder {
    private final IdGenerator idGenerator;

    public Sitemap build(List<Page> pages) {
        WebPageDocument webPageDocument = new WebPageDocument(pages.get(0).getUrl(), Collections.emptyList());
        String id = idGenerator.generateId().toString();
        return new Sitemap(id, webPageDocument);
    }
}
