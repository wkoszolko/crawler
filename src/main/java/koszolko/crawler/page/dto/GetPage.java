package koszolko.crawler.page.dto;

import koszolko.crawler.page.model.Url;
import lombok.Value;

@Value
public class GetPage {
    private final Url url;
}
