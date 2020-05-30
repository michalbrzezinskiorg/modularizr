package com.decentralizer.spreadr.modules.cms;

import com.decentralizer.spreadr.modules.cms.domain.Article;
import reactor.core.publisher.Mono;

public interface ArticlesRepository {
    Mono<Article> findByUrl(String url);
}
