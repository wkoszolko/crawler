package koszolko.crawler.page.dto;

import org.springframework.util.Assert;

import java.net.URI;

public class Url {
    private final URI url;

    public Url(String url) {
        Assert.notNull(url, "Url can not be null!");
        this.url = URI.create(url);
    }

    public boolean isSameDomain(Url url) {
        return this.url.getHost().equals(url.url.getHost());
    }

    public String asString() {
        return this.url.toString();
    }
}
