package koszolko.crawler.sitemap.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Sitemap {
    private final String id;
    private final List<WebPageDocument> webPageDocuments = new ArrayList<>();

    public Sitemap(String id, List<WebPageDocument> webPageDocuments) {
        this.id = id;
        this.webPageDocuments.addAll(webPageDocuments);
    }
}
