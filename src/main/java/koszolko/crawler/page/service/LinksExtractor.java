package koszolko.crawler.page.service;

import koszolko.crawler.page.dto.ExtractLink;
import koszolko.crawler.page.model.Link;
import koszolko.crawler.page.model.LinkType;
import koszolko.crawler.page.model.Url;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LinksExtractor {
    Map<LinkType, List<Link>> extract(ExtractLink extractLink) {
        List<Link> staticLinks = extractStaticLinks(extractLink);
        List<Link> domainLinks = extractDomainLinks(extractLink);
        List<Link> externalLinks = extractExternalLinks(extractLink);
        return buildMap(staticLinks, domainLinks, externalLinks);
    }

    private Map<LinkType, List<Link>> buildMap(List<Link> staticLinks, List<Link> domainLinks, List<Link> externalLinks) {
        Map<LinkType, List<Link>> links = new HashMap<>();
        links.put(LinkType.DOMAIN, domainLinks);
        links.put(LinkType.STATIC, staticLinks);
        links.put(LinkType.EXTERNAL, externalLinks);
        return links;
    }

    private List<Link> extractExternalLinks(ExtractLink extractLink) {
        Document doc = extractLink.getDoc();
        Url pageUrl = extractLink.getUrl();
        Elements links = doc.select("a[href]");
        return links.stream()
                .map(link -> link.attr("abs:href"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .filter(Url::isValidUrl)
                .map(Url::new)
                .filter(url -> !pageUrl.isSameDomain(url))
                .map(Link::externalLink)
                .collect(toList());
    }

    private List<Link> extractDomainLinks(ExtractLink extractLink) {
        Document doc = extractLink.getDoc();
        Url pageUrl = extractLink.getUrl();
        Elements links = doc.select("a[href]");
        return links.stream()
                .map(link -> link.attr("abs:href"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .filter(Url::isValidUrl)
                .map(Url::new)
                .filter(pageUrl::isSameDomain)
                .map(Link::domainLink)
                .collect(toList());
    }

    private List<Link> extractStaticLinks(ExtractLink extractLink) {
        Document doc = extractLink.getDoc();

        Elements staticElements = doc.select("[src]");
        Stream<Link> staticLinks = staticElements.stream()
                .map(link -> link.attr("abs:src"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .filter(Url::isValidUrl)
                .map(Url::new)
                .map(Link::staticLink);

        Elements importElements = doc.select("link[href]");
        Stream<Link> importLinks = importElements.stream()
                .map(link -> link.attr("abs:href"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .filter(Url::isValidUrl)
                .map(Url::new)
                .map(Link::staticLink);

        return Stream.concat(staticLinks, importLinks).collect(Collectors.toList());
    }
}
