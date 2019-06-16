package koszolko.crawler.page;

import koszolko.crawler.page.dto.GetPage;
import koszolko.crawler.page.model.Page;
import koszolko.crawler.page.service.PageCrawler;
import koszolko.crawler.shared.logging.LogExecutionTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class PageFacade {
    private final PageCrawler pageCrawler;

    @LogExecutionTime
    public Optional<Page> fetch(GetPage getPage) {
        return pageCrawler.crawl(getPage.getUrl());
    }
}
