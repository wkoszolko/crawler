package koszolko.crawler.page.service;

import koszolko.crawler.page.dto.Page;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CrawlPageTest {
    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Autowired
    private PageCrawler pageCrawler;

    @Test
    public void should_return_page_with_links_for_corrent_input() {
        //given
        String correctUrl = "https://www.elastic.co/";

        //when
        Optional<Page> maybePage = pageCrawler.crawl(correctUrl);
        Page page = maybePage.orElse(null);

        //then
        softly.assertThat(page).isNotNull();
        softly.assertThat(page.getUrl()).isEqualTo(correctUrl);
        softly.assertThat(page.getLinks()).size().isGreaterThan(0);
    }
}