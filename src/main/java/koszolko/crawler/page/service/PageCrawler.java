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

    public Optional<Page> crawl(Url url) {
        Optional<Document> maybeDocument = fetchPage(url);
        return maybeDocument.map(doc -> buildPage(doc, url));
    }

    private Page buildPage(Document document, Url url) {
        ExtractLinkCommand command = new ExtractLinkCommand(document, url);
        Map<LinkType, List<Link>> links = linksExtractor.extract(command);
        return new Page(document.location(), links);
    }

    Optional<Document> fetchPage(Url url) {
        try {
            String stringUrl = url.asString();
            return Optional.ofNullable(Jsoup.connect(stringUrl).get());
        } catch (Exception e) {
            log.warn("Could not fetch page, url: " + url, e);
        }
        return Optional.empty();
    }

}
