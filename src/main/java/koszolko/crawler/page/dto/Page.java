package koszolko.crawler.page.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
public class Page {
    private final String parentUrl;
    private final String url;
    private final List<Link> links = new ArrayList<>();

    public boolean isRoot() {
        return parentUrl==null;
    }
}
