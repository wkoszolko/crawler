package koszolko.crawler.page.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Optional;

/**
 * Load page from disk.
 * Test data are store in resources/pages directory.
 */
@Slf4j
public class FileSystemPageCrawler extends PageCrawler {
    public FileSystemPageCrawler(LinksExtractor linksExtractor) {
        super(linksExtractor);
    }

    @Override
    Optional<Document> fetchPage(String url) {
        log.info("Fetch page (url: {}) from disk.", url);
        try {
            File input = new ClassPathResource("pages/elastic_root.html").getFile();
            return Optional.ofNullable(Jsoup.parse(input, "UTF-8", url));
        } catch (Exception e) {
            log.warn("Could not fetch page, url: " + url, e);
        }
        return Optional.empty();
    }

}
