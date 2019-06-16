package koszolko.crawler.page.service;

import koszolko.crawler.shared.model.Url;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Load page from disk.
 * Test data are store in resources/pages directory.
 */
@Slf4j
@Primary
@Component
public class FileSystemPageCrawler extends PageCrawler {
    private static final Map<String, String> pages = new HashMap<>();

    public FileSystemPageCrawler(LinksExtractor linksExtractor) {
        super(linksExtractor);
        initPages();
    }

    private void initPages() {
        pages.put("https://www.elastic.co/", "pages/elastic_root.html");
        pages.put("https://www.recursive.com/", "pages/recursive_page_root.html");
        pages.put("https://www.recursive.com/child_1", "pages/recursive_page_child_1.html");
        pages.put("https://www.recursive.com/child_2", "pages/recursive_page_child_2.html");
    }

    @Override
    Optional<Document> fetchPage(Url url) {
        log.info("Fetch page (url: {}) from disk.", url);
        try {
            String filePath = pages.get(url.asString());
            File input = new ClassPathResource(filePath).getFile();
            return Optional.ofNullable(Jsoup.parse(input, "UTF-8", url.asString()));
        } catch (Exception e) {
            log.warn("Could not fetch page, url: " + url, e);
        }
        return Optional.empty();
    }
}
