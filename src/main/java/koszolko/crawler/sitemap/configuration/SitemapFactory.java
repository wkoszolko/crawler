package koszolko.crawler.sitemap.configuration;

import koszolko.crawler.page.PageFacade;
import koszolko.crawler.sitemap.SitemapFacade;
import koszolko.crawler.sitemap.service.DomainCrawler;
import koszolko.crawler.sitemap.service.SitemapBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

import java.util.concurrent.ExecutorService;

@Configuration
class SitemapFactory {
    @Bean
    SitemapFacade sitemapFacade(SitemapBuilder sitemapBuilder, DomainCrawler domainCrawler) {
        return new SitemapFacade(sitemapBuilder, domainCrawler);
    }

    @Bean
    DomainCrawler domainCrawler(PageFacade pageFacade, ExecutorService httpFetchCacheThreadPool) {
        return new DomainCrawler(pageFacade, httpFetchCacheThreadPool);
    }
    @Bean
    SitemapBuilder sitemapBuilder(IdGenerator idGenerator) {
        return new SitemapBuilder(idGenerator);
    }

    @Bean
    IdGenerator idGenerator() {
        return new JdkIdGenerator();
    }
}
