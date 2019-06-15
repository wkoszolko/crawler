package koszolko.crawler.page.dto;

import lombok.Value;

@Value
public class GetPageCommand {
    private final String url;
}
