package koszolko.crawler.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

//todo zastanowic sie czy potrzebny podzial dto/model
@Value
@AllArgsConstructor
public class Page {
    @Getter private final String url;
    private final Map<LinkType, List<Link>> links = new HashMap<>();

    public Page(String url, Map<LinkType, List<Link>> links) {
        this.url = url;
        links.forEach(this.links::put);
    }

    public List<Link> getLinks() {
        List<Link> allLinks = this.links.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        return Collections.unmodifiableList(allLinks);
    }

    public List<Link> getStaticLinks() {
        List<Link> links = this.links.get(LinkType.STATIC);
        return Collections.unmodifiableList(links);
    }

    public List<Link> getDomainLinks() {
        List<Link> links = this.links.get(LinkType.DOMAIN);
        return Collections.unmodifiableList(links);
    }

    public List<Link> getExternalLinks() {
        List<Link> links = this.links.get(LinkType.EXTERNAL);
        return Collections.unmodifiableList(links);
    }
}
