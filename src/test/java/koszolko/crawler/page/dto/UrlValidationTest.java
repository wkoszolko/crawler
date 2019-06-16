package koszolko.crawler.page.dto;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UrlValidationTest {
    @Test
    public void should_return_false_for_wrong_protocol() {
        //given
        String ftpUrl = "ftp://example.com";

        //when
        boolean result = Url.isValidUrl(ftpUrl);

        //then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void should_return_false_for_empty_protocol() {
        //given
        String noProtocolUrl = "example.com";

        //when
        boolean result = Url.isValidUrl(noProtocolUrl);

        //then
        Assertions.assertThat(result).isFalse();
    }
}