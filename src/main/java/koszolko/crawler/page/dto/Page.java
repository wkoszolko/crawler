package koszolko.crawler.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Value
@AllArgsConstructor
public class Page {
    private final String parentUrl;
    @Getter private final String url;
    private final List<Link> links = new ArrayList<>();

    public Page(String url, List<Link> links) {
        parentUrl = null;
        this.url = url;
        this.links.addAll(links);
    }

    public List<Link> getLinks() {
        return Collections.unmodifiableList(this.links);
    }

    public boolean isRoot() {
        return parentUrl==null;
    }
}
