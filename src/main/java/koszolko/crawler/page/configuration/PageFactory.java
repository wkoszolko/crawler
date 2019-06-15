package koszolko.crawler.page.configuration;

import koszolko.crawler.page.PageFacade;
import koszolko.crawler.page.service.LinksExtractor;
import koszolko.crawler.page.service.PageCrawler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PageFactory {
    @Bean
    PageFacade pageFacade(PageCrawler pageCrawler) {
        return new PageFacade(pageCrawler);
    }

    @Bean
    PageCrawler pageCrawler() {
        LinksExtractor linksExtractor = new LinksExtractor();
        return new PageCrawler(linksExtractor);
    }
}
