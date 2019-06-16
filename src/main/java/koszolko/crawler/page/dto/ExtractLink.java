package koszolko.crawler.page.dto;

import koszolko.crawler.page.model.Url;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.nodes.Document;


@Data
@AllArgsConstructor
public class ExtractLink {
    private final Document doc;
    private final Url url;
}
