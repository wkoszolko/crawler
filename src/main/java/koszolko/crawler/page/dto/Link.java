package koszolko.crawler.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Link {
    @Getter private String url;
    private LinkType type;

    public static Link staticLink(String url) {
        return new Link(url, LinkType.STATIC);
    }

    public static Link staticDomain(String url) {
        return new Link(url, LinkType.DOMAIN);
    }
}
