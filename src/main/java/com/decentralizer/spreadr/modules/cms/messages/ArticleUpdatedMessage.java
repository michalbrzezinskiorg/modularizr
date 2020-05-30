package com.decentralizer.spreadr.modules.cms.messages;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.cms.domain.Article;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ArticleUpdatedMessage implements ApplicationMessage<Article> {
    private final Article payload;
    private final boolean isCompensation;
    private final ZonedDateTime published;

    public ArticleUpdatedMessage(Article a, boolean compensation) {
        payload = a;
        isCompensation = compensation;
        published = ZonedDateTime.now();
    }
}
