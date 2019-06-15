package koszolko.crawler.page.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class UrlTest {

    @Test
    public void should_create_object_for_correct_url() {
        //given
        String correctUrl = "https://example.com";

        //when
        Url url = new Url(correctUrl);
    }

    @Test
    public void should_throw_exception_for_null_url() {
        //given
        String nullUrl = null;

        //when
        Throwable thrown = catchThrowable(() -> {
            new Url(nullUrl);
        });

        //then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Url can not be null!");
    }
}