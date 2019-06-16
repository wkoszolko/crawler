package koszolko.crawler.sitemap.service;

import koszolko.crawler.page.PageFacade;
import koszolko.crawler.page.dto.GetPage;
import koszolko.crawler.page.model.Link;
import koszolko.crawler.page.model.Page;
import koszolko.crawler.page.model.Url;
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
    private final Queue<Url> urlsToFetch = new LinkedList<>();
    private final Set<Url> fetchedUrls = new HashSet<>();
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
        urlsToFetch.addAll(page.getDomainLinks().stream().map(Link::getUrl).collect(toList()));
        while (limitNotExceeded() && hasUrlsToFetch()) {
            Url nextUrl = urlsToFetch.poll();
            if (notFetchedUrl(nextUrl)) {
                futurePages.add(executor.submit(createPageTask(nextUrl)));
                counter.incrementAndGet();
                fetchedUrls.add(nextUrl);
            }
        }
    }

    private boolean hasUrlsToFetch() {
        return urlsToFetch.size() > 0;
    }

    private boolean limitNotExceeded() {
        return counter.get() < this.input.getLimit();
    }

    private boolean notFetchedUrl(Url url) {
        return !this.fetchedUrls.contains(url);
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
        this.fetchedUrls.add(rootPageUrl);
    }

    private Callable<Optional<Page>> createPageTask(Url url) {
        log.info("Fetching url: {}", url);
        GetPage getPage = new GetPage(url);
        return () -> pageFacade.fetch(getPage);
    }
}
