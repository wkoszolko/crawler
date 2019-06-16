package koszolko.crawler.sitemap.service;

import koszolko.crawler.page.PageFacade;
import koszolko.crawler.page.dto.GetPageCommand;
import koszolko.crawler.page.dto.Link;
import koszolko.crawler.page.dto.Page;
import koszolko.crawler.page.dto.Url;
import koszolko.crawler.sitemap.dto.CrawlDomain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

@Slf4j
@AllArgsConstructor
public class DomainCrawler {
    private final PageFacade pageFacade;
    private final ExecutorService executorService;

    public List<Page> crawlDomain(CrawlDomain crawlDomain) {
        List<Page> pages = new ArrayList<>();
        Queue<Url> urlsToCrawl = new LinkedList<>();
        Set<Url> crawledUrls = new HashSet<>();
        int counter = 1;

        Url rootPageUrl = crawlDomain.getUrl();
        int pageLimit = crawlDomain.getLimit();

        Queue<Future<Optional<Page>>> futurePages = new LinkedList<>();
        futurePages.add((executorService.submit(createPageTask(rootPageUrl))));
        crawledUrls.add(rootPageUrl);

        while (!futurePages.isEmpty()) {
            Optional<Page> maybePage = Optional.empty();
            try {
                maybePage = futurePages.poll().get(15, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.warn("Could not fetched page!", e);
            }
            if (maybePage.isPresent()) {
                pages.add(maybePage.get());
                urlsToCrawl.addAll(maybePage.get().getDomainLinks().stream().map(Link::getUrl).collect(toList()));
                while (counter < pageLimit && urlsToCrawl.size() > 0) {
                    Url currentUrlToCrawl = urlsToCrawl.poll();
                    if (isNewUrl(crawledUrls, currentUrlToCrawl)) {
                        ++counter;
                        crawledUrls.add(currentUrlToCrawl);
                        futurePages.add(executorService.submit(createPageTask(currentUrlToCrawl)));
                    }
                }
            }
        }
        return pages;
    }

    private boolean isNewUrl(Set<Url> crawledUrls, Url currentUrlToCrawl) {
        return !crawledUrls.contains(currentUrlToCrawl);
    }

    private Callable<Optional<Page>> createPageTask(Url url) {
        log.info("Fetching url: {}", url);
        GetPageCommand getPage = new GetPageCommand(url);
        return () -> pageFacade.crawlDomain(getPage);
    }
}
