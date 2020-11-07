package com.decentralizer.spreadr.modules.cms;

import com.decentralizer.spreadr.configuration.ApplicationEventsPublisher;
import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.cms.domain.Article;
import com.decentralizer.spreadr.modules.cms.dto.ArticleDto;
import com.decentralizer.spreadr.modules.cms.events.ArticleCreatedMessage;
import com.decentralizer.spreadr.modules.cms.events.ArticleUpdatedMessage;
import com.decentralizer.spreadr.modules.cms.exceptions.NoSuchArticleExists;
import com.decentralizer.spreadr.modules.cms.exceptions.SuchArticleExists;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class ArticlesService {
    private final ApplicationEventsPublisher applicationEventsPublisher;
    private final ArticlesRepositoryForCms articlesRepositoryForCms;
    private final ModelMapper modelMapper;

    public void create(ArticleDto article) {
        ApplicationMessage event = new ArticleCreatedMessage(modelMapper.map(article, Article.class), false);
        articlesRepositoryForCms.findByUrl(article.getUrl())
                .flatMap(a -> Mono.error(new SuchArticleExists()))
                .switchIfEmpty(Mono.defer(() -> create(event)))
                .subscribe();
    }

    public void update(ArticleDto article) {
        articlesRepositoryForCms.findByUrl(article.getUrl())
                .flatMap(a -> Mono.just(updateArticle(a, article)))
                .switchIfEmpty(Mono.error(new NoSuchArticleExists()))
                .doOnSuccess(a -> applicationEventsPublisher.publish(new ArticleUpdatedMessage(a, false)));
    }

    private Article updateArticle(Article a, ArticleDto article) {
        a.setLink(article.getUrl());
        return a;
    }

    private Mono<Article> create(ApplicationMessage event) {
        applicationEventsPublisher.publish(event);
        return Mono.empty();
    }
}
