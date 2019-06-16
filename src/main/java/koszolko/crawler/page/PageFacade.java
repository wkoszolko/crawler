package koszolko.crawler.page;

import koszolko.crawler.page.dto.GetPage;
import koszolko.crawler.page.model.Page;
import koszolko.crawler.page.service.PageCrawler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class PageFacade {
    private final PageCrawler pageCrawler;

    public Optional<Page> fetch(GetPage getPage) {
        long startTime = System.currentTimeMillis();
        Optional<Page> page = pageCrawler.crawl(getPage.getUrl());
        long endTime = System.currentTimeMillis();
        //todo dodac aspekt logujacy czas wykonania
        log.info("Fetched url {} in {} ms", getPage.getUrl(), (endTime - startTime));
        return page;
    }
}
