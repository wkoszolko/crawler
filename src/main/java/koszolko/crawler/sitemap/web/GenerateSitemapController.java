package koszolko.crawler.sitemap.web;

import koszolko.crawler.sitemap.SitemapFacade;
import koszolko.crawler.sitemap.dto.GenerateSitemap;
import koszolko.crawler.sitemap.dto.Sitemap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@AllArgsConstructor
class GenerateSitemapController {
    private final SitemapFacade sitemapFacade;

    @PostMapping(value = "/sitemaps", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Sitemap> generateSitemap(@RequestBody @Valid GenerateSitemap command) {
        Sitemap sitemap = sitemapFacade.generate(command);
        String id = sitemap.getId();
        log.info("Sitemap has been generated. Sitemap id: {}", id);
        return ResponseEntity
                .created(URI.create("/sitemaps/" + id))
                .body(sitemap);
    }
}
