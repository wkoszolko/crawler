package koszolko.crawler.sitemap.dto;

import koszolko.crawler.page.dto.Link;
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
    private List<String> links = new ArrayList<>();

}
