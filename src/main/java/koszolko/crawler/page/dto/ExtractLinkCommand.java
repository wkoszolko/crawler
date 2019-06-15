package koszolko.crawler.page.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.nodes.Document;


@Data
@AllArgsConstructor
public class ExtractLinkCommand {
    private final Document doc;
    private final Url rootPage;
}
