package koszolko.crawler.page.service;

import koszolko.crawler.page.dto.Link;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class LinksExtractor {
    List<Link> extract(Document doc) {
        List<Link> links = new ArrayList<>();
        List<Link> staticLinks = extractStaticLinks(doc);
        List<Link> pageLinks = extractLinks(doc);
        links.addAll(staticLinks);
        links.addAll(pageLinks);
        return links;
    }

    private List<Link> extractLinks(Document doc) {
        Elements links = doc.select("a[href]");
        return links.stream()
                .map(link -> link.attr("abs:href"))
                .filter(StringUtils::isNotBlank)
                .map(Link::staticLink)
                .collect(toList());
    }

    private List<Link> extractStaticLinks(Document doc) {
        Elements staticLinks = doc.select("[src]");
        return staticLinks.stream()
                .map(link -> link.attr("abs:src"))
                .filter(StringUtils::isNotBlank)
                .map(Link::staticLink)
                .collect(toList());
    }
}
