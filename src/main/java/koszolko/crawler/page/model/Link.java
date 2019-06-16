package koszolko.crawler.page.model;

import koszolko.crawler.shared.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Link {
    @Getter
    private Url url;
    private LinkType type;

    public static Link staticLink(Url url) {
        return new Link(url, LinkType.STATIC);
    }

    public static Link domainLink(Url url) {
        return new Link(url, LinkType.DOMAIN);
    }

    public static Link externalLink(Url url) {
        return new Link(url, LinkType.EXTERNAL);
    }
}
