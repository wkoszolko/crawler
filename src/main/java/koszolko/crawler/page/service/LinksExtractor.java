package koszolko.crawler.page.service;

import koszolko.crawler.page.dto.ExtractLinkCommand;
import koszolko.crawler.page.dto.Link;
import koszolko.crawler.page.dto.LinkType;
import koszolko.crawler.page.dto.Url;
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
    Map<LinkType, List<Link>> extract(ExtractLinkCommand extractLinkCommand) {
        Document doc = extractLinkCommand.getDoc();
        List<Link> staticLinks = extractStaticLinks(doc);
        List<Link> domainLinks = extractDomainLinks(extractLinkCommand);
        List<Link> externalLinks = extractExternalLinks(extractLinkCommand);
        Map<LinkType, List<Link>> links = new HashMap<>();
        links.put(LinkType.DOMAIN, domainLinks);
        links.put(LinkType.STATIC, staticLinks);
        links.put(LinkType.EXTERNAL, externalLinks);
        return links;
    }

    private List<Link> extractExternalLinks(ExtractLinkCommand extractLinkCommand) {
        Document doc = extractLinkCommand.getDoc();
        Url rootPage = extractLinkCommand.getRootPage();
        Elements links = doc.select("a[href]");
        return links.stream()
                .map(link -> link.attr("abs:href"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .map(Url::new)
                .filter(url -> !url.isSameDomain(rootPage))
                .map(url -> Link.staticDomain(url.asString()))
                .collect(toList());
    }

    private List<Link> extractDomainLinks(ExtractLinkCommand extractLinkCommand) {
        Document doc = extractLinkCommand.getDoc();
        Url rootPage = extractLinkCommand.getRootPage();
        Elements links = doc.select("a[href]");
        return links.stream()
                .map(link -> link.attr("abs:href"))
                .filter(StringUtils::isNotBlank)
                .distinct()
                .map(Url::new)
                .filter(url -> url.isSameDomain(rootPage))
                .map(url -> Link.staticDomain(url.asString()))
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
