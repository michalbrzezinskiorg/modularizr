package com.decentralizer.spreadr.modules.cms;

import com.decentralizer.spreadr.modules.cms.domain.Article;
import reactor.core.publisher.Mono;

public interface ArticlesRepositoryForCms {
    Mono<Article> findByUrl(String url);

    void save(Article article);
}
