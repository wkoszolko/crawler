package koszolko.crawler.sitemap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebPageDocument {
    private String url;
    private List<String> domainLinks = new ArrayList<>();
    private List<String> staticLinks = new ArrayList<>();
    private List<String> externalLinks = new ArrayList<>();
}
