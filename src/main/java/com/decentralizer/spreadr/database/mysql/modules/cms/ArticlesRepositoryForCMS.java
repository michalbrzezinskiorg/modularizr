package com.decentralizer.spreadr.database.mysql.modules.cms;

import com.decentralizer.spreadr.database.mysql.ArticlesRepositoryMysql;
import com.decentralizer.spreadr.modules.cms.ArticlesRepository;
import com.decentralizer.spreadr.modules.cms.domain.Article;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ArticlesRepositoryForCMS implements ArticlesRepository {
    ArticlesRepositoryMysql articlesRepositoryMysql;

    @Override
    public Mono<Article> findByUrl(String url) {
        return articlesRepositoryMysql.findByLink(url).map(a -> Mono.just(a)).orElse(Mono.empty());
    }
}
