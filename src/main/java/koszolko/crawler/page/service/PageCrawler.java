package koszolko.crawler.page.service;

import koszolko.crawler.page.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class PageCrawler {
    private final LinksExtractor linksExtractor;

    public Optional<Page> crawl(String url) {
        Optional<Document> maybeDocument = fetchPage(url);
        return maybeDocument.map(doc -> buildPage(doc, url));
    }

    private Page buildPage(Document document, String rootUrl) {
        ExtractLinkCommand command = new ExtractLinkCommand(document, new Url(rootUrl));
        Map<LinkType, List<Link>> links = linksExtractor.extract(command);
        return new Page(document.location(), links);
    }

    Optional<Document> fetchPage(String url) {
        try {
            return Optional.ofNullable(Jsoup.connect(url).get());
        } catch (Exception e) {
            log.warn("Could not fetch page, url: " + url, e);
        }
        return Optional.empty();
    }

}
