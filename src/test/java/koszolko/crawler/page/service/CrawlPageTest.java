package koszolko.crawler.page.service;

import koszolko.crawler.page.model.Page;
import koszolko.crawler.page.model.Url;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CrawlPageTest {
    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Autowired
    private PageCrawler pageCrawler;

    @Test
    public void should_return_page_with_links_for_correct_input() {
        //given
        Url correctUrl = new Url("https://www.elastic.co/");

        //when
        Optional<Page> maybePage = pageCrawler.crawl(correctUrl);
        Page page = maybePage.orElse(null);

        //then
        softly.assertThat(page).isNotNull();
        softly.assertThat(page.getUrl()).isEqualTo(correctUrl.asString());
        softly.assertThat(page.getLinks()).size().isGreaterThan(0);
        softly.assertThat(page.getStaticLinks()).size().isEqualTo(50);
        softly.assertThat(page.getDomainLinks()).size().isEqualTo(74);
        softly.assertThat(page.getExternalLinks().size()).isEqualTo(10);
    }

    @Test
    public void should_return_empty_optional_for_wrong_input() {
        //given
        Url wrongUrl = new Url("wrong_url");

        //when
        Optional<Page> maybePage = pageCrawler.crawl(wrongUrl);

        //then
        assertThat(maybePage.isPresent()).isFalse();
    }
}