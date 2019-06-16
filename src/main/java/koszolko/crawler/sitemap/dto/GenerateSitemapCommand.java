package koszolko.crawler.sitemap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateSitemapCommand {
    @NotNull
    private String url;
    private int limit = 100;
}
