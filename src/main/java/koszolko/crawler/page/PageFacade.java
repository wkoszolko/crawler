package koszolko.crawler.page;

import koszolko.crawler.page.dto.GetPageCommand;
import koszolko.crawler.page.dto.Page;
import koszolko.crawler.page.service.PageCrawler;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PageFacade {
    private final PageCrawler pageCrawler;

    public List<Page> crawlDomain(GetPageCommand command) {
        //fetch only one page
        Optional<Page> maybePage = pageCrawler.crawl(command.getUrl());
        List<Page> pages = new ArrayList<>();
        maybePage.ifPresent(pages::add);
        return pages;
    }
}
