package com.decentralizer.spreadr.modules.cms;

import com.decentralizer.spreadr.modules.cms.domain.Article;
import com.decentralizer.spreadr.modules.cms.events.ArticleCreatedMessage;
import com.decentralizer.spreadr.modules.cms.events.ArticleUpdatedMessage;
import org.springframework.stereotype.Component;

@Component
public class CmsEventHandler {

    ArticlesRepositoryForCms articlesRepositoryForCms;

    public void handleMessage(ArticleUpdatedMessage message) {
        if (message.isCompensation())
            handleCompensation(message);
        else articlesRepositoryForCms.save(message.getPayload());
    }

    private void handleCompensation(ArticleUpdatedMessage message) {
        Article recent = message.getPayload();
    }

    public void handleMessage(ArticleCreatedMessage message) {
        if (message.isCompensation())
            handleCompensation(message);
        else articlesRepositoryForCms.save(message.getPayload());
    }

    private void handleCompensation(ArticleCreatedMessage message) {
        Article recent = message.getPayload();
    }
}
