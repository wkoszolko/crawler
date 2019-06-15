package koszolko.crawler.page.service;

import koszolko.crawler.page.dto.Link;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LinksExtractor {
    List<Link> extract(Document doc) {
        List<Link> staticLinks = extractStaticLinks(doc);
        List<Link> pageLinks = extractLinks(doc);
        List<Link> links = new ArrayList<>();
        links.addAll(staticLinks);
        links.addAll(pageLinks);
        return links;
    }

    private List<Link> extractLinks(Document doc) {
        Elements links = doc.select("a[href]");
        return links.stream()
                .map(link -> link.attr("abs:href"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .map(Link::staticDomain)
                .collect(toList());
    }

    private List<Link> extractStaticLinks(Document doc) {
        Elements staticElements = doc.select("[src]");
        Stream<Link> staticLinks = staticElements.stream()
                .map(link -> link.attr("abs:src"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .map(Link::staticLink);

        Elements importElements = doc.select("link[href]");
        Stream<Link> importLinks = importElements.stream()
                .map(link -> link.attr("abs:href"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .map(Link::staticLink);

        return Stream.concat(staticLinks, importLinks).collect(Collectors.toList());
    }
}
