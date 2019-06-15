package koszolko.crawler.page;

import koszolko.crawler.page.dto.GetPageCommand;
import koszolko.crawler.page.dto.Page;
import koszolko.crawler.page.service.PageCrawler;
import lombok.AllArgsConstructor;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PageFacade {
    private final PageCrawler pageCrawler;

    public List<Page> crawlDomain(GetPageCommand command) {
        //simple stab implementation
        Optional<Page> maybePage = pageCrawler.crawl(command.getUrl());
        return Collections.singletonList(maybePage.get());
    }
}
