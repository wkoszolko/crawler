package koszolko.crawler.sitemap.service;

import koszolko.crawler.page.PageFacade;
import koszolko.crawler.page.dto.Page;
import koszolko.crawler.sitemap.dto.CrawlDomain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@AllArgsConstructor
public class DomainCrawler {
    private final PageFacade pageFacade;
    private final ExecutorService executorService;

    public List<Page> crawlDomain(CrawlDomain crawlDomain) {
        CrawlDomainTask task = new CrawlDomainTask(
                pageFacade,
                executorService,
                crawlDomain
        );
        return task.execute();
    }
}
