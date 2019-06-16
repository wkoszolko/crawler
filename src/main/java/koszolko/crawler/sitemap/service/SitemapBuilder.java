package koszolko.crawler.sitemap.service;

import koszolko.crawler.page.model.Link;
import koszolko.crawler.page.model.Page;
import koszolko.crawler.page.model.Url;
import koszolko.crawler.sitemap.dto.Sitemap;
import koszolko.crawler.sitemap.dto.WebPageDocument;
import lombok.AllArgsConstructor;
import org.springframework.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SitemapBuilder {
    private final IdGenerator idGenerator;

    public Sitemap build(List<Page> pages) {
        List<WebPageDocument> webPageDocuments = new ArrayList<>();
        for (Page p : pages) {
            String url = p.getUrl();
            List<String> staticLinks = p.getStaticLinks().stream().map(Link::getUrl).map(Url::asString).collect(Collectors.toList());
            List<String> domainLinks = p.getDomainLinks().stream().map(Link::getUrl).map(Url::asString).collect(Collectors.toList());
            List<String> externalLinks = p.getExternalLinks().stream().map(Link::getUrl).map(Url::asString).collect(Collectors.toList());
            webPageDocuments.add(new WebPageDocument(url, staticLinks, domainLinks, externalLinks));
        }

        String id = idGenerator.generateId().toString();
        return new Sitemap(id, webPageDocuments);
    }
}
