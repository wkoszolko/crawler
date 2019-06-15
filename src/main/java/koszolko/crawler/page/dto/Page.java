package koszolko.crawler.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor
public class Page {
    private final String parentUrl;
    @Getter private final String url;
    private final Map<LinkType, List<Link>> links = new HashMap<>();

    public Page(String url, Map<LinkType, List<Link>> links) {
        parentUrl = null;
        this.url = url;
        links.forEach(this.links::put);
    }

    public List<Link> getLinks() {
        List<Link> allLinks = this.links.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        return Collections.unmodifiableList(allLinks);
    }

    public boolean isRoot() {
        return parentUrl==null;
    }
}
