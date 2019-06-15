package koszolko.crawler.sitemap.configuration;

import koszolko.crawler.page.PageFacade;
import koszolko.crawler.sitemap.SitemapFacade;
import koszolko.crawler.sitemap.service.SitemapBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

@Configuration
class SitemapFactory {
    @Bean
    SitemapFacade sitemapFacade(PageFacade pageFacade, SitemapBuilder sitemapBuilder) {
        return new SitemapFacade(pageFacade, sitemapBuilder);
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
