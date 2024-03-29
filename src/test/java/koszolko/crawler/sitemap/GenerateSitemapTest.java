package koszolko.crawler.sitemap;

import com.fasterxml.jackson.databind.ObjectMapper;
import koszolko.crawler.sitemap.dto.GenerateSitemap;
import koszolko.crawler.sitemap.dto.Sitemap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GenerateSitemapTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_return_sitemap_for_correct_input() throws Exception {
        GenerateSitemap correctRequest = new GenerateSitemap("https://www.elastic.co/", 100);

        MvcResult result = mvc.perform(post("/sitemaps")
                .content(objectMapper.writeValueAsString(correctRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(201))
                .andExpect(
                        header().exists("location")
                ).andDo(print())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Sitemap sitemap = objectMapper.readValue(jsonResponse, Sitemap.class);
        assertThat(sitemap).isNotNull();
        assertThat(sitemap.getWebPageDocuments()).size().isEqualTo(1);
        assertThat(sitemap.getWebPageDocuments().get(0).getDomainLinks()).size().isEqualTo(74);
        assertThat(sitemap.getWebPageDocuments().get(0).getStaticLinks()).size().isEqualTo(50);
        assertThat(sitemap.getWebPageDocuments().get(0).getExternalLinks()).size().isEqualTo(10);
    }
}
