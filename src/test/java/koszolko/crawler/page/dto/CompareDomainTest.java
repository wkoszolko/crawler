package koszolko.crawler.page.dto;

import koszolko.crawler.page.model.Url;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CompareDomainTest {

    @Test
    public void should_return_true_for_identical_domains() {
        //given
        Url url1 = new Url("https://example.com/some/path");
        Url url2 = new Url("https://example.com:8080/some/different/path");

        //when
        boolean result = url1.isSameDomain(url2);

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void should_return_false_for_sub_domain() {
        //given
        Url domain = new Url("https://example.com/some/path");
        Url subDomain = new Url("https://sub.example.com:8080/some/different/path");

        //when
        boolean result = domain.isSameDomain(subDomain);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void should_return_true_for_the_same_domain_without_www() {
        //given
        Url urlWithWWW = new Url("https://google.com/search/howsearchworks/?fg=1");
        Url url = new Url("https://www.google.com/about/products/");

        //when
        boolean result = urlWithWWW.isSameDomain(url);

        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void should_return_true_for_the_same_domain_ignoring_case() {
        //given
        Url url = new Url("https://google.com/search/howsearchworks/?fg=1");
        Url urlUpperCase = new Url("HTTPS://WWW.GOOGLE.COM/ABOUT/PRODUCTS/");

        //when
        boolean result = url.isSameDomain(urlUpperCase);

        //then
        Assertions.assertThat(result).isTrue();
    }
}