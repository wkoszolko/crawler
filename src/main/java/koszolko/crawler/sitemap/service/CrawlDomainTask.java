package koszolko.crawler.sitemap.service;

import koszolko.crawler.page.PageFacade;
import koszolko.crawler.page.dto.GetPageCommand;
import koszolko.crawler.page.dto.Link;
import koszolko.crawler.page.dto.Page;
import koszolko.crawler.page.dto.Url;
import koszolko.crawler.sitemap.dto.CrawlDomain;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Slf4j
class CrawlDomainTask {
    private final PageFacade pageFacade;
    private final ExecutorService executor;
    private final Queue<Url> urlsToCrawl = new LinkedList<>();
    private final Set<Url> crawledUrls = new HashSet<>();
    private final CrawlDomain input;
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Queue<Future<Optional<Page>>> futurePages = new LinkedList<>();
    private final List<Page> pages = new ArrayList<>();
    private boolean executed = false;

    CrawlDomainTask(@NotNull PageFacade pageFacade, @NotNull ExecutorService executor, @NotNull CrawlDomain crawlDomain) {
        this.pageFacade = pageFacade;
        this.executor = executor;
        this.input = crawlDomain;
    }

    List<Page> execute() {
        if (executed) {
            throw new IllegalStateException("Task has been executed!");
        }
        processRootPage();
        processPagesUnitLimitExceeded();
        this.executed = true;
        return this.pages;
    }

    private void processPagesUnitLimitExceeded() {
        while (!futurePages.isEmpty()) {
            Optional<Page> maybePage = getPageTaskResult();
            if (maybePage.isPresent()) {
                Page newPage = maybePage.get();
                processNewPage(newPage);
            }
        }
    }

    private void processNewPage(Page page) {
        this.pages.add(page);
        urlsToCrawl.addAll(page.getDomainLinks().stream().map(Link::getUrl).collect(toList()));
        while (counter.get() < this.input.getLimit() && urlsToCrawl.size() > 0) {
            Url currentUrlToCrawl = urlsToCrawl.poll();
            if (isNewUrl(crawledUrls, currentUrlToCrawl)) {
                counter.incrementAndGet();
                crawledUrls.add(currentUrlToCrawl);
                futurePages.add(executor.submit(createPageTask(currentUrlToCrawl)));
            }
        }
    }

    private Optional<Page> getPageTaskResult() {
        Optional<Page> maybePage = Optional.empty();
        try {
            int timeout = input.getFetchPageTimeoutInSeconds();
            maybePage = futurePages.poll().get(timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.warn("Could not fetched page!", e);
        }
        return maybePage;
    }

    private void processRootPage() {
        Url rootPageUrl = this.input.getUrl();
        Callable<Optional<Page>> rootPageTask = createPageTask(rootPageUrl);
        this.futurePages.add(executor.submit(rootPageTask));
        this.crawledUrls.add(rootPageUrl);
    }

    private Callable<Optional<Page>> createPageTask(Url url) {
        log.info("Fetching url: {}", url);
        GetPageCommand getPage = new GetPageCommand(url);
        return () -> pageFacade.crawlDomain(getPage);
    }

    private boolean isNewUrl(Set<Url> crawledUrls, Url currentUrlToCrawl) {
        return !crawledUrls.contains(currentUrlToCrawl);
    }

}
