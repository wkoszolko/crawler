package koszolko.crawler.page.dto;

import koszolko.crawler.page.model.Url;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UrlEqualsTest {

    @Test
    public void should_return_false_for_different_path() {
        //given
        Url url = new Url("https://www.google.com");
        Url urlWIthEndingSlash = new Url("https://www.google.com/abs");

        //when
        boolean result = url.equals(urlWIthEndingSlash);

        //then
        Assertions.assertThat(result).isFalse();
    }
}
