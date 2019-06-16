package koszolko.crawler.sitemap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateSitemap {
    @NotNull
    @URL
    private String url;
    private int limit = 50;
}
