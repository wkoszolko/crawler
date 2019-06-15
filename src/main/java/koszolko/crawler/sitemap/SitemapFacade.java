package koszolko.crawler.sitemap;

import koszolko.crawler.page.PageFacade;
import koszolko.crawler.page.dto.GetPageCommand;
import koszolko.crawler.page.dto.Page;
import koszolko.crawler.sitemap.dto.GenerateSitemapCommand;
import koszolko.crawler.sitemap.dto.Sitemap;
import koszolko.crawler.sitemap.service.SitemapBuilder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SitemapFacade {
    private final PageFacade pageFacade;
    private final SitemapBuilder sitemapBuilder;

    public Sitemap generate(GenerateSitemapCommand command) {
        GetPageCommand getPage = new GetPageCommand(command.getUrl());
        List<Page> pages = pageFacade.crawlDomain(getPage);
        return sitemapBuilder.build(pages);
    }
}
