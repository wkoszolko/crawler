package koszolko.crawler.sitemap;

import com.fasterxml.jackson.databind.ObjectMapper;
import koszolko.crawler.sitemap.dto.GenerateSitemapCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GenerateSitemapValidationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_return_400_for_incorrect_input() throws Exception {
        GenerateSitemapCommand correctRequest = new GenerateSitemapCommand("www.google.com", 100);

        MvcResult result = mvc.perform(post("/sitemaps")
                .content(objectMapper.writeValueAsString(correctRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andDo(print())
                .andReturn();
    }
}
