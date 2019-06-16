package koszolko.crawler.page;

import koszolko.crawler.page.dto.GetPageCommand;
import koszolko.crawler.page.dto.Page;
import koszolko.crawler.page.service.PageCrawler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class PageFacade {
    private final PageCrawler pageCrawler;

    public Optional<Page> fetch(GetPageCommand command) {
        long startTime = System.currentTimeMillis();
        Optional<Page> page = pageCrawler.crawl(command.getUrl());
        long endTime = System.currentTimeMillis();
        //todo dodac aspekt logujacy czas wykonania
        log.info("Fetched url {} in {} ms", command.getUrl(), (endTime - startTime));
        return page;
    }
}
