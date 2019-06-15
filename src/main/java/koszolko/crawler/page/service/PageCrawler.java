package koszolko.crawler.page.service;

import koszolko.crawler.page.dto.Page;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
public class PageCrawler {

    public Optional<Page> crawl(String url) {
        return Optional.of(new Page(null, url));
    }
}
