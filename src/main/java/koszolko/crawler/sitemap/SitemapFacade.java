package koszolko.crawler.sitemap;

import koszolko.crawler.page.dto.Page;
import koszolko.crawler.page.dto.Url;
import koszolko.crawler.sitemap.dto.CrawlDomain;
import koszolko.crawler.sitemap.dto.GenerateSitemapCommand;
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

    public Sitemap generate(GenerateSitemapCommand command) {
        log.info("Start build sitemap for url: {} with limit: {}", command.getUrl(), command.getLimit());
        CrawlDomain crawlDomain = new CrawlDomain(new Url(command.getUrl()), command.getLimit());
        List<Page> domain = domainCrawler.crawlDomain(crawlDomain);
        return sitemapBuilder.build(domain);
    }

}
