package koszolko.crawler.page.dto;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CheckDomainTest {
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
