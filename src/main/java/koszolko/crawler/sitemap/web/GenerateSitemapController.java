package koszolko.crawler.sitemap.web;

import koszolko.crawler.sitemap.SitemapFacade;
import koszolko.crawler.sitemap.dto.GenerateSitemap;
import koszolko.crawler.sitemap.dto.Sitemap;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@SuppressWarnings("unused")
@RestController
@AllArgsConstructor
class GenerateSitemapController {
    private final SitemapFacade sitemapFacade;

    @PostMapping("/sitemaps")
    ResponseEntity<Sitemap> registerObject(@RequestBody @Valid GenerateSitemap command) {
        Sitemap sitemap = sitemapFacade.generate(command);
        String id = sitemap.getId();
        return ResponseEntity
                .created(URI.create("/sitemaps/" + id))
                .body(sitemap);
    }
}
