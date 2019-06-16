package koszolko.crawler.page.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.net.URI;

@Slf4j
@ToString
@EqualsAndHashCode
public class Url {
    private final URI url;

    public Url(String url) {
        Assert.notNull(url, "Url can not be null!");
        this.url = URI.create(url);
    }

    public static boolean isValidUrl(String url) {
        try {
            //todo dodac walidowanie protokolu - tylko http, https
            URI tmp = URI.create(url);
            return StringUtils.isNotBlank(tmp.getHost());
        } catch (Exception ignore) {
        }
        return false;
    }

    public boolean isSameDomain(Url url) {
        boolean result = this.getDomain().equals(url.getDomain());
        log.debug("Check domain. Result: {}, Url: {}, url: {}", result, this.asString(), url.asString());
        return result;
    }

    private String getDomain() {
        String domain = this.url.getHost().toLowerCase();
        if (domain.startsWith("www.")) {
            domain = domain.substring(4);
        }
        return domain;
    }

    public String asString() {
        return this.url.toString();
    }
}
