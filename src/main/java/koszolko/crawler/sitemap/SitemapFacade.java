package koszolko.crawler.sitemap;

import koszolko.crawler.page.model.Page;
import koszolko.crawler.shared.model.Url;
import koszolko.crawler.sitemap.configuration.FetchTimeoutConfiguration;
import koszolko.crawler.sitemap.dto.CrawlDomain;
import koszolko.crawler.sitemap.dto.GenerateSitemap;
import koszolko.crawler.sitemap.dto.Sitemap;
import koszolko.crawler.sitemap.service.DomainCrawler;
import koszolko.crawler.sitemap.service.SitemapBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class SitemapFacade {
    private final SitemapBuilder sitemapBuilder;
    private final DomainCrawler domainCrawler;
    private final FetchTimeoutConfiguration config;

    public Sitemap generate(GenerateSitemap command) {
        log.info("Start build sitemap for url: {} with limit: {}", command.getUrl(), command.getLimit());
        CrawlDomain crawlDomain = new CrawlDomain(
                new Url(command.getUrl()),
                command.getLimit(),
                config.getFetchTimeoutInSeconds()
        );
        List<Page> domain = domainCrawler.crawlDomain(crawlDomain);
        return sitemapBuilder.build(domain);
    }

}
