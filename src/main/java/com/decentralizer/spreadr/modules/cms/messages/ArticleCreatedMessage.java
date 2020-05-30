package com.decentralizer.spreadr.modules.cms.messages;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.cms.domain.Article;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ArticleCreatedMessage implements ApplicationMessage<Article> {
    Article payload;
    boolean isCompensation;
    ZonedDateTime published;

    public ArticleCreatedMessage(Article article, boolean isCompensation) {
        payload = article;
        this.isCompensation = isCompensation;
        this.published = ZonedDateTime.now();
    }
}
